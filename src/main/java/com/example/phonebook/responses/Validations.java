package com.example.phonebook.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Validations {
    private List<Exceptions> exceptions = new ArrayList<>();

    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Exceptions {
        @JsonFormat(pattern = "yyyy-MM-dd HH::mm")
        private LocalDateTime timestamp;
        private String message;
        private String exceptionClass;
        private HttpStatus httpStatus;
        private Integer httpStatusCode;

        public Exceptions(String message, String exceptionClass, HttpStatus httpStatus) {
            this(LocalDateTime.now(), message, exceptionClass, httpStatus);
        }

        public Exceptions(LocalDateTime dateTime, String message, String exceptionClass, HttpStatus httpStatus) {
            this.timestamp = dateTime;
            this.message = message;
            this.exceptionClass = exceptionClass;
            this.httpStatus = httpStatus;
            this.httpStatusCode = httpStatus.value();
        }
    }

    public void addErrorDetails(Exceptions exceptions) {
        this.exceptions.add(exceptions);
    }

}