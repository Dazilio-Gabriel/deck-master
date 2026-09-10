package br.faesa.deckmaster.model.deck;

import br.faesa.deckmaster.model.carta.Carta;

public class ItemDeck {
    private final Carta carta;
    private int quantidade;

    public ItemDeck(Carta carta, int quantidade) {
        this.carta = carta;
        this.quantidade = quantidade;
    }

    public Carta getCarta() { return carta; }
    public int getQuantidade() { return quantidade; }

    public void incrementar(int quantidade) {
        if (quantidade > 0) {
            this.quantidade += quantidade;
        }
    }

    public boolean decrementar(int quantidade) {
        if (quantidade <= 0 || this.quantidade < quantidade) {
            return false;
        }
        this.quantidade -= quantidade;
        return true;
    }
}
