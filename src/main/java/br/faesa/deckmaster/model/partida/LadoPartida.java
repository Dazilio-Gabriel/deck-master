package br.faesa.deckmaster.model.partida;

import br.faesa.deckmaster.model.carta.Carta;
import br.faesa.deckmaster.model.carta.Criatura;
import br.faesa.deckmaster.model.carta.Terreno;
import br.faesa.deckmaster.model.conta.Jogador;
import br.faesa.deckmaster.model.deck.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LadoPartida {
    public static final int VIDA_INICIAL = 20;
    public static final int MAO_INICIAL = 7;

    private final Jogador jogador;
    private final Deck deck;
    private int pontosVida = VIDA_INICIAL;
    private final List<Carta> monte = new ArrayList<>();
    private final List<Carta> mao = new ArrayList<>();
    private final List<Criatura> campo = new ArrayList<>();
    private final List<Terreno> terrenos = new ArrayList<>();
    private boolean rendeuSe;

    public LadoPartida(Jogador jogador, Deck deck, Random random) {
        this.jogador = jogador;
        this.deck = deck;
        this.monte.addAll(deck.expandir());
        Collections.shuffle(this.monte, random);
    }

    public Jogador getJogador() { return jogador; }
    public Deck getDeck() { return deck; }
    public int getPontosVida() { return pontosVida; }
    public List<Carta> getMao() { return Collections.unmodifiableList(mao); }
    public List<Criatura> getCampo() { return Collections.unmodifiableList(campo); }
    public List<Terreno> getTerrenos() { return Collections.unmodifiableList(terrenos); }
    public boolean isRendeuSe() { return rendeuSe; }
    public void render() { this.rendeuSe = true; }

    public void distribuirMaoInicial() {
        for (int i = 0; i < MAO_INICIAL; i++) {
            comprarCarta();
        }
    }

    public Carta comprarCarta() {
        if (monte.isEmpty()) {
            return null;
        }
        Carta carta = monte.remove(0);
        mao.add(carta);
        return carta;
    }

    public boolean monteVazio() { return monte.isEmpty(); }

    public int getTamanhoMonte() { return monte.size(); }

    public void receberDano(int dano) {
        pontosVida -= dano;
        if (pontosVida < 0) {
            pontosVida = 0;
        }
    }

    public void curar(int pontos) {
        pontosVida += pontos;
    }

    public void invocar(Criatura criatura) {
        mao.remove(criatura);
        campo.add(criatura);
    }

    public void posicionar(Terreno terreno) {
        mao.remove(terreno);
        terrenos.add(terreno);
    }

    public void descartar(Carta carta) {
        mao.remove(carta);
    }

    // remove uma copia por entrada: a mesma carta pode estar varias vezes em campo
    public void removerDoCampo(List<Criatura> criaturas) {
        for (Criatura criatura : criaturas) {
            campo.remove(criatura);
        }
    }

    public int calcularRecursosDoTurno(int numeroTurnoDoLado) {
        int base = Math.min(numeroTurnoDoLado, 10);
        int extra = 0;
        for (Terreno t : terrenos) {
            extra += t.produzirRecurso();
        }
        return base + extra;
    }

    public int calcularPoderDeAtaque() {
        int total = 0;
        for (Criatura c : campo) {
            total += c.getAtaque();
        }
        return total;
    }

    public boolean estaDerrotado() {
        return pontosVida <= 0 || rendeuSe;
    }
}
