package com.service.todoit.domain.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TodoStatus {
    COMPLETE("complete"),
    INCOMPLETE("incomplete");

    private final String status;
}
