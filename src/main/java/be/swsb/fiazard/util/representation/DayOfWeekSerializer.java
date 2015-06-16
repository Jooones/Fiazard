package be.swsb.fiazard.util.representation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.DayOfWeek;

public class DayOfWeekSerializer extends JsonSerializer<DayOfWeek> {
    @Override
    public void serialize(DayOfWeek day, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {
        jgen.writeNumber(day.getValue());
    }

}
