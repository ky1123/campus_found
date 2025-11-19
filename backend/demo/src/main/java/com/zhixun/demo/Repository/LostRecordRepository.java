// LostRecordRepository.java
package com.zhixun.demo.Repository;

import com.zhixun.demo.Entity.LostRecord;
import com.zhixun.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LostRecordRepository extends JpaRepository<LostRecord, Long> {

    // 根据失主查找记录
    List<LostRecord> findByOwnerUser(User ownerUser);

    // 根据状态查找
    List<LostRecord> findByStatus(String status);

    // 搜索丢失物品
    List<LostRecord> findByItemNameContainingIgnoreCase(String keyword);
}