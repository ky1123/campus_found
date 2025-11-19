package com.zhixun.demo.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "lost_records") // 指定数据库表名
public class LostRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 物品名称
    @Column(name = "item_name", nullable = false, length = 200)
    private String itemName;

    // 物品描述
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // 物品分类
    @Column(name = "category", length = 100)
    private String category;

    // 丢失地点
    @Column(name = "lost_location", nullable = false, length = 300)
    private String lostLocation;

    // 丢失时间
    @Column(name = "lost_time", nullable = false)
    private LocalDateTime lostTime;

    // 图片URL
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    // 联系电话
    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    // 状态
    @Column(name = "status", length = 50)
    private String status = "LOST"; // 默认值：LOST

    // 创建时间
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 更新时间
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // === 关联关系 ===

    // 失主用户（多对一关系）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id", nullable = false)
    private User ownerUser;

    // === 构造方法 ===

    // 默认构造方法 (JPA要求)
    public LostRecord() {
    }

    // 基本构造方法
    public LostRecord(String itemName, String lostLocation, LocalDateTime lostTime, User ownerUser) {
        this.itemName = itemName;
        this.lostLocation = lostLocation;
        this.lostTime = lostTime;
        this.ownerUser = ownerUser;
    }

    // 完整构造方法
    public LostRecord(String itemName, String description, String category,
            String lostLocation, LocalDateTime lostTime, String imageUrl,
            String contactPhone, BigDecimal reward, User ownerUser) {
        this.itemName = itemName;
        this.description = description;
        this.category = category;
        this.lostLocation = lostLocation;
        this.lostTime = lostTime;
        this.imageUrl = imageUrl;
        this.contactPhone = contactPhone;

        this.ownerUser = ownerUser;
    }

    // === 其他方法 ===

    @Override
    public String toString() {
        return "LostRecord{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", category='" + category + '\'' +
                ", lostLocation='" + lostLocation + '\'' +
                ", lostTime=" + lostTime +
                ", contactPhone='" + contactPhone + '\'' +
                ", status='" + status + '\'' +
                ", ownerUserId=" + (ownerUser != null ? ownerUser.getId() : "null") +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LostRecord that = (LostRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // === 业务方法 ===

    /**
     * 检查是否仍处于丢失状态
     */
    public boolean isStillLost() {
        return "LOST".equals(status);
    }

    /**
     * 标记为已找到
     */
    public void markAsFound() {
        this.status = "FOUND";
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 标记为已取消
     */
    public void markAsCancelled() {
        this.status = "CANCELLED";
        this.updatedAt = LocalDateTime.now();
    }

    // === Getter 和 Setter 方法 ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLostLocation() {
        return lostLocation;
    }

    public void setLostLocation(String lostLocation) {
        this.lostLocation = lostLocation;
    }

    public LocalDateTime getLostTime() {
        return lostTime;
    }

    public void setLostTime(LocalDateTime lostTime) {
        this.lostTime = lostTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

}