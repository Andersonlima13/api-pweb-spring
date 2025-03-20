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

    // Método para criar usuário
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

    /// login user
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUsuario(@RequestBody Usuario usuario) {
        System.out.println("Recebendo login: " + usuario.getEmail() + ", " + usuario.getPassword());

        boolean loginValido = firebaseService.verificarUsuario(usuario.getEmail(), usuario.getPassword());

        Map<String, String> response = new HashMap<>();

        if (loginValido) {
            response.put("message", "Login bem-sucedido!");
            return ResponseEntity.ok(response); // Resposta com sucesso no login
        } else {
            response.put("message", "Credenciais inválidas!");
            return ResponseEntity.status(401).body(response); // Erro caso as credenciais sejam inválidas
        }
    }

}
