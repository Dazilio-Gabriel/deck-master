package br.faesa.deckmaster.persistence;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class RepositorioMemoria<T> implements Repositorio<T> {
    private final Map<Integer, T> dados = new LinkedHashMap<>();
    private final Function<T, Integer> extratorId;

    public RepositorioMemoria(Function<T, Integer> extratorId) {
        this.extratorId = extratorId;
    }

    @Override
    public int inserir(T objeto) {
        int id = extratorId.apply(objeto);
        dados.put(id, objeto);
        return id;
    }

    @Override
    public boolean alterar(T objeto) {
        int id = extratorId.apply(objeto);
        if (!dados.containsKey(id)) {
            return false;
        }
        dados.put(id, objeto);
        return true;
    }

    @Override
    public boolean excluir(int id) {
        return dados.remove(id) != null;
    }

    @Override
    public T buscarPorId(int id) {
        return dados.get(id);
    }

    @Override
    public List<T> listarTodos() {
        return new ArrayList<>(dados.values());
    }
}
