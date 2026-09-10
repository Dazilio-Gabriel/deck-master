package br.faesa.deckmaster.model.deck;

import java.util.ArrayList;
import java.util.List;

public class ResultadoValidacao {
    private final List<String> erros = new ArrayList<>();
    private final List<String> avisos = new ArrayList<>();

    public void adicionarErro(String mensagem) { erros.add(mensagem); }
    public void adicionarAviso(String mensagem) { avisos.add(mensagem); }

    public List<String> getErros() { return erros; }
    public List<String> getAvisos() { return avisos; }

    public boolean isValido() { return erros.isEmpty(); }

    @Override
    public String toString() {
        if (isValido() && avisos.isEmpty()) {
            return "Deck valido.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(isValido() ? "Deck valido com avisos:" : "Deck invalido:");
        for (String e : erros) {
            sb.append("\n  [erro] ").append(e);
        }
        for (String a : avisos) {
            sb.append("\n  [aviso] ").append(a);
        }
        return sb.toString();
    }
}
