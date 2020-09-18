package pl.kamilprzenioslo.muzykant.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.CONFLICT,
    reason = "Given current password doesn't match actual current password")
public class PasswordMismatchException extends RuntimeException {}
