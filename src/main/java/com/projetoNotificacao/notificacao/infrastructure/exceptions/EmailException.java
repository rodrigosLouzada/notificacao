package com.projetoNotificacao.notificacao.infrastructure.exceptions;

public class EmailException extends RuntimeException{

    public EmailException(String msg){
        super(msg);
    }
    public EmailException(String msg, Throwable throwable){
        super(msg, throwable);
    }

}
