package uk.gov.dwp.queue.triage.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import uk.gov.dwp.queue.triage.id.Id;

import java.io.IOException;

public class IdDeserializer extends JsonDeserializer<Id> {

    @Override
    public Id deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return null;
    }
}
