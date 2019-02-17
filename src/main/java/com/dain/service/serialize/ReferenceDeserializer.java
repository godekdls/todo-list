package com.dain.service.serialize;

import com.dain.model.ToDoReference;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class ReferenceDeserializer extends JsonDeserializer<ToDoReference> {

    @Override
    public ToDoReference deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        long referredId = parser.getValueAsLong();
        ToDoReference toDoReference = new ToDoReference();
        toDoReference.setReferredId(referredId);
        return toDoReference;
    }

}
