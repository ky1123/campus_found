// UserRepository.java
package com.zhixun.demo.Repository;

import com.zhixun.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 根据用户名查找
    Optional<User> findByName(String name);

    // 根据手机号查找用户
    Optional<User> findByPhoneNumber(String phoneNumber);

    // 检查手机号是否存在
    boolean existsByPhoneNumber(String phoneNumber);

    // 复杂查询：统计用户发布的物品数量
    @Query("SELECT COUNT(i) FROM Item i WHERE i.finderUser.id = :userId")
    Long countItemsByUser(@Param("userId") Long userId);
}