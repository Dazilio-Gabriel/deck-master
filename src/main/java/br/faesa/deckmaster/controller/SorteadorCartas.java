package br.faesa.deckmaster.controller;

import br.faesa.deckmaster.model.carta.Carta;
import br.faesa.deckmaster.model.carta.Raridade;
import br.faesa.deckmaster.model.colecao.Pacote;
import br.faesa.deckmaster.persistence.CatalogoCartas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SorteadorCartas {
    private final CatalogoCartas catalogo;
    private final Random random;

    public SorteadorCartas(CatalogoCartas catalogo, Random random) {
        this.catalogo = catalogo;
        this.random = random;
    }

    public List<Carta> sortear(Pacote pacote) {
        List<Carta> sorteadas = new ArrayList<>();
        boolean garantiuRara = false;
        for (int i = 0; i < pacote.getQuantidadeCartas(); i++) {
            boolean ultima = i == pacote.getQuantidadeCartas() - 1;
            Raridade raridade = (ultima && !garantiuRara) ? sortearRaridadeMinimaRara() : sortearRaridade();
            if (raridade.ordinal() >= Raridade.RARA.ordinal()) {
                garantiuRara = true;
            }
            Carta carta = sortearPorRaridade(raridade);
            if (carta != null) {
                sorteadas.add(carta);
            }
        }
        return sorteadas;
    }

    public Carta sortearPorRaridade(Raridade raridade) {
        List<Carta> candidatas = catalogo.listarPorRaridade(raridade);
        if (candidatas.isEmpty()) {
            candidatas = catalogo.listarPorRaridade(Raridade.COMUM);
        }
        if (candidatas.isEmpty()) {
            return null;
        }
        return candidatas.get(random.nextInt(candidatas.size()));
    }

    private Raridade sortearRaridade() {
        double sorteio = random.nextDouble();
        double acumulado = 0.0;
        for (Raridade r : Raridade.values()) {
            acumulado += r.getProbabilidadeSorteio();
            if (sorteio <= acumulado) {
                return r;
            }
        }
        return Raridade.COMUM;
    }

    private Raridade sortearRaridadeMinimaRara() {
        double sorteio = random.nextDouble();
        if (sorteio < 0.70) {
            return Raridade.RARA;
        }
        if (sorteio < 0.95) {
            return Raridade.EPICA;
        }
        return Raridade.LENDARIA;
    }
}
