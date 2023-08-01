package ru.otus.homework.anton.sokolov;


import com.fasterxml.jackson.core.JacksonException;
import ru.otus.homework.anton.sokolov.result.*;
import ru.otus.homework.anton.sokolov.result.serialize.Serializer;
import ru.otus.homework.anton.sokolov.sms.ChatSession;
import ru.otus.homework.anton.sokolov.sms.Sms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final Path jsonPath = Path.of("serialization/src/main/resources/result.json");
    private static final Path xmlPath = Path.of("serialization/src/main/resources/result.xml");
    private static final Path yamlPath = Path.of("serialization/src/main/resources/result.yaml");
    private static final Path csvPath = Path.of("serialization/src/main/resources/result.csv");

    public static void main(String[] args) {
        try {
            Path path = Path.of("serialization/src/main/resources/sms.json");
            String s = Files.readString(path);

            Sms sms = Sms.deserialize(s);
            System.out.println(sms);

            SmsResult result = businessLogic(sms);

            workWithResult(result, SerializeType.JSON, jsonPath);
            workWithResult(result, SerializeType.XML, xmlPath);
            workWithResult(result, SerializeType.YAML, yamlPath);
            workWithResult(result, SerializeType.CSV, csvPath);

        } catch (JacksonException e) {
            System.out.println("ParseException: " + Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            System.out.println("IOException: " + Arrays.toString(e.getStackTrace()));
        }
    }

    private static SmsResult businessLogic(Sms sms) {
        List<ChatSessionResult> chatSessions = new ArrayList<>();
        for (ChatSession chatSession : sms.getChatSessions()) {
            String chatIdentifier = chatSession.getChatIdentifier();

            List<MemberResult> members = new ArrayList<>();
            chatSession.getMembers().forEach(member -> members.add(new MemberResult(member.getLast())));

            List<MessageResult> messages = new ArrayList<>();
            chatSession.getMessages().forEach(message -> messages.add(
                    new MessageResult(message.getBelongNumber(), message.getSendDate(), message.getText())));

            Collections.sort(messages);
            chatSessions.add(new ChatSessionResult(chatIdentifier, members, messages));
        }
        return new SmsResult(chatSessions);
    }

    private static void workWithResult(SmsResult result, SerializeType serializeType, Path path) throws IOException {
        String serializeResult = Serializer.serialize(result, serializeType);
        Files.writeString(path, serializeResult);

        String deserializeString = Files.readString(path);

        System.out.println(Serializer.deserialize(deserializeString, serializeType));
        System.out.println("_______________________________________________________________________");
    }
}