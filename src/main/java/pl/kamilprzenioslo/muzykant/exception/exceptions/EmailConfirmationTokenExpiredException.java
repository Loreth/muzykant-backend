package pl.kamilprzenioslo.muzykant.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.CONFLICT,
    reason = "Email confirmation token has expired. Sign up again.")
public class EmailConfirmationTokenExpiredException extends RuntimeException {}
