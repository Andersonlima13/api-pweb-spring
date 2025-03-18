package com.apicontrole.demo.service;

import com.apicontrole.demo.model.Usuario;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

    public String salvarUsuario(Usuario usuario) {
        Firestore db = FirestoreClient.getFirestore();

        try {
            db.collection("usuarios")
                    .document(usuario.getEmail())
                    .set(usuario)
                    .get();
            return "Usuário salvo com sucesso!";
        } catch (InterruptedException | ExecutionException e) {
            return "Erro ao salvar usuário: " + e.getMessage();
        }
    }
}
