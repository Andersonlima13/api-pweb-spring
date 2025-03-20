package com.apicontrole.demo.service;

import com.apicontrole.demo.model.Usuario;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.Map;
import java.util.HashMap;

@Service
public class FirebaseService {

    public FirebaseService() {
        try {
            FileInputStream serviceAccount = new FileInputStream(
                    "src/main/resources/bd-controle-gastos-firebase-adminsdk-fbsvc-21f4dff4e8.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options); // Inicializando o Firebase
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para salvar o usuário
    public String salvarUsuario(Usuario usuario) {
        Firestore db = FirestoreClient.getFirestore();

        try {
            // Criando um Map com os dados do usuário
            Map<String, Object> usuarioData = new HashMap<>();
            usuarioData.put("name", usuario.getNome());
            usuarioData.put("email", usuario.getEmail());
            usuarioData.put("password", usuario.getPassword()); // Armazenando a senha (não recomendado em produção sem
                                                                // criptografia)

            // Salvando os dados do usuário no Firestore com o email como ID
            db.collection("usuarios")
                    .document(usuario.getEmail())
                    .set(usuarioData)
                    .get();

            return "Usuário salvo com sucesso!";
        } catch (InterruptedException | ExecutionException e) {
            return "Erro ao salvar usuário: " + e.getMessage();
        }
    }

    // Método para verificar se o usuário existe e se a senha está correta
    public boolean verificarUsuario(String email, String senha) {
        Firestore db = FirestoreClient.getFirestore();

        try {
            // Buscando o documento do usuário com o email
            Map<String, Object> usuarioData = db.collection("usuarios")
                    .document(email)
                    .get()
                    .get()
                    .getData();

            if (usuarioData != null && usuarioData.get("password") != null) {
                // Comparando a senha recebida com a armazenada no Firestore
                String senhaArmazenada = usuarioData.get("password").toString();
                return senhaArmazenada.equals(senha); // Comparando as senhas
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return false; // Retorna false caso o usuário não seja encontrado ou a senha seja incorreta
    }
}
