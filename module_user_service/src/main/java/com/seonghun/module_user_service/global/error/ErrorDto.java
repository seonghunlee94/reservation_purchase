package com.seonghun.module_user_service.global.error;

public class ErrorDto {
    private final String message;

    public ErrorDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
