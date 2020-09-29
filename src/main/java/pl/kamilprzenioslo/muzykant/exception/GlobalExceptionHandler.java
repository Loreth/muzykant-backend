package pl.kamilprzenioslo.muzykant.exception;

import io.jsonwebtoken.ExpiredJwtException;
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
    var body = makeBody(exception, HttpStatus.UNAUTHORIZED);

    return handleExceptionInternal(
        exception, body, new HttpHeaders(), HttpStatus.UNAUTHORIZED, webRequest);
  }

  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<Object> handleExpiredJwtException(
      ExpiredJwtException exception, WebRequest webRequest) {
    var body = makeBody(exception, HttpStatus.UNAUTHORIZED);

    return handleExceptionInternal(
        exception, body, new HttpHeaders(), HttpStatus.UNAUTHORIZED, webRequest);
  }

  private Map<String, Object> makeBody(Exception exception, HttpStatus status) {
    Map<String, Object> body = new HashMap<>();
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    body.put("timestamp", Calendar.getInstance().getTime());
    body.put("exception", exception);
    body.put("message", exception.getMessage());

    return body;
  }
}
