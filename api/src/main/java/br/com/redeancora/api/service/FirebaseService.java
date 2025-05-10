package br.com.redeancora.api.service;

import br.com.redeancora.api.dto.CategoriaDTO;
import br.com.redeancora.api.dto.PecaDTO;
import com.google.firebase.database.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

    @Async
    public CompletableFuture<List<PecaDTO>> buscarTodasAsPecas() {
        List<PecaDTO> pecas = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Pecas");

        CompletableFuture<List<PecaDTO>> future = new CompletableFuture<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    PecaDTO peca = item.getValue(PecaDTO.class);
                    peca.setId(item.getKey());
                    pecas.add(peca);
                }
                future.complete(pecas);  // Completa o futuro com os dados
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        return future;
    }

    @Async
    public CompletableFuture<List<PecaDTO>> buscarFavoritas() throws ExecutionException, InterruptedException {
        List<PecaDTO> favoritas = new ArrayList<>();

        // Aguarda a conclusão da busca por todas as peças
        List<PecaDTO> pecas = buscarTodasAsPecas().get();  // Aqui usamos .get() para aguardar a resposta

        for (PecaDTO peca : pecas) {
            if (peca.getisFavorito()) {
                favoritas.add(peca);
            }
        }

        return CompletableFuture.completedFuture(favoritas); // Retorna as favoritas como um CompletableFuture
    }

    @Async
    public CompletableFuture<List<CategoriaDTO>> buscarCategorias() {
        List<CategoriaDTO> categorias = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Category");

        CompletableFuture<List<CategoriaDTO>> future = new CompletableFuture<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    categorias.add(item.getValue(CategoriaDTO.class));
                }
                future.complete(categorias);  // Completa o futuro com os dados
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        return future;
    }


    @Async
    public CompletableFuture<PecaDTO> buscarPecaPorId(String id) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Pecas").child(id);
        CompletableFuture<PecaDTO> future = new CompletableFuture<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                PecaDTO peca = snapshot.getValue(PecaDTO.class);
                if (peca != null) {
                    peca.setId(snapshot.getKey());
                    future.complete(peca);
                } else {
                    future.complete(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        return future;
    }

    public CompletableFuture<Void> atualizarFavorito(String id, boolean valor) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Pecas").child(id);
        CompletableFuture<Void> future = new CompletableFuture<>();

        ref.child("isFavorito").setValue(valor, (error, ref1) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(null);
            }
        });

        return future;
    }
}