package br.com.redeAncora.app.Services;

import java.util.List;

import br.com.redeAncora.app.Domain.CategoryDomain;
import br.com.redeAncora.app.Domain.PecasDomain;
import br.com.redeAncora.app.dto.CategoriaDTO;
import br.com.redeAncora.app.dto.PecaDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/api/pecas")
    Call<List<PecaDTO>> getTodasPecas();

    @GET("/api/pecas/favoritas")
    Call<List<PecaDTO>> getFavoritas();

    @GET("/api/categorias")
    Call<List<CategoriaDTO>> getCategorias();

    @GET("/api/pecas/{id}")
    Call<PecaDTO> getPecaPorId(@Path("id") String id);

    @PUT("/api/pecas/{id}/favorito")
    Call<Void> atualizarFavorito(
            @Path("id") String id,
            @Query("valor") boolean valor,
            @Query("apiKey") String apiKey
    );
}