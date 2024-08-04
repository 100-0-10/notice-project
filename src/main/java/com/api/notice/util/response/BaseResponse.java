package com.api.notice.util.response;

import com.api.notice.util.message.BaseMessageSource;
import jakarta.annotation.Nullable;

public record BaseResponse(String code, String message, Object data) {

    /**
     * message 값을 설정한다.
     * message.properties 키와 값 args(nullable) 에 대한 메시지를 가져와 설정한다.
     * 키에 대한 값이 없으면 messageOrKey 로 설정한다.
     */
    public static BaseResponse ofMessage(String messageOrKey, @Nullable Object... args) {
        return new BaseResponse(null, BaseMessageSource.getMessageWithArgs(messageOrKey, args), null);
    }

    /**
     * ofData 값을 설정한다.
     */
    public static BaseResponse ofData(Object data) {
        return new BaseResponse(null, null, data);
    }

    /**
     * message, data 값을 설정한다.
     * message.properties 키와 값 args(nullable) 에 대한 메시지를 가져와 설정한다.
     * 키에 대한 값이 없으면 messageOrKey 로 설정한다.
     */
    public static BaseResponse ofDataAndMessage(Object data, String messageOrKey, @Nullable Object... args) {
        return new BaseResponse(null, BaseMessageSource.getMessageWithArgs(messageOrKey, args), data);
    }

    /**
     * code 값과 message 값을 설정한다.
     * message.properties 키와 값 args(nullable) 에 대한 메시지를 가져와 설정한다.
     * 키에 대한 값이 없으면 messageOrKey 로 설정한다.
     */
    public static BaseResponse ofCodeAndMessage(String code, String messageOrKey, @Nullable Object... args) {
        return new BaseResponse(code, BaseMessageSource.getMessageWithArgs(messageOrKey, args), null);
    }

    /**
     * code 값과 message 값과 data 값을 설정한다.
     * message.properties 키와 값 args(nullable) 에 대한 메시지를 가져와 설정한다.
     * 키에 대한 값이 없으면 messageOrKey 로 설정한다.
     * data 값을 설정한다.
     */
    public static BaseResponse ofCodeAndMessageAndData(String code, String messageOrKey, Object data, @Nullable Object... args) {
        return new BaseResponse(code, BaseMessageSource.getMessageWithArgs(messageOrKey, args), data);
    }
}