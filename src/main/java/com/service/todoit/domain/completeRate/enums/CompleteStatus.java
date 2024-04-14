package com.service.todoit.domain.completeRate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CompleteStatus {
    DAY("day"),
    WEEK("week"),
    MONTH("month");
    private final String competeStatusName;
}
