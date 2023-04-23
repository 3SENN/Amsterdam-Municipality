package nl.hva.amsserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Conflict exception.
 * {@code 409 Conflict}.
 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.8">HTTP/1.1: Semantics and Content, section 6.5.8</a>
 *
 * @author Harmohat Khangura
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    /**
     * Instantiates a new Conflict exception.
     *
     * @param message The message to give with the exception
     */
    public ConflictException(String message) {
        super(message);
    }
}