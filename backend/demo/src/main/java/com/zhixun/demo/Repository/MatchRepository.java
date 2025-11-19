package com.zhixun.demo.Repository;

import com.zhixun.demo.Entity.Match;
import com.zhixun.demo.Entity.LostRecord;
import com.zhixun.demo.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    // 根据丢失记录查找匹配
    List<Match> findByLostRecord(LostRecord lostRecord);

    // 根据找到的物品查找匹配
    List<Match> findByFoundItem(Item foundItem);

    // 根据匹配状态查找
    List<Match> findByStatus(String status);

    // 查找特定丢失记录和物品的匹配（用于检查是否已存在）
    Optional<Match> findByLostRecordAndFoundItem(LostRecord lostRecord, Item foundItem);

    // 查找高置信度的匹配（分数大于等于阈值）
    @Query("SELECT m FROM Match m WHERE m.matchScore >= :threshold")
    List<Match> findHighConfidenceMatches(@Param("threshold") Double threshold);

    // 统计特定状态的匹配数量
    long countByStatus(String status);

    // 查找用户相关的匹配（作为失主或拾获者）
    @Query("SELECT m FROM Match m WHERE m.lostRecord.ownerUser.id = :userId OR m.foundItem.finderUser.id = :userId")
    List<Match> findUserRelatedMatches(@Param("userId") Long userId);
}
