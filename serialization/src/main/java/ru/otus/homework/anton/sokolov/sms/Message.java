package ru.otus.homework.anton.sokolov.sms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    @JsonProperty("ROWID")
    private long rowId;
    private String attributedBody;
    @JsonProperty("belong_number")
    private String belongNumber;
    private long date;
    @JsonProperty("date_read")
    private long dateRead;
    private String guid;
    @JsonProperty("handle_id")
    private long handleId;
    @JsonProperty("has_dd_results")
    private int hasDdResults;
    @JsonProperty("is_deleted")
    private int isDeleted;
    @JsonProperty("is_from_me")
    private int isFromMe;
    @JsonProperty("send_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss", timezone = "GMT+3")
    private Date sendDate;
    @JsonProperty("send_status")
    private int sendStatus;
    private String service;
    private String text;
}
