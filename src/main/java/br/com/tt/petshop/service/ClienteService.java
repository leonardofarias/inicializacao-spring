package br.com.tt.petshop.service;

import br.com.tt.petshop.exception.BusinessException;
import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Long adicionar(Cliente cli) throws BusinessException {
        validaNome(cli);
        validaCpf(cli);
        clienteRepository.save(cli);
        return cli.getId();
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
