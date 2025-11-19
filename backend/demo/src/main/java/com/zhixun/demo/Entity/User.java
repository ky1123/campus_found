package com.zhixun.demo.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users") // 指定数据库表名
public class User {

    @Id // 数据库生成ID：IDENTITY。在MySQL中，通常使用AUTO_INCREMENT来定义自增列。
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id
    private Long id;

    @Column(nullable = false, unique = true, length = 30) // 用户名不可重复约束
    private String name;

    @Column(nullable = false, unique = false, length = 20) // 密码没约束
    private String password;

    @Column(nullable = false, unique = false, length = 15) // 电话
    private String phoneNumber;

    @Column(nullable = true, unique = false, length = 30) // 其他联系方式
    private String otherContact;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // 默认构造方法 (JPA要求)
    public User() {

    }

    public User(String name, String password, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(String name, String password, String phoneNumber, String otherContact) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.otherContact = otherContact;
    }

    // toString 方法 (调试用)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", otherContact='" + otherContact + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // === Getter 和 Setter 方法 ===
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOtherContact() {
        return otherContact;
    }

    public void setOtherContact(String otherContact) {
        this.otherContact = otherContact;
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

}