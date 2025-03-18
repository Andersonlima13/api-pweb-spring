package com.apicontrole.demo.controller;

import com.apicontrole.demo.model.Usuario;
import com.apicontrole.demo.service.FirebaseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firebase")
public class FirebaseController {

    private final FirebaseService firebaseService;

    // Injeta o FirebaseService no construtor
    public FirebaseController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@RequestBody Usuario usuario) {
        return firebaseService.salvarUsuario(usuario);
    }

    @GetMapping("/teste")
    public String testarAPI() {
        return "API funcionando!";
    }
}
