package br.com.tt.petshop.exception.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorDto {

    private String key;
    private String message;
    private LocalDateTime time;

    private List<ApiErrorDetailDto> details;

    public ApiErrorDto(String key, String message) {
        this.key = key;
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public ApiErrorDto(String key, String message, List<ApiErrorDetailDto> details) {
        this.key = key;
        this.message = message;
        this.details = details;
        this.time = LocalDateTime.now();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public List<ApiErrorDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<ApiErrorDetailDto> details) {
        this.details = details;
    }
}
