package com.apicontrole.demo.controller;

import com.apicontrole.demo.model.Usuario;
import com.apicontrole.demo.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private FirebaseService firebaseService;

    @PostMapping("/criar")
    public ResponseEntity<Map<String, String>> criarUsuario(@RequestBody Usuario usuario) {
        // Logando os dados recebidos
        System.out.println(
                "Recebendo usuário: " + usuario.getNome() + ", " + usuario.getEmail() + ", " + usuario.getPassword());

        String resultado = firebaseService.salvarUsuario(usuario);
        Map<String, String> response = new HashMap<>();

        if (resultado.contains("sucesso")) {
            response.put("message", "Usuário criado com sucesso!");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Erro ao criar usuário.");
            return ResponseEntity.status(500).body(response);
        }
    }
}
