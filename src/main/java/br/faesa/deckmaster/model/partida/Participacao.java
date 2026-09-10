package br.faesa.deckmaster.model.partida;

import br.faesa.deckmaster.model.conta.Jogador;
import br.faesa.deckmaster.model.deck.Deck;

public class Participacao {
    private final Jogador jogador;
    private final Deck deck;
    private int pontosVidaFinais;
    private int pontosGanhos;
    private boolean venceu;
    private boolean rendeuSe;

    public Participacao(Jogador jogador, Deck deck) {
        this.jogador = jogador;
        this.deck = deck;
    }

    public Jogador getJogador() { return jogador; }
    public Deck getDeck() { return deck; }
    public int getPontosVidaFinais() { return pontosVidaFinais; }
    public int getPontosGanhos() { return pontosGanhos; }
    public boolean isVenceu() { return venceu; }
    public boolean isRendeuSe() { return rendeuSe; }

    public void registrarResultado(boolean venceu, int pontosVidaFinais, int pontosGanhos, boolean rendeuSe) {
        this.venceu = venceu;
        this.pontosVidaFinais = pontosVidaFinais;
        this.pontosGanhos = pontosGanhos;
        this.rendeuSe = rendeuSe;
    }
}
