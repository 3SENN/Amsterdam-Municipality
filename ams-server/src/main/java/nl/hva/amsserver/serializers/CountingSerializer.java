package nl.hva.amsserver.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import nl.hva.amsserver.models.counting.Counting;


import java.io.IOException;

/**
 * The type Counting serializer
 *
 * @author Jordy Mol
 */
public class CountingSerializer extends StdSerializer<Counting> {
    /**
     * Instantiates a new Counting serializer
     */
    public CountingSerializer() {
        this(null);
    }

    /**
     * Instantiates a new Counting serializer
     *
     * @param t The Counting model
     */
    public CountingSerializer(Class<Counting> t) {
        super(t);
    }

    @Override
    public void serialize(Counting value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeEndObject();
    }

}
