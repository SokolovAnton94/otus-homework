package ru.otus.homework.anton.sokolov.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChatSession {
    @JsonProperty("chat_id")
    private long chatId;
    @JsonProperty("chat_identifier")
    private String chatIdentifier;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("is_deleted")
    private int isDeleted;
    private List<Member> members;
    private List<Message> messages;
}