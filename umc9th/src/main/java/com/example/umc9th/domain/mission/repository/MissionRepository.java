package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.entity.Mission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.umc9th.domain.mission.dto.MissionShowDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("""
        select new com.example.umc9th.domain.mission.dto.MissionShowDto(m.score, s.name, m.todo)
        from UserMission um
        join um.mission m
        join m.store s
        where um.member.id = :memberId
          and um.isFinished = true
          and (:cursorId is null or um.id < :cursorId)
        order by um.id desc
    """)
    List<MissionShowDto> findCompletedMissions(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("""
        select new com.example.umc9th.domain.mission.dto.MissionShowDto(m.score, s.name, m.todo)
        from UserMission um
        join um.mission m
        join m.store s
        where um.member.id = :memberId
          and um.isFinished = false
          and (:cursorId is null or um.id < :cursorId)
        order by um.id desc
    """)
    List<MissionShowDto> findOngoingMissions(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

}
