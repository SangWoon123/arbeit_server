package com.project.arbeit.task;

import com.project.arbeit.common.BaseEntity;
import com.project.arbeit.store.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Integer weekdays; // 비트마스크 월(1), 화(2), 수(4), 목(8), 금(16), 토(32), 일(64)

    private Integer startTime;

    private Integer endTime;

    private String attachment;

    private boolean overnight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}
