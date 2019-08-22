package br.com.tt.petshop.client;

import br.com.tt.petshop.client.dto.CreditoDto;
import br.com.tt.petshop.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
@Component
@Qualifier("restTemplate")
public class CreditoApiRTClient implements CreditoApiClient {

    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditoApiRTClient.class);
    private final String creditoApiUrl;

    public CreditoApiRTClient(RestTemplate restTemplate,
                              @Value("${api.credito-api.url}") String creditoApiUrl) {
        this.restTemplate = restTemplate;
        this.creditoApiUrl = creditoApiUrl;
    }

    @Override
    public CreditoDto verificaSituacao(String cpf) throws BusinessException {
        URI url = URI.create(String.format("%s/credito/%s", creditoApiUrl, cpf));

        try{
            return restTemplate.getForObject(url, CreditoDto.class);
        }catch (HttpClientErrorException e) {
            LOGGER.info("Erro ao acessar serviço de crédito", e);
            if (e.getStatusCode().is4xxClientError()) {
                throw new BusinessException("Verifique o CPF enviado: " + e.getMessage());
            } else {
                throw new BusinessException("Serviço de consulta ao Crédito indisponível");
            }
        }catch (Exception e){
            LOGGER.info("Erro ao acessar serviço de crédito", e);
            throw new BusinessException("Serviço de consulta ao Crédito indisponível");
        }
    }
}
