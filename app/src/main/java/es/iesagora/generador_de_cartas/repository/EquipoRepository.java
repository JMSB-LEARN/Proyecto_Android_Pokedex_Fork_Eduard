package es.iesagora.generador_de_cartas.repository;

import java.util.ArrayList;
import java.util.List;

import es.iesagora.generador_de_cartas.model.Pokemon;

public class EquipoRepository {

    private List<Pokemon> equipo;

    public EquipoRepository() {
        equipo = new ArrayList<>();
    }

    public List<Pokemon> getEquipo() {
        return equipo;
    }

    public Pokemon getPokemon(int position) {
        if (position >= 0 && position < equipo.size()) {
            return equipo.get(position);
        }
        return null;
    }

    public void anadirPokemon(Pokemon p) {
        equipo.add(p);
    }

    public void eliminarPokemon(Pokemon p) {
        equipo.remove(p);
    }

}
