package es.iesagora.generador_de_cartas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Random;

import es.iesagora.generador_de_cartas.databinding.FragmentGeneradorBinding;
import es.iesagora.generador_de_cartas.model.Pokemon;
import es.iesagora.generador_de_cartas.repository.PokemonRepository;

public class GeneradorFragment extends Fragment {

    private FragmentGeneradorBinding binding;

    private PokemonRepository equipoPokemon;
    private PokemonRepository pokemonRepository;
    private List<Pokemon> listaCompleta;
    private Random random = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGeneradorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pokemonRepository = new PokemonRepository(false);
        equipoPokemon = ((MainActivity) requireActivity()).equipoPokemon;

        listaCompleta = pokemonRepository.getListaPokemons();

        binding.buttonGenerar.setOnClickListener(v -> generarPokemon());
    }

    private void generarPokemon() {

        if (listaCompleta.isEmpty()) return;

        Pokemon randomPoke = listaCompleta.get(random.nextInt(listaCompleta.size()));

        binding.imageView2.setImageResource(randomPoke.getImage());
        binding.textViewNombre.setText(randomPoke.getNombre());
        binding.textViewNumero.setText("Nº " + randomPoke.getNumero());

        List<Pokemon> equipo = equipoPokemon.getListaPokemons();

        if (equipo.contains(randomPoke)) {
            Toast.makeText(requireContext(),
                    getString(R.string.duplicado),
                    Toast.LENGTH_SHORT).show();
        } else {
            equipoPokemon.anadirPokemon(randomPoke);
            Toast.makeText(requireContext(),
                    randomPoke.getNombre() + getString(R.string.anadido),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
