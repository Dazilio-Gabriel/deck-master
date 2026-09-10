package br.faesa.deckmaster.model.colecao;

import br.faesa.deckmaster.model.carta.Carta;
import br.faesa.deckmaster.model.conta.Jogador;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Colecao {
    public static final int MAX_COPIAS = 4;

    private final Jogador jogador;
    private final Map<Integer, ItemColecao> itens = new LinkedHashMap<>();

    public Colecao(Jogador jogador) {
        this.jogador = jogador;
    }

    public Jogador getJogador() { return jogador; }

    public List<ItemColecao> listarItens() {
        return new ArrayList<>(itens.values());
    }

    public ItemColecao buscarItem(Carta carta) {
        return itens.get(carta.getIdCarta());
    }

    public int quantidadeDe(Carta carta) {
        ItemColecao item = buscarItem(carta);
        return item == null ? 0 : item.getQuantidade();
    }

    // devolve fragmentos quando passa do limite de copias
    public int creditarCarta(Carta carta, int quantidade) {
        ItemColecao item = itens.get(carta.getIdCarta());
        if (item == null) {
            item = new ItemColecao(carta, 0);
            itens.put(carta.getIdCarta(), item);
        }
        int excedente = 0;
        for (int i = 0; i < quantidade; i++) {
            if (item.getQuantidade() >= MAX_COPIAS) {
                excedente += carta.calcularValorFragmentos();
            } else {
                item.incrementar(1);
            }
        }
        if (excedente > 0) {
            jogador.creditarFragmentos(excedente);
        }
        return excedente;
    }

    public boolean debitarCarta(Carta carta, int quantidade) {
        ItemColecao item = itens.get(carta.getIdCarta());
        if (item == null) {
            return false;
        }
        boolean ok = item.decrementar(quantidade);
        if (ok && item.getQuantidade() == 0) {
            itens.remove(carta.getIdCarta());
        }
        return ok;
    }

    public int totalCartas() {
        int total = 0;
        for (ItemColecao item : itens.values()) {
            total += item.getQuantidade();
        }
        return total;
    }

    public int totalCartasDistintas() {
        return itens.size();
    }
}
