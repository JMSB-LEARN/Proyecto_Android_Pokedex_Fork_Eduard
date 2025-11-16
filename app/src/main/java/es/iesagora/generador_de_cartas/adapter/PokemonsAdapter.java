package es.iesagora.generador_de_cartas.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.iesagora.generador_de_cartas.R;
import es.iesagora.generador_de_cartas.databinding.ViewholderPokedexBinding;
import es.iesagora.generador_de_cartas.model.Pokemon;

public class PokemonsAdapter extends RecyclerView.Adapter<PokemonsAdapter.PokemonViewHolder> {

    public interface OnPokemonClickListener {
        void onPokemonClick(Pokemon pokemon);
    }

    private List<Pokemon> pokemons;
    private OnPokemonClickListener listener;

    public PokemonsAdapter(List<Pokemon> pokemons, OnPokemonClickListener listener) {
        this.pokemons = pokemons;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderPokedexBinding binding = ViewholderPokedexBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PokemonViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemons.get(position);

        holder.binding.tvNum.setText("#" + pokemon.getNumero());
        holder.binding.tvNombre.setText(pokemon.getNombre());
        holder.binding.ivPokemon.setImageResource(pokemon.getImage());

        holder.itemView.setOnClickListener(v ->
                listener.onPokemonClick(pokemon)
        );
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        ViewholderPokedexBinding binding;

        public PokemonViewHolder(ViewholderPokedexBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
