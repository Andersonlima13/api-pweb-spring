package com.apicontrole.demo.service;

import com.apicontrole.demo.model.Usuario;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseService {

    public FirebaseService() {
        try {
            FileInputStream serviceAccount = new FileInputStream(
                    "src/main/resources/bd-controle-gastos-firebase-adminsdk-fbsvc-21f4dff4e8.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(com.google.auth.oauth2.GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options); // Inicializando o Firebase
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String salvarUsuario(Usuario usuario) {
        Firestore db = FirestoreClient.getFirestore();

        try {
            // Criando um Map com os dados do usuário
            Map<String, Object> usuarioData = new HashMap<>();
            usuarioData.put("nome", usuario.getNome());
            usuarioData.put("email", usuario.getEmail());
            usuarioData.put("password", usuario.getPassword());

            // Salvando os dados do usuário no Firestore com o email como ID
            db.collection("usuarios")
                    .document(usuario.getEmail()) // Usando o email como ID do documento
                    .set(usuarioData) // Definindo os campos explicitamente
                    .get();

            return "Usuário salvo com sucesso!";
        } catch (InterruptedException | ExecutionException e) {
            return "Erro ao salvar usuário: " + e.getMessage();
        }
    }
}
