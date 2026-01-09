package com.project.roomie.ports.out;

import com.project.roomie.core.model.Chat;

public interface ChatPortOut {

    Chat save(Chat chat);
}