package com.zhixun.demo.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "matches", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "lost_record_id", "found_item_id" })
}) // 复合唯一约束
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 匹配分数
    @Column(name = "match_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal matchScore;

    // 匹配状态
    @Column(name = "status", length = 50)
    private String status = "PENDING"; // 默认值：PENDING

    // 确认时间
    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    // 创建时间
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 更新时间
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // === 关联关系 ===

    // 丢失记录（多对一关系）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lost_record_id", nullable = false)
    private LostRecord lostRecord;

    // 找到的物品（多对一关系）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "found_item_id", nullable = false)
    private Item foundItem;

    // 匹配操作者（多对一关系，可为空 - 系统自动匹配）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matched_by_user_id")
    private User matchedByUser;

    // === 构造方法 ===

    // 默认构造方法 (JPA要求)
    public Match() {
    }

    // 基本构造方法（系统自动匹配）
    public Match(LostRecord lostRecord, Item foundItem, BigDecimal matchScore, String matchReason) {
        this.lostRecord = lostRecord;
        this.foundItem = foundItem;
        this.matchScore = matchScore;

        // matchedByUser 为 null 表示系统自动匹配
    }

    // 完整构造方法（人工匹配）
    public Match(LostRecord lostRecord, Item foundItem, BigDecimal matchScore,
            String matchReason, User matchedByUser) {
        this.lostRecord = lostRecord;
        this.foundItem = foundItem;
        this.matchScore = matchScore;

        this.matchedByUser = matchedByUser;
    }

    // === 其他方法 ===

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", matchScore=" + matchScore +
                ", status='" + status + '\'' +
                ", lostRecordId=" + (lostRecord != null ? lostRecord.getId() : "null") +
                ", foundItemId=" + (foundItem != null ? foundItem.getId() : "null") +
                ", matchedByUserId=" + (matchedByUser != null ? matchedByUser.getId() : "null") +
                ", confirmedAt=" + confirmedAt +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Match match = (Match) o;
        return Objects.equals(id, match.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // === 业务方法 ===

    /**
     * 检查匹配是否处于待处理状态
     */
    public boolean isPending() {
        return "PENDING".equals(status);
    }

    /**
     * 检查匹配是否已被确认
     */
    public boolean isConfirmed() {
        return "CONFIRMED".equals(status);
    }

    /**
     * 检查匹配是否已被拒绝
     */
    public boolean isRejected() {
        return "REJECTED".equals(status);
    }

    /**
     * 确认匹配
     */
    public void confirm() {
        this.status = "CONFIRMED";
        this.confirmedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 拒绝匹配
     */
    public void reject() {
        this.status = "REJECTED";
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 重新打开匹配（重置为待处理状态）
     */
    public void reopen() {
        this.status = "PENDING";
        this.confirmedAt = null;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 检查是否为系统自动匹配
     */
    public boolean isSystemMatched() {
        return matchedByUser == null;
    }

    /**
     * 检查匹配分数是否高于阈值
     */
    public boolean isHighConfidenceMatch(double threshold) {
        return matchScore != null && matchScore.compareTo(BigDecimal.valueOf(threshold)) >= 0;
    }

    /**
     * 获取匹配分数百分比形式
     */
    public String getMatchScorePercentage() {
        if (matchScore == null)
            return "0%";
        return matchScore.multiply(BigDecimal.valueOf(100)).setScale(0) + "%";
    }

    // === Getter 和 Setter 方法 ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(BigDecimal matchScore) {
        this.matchScore = matchScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LostRecord getLostRecord() {
        return lostRecord;
    }

    public void setLostRecord(LostRecord lostRecord) {
        this.lostRecord = lostRecord;
    }

    public Item getFoundItem() {
        return foundItem;
    }

    public void setFoundItem(Item foundItem) {
        this.foundItem = foundItem;
    }

    public User getMatchedByUser() {
        return matchedByUser;
    }

    public void setMatchedByUser(User matchedByUser) {
        this.matchedByUser = matchedByUser;
    }
}