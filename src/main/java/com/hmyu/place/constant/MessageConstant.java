package com.hmyu.place.constant;

/**
 * Desc : 응답 코드 상수 클래스
 */
public enum MessageConstant {

    //정상 응답
    OK("0000", "성공"),

    // HTTP STATUS CODE 기반 오류
    BAD_REQUEST("0400", "클라이언트 요청 오류로 인해 요청을 처리 할 수 없습니다."),
    UNAUTHORIZED("0401", "요청 권한이 없습니다."),
    FORBIDDEN("0403", "잘못된 요청입니다."),
    NOT_FOUND("0404", "요청하신 페이지(Resource)를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED("0405", "요청하신 방법(Method)가 잘못되었습니다."),

    // 서버 처리 오류
    SERVER_ERROR("9999", "서버 처리 중 오류가 발생했습니다. 관리자에게 문의하세요."),
    TIMEOUT("9000", "타임아웃 발생하였습니다."),

    // 요청 권한 오류
    NO_PERMISSION("1000", "사용 권한이 없습니다."),
    INVALID_PERMISSION("1001", "사용권한 정보가 올바르지 않습니다."),

    // 공통
    INVALID_PARAMETER("0001", "필수 입력 파라미터가 누락되었습니다."),
    INSERT_ERROR("0002", "저장 중 오류가 발생했습니다."),
    SELECT_ERROR("0003", "조회 중 오류가 발생했습니다."),
    NOT_EXISTS_DATA("0004", "조회된 데이터가 없습니다."),
    EXISTS_DATA("0005", "기존 데이터가 존재합니다."),

    ;

    private String code;
    private String message;
    private String extraMessage;

    private MessageConstant(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setExtraMessage(String extraMessage) {
        this.extraMessage = extraMessage;
    }

    public String getExtraMessage() {
        return extraMessage;
    }
}
