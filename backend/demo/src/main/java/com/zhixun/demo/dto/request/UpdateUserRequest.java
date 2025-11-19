package com.zhixun.demo.dto.request;

import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @Size(max = 100, message = "姓名长度不能超过100个字符")
    private String name;

    @Size(max = 100, message = "其他联系方式长度不能超过100个字符")
    private String otherContact;

    // === 构造方法 ===

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String name, String otherContact) {
        this.name = name;
        this.otherContact = otherContact;
    }

    // === Getter 和 Setter 方法 ===

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherContact() {
        return otherContact;
    }

    public void setOtherContact(String otherContact) {
        this.otherContact = otherContact;
    }

    // === 其他方法 ===

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "name='" + name + '\'' +
                ", otherContact='" + otherContact + '\'' +
                '}';
    }
}