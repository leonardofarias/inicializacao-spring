package br.com.tt.petshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ClientNotFoundException extends RuntimeException{

    private final Long clientId;

    public ClientNotFoundException(Long clientId) {
        super("Cliente não existe");
        this.clientId = clientId;
    }

    public Long getClientId() {
        return clientId;
    }
}
