package br.com.tt.petshop.service;

import br.com.tt.petshop.client.CreditoApiClient;
import br.com.tt.petshop.client.CreditoApiRTClient;
import br.com.tt.petshop.client.dto.CreditoDto;
import br.com.tt.petshop.exception.BusinessException;
import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final CreditoApiClient creditoApiClient;

    public ClienteService(ClienteRepository clienteRepository,
                          @Qualifier("feign") CreditoApiClient creditoApiClient) {
        this.clienteRepository = clienteRepository;
        this.creditoApiClient = creditoApiClient;
    }

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente adicionar(Cliente cli) throws BusinessException {
        validaNome(cli);
        validaCpf(cli);
        validaSituacaoCredito(cli.getCpf().getValor());
        clienteRepository.save(cli);
        return cli;
    }

    private void validaSituacaoCredito(String valor) throws BusinessException{
        CreditoDto dto = creditoApiClient.verificaSituacao(valor);

        if("NEGATIVADO".equals(dto.getSituacao())){
            throw new BusinessException("Verifique sua situação com o governo");
        }
    }

    private void validaNome(Cliente cli) throws BusinessException {

        if (Objects.isNull(cli) || Objects.isNull(cli.getNome())) {
            throw new BusinessException("Cliente inválido");
        }

        String[] partesNome = cli.getNome().trim().split(" ");
        for (String p : partesNome) {
            if (p.length() < 2) {
                throw new BusinessException("Nome inválido");
            }
        }

        if (partesNome.length < 2) {
            throw new BusinessException("Nome deve conter nome e sobrenome e cada um deve conter mais de duas letras");
        }
    }

    private void validaCpf(Cliente cli) throws BusinessException {
        if (Objects.isNull(cli) || Objects.isNull(cli.getCpf().getValor())) {
            throw new BusinessException("Cliente inválido");
        }

        //String cpfTest = cli.getCpf().replaceAll("[^\\d]", "");
        if (!cli.getCpf().isValid()) {
            throw new BusinessException("CPF inválido");
        // }else{
        //     cli.setCpf(cpfTest);
        }

    }

    public void remover(Long id) {
        //TODO alterar no JPA
        Cliente cliente = new Cliente();
        cliente.setId(id);
        clienteRepository.delete(cliente);
    }

    public Optional<Cliente> buscarCliente(Long id){
        return clienteRepository.findById(id);
    }

    public void validaClienteInadimplente(Long clientId) throws BusinessException {
        Optional<Cliente> clienteOptional = buscarCliente(clientId);

        if(clienteOptional.isPresent()){
            Cliente cliente = clienteOptional.get();
            if(cliente.getInadimplente()){
                throw new BusinessException("Cliente inadimplente");
            }
        }
    }

    public void update(Cliente cliente) {
        clienteRepository.save(cliente);
    }

}
