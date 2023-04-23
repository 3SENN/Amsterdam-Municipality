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

public class QuizSerializer extends StdSerializer<Quiz> {

    public QuizSerializer() {
        this(null);
    }

    public QuizSerializer(Class<Quiz> t) {
        super(t);
    }

    @Override
    public void serialize(
            Quiz value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("title", value.getTitle());
        jgen.writeNumberField("amountToRegister", value.getAmountToRegister());
        jgen.writeStringField("location", Objects.equals(value.getLocation(), "1") ? "Digital" : "Real-life");
        jgen.writeStringField("link", value.getLink());
        jgen.writeStringField("address", value.getAddress());
        jgen.writeNumberField("totalQuestions", value.getTotalQuestions());
        jgen.writeNumberField("level", value.getLevel());
        jgen.writeStringField("endsAt", value.getEndsAt().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")));
        jgen.writeStringField("startsAt", value.getStartsAt().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")));
        jgen.writeStringField("createdAt", value.getCreatedAt().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")));
        jgen.writeStringField("updatedAt", value.getUpdatedAt().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")));
        jgen.writeEndObject();
    }
}
