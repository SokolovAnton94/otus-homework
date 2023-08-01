package ru.otus.homework.anton.sokolov.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Member {
    private String first;
    @JsonProperty("handle_id")
    private long handleId;
    @JsonProperty("image_path")
    private String imagePath;
    private String last;
    private String middle;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String service;
    @JsonProperty("thumb_path")
    private String thumbPath;
}
