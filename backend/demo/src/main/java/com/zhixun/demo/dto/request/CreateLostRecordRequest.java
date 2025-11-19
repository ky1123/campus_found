package com.zhixun.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class CreateLostRecordRequest {

    // 物品名称
    @NotBlank(message = "物品名称不能为空")
    @Size(max = 200, message = "物品名称长度不能超过200个字符")
    private String itemName;

    // 物品描述
    @Size(max = 1000, message = "物品描述长度不能超过1000个字符")
    private String description;

    // 物品分类
    @Size(max = 100, message = "物品分类长度不能超过100个字符")
    private String category;

    // 丢失地点
    @NotBlank(message = "丢失地点不能为空")
    @Size(max = 300, message = "丢失地点长度不能超过300个字符")
    private String lostLocation;

    // 丢失时间
    @NotNull(message = "丢失时间不能为空")
    private LocalDateTime lostTime;

    // 图片URL
    @Size(max = 500, message = "图片URL长度不能超过500个字符")
    private String imageUrl;

    // 联系电话
    @Pattern(regexp = "^[0-9\\-+()\\s]*$", message = "联系电话格式不正确")
    @Size(max = 20, message = "联系电话长度不能超过20个字符")
    private String contactPhone;

    // === Getter和Setter方法 ===

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
}