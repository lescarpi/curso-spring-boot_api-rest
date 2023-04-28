package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidarCancelamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancelaConsultaService {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private List<ValidarCancelamentoDeConsulta> validadores;

    public ResponseEntity cancelar(DadosCancelamentoConsulta dados) {
        if(!repository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Consulta informada não existe!");
        }

        validadores.forEach(v -> v.validar(dados));

        repository.deleteById(dados.idConsulta());
        return ResponseEntity.noContent().build();
    }

}
