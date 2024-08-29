package com.icbt.ABC_Rest.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QADto {
    private Long id;
    private String question;
    private String answer;
    private LocalDateTime createdAt;
    private LocalDateTime answeredAt;
    private String userEmail;
}