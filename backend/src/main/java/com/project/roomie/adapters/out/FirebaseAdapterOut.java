package com.project.roomie.adapters.out;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.project.roomie.ports.out.NotificacoesPortOut;
import org.springframework.stereotype.Component;

@Component
public class FirebaseAdapterOut implements NotificacoesPortOut {

    @Override
    public void enviar(String firebaseToken, String mensagem) {

        if (firebaseToken == null || firebaseToken.isBlank()) {
            return;
        }

        Message message = Message.builder()
                .setToken(firebaseToken)
                .putData("mensagem", mensagem)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação Firebase: " + e.getMessage());
        }
    }
}