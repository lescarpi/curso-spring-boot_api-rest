package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidarHorarioAntecedenciaCancelamento implements ValidarCancelamentoDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        LocalDateTime data = consultaRepository.findDataById(dados.idConsulta());

        Long diferencaEmHoras = Duration.between(LocalDateTime.now(), data).toHours();

        if(diferencaEmHoras < 24) {
            throw new ValidacaoException("Cancelamento só pode ser feito com no mínimo 24 horas de antecedência!");
        }
    }
}
