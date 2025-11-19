package com.zhixun.demo.dto.response;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    // === 构造方法 ===

    // 默认构造方法
    public ApiResponse() {
    }

    // 全参数构造方法
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // === 静态工厂方法 ===

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "成功", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    // 带错误码的error方法（可选）
    public static <T> ApiResponse<T> error(int code, String message) {
        // 如果你需要错误码，可以在这里扩展
        return new ApiResponse<>(false, message, null);
    }

    // === Getter 和 Setter 方法 ===

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // === 其他方法 ===

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}