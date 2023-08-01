package ru.otus.homework.anton.sokolov.result.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.otus.homework.anton.sokolov.result.ChatSessionResult;
import ru.otus.homework.anton.sokolov.result.SmsResult;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class SmsResultSerializer extends JsonSerializer<SmsResult> {
    public SmsResultSerializer() {
    }

    @Override
    public void serialize(SmsResult value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        List<ChatSessionResult> chatSessions = value.getChatSessions();
        for (ChatSessionResult chatSessionResult : chatSessions) {
            String chatIdentifier = chatSessionResult.getChatIdentifier();
            String members = listToString(chatSessionResult.getMembers());
            String messages = listToString(chatSessionResult.getMessages());

            gen.writeStartObject();
            gen.writeStringField("chat_identifier", chatIdentifier);
            gen.writeStringField("members", members);
            gen.writeStringField("messages", messages);
            gen.writeEndObject();
        }
    }

    private <T> String listToString(List<T> list) {
        StringBuilder sb = new StringBuilder();

        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T object = iterator.next();
            sb.append(object.toString());

            if (iterator.hasNext()) {
                sb.append(Serializer.arraySeparator);
            }
        }

        return sb.toString();
    }
}
