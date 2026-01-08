package com.project.roomie.adapters.in;

import com.project.roomie.dto.auth.AuthDTO;
import com.project.roomie.dto.auth.RefreshDTO;
import com.project.roomie.ports.in.AuthPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthAdapterIn{

    private final AuthPortIn authPortIn;

    @Autowired
    AuthAdapterIn(AuthPortIn authPortIn){
        this.authPortIn = authPortIn;
    }

    @PostMapping("/logar")
    public ResponseEntity logar(@RequestBody AuthDTO authDTO){
        return authPortIn.logar(authDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestBody RefreshDTO refreshDTO){ return authPortIn.refresh(refreshDTO); }
}