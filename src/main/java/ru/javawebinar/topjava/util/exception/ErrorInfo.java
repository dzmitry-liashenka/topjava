package ru.javawebinar.topjava.util.exception;

import org.springframework.http.HttpStatus;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String detail;
    private final int status;

    public ErrorInfo(CharSequence url, ErrorType type, String detail, HttpStatus status) {
        this.url = url.toString();
        this.type = type;
        this.detail = detail;
        this.status = status.value();
    }
}