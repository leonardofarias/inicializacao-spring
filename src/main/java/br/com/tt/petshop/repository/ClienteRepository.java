package br.com.tt.petshop.repository;

import br.com.tt.petshop.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

//    List<Cliente> clientes = new ArrayList<>(Arrays.asList(
//        new Cliente(3L,"Leonardo", "01773449036",true),
//        new Cliente(4L,"Juca", "02146188030", false)));
//
//    public List<Cliente> findAll() {
//        return clientes;
//    }
//
//    public void save(Cliente cli) {
//        try{
//            cli.setId(SecureRandom.getInstanceStrong().nextLong());
//        }catch (NoSuchAlgorithmException e){
//            e.printStackTrace();
//        }
//        clientes.add(cli);
//    }
//
//    public void delete(Cliente cli){
//        clientes.remove(cli);
//    }
//
//    public Cliente findById(Long id){
//        /*Cliente cliente = new Cliente();
//        cliente.setId(id);
//        int posicao = clientes.indexOf(cliente);
//        if(posicao > 0){
//            return clientes.get(posicao);
//        }else{
//            return null;
//        }*/
//        for (Cliente cliente: clientes) {
//            if(cliente.getId().equals(id)){
//                return cliente;
//            }
//        }
//        return null;
//    }
}
