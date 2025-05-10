package br.com.redeAncora.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import br.com.redeAncora.app.Adapter.PecasAdapter;
import br.com.redeAncora.app.Domain.PecasDomain;

import br.com.redeAncora.app.Services.ApiService;
import br.com.redeAncora.app.Services.RetrofitClient;
import br.com.redeAncora.app.databinding.ActivityFavoriteBinding;
import br.com.redeAncora.app.dto.PecaDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityFavorite extends BaseActivity {
    // Responsável por vincular os elementos da interface.
    ActivityFavoriteBinding binding;

    private ArrayList<PecasDomain> allFavoriteItems = new ArrayList<>();
    private PecasAdapter adapter;

    /**
     * Configura a view e inicializa as listas de categorias e peças populares.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initFavoriteList();
        binding.backBtn.setOnClickListener(v -> finish());
    }


    /**
     * Busca dados de peças favoritas no Firebase e popula a RecyclerView
     */
    private void initFavoriteList() {
        binding.progressBarFavorite.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getApiService();
        adapter = new PecasAdapter(allFavoriteItems);
        binding.favoriteRecyclerView.setLayoutManager(new GridLayoutManager(ActivityFavorite.this, 2));
        binding.favoriteRecyclerView.setAdapter(adapter);

        apiService.getFavoritas().enqueue(new Callback<List<PecaDTO>>() {
            @Override
            public void onResponse(Call<List<PecaDTO>> call, Response<List<PecaDTO>> response) {
                binding.progressBarFavorite.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    allFavoriteItems.clear();

                    for (PecaDTO dto : response.body()) {
                        PecasDomain peca = new PecasDomain(dto);
                        allFavoriteItems.add(peca);
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("API", "Erro ao buscar favoritas. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PecaDTO>> call, Throwable t) {
                binding.progressBarFavorite.setVisibility(View.GONE);
                Log.e("API", "Erro de conexão: " + t.getMessage());
            }
        });
    }
}