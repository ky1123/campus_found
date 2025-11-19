package com.zhixun.demo.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Setter
@Getter
@Entity
@Table(name = "items") // 指定数据库表名
public class Item {

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

    // 拾获地点
    @Column(name = "found_location", nullable = false, length = 300)
    private String foundLocation;

    // 拾获时间
    @Column(name = "found_time", nullable = false)
    private LocalDateTime foundTime;

    // 图片URL
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    // 物品状态
    @Column(name = "status", length = 50)
    private String status = "FOUND"; // 默认值：FOUND

    // 创建时间
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 更新时间
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // === 关联关系 ===

    // 拾获者用户（多对一关系）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finder_user_id", nullable = false)
    private User finderUser;

    // === 构造方法 ===

    // 默认构造方法 (JPA要求)
    public Item() {
    }

    // 基本构造方法
    public Item(String itemName, String foundLocation, LocalDateTime foundTime, User finderUser) {
        this.itemName = itemName;
        this.foundLocation = foundLocation;
        this.foundTime = foundTime;
        this.finderUser = finderUser;
    }

    // 完整构造方法
    public Item(String itemName, String description, String category,
            String foundLocation, LocalDateTime foundTime, String imageUrl,
            String storageLocation, User finderUser) {
        this.itemName = itemName;
        this.description = description;
        this.category = category;
        this.foundLocation = foundLocation;
        this.foundTime = foundTime;
        this.imageUrl = imageUrl;
        this.finderUser = finderUser;
    }

    // === 其他方法 ===

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", category='" + category + '\'' +
                ", foundLocation='" + foundLocation + '\'' +
                ", foundTime=" + foundTime +
                ", status='" + status + '\'' +
                ", finderUserId=" + (finderUser != null ? finderUser.getId() : "null") +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // === 业务方法 ===

    /**
     * 检查物品是否可以归还
     */
    public boolean canBeReturned() {
        return "FOUND".equals(status);
    }

    /**
     * 标记为已归还
     */
    public void markAsReturned() {
        this.status = "RETURNED";
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 标记为待认领
     */
    public void markAsPending() {
        this.status = "PENDING";
        this.updatedAt = LocalDateTime.now();
    }
    // === Getter 和 Setter 方法 ===

}