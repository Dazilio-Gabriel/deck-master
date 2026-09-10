package br.faesa.deckmaster.controller;

import br.faesa.deckmaster.model.carta.Carta;
import br.faesa.deckmaster.model.conta.Jogador;
import br.faesa.deckmaster.model.deck.Deck;
import br.faesa.deckmaster.model.partida.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class CtrlRealizarPartida {
    public static final int LIMITE_TURNOS = 60;

    private final MotorRegrasPartida motor;
    private final Random random;
    private int proximoId = 1;

    public CtrlRealizarPartida(MotorRegrasPartida motor, Random random) {
        this.motor = motor;
        this.random = random;
    }

    public Partida iniciar(Jogador jogadorA, Deck deckA, Jogador jogadorB, Deck deckB, ModoPartida modo) {
        LadoPartida ladoA = new LadoPartida(jogadorA, deckA, random);
        LadoPartida ladoB = new LadoPartida(jogadorB, deckB, random);
        Partida partida = new Partida(proximoId++, modo, ladoA, ladoB);
        partida.iniciar();
        return partida;
    }

    public void executarAutomatico(Partida partida, Consumer<String> log) {
        LadoPartida atual = random.nextBoolean() ? partida.getLadoA() : partida.getLadoB();
        log.accept("Comeca: " + atual.getJogador().getApelido());

        int turnoDoLadoA = 0;
        int turnoDoLadoB = 0;

        while (partida.getStatus() == StatusPartida.EM_ANDAMENTO) {
            int numeroDoLado = (atual == partida.getLadoA()) ? ++turnoDoLadoA : ++turnoDoLadoB;
            Turno turno = partida.iniciarNovoTurno(atual, numeroDoLado);

            Carta comprada = atual.comprarCarta();
            if (comprada == null && atual.monteVazio()) {
                encerrar(partida, partida.oponenteDe(atual), "monte esgotado", log);
                return;
            }
            log.accept(String.format("-- Turno %d | %s | vida %d x %d | recursos %d",
                    turno.getNumero(), atual.getJogador().getApelido(),
                    atual.getPontosVida(), partida.oponenteDe(atual).getPontosVida(),
                    turno.getRecursosDisponiveis()));

            jogarCartasDaMao(partida, atual, turno, log);

            if (numeroDoLado > 1) {
                Jogada ataque = motor.resolverCombate(partida, atual, turno, turno.getJogadas().size() + 1);
                if (ataque != null && ataque.getDanoCausado() > 0) {
                    log.accept("   " + atual.getJogador().getApelido() + " ataca: " + ataque.descrever());
                }
            }

            turno.encerrar();

            if (motor.verificarFimDePartida(partida)) {
                LadoPartida vencedor = partida.getLadoA().estaDerrotado() ? partida.getLadoB() : partida.getLadoA();
                encerrar(partida, vencedor, "pontos de vida zerados", log);
                return;
            }
            if (partida.getNumeroTurnos() >= LIMITE_TURNOS) {
                LadoPartida vencedor = partida.getLadoA().getPontosVida() >= partida.getLadoB().getPontosVida()
                        ? partida.getLadoA() : partida.getLadoB();
                encerrar(partida, vencedor, "limite de turnos atingido", log);
                return;
            }
            atual = partida.oponenteDe(atual);
        }
    }

    // ia simples: joga terreno, depois a carta mais cara que couber
    private void jogarCartasDaMao(Partida partida, LadoPartida lado, Turno turno, Consumer<String> log) {
        boolean jogou = true;
        int sequencia = 1;
        while (jogou) {
            jogou = false;
            List<Carta> mao = new ArrayList<>(lado.getMao());
            Carta escolhida = null;
            for (Carta c : mao) {
                if (!c.ehJogavel(turno.getRecursosDisponiveis())) {
                    continue;
                }
                if (escolhida == null || c.getCustoMana() > escolhida.getCustoMana()) {
                    escolhida = c;
                }
            }
            if (escolhida != null) {
                Jogada jogada = motor.aplicarCarta(partida, lado, turno, escolhida, sequencia++);
                if (jogada != null) {
                    log.accept("   " + jogada.descrever());
                    jogou = true;
                }
            }
        }
    }

    public void renderSe(Partida partida, LadoPartida lado, Consumer<String> log) {
        lado.render();
        encerrar(partida, partida.oponenteDe(lado), "rendicao", log);
    }

    private void encerrar(Partida partida, LadoPartida vencedor, String motivo, Consumer<String> log) {
        LadoPartida perdedor = partida.oponenteDe(vencedor);
        partida.encerrar(vencedor.getJogador(), motivo);

        int pontosVencedor = partida.ehRanqueada() ? 25 : 0;
        int pontosPerdedor = partida.ehRanqueada() ? -15 : 0;

        partida.participacaoDe(vencedor.getJogador())
                .registrarResultado(true, vencedor.getPontosVida(), pontosVencedor, false);
        partida.participacaoDe(perdedor.getJogador())
                .registrarResultado(false, perdedor.getPontosVida(), pontosPerdedor, perdedor.isRendeuSe());

        vencedor.getJogador().registrarResultado(true);
        perdedor.getJogador().registrarResultado(false);
        vencedor.getJogador().ganharExperiencia(50);
        perdedor.getJogador().ganharExperiencia(20);
        vencedor.getJogador().creditarCristais(60);
        perdedor.getJogador().creditarCristais(20);

        log.accept(String.format("FIM: %s vence por %s (%d x %d) em %d turnos",
                vencedor.getJogador().getApelido(), motivo,
                vencedor.getPontosVida(), perdedor.getPontosVida(), partida.getNumeroTurnos()));
    }
}
