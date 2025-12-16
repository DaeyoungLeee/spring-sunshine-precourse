package core.exception;

public class ExternalApiException extends RuntimeException {
    public ExternalApiException() {
        super("외부 API 호출 중 오류가 발생했습니다.");
    }
    
    public ExternalApiException(String message) {
        super(message);
    }
    
    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
