package com.zhixun.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class CreateItemRequest {

    @NotBlank(message = "物品名称不能为空")
    @Size(max = 200, message = "物品名称长度不能超过200个字符")
    private String itemName;

    @Size(max = 1000, message = "描述长度不能超过1000个字符")
    private String description;

    @Size(max = 100, message = "分类长度不能超过100个字符")
    private String category;

    @NotBlank(message = "拾获地点不能为空")
    @Size(max = 300, message = "拾获地点长度不能超过300个字符")
    private String foundLocation;

    @NotNull(message = "拾获时间不能为空")
    private LocalDateTime foundTime;

    @Size(max = 500, message = "图片URL长度不能超过500个字符")
    private String imageUrl;

    @Size(max = 300, message = "存放地点长度不能超过300个字符")
    private String storageLocation;

    // === 构造方法 ===

    public CreateItemRequest() {
    }

    // 基本构造方法
    public CreateItemRequest(String itemName, String foundLocation, LocalDateTime foundTime) {
        this.itemName = itemName;
        this.foundLocation = foundLocation;
        this.foundTime = foundTime;
    }

    // 完整构造方法
    public CreateItemRequest(String itemName, String description, String category,
            String foundLocation, LocalDateTime foundTime,
            String imageUrl, String storageLocation) {
        this.itemName = itemName;
        this.description = description;
        this.category = category;
        this.foundLocation = foundLocation;
        this.foundTime = foundTime;
        this.imageUrl = imageUrl;
        this.storageLocation = storageLocation;
    }

    // === 其他方法 ===

    @Override
    public String toString() {
        return "CreateItemRequest{" +
                "itemName='" + itemName + '\'' +
                ", category='" + category + '\'' +
                ", foundLocation='" + foundLocation + '\'' +
                ", foundTime=" + foundTime +
                '}';
    }

    // === Getter 和 Setter 方法 ===

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

    public String getFoundLocation() {
        return foundLocation;
    }

    public void setFoundLocation(String foundLocation) {
        this.foundLocation = foundLocation;
    }

    public LocalDateTime getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(LocalDateTime foundTime) {
        this.foundTime = foundTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }
}