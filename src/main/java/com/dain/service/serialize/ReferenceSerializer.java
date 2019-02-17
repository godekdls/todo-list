package com.dain.service.serialize;

import com.dain.model.ToDoReference;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ReferenceSerializer extends JsonSerializer<ToDoReference> {

    @Override
    public void serialize(ToDoReference value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getReferredId());
    }

}
