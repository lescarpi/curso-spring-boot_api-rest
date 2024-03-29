package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaConsultasService agenda;

    @Autowired
    private CancelaConsultaService cancela;

    @PostMapping("/agendar")
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        DadosDetalhamentoConsulta detalheConsulta = agenda.agendar(dados);

        return ResponseEntity.ok(detalheConsulta);
    }

    @DeleteMapping("/cancelar")
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        return cancela.cancelar(dados);
    }

}
