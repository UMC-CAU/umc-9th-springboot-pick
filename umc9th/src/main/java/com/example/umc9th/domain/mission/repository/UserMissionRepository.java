package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.entity.mapping.UserMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    long countByMember_IdAndIsFinishedTrue(Long memberId);
    Page<UserMission> findByMember_IdAndFinishedFalse(Long memberId, Pageable pageable);

}
