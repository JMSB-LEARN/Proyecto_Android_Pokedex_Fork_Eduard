package es.iesagora.generador_de_cartas;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.iesagora.generador_de_cartas.adapter.PokemonsAdapter;
import es.iesagora.generador_de_cartas.databinding.FragmentEquipoBinding;
import es.iesagora.generador_de_cartas.model.Pokemon;
import es.iesagora.generador_de_cartas.repository.EquipoRepository;

import java.util.List;

public class equipoFragment extends Fragment {

    private FragmentEquipoBinding binding;
    private EquipoRepository repository;
    private PokemonsAdapter adapter;
    private List<Pokemon> listaEquipo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEquipoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = ((MainActivity) requireActivity()).equipoRepository;
        listaEquipo = repository.getEquipo();


        adapter = new PokemonsAdapter(listaEquipo, pokemon -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("detalles", pokemon);

            Navigation.findNavController(requireView())
                    .navigate(R.id.action_equipoFragment_to_detallesFragment2, bundle);
        });


        binding.rvEquipo.setAdapter(adapter);
        binding.rvEquipo.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        actualizarVistas();

        activarSwipe();
    }

    private void actualizarVistas() {
        if (listaEquipo.isEmpty()) {
            binding.tvVacio.setVisibility(View.VISIBLE);
            binding.rvEquipo.setVisibility(View.GONE);
        } else {
            binding.tvVacio.setVisibility(View.GONE);
            binding.rvEquipo.setVisibility(View.VISIBLE);
        }
    }

    private void activarSwipe() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getBindingAdapterPosition();

                Pokemon p = repository.getPokemon(pos);

                repository.eliminarPokemon(p);

                adapter.notifyItemRemoved(pos);
                actualizarVistas();
            }
        };

        new ItemTouchHelper(callback).attachToRecyclerView(binding.rvEquipo);
    }
}
