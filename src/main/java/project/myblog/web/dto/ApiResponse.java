package project.myblog.web.dto;

import project.myblog.exception.ExceptionResponse;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse<T> {
    private final boolean success;
    private final T data;
    private final ExceptionResponse exception;

    public ApiResponse(boolean success, T data, ExceptionResponse exception) {
        this.success = success;
        this.data = data;
        this.exception = exception;
    }

    public static <T> ApiResponse<T> succeed(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> fail(ExceptionResponse exception) {
        return new ApiResponse<>(false, null, exception);
    }

    public static ApiResponse succeedId(Long id) {
        Map<String, Long> map = new HashMap<>();
        map.put("id", id);
        return new ApiResponse<>(true, map, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public ExceptionResponse getException() {
        return exception;
    }
}
