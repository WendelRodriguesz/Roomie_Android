package com.project.roomie.ports.in;

import com.project.roomie.dto.auth.AuthDTO;
import com.project.roomie.dto.auth.RefreshDTO;
import org.springframework.http.ResponseEntity;

public interface AuthPortIn {

    ResponseEntity logar(AuthDTO authDTO);
    ResponseEntity refresh(RefreshDTO refreshDTO);
}