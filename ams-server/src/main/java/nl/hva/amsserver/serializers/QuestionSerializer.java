package nl.hva.amsserver.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import nl.hva.amsserver.models.quiz.Question;

import java.io.IOException;

/**
 * The type Question serializer.
 *
 * @author Harmohat Khangura
 */
public class QuestionSerializer extends StdSerializer<Question> {
    /**
     * Instantiates a new Question serializer.
     */
    public QuestionSerializer() {
        this(null);
    }

    /**
     * Instantiates a new Question serializer.
     *
     * @param t The question model
     */
    public QuestionSerializer(Class<Question> t) {
        super(t);
    }

    @Override
    public void serialize(Question value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("question", value.getQuestion());
        gen.writeStringField("info", value.getInfo());
        gen.writeNumberField("point", value.getPoints());
        gen.writeBooleanField("hasMultipleAnswers", value.isHasMultipleAnswers());
        gen.writeEndObject();
    }
}
