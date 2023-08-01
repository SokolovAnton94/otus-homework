package ru.otus.homework.anton.sokolov.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"belong_number", "send_date", "text"})
public class MessageResult implements Comparable<MessageResult> {
    @JsonProperty("belong_number")
    private String belongNumber;
    @JsonProperty("send_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss", timezone = "GMT+3")
    private Date sendDate;
    private String text;


    @Override
    public int compareTo(MessageResult o) {
        int numberCompareResult = belongNumber.compareTo(o.belongNumber);
        if (numberCompareResult == 0) {
            return sendDate.compareTo(o.sendDate);
        } else {
            return numberCompareResult;
        }
    }

    @Override
    public String toString() {
        return "belongNumber='" + belongNumber + '\'' +
                "; sendDate='" + sendDate + '\'' +
                "; text='" + text + '\'';
    }
}
