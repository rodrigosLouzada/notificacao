package com.projetoNotificacao.notificacao.controller;


import com.projetoNotificacao.notificacao.business.EmailService;
import com.projetoNotificacao.notificacao.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> enviaremail(@RequestBody TarefasDTO tarefasDTO){

        emailService.enviaEmail(tarefasDTO);
        return ResponseEntity.ok().build();
    }
}
