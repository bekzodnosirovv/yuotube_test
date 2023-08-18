package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailHistoryDTO {
    private String id;
    private String toEmail;
    private String title;
    private String message;
    private LocalDateTime createdDate;
}
