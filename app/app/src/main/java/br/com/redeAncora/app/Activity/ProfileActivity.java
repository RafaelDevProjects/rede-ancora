package br.com.redeAncora.app.Activity;

import android.os.Bundle;

import br.com.redeAncora.app.databinding.ActivityProfileBinding;

public class ProfileActivity extends BaseActivity {
    // Responsável por vincular os elementos da interface.
    ActivityProfileBinding binding;


    /**
     * Configura a view e define a funcionalidade do botão de voltar.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(v -> finish());
    }
}