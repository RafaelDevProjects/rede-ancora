package br.com.redeAncora.app.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import br.com.redeAncora.app.Domain.PecasDomain;
import br.com.redeAncora.app.R;
import br.com.redeAncora.app.Services.ApiService;
import br.com.redeAncora.app.Services.RetrofitClient;
import br.com.redeAncora.app.databinding.ActivityDetailBinding;
import br.com.redeAncora.app.dto.PecaDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends BaseActivity {
    final String SECRET_KEY = "rede_ancora_secret_key"; //ADICIONAR A SECRET KEY AQUI
    // Responsável por vincular os elementos da interface.
    ActivityDetailBinding binding;
    PecasDomain object;
    private boolean isFavorited = false; // Variável para controlar o estado do favorito
    private DatabaseReference favRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();

        setupFavoriteButton();
    }

    private String pecaId;
    /**
     * Preenche os elementos da tela com os dados do objeto PecasDomain e carrega a imagem usando Glide.
     */
    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());

        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.totalCapacityTxt.setText(object.getDetalhes());
        binding.highestSpeedTxt.setText(object.getHighestSpeed());
        binding.engineOutputTxt.setText(object.getMarca());
        binding.priceTxt.setText("$" + NumberFormat.getNumberInstance().format(object.getPrice()));
        binding.ratingTxt.setText(String.valueOf(object.getRating()));

        Glide.with(DetailActivity.this)
                .load(object.getPicUrl())
                .into(binding.pic);
    }

    /**
     * Obtém o objeto PecasDomain passado como parâmetro na Intent.
     */
    private void getIntentExtra() {
        object = (PecasDomain) getIntent().getSerializableExtra("object");
        pecaId = getIntent().getStringExtra("pecaId");
    }

    /**
     * Configura o botão de favoritos, verificando no Firebase e alternando o estado.
     */
    private void setupFavoriteButton() {
        ApiService apiService = RetrofitClient.getApiService();

        apiService.getPecaPorId(pecaId).enqueue(new Callback<PecaDTO>() {
            @Override
            public void onResponse(Call<PecaDTO> call, Response<PecaDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    isFavorited = response.body().getisFavorito();
                    updateFavoriteIcon();

                    binding.coracaoDeFavoritar.setOnClickListener(v -> {
                        isFavorited = !isFavorited;
                        updateFavoriteIcon();

                        apiService.atualizarFavorito(pecaId, isFavorited, SECRET_KEY).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (!response.isSuccessful()) {
                                    Log.e("API", "Erro ao atualizar favorito. Código: " + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("API", "Falha ao atualizar favorito: " + t.getMessage());
                            }
                        });
                    });

                } else {
                    Log.e("API", "Erro ao buscar peça. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PecaDTO> call, Throwable t) {
                Log.e("API", "Erro de conexão: " + t.getMessage());
            }
        });
    }

    /**
     * Atualiza o ícone do botão de favorito de acordo com o estado atual.
     */
    private void updateFavoriteIcon() {
        int iconRes = isFavorited ? R.drawable.star : R.drawable.fav_icon;
        binding.coracaoDeFavoritar.setImageResource(iconRes);
    }
}
