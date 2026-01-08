package com.project.roomie.ports.out;

public interface NotificacoesPortOut {

    void enviar(String firebase_token, String mensagem);
}