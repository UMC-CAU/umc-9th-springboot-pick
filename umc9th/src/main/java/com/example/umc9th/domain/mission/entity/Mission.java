package com.example.umc9th.domain.mission.entity;


import com.example.umc9th.domain.member.entity.mapping.MemberTerm;
import com.example.umc9th.domain.mission.entity.mapping.UserMission;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "mission")
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deadline",  nullable = false)
    private LocalDate deadline;

    @Column(name = "todo", nullable = false)
    private String todo;

    @Column(name = "score",  nullable = false)
    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy= "mission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserMission> userMissionList = new ArrayList<>();

}
