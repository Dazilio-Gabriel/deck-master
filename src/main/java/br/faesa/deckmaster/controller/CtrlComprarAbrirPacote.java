package br.faesa.deckmaster.controller;

import br.faesa.deckmaster.model.carta.Carta;
import br.faesa.deckmaster.model.colecao.Colecao;
import br.faesa.deckmaster.model.colecao.Pacote;
import br.faesa.deckmaster.model.conta.Jogador;

import java.util.ArrayList;
import java.util.List;

public class CtrlComprarAbrirPacote {
    private final SorteadorCartas sorteador;

    public CtrlComprarAbrirPacote(SorteadorCartas sorteador) {
        this.sorteador = sorteador;
    }

    public boolean comprar(Jogador jogador, Pacote pacote, int quantidade) {
        if (!pacote.isAtivo() || quantidade <= 0) {
            return false;
        }
        int custoTotal = pacote.getPrecoCristais() * quantidade;
        return jogador.debitarCristais(custoTotal);
    }

    public List<Carta> abrir(Colecao colecao, Pacote pacote) {
        List<Carta> obtidas = sorteador.sortear(pacote);
        for (Carta carta : obtidas) {
            colecao.creditarCarta(carta, 1);
        }
        return obtidas;
    }

    public List<Carta> comprarEAbrir(Jogador jogador, Colecao colecao, Pacote pacote, int quantidade) {
        List<Carta> todas = new ArrayList<>();
        if (!comprar(jogador, pacote, quantidade)) {
            return todas;
        }
        for (int i = 0; i < quantidade; i++) {
            todas.addAll(abrir(colecao, pacote));
        }
        return todas;
    }
}
