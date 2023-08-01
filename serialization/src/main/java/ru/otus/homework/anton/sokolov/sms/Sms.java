package ru.otus.homework.anton.sokolov.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

@Data
public class Sms {
    @JsonProperty("chat_sessions")
    List<ChatSession> chatSessions;

    public static Sms deserialize(String json) throws JacksonException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Sms.class);
    }
}
