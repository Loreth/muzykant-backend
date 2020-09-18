package pl.kamilprzenioslo.muzykant.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.CONFLICT,
    reason =
        "There is no email tied to this token. Either the token is incorrect, or confirmation period has expired and it's been deleted.")
public class EmailConfirmationTokenNotFoundException extends RuntimeException {}
