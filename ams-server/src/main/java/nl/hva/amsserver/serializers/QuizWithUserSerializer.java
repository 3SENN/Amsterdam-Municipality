package nl.hva.amsserver.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import nl.hva.amsserver.models.quiz.Quiz;
import nl.hva.amsserver.models.user.User;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * The type Quiz with user serializer.
 *
 * @author Harmohat Khangura
 */
public class QuizWithUserSerializer extends StdSerializer<Quiz> {

    /**
     * Instantiates a new Quiz with user serializer.
     */
    public QuizWithUserSerializer() {
        this(null);
    }

    /**
     * Instantiates a new Quiz with user serializer.
     *
     * @param t The quiz model
     */
    public QuizWithUserSerializer(Class<Quiz> t) {
        super(t);
    }

    @Override
    public void serialize(
            Quiz value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        jgen.writeStartObject();

        jgen.writeObjectField("data", value);

        jgen.writeEndObject();

    }
}
