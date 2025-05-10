package br.com.redeAncora.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import br.com.redeAncora.app.Domain.CategoryDomain;
import br.com.redeAncora.app.databinding.ViewholderCategoryBinding;

/**
 * Adaptador para exibir uma lista de categorias em um RecyclerView.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {
    private ArrayList<CategoryDomain> items;
    private Context context;
    private OnCategoryClickListener listener; // Adicionar um listener

    public interface OnCategoryClickListener {
        void onCategoryClick(String category);
    }

    /**
     * Construtor que recebe a lista de categorias.
     * @param items Lista de objetos CategoryDomain
     */
    public CategoryAdapter(ArrayList<CategoryDomain> items, OnCategoryClickListener listener) {
        this.items = items;
        this.listener = listener;
    }



    /**
     * Método chamado para criar novas instâncias do ViewHolder.
     * @param parent O ViewGroup no qual a nova View será adicionada
     * @param viewType O tipo de view (não utilizado aqui)
     * @return Um novo ViewHolder
     */
    @NonNull
    @Override
    public CategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        // Inflando o layout do ViewHolder utilizando View Binding
        ViewholderCategoryBinding binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new Viewholder(binding);
    }


    /**
     * Método chamado para exibir os dados na posição especificada.
     * @param holder O ViewHolder que deve ser atualizado
     * @param position A posição do item na lista
     */
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Viewholder holder, int position) {
        CategoryDomain category = items.get(position);

        // Definindo o nome da categoria no TextView
        holder.binding.titleTxt.setText(items.get(position).getTitle());


        // Carregando a imagem da categoria utilizando Glide
        Glide.with(context)
                .load(items.get(position).getPicUrl())
                .into(holder.binding.pic);

        // Define o clique na categoria
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoryClick(category.getTitle()); // Passa o título da categoria clicada
            }
        });
    }

    /**
     * Método que retorna a quantidade de itens na lista.
     * @return Número total de itens
     */
    @Override
    public int getItemCount() {
        return items.size(); // Retorna a quantidade de categorias na lista
    }

    /**
     * Classe interna que representa o ViewHolder para cada item da lista.
     */
    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderCategoryBinding binding;

        public Viewholder(ViewholderCategoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
