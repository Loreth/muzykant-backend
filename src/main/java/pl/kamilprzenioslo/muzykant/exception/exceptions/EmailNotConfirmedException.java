package pl.kamilprzenioslo.muzykant.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.CONFLICT,
    reason = "Email has not been confirmed. User must confirm his email in order to continue.")
public class EmailNotConfirmedException extends RuntimeException {

}
