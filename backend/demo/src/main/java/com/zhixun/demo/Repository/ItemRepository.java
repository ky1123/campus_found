// ItemRepository.java
package com.zhixun.demo.Repository;

import com.zhixun.demo.Entity.Item;
import com.zhixun.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // 根据拾获者查找物品
    List<Item> findByFinderUser(User finderUser);

    // 根据状态查找物品
    List<Item> findByStatus(String status);

    // 根据分类查找
    List<Item> findByCategory(String category);

    // 搜索物品名称
    List<Item> findByItemNameContainingIgnoreCase(String keyword);

    // 查找最近发布的物品
    @Query("SELECT i FROM Item i ORDER BY i.createdAt DESC")
    List<Item> findRecentItems();
}
