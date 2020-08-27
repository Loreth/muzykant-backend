package pl.kamilprzenioslo.muzykant.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.CONFLICT,
    reason =
        "Given email is already taken by a registered user or it is awaiting for confirmation.")
public class EmailAlreadyTakenException extends RuntimeException {

}
