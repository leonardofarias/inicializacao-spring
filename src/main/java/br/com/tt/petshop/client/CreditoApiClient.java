package br.com.tt.petshop.client;

import br.com.tt.petshop.client.dto.CreditoDto;
import br.com.tt.petshop.exception.BusinessException;

public interface CreditoApiClient {
    CreditoDto verificaSituacao(String cpf) throws BusinessException;
}
