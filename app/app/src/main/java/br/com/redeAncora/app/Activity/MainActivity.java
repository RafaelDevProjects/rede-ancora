package br.com.redeAncora.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;



import java.util.ArrayList;
import java.util.List;

import br.com.redeAncora.app.Adapter.PecasAdapter;
import br.com.redeAncora.app.Adapter.CategoryAdapter;
import br.com.redeAncora.app.Domain.PecasDomain;
import br.com.redeAncora.app.Domain.CategoryDomain;
import br.com.redeAncora.app.Services.ApiService;
import br.com.redeAncora.app.databinding.ActivityMainBinding;
import br.com.redeAncora.app.dto.CategoriaDTO;
import br.com.redeAncora.app.dto.PecaDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {
    // Responsável por vincular os elementos da interface.
    ActivityMainBinding binding;

    private ArrayList<PecasDomain> allItems = new ArrayList<>();
    private ArrayList<PecasDomain> filteredItems = new ArrayList<>();
    private PecasAdapter adapter;
    private ApiService apiService;

    /**
     * Configura a view e inicializa as listas de categorias e peças populares.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080") // ou IP local do seu backend
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        initCategoryList();
        initPopularList();
        bottomNavigation();

        initCategoryList();
        initPopularList();
        bottomNavigation();

    }


    /**
     * Configura a navegação para a ProfileActivity.
     */
    private void bottomNavigation() {
        binding.profileBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProfileActivity.class)));
        binding.favoriteIcon.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ActivityFavorite.class)));
    }


    /**
     * Busca dados de peças populares no Firebase e popula a RecyclerView
     */


    private void initPopularList() {
        binding.progressBarPopular.setVisibility(View.VISIBLE);

        adapter = new PecasAdapter(filteredItems);
        binding.recyclerViewPopular.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        binding.recyclerViewPopular.setAdapter(adapter);

        apiService.getTodasPecas().enqueue(new Callback<List<PecaDTO>>() {
            @Override
            public void onResponse(Call<List<PecaDTO>> call, Response<List<PecaDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allItems.clear();
                    filteredItems.clear();

                    for (PecaDTO dto : response.body()) {
                        PecasDomain peca = new PecasDomain(dto);
                        allItems.add(peca);
                    }

                    filteredItems.addAll(allItems);
                    adapter.notifyDataSetChanged();
                }
                binding.progressBarPopular.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<PecaDTO>> call, Throwable t) {
                t.printStackTrace();
                binding.progressBarPopular.setVisibility(View.GONE);
            }
        });

        // Listener para o campo de busca
        binding.editTextText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterItemsBySearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Filtra os itens populares pela categoria clicada.
     */
    private void filterItemsByCategory(String category) {
        filteredItems.clear();

        if (category.equals("Todos")) {
            filteredItems.addAll(allItems);
        } else {
            for (PecasDomain peca : allItems) {
                if (peca.getCategory().equals(category)) {
                    filteredItems.add(peca);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    /**
     * Filtra os itens populares pela busca do usuário.
     */
    private void filterItemsBySearch(String query) {
        filteredItems.clear();
        for (PecasDomain peca : allItems) {
            if (peca.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredItems.add(peca);
            }
        }
        adapter.notifyDataSetChanged();
    }


    /**
     * Busca dados das categorias no Firebase e popula a RecyclerView correspondente.
     */
    private void initCategoryList() {
        binding.progressBarCategory.setVisibility(View.VISIBLE);

        apiService.getCategorias().enqueue(new Callback<List<CategoriaDTO>>() {
            @Override
            public void onResponse(Call<List<CategoriaDTO>> call, Response<List<CategoriaDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<CategoryDomain> items = new ArrayList<>();
                    // Converte CategoriaDTO para CategoryDomain
                    for (CategoriaDTO dto : response.body()) {
                        CategoryDomain category = new CategoryDomain(dto);
                        items.add(category);
                    }

                    // Exibe na RecyclerView
                    binding.recyclerViewCategory.setLayoutManager(
                            new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    CategoryAdapter categoryAdapter = new CategoryAdapter(items, MainActivity.this::filterItemsByCategory);
                    binding.recyclerViewCategory.setAdapter(categoryAdapter);
                    binding.recyclerViewCategory.setNestedScrollingEnabled(true);
                }
                binding.progressBarCategory.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<CategoriaDTO>> call, Throwable t) {
                t.printStackTrace();
                binding.progressBarCategory.setVisibility(View.GONE);
            }
        });
    }
}