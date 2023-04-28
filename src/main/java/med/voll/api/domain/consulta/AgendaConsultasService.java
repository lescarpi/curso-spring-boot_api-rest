package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.ValidarAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.ValidarHorarioFuncionamentoClinica;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaConsultasService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private List<ValidarAgendamentoDeConsulta> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {

        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Não existe paciente com id informado!");
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Não existe médico com id informado!");
        }

        validadores.forEach(v -> v.validar(dados));

        Medico medico = escolherMedico(dados);
        if(medico == null) {
            throw new ValidacaoException("Não há nenhum médico com essa especialidade livre nesta data!");
        }
        Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        Consulta consulta = new Consulta(null, medico, paciente, dados.data());

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for informado!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());

    }

}
