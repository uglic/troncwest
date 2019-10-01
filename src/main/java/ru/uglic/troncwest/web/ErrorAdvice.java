package ru.uglic.troncwest.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.uglic.troncwest.exception.EntityNotFoundByIdException;
import ru.uglic.troncwest.exception.IllegalArgumentByIdException;

import java.util.Arrays;
import java.util.Locale;

@ControllerAdvice
public class ErrorAdvice {
    private final static Logger log = LoggerFactory.getLogger(ErrorAdvice.class);
    private final MessageSource messageSource;

    public ErrorAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseBody
    @ExceptionHandler(EntityNotFoundByIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String entityNotFoundHandler(EntityNotFoundByIdException ex) {
        return getMessageFromSource(ex.getMessage(), new Object[]{ex.getId(), ex.getClazz().getSimpleName()});
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentByIdException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String methodArgumentNotValidHandler(IllegalArgumentByIdException ex) {
        return getMessageFromSource(ex.getMessage(), new Object[]{
                ex.getProductId(), ex.getStockId(), ex.getCustomerId(), ex.getQuantity(), ex.getBalanceBefore()
        });
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String methodArgumentNotValidHandler(MethodArgumentNotValidException ex) {
        return getRootCause(ex).getMessage();
    }

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String otherErrorHandler(Throwable ex) {
        return getRootCause(ex).getMessage();
    }

    private String getMessageFromSource(String messageCode, Object[] messageArguments) {
        try {
            return messageSource.getMessage(messageCode, messageArguments, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            if (messageArguments != null) {
                return "{" + messageCode + ":" + Arrays.toString(messageArguments) + "}";
            }
            return "{" + messageCode + "}";
        } catch (IllegalArgumentException e) {
            return messageSource.getMessage(messageCode, null, Locale.getDefault());
        }
    }

    private static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;
        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}