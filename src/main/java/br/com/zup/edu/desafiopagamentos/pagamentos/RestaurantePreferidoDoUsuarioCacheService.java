package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.pagamentos.response.FormasDePagamentoResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantePreferidoDoUsuarioCacheService {
    private final RestaurantePreferidoDoUsuarioRepository cacheRepository;


    public RestaurantePreferidoDoUsuarioCacheService(RestaurantePreferidoDoUsuarioRepository cacheRepository) {
        this.cacheRepository = cacheRepository;

    }

    public Optional<RestaurantePreferidoDoUsuario> buscarEmCache(Long idRestaurante, Long idUsuario) {
        String id = idRestaurante.toString().concat(idUsuario.toString());
        return cacheRepository.findById(id);
    }

    public void salvar(Long idUsuario, Long idRestaurante, List<FormasDePagamentoResponse> formasdePagamento) {
        String id = idRestaurante.toString().concat(idUsuario.toString());
        RestaurantePreferidoDoUsuario restaurantePreferidoDoUsuario = new RestaurantePreferidoDoUsuario(id, formasdePagamento);
        cacheRepository.save(restaurantePreferidoDoUsuario);

    }

    public void atualizar(RestaurantePreferidoDoUsuario restaurantePreferidoDoUsuario) {
        cacheRepository.save(restaurantePreferidoDoUsuario);
    }
}
