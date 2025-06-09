package com.projetoNotificacao.notificacao.business;

import com.projetoNotificacao.notificacao.business.dto.TarefasDTO;
import com.projetoNotificacao.notificacao.infrastructure.exceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${envio.email.remetente}")
    public String remetente;

    @Value("${envio.email.nomeRemetente}")
    private String nomeRemetente;

    public void enviaEmail(TarefasDTO tarefasDTO){

        try {
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mensagem, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(new InternetAddress(remetente, nomeRemetente));
            mimeMessageHelper.setTo(InternetAddress.parse(tarefasDTO.getEmailUsuario()));
            mimeMessageHelper.setSubject("Notificação de email");

            Context context = new Context();
            context.setVariable("nomeTarefa", tarefasDTO.getNomeTarefa());
            context.setVariable("dataEvento", tarefasDTO.getDataEvento());
            context.setVariable("descricao" , tarefasDTO.getDescricao());

            String template = templateEngine.process("notificacao", context);

            mimeMessageHelper.setText(template, true);
            javaMailSender.send(mensagem);

        } catch(MessagingException | UnsupportedEncodingException e) {
            throw  new EmailException("Erro ao enviar o email", e.getCause());
        }
    }
}
