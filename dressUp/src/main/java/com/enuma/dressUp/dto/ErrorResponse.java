package com.enuma.dressUp.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ErrorResponse {
    private String message;
    private HttpStatus status;
    private LocalDateTime dateTime;
    private String debugMessage;
}

