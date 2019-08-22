package br.com.tt.petshop.client;

import br.com.tt.petshop.client.dto.CreditoDto;
import br.com.tt.petshop.exception.BusinessException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@Qualifier("feign")
@FeignClient(value = "credito-api", url = "${api.credito-api.url}")
public interface CreditoApiFeignClient extends CreditoApiClient {

    @Override
    @GetMapping("/credito/{cpf}")
    CreditoDto verificaSituacao(@PathVariable("cpf") String cpf) throws BusinessException;
}
