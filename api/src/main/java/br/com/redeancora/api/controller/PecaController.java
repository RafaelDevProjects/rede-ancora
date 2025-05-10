package br.com.redeancora.api.controller;

import br.com.redeancora.api.dto.CategoriaDTO;
import br.com.redeancora.api.dto.PecaDTO;
import br.com.redeancora.api.service.FirebaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class PecaController {

    private final FirebaseService service;
    private final String SECRET_KEY = "COLOCAR_SECRET_AQUI"; // mesma chave do app


    public PecaController(FirebaseService service) {
        this.service = service;
    }

    @GetMapping("/pecas")
    public CompletableFuture<List<PecaDTO>> getTodas() {
        return service.buscarTodasAsPecas();
    }

    @GetMapping("/pecas/favoritas")
    public CompletableFuture<List<PecaDTO>> getFavoritas() throws ExecutionException, InterruptedException {
        return service.buscarFavoritas();
    }

    @GetMapping("/categorias")
    public CompletableFuture<List<CategoriaDTO>> getCategorias() {
        return service.buscarCategorias();
    }

    @GetMapping("pecas/{id}")
    public CompletableFuture<ResponseEntity<PecaDTO>> getPecaPorId(@PathVariable String id) {
        return service.buscarPecaPorId(id)
                .thenApply(peca -> peca != null ?
                        ResponseEntity.ok(peca) :
                        ResponseEntity.notFound().build());
    }

    @PutMapping("pecas/{id}/favorito")
    public CompletableFuture<ResponseEntity<Object>> atualizarFavorito(
            @PathVariable String id,
            @RequestParam boolean valor,
            @RequestParam String apiKey) {

        if (!SECRET_KEY.equals(apiKey)) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        return service.atualizarFavorito(id, valor)
                .thenApply(v -> ResponseEntity.ok().build())
                .exceptionally(e -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}