package ru.otus.homework.anton.sokolov.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsResult {
    @JsonProperty("chat_sessions")
    List<ChatSessionResult> chatSessions;


}
