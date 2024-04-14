package com.service.todoit.domain.completeRate;

import com.service.todoit.domain.completeRate.enums.CompleteStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompleteRate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Byte rate;

    private Byte completeCount;

    private Byte incompleteCount;

    private LocalDateTime CreatedAt;

    @Enumerated(EnumType.STRING)
    private CompleteStatus status;

    private Long userId;
}
