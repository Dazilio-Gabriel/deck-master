package br.faesa.deckmaster.model.colecao;

import br.faesa.deckmaster.model.carta.Carta;
import java.time.LocalDateTime;

public class ItemColecao {
    private final Carta carta;
    private int quantidade;
    private final LocalDateTime dataPrimeiraObtencao;
    private boolean favorita;

    public ItemColecao(Carta carta, int quantidade) {
        this.carta = carta;
        this.quantidade = quantidade;
        this.dataPrimeiraObtencao = LocalDateTime.now();
    }

    public Carta getCarta() { return carta; }
    public int getQuantidade() { return quantidade; }
    public LocalDateTime getDataPrimeiraObtencao() { return dataPrimeiraObtencao; }
    public boolean isFavorita() { return favorita; }
    public void setFavorita(boolean favorita) { this.favorita = favorita; }

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
