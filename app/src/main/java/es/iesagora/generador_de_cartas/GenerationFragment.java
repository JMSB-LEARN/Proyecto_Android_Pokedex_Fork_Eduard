package es.iesagora.generador_de_cartas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import es.iesagora.generador_de_cartas.adapter.PokemonsAdapter;
import es.iesagora.generador_de_cartas.databinding.FragmentGenerationBinding;
import es.iesagora.generador_de_cartas.repository.PokemonRepository;

public class GenerationFragment extends Fragment {

    private static final String ARG_GEN = "GEN";
    private FragmentGenerationBinding binding;
    private int gen;

    public static GenerationFragment newInstance(int gen) {
        GenerationFragment fragment = new GenerationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GEN, gen);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGenerationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gen = getArguments().getInt(ARG_GEN);

        PokemonRepository repository = new PokemonRepository(false);

        binding.recyclerGeneration.setLayoutManager(new GridLayoutManager(getContext(), 2));

        PokemonsAdapter adapter = new PokemonsAdapter(
                repository.getPokemonsByGeneration(gen),
                pokemon -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("detalles", pokemon);

                    Navigation.findNavController(requireView())
                            .navigate(R.id.action_pokedexFragment_to_detallesFragment, bundle);
                }
        );

        binding.recyclerGeneration.setAdapter(adapter);
    }
}
