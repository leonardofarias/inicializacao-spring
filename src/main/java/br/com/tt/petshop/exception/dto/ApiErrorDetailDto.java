package br.com.tt.petshop.exception.dto;

public class ApiErrorDetailDto {
    private String message;

    public ApiErrorDetailDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
