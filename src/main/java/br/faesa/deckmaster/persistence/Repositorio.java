package br.faesa.deckmaster.persistence;

import java.util.List;

public interface Repositorio<T> {
    int inserir(T objeto);
    boolean alterar(T objeto);
    boolean excluir(int id);
    T buscarPorId(int id);
    List<T> listarTodos();
}
