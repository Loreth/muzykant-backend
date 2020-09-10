package pl.kamilprzenioslo.muzykant.exception;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<Object> handleUsernameNotFoundException(
      UsernameNotFoundException exception, WebRequest webRequest) {
    Map<String, Object> body = new HashMap<>();
    body.put("status", HttpStatus.UNAUTHORIZED.value());
    body.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
    body.put("timestamp", Calendar.getInstance().getTime());
    body.put("exception", exception);
    body.put("message", exception.getMessage());

    return handleExceptionInternal(
        exception, body, new HttpHeaders(), HttpStatus.UNAUTHORIZED, webRequest);
  }
}
