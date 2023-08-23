package ru.otus.homework.anton.sokolov.result.serialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import ru.otus.homework.anton.sokolov.result.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Serializer {

    public static final String arraySeparator = "####";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.of("GMT+3"));

    public static String serialize(SmsResult smsResult, SerializeType type) throws JacksonException {
        return switch (type) {
            case JSON -> serializeJson(smsResult);
            case XML -> serializeXml(smsResult);
            case YAML -> serializeYaml(smsResult);
            case CSV -> serializeCsv(smsResult);
        };
    }

    public static SmsResult deserialize(String deserializeString, SerializeType type) throws JacksonException {
        return switch (type) {
            case JSON -> deserializeJson(deserializeString);
            case XML -> deserializeXml(deserializeString);
            case YAML -> deserializeYaml(deserializeString);
            case CSV -> deserializeCsv(deserializeString);
        };
    }

    private static String serializeJson(SmsResult smsResult) throws JacksonException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(smsResult);
    }

    private static String serializeXml(SmsResult smsResult) throws JacksonException {
        ObjectMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(smsResult);
    }

    private static String serializeYaml(SmsResult smsResult) throws JacksonException {
        ObjectMapper mapper = new YAMLMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(smsResult);
    }

    private static String serializeCsv(SmsResult smsResult) throws JacksonException {
        SimpleModule module = new SimpleModule();
        module.addSerializer(SmsResult.class, new SmsResultSerializer());

        CsvMapper mapper = new CsvMapper();
        mapper.registerModule(module);
        CsvSchema csvSchema = CsvSchema.builder()
                .addColumn("chat_identifier")
                .addArrayColumn("members")
                .addArrayColumn("messages")
                .build()
                .withHeader();

        return mapper.writer(csvSchema).writeValueAsString(smsResult);
    }

    public static SmsResult deserializeJson(String json) throws JacksonException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, SmsResult.class);
    }

    private static SmsResult deserializeXml(String xml) throws JacksonException {
        ObjectMapper mapper = new XmlMapper();
        return mapper.readValue(xml, SmsResult.class);
    }

    private static SmsResult deserializeYaml(String yaml) throws JacksonException {
        ObjectMapper mapper = new YAMLMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.readValue(yaml, SmsResult.class);
    }

    private static SmsResult deserializeCsv(String csv) {
        String[] split = csv.split("\n");
        List<ChatSessionResult> chatSessionResults = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                continue;
            }
            String[] chatSessions = split[i].split(",");
            if (chatSessions.length < 3) {
                continue;
            }

            ChatSessionResult chatSessionResult = new ChatSessionResult();
            chatSessionResult.setChatIdentifier(chatSessions[0]);

            String[] lasts = chatSessions[1].split(arraySeparator);
            List<MemberResult> memberResults = new ArrayList<>();
            for (String last : lasts) {
                memberResults.add(new MemberResult(getFieldValue(last)));
            }
            chatSessionResult.setMembers(memberResults);

            String[] messages = chatSessions[2].split(arraySeparator);
            List<MessageResult> messageResults = new ArrayList<>();
            for (String message : messages) {
                String[] messageResultFields = message.split(";");
                MessageResult messageResult = new MessageResult();
                for (String messageResultField : messageResultFields) {
                    if (messageResultField.contains("belongNumber='")) {
                        messageResult.setBelongNumber(getFieldValue(messageResultField));
                    }
                    if (messageResultField.contains("sendDate=")) {
                        String sendDate = getFieldValue(messageResultField);
                        try {
                            messageResult.setSendDate(dateFormat.parse(sendDate));
                        } catch (ParseException e) {
                            System.out.println("SendDate = " + sendDate + " was not parse");
                            messageResult.setSendDate(null);
                        }
                    }
                    if (messageResultField.contains("text='")) {
                        messageResult.setText(getFieldValue(messageResultField));
                    }
                }
                messageResults.add(messageResult);
            }
            chatSessionResult.setMessages(messageResults);
            chatSessionResults.add(chatSessionResult);
        }
        return new SmsResult(chatSessionResults);
    }

    private static String getFieldValue(String field) {
        return field.substring(field.indexOf("'") + 1, field.lastIndexOf("'"));
    }
}
