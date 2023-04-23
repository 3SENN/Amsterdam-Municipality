package nl.hva.amsserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Internal server error.
 * {@code 500 Internal Server Error}.
 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.6.1">HTTP/1.1: Semantics and Content, section 6.6.1</a>
 *
 * @author Harmohat Khangura
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends RuntimeException {

    /**
     * Instantiates a new Internal server error.
     *
     * @param message The message to give with the exception
     */
    public InternalServerError(String message) {
        super(message);
    }
}