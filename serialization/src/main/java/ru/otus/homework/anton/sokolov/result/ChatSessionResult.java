package ru.otus.homework.anton.sokolov.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"chat_identifier", "members", "messages"})
public class ChatSessionResult {
    @JsonProperty("chat_identifier")
    private String chatIdentifier;
    private List<MemberResult> members;
    private List<MessageResult> messages;
}