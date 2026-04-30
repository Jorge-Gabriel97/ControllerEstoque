package br.com.springboot.service;

import br.com.springboot.model.Cliente;
import br.com.springboot.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void salvar(Cliente cliente) {
        boolean cpfJaExiste = cliente.getId() == null
                && clienteRepository.existsByCpf(cliente.getCpf());

        if (cpfJaExiste) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + id));
    }

    public void remover(Long id) {
        clienteRepository.deleteById(id);
    }
}