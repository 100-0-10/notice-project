package com.api.notice.util.message;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class BaseMessageSource {

    private final MessageSourceAccessor source;

    private static final Locale DEFAULT_LOCALE = Locale.KOREAN;

    static MessageSourceAccessor messageSourceAccessor;

    @PostConstruct
    public void initialize() {
        messageSourceAccessor = source;
    }

    public static String getMessage(String messageKey) {
        return createResponseMessage(messageKey, null, null);
    }

    public static String getMessageWithArgs(String messageKey, Object[] args) {
        return createResponseMessage(messageKey, null, args);
    }

    public static String getMessageOrDefaultMessage(String messageKey, String defaultMessage) {
        return createResponseMessage(messageKey, defaultMessage, null);
    }

    public static String getMessageOrDefaultMessageWithArgs(String messageKey, String defaultMessage, Object[] args) {
        return createResponseMessage(messageKey, defaultMessage, args);
    }

    private static String createResponseMessage(String messageKey, String defaultMessage, Object[] args) {
        try {
            return messageSourceAccessor.getMessage(messageKey, args, DEFAULT_LOCALE);
        } catch (NoSuchMessageException noSuchMessageException) {
            if (defaultMessage != null) {
                return defaultMessage;
            }

            return messageKey;
        } catch (Exception exception) {
            log.debug("##-- Message Source Exception --##");
        }

        return null;
    }
}
