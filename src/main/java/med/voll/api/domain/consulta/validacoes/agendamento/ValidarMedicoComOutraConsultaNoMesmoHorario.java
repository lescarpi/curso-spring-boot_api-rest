package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarMedicoComOutraConsultaNoMesmoHorario implements ValidarAgendamentoDeConsulta {

    @Autowired
    ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        Boolean medicoPossuiConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if(medicoPossuiConsultaNoMesmoHorario) {
            throw new ValidacaoException("Médico já possui consulta nesse horário!");
        }
    }

}
