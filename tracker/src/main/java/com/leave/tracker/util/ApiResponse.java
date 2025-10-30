package com.leave.tracker.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;

    public ApiResponse(T data) {
        this.success = true;
        this.data = data;
        this.message = "Success";
    }

    public ApiResponse(String message) {
        this.success = false;
        this.data = null;
        this.message = message;
    }
}

