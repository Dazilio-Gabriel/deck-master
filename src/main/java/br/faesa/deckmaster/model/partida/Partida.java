package br.faesa.deckmaster.model.partida;

import br.faesa.deckmaster.model.conta.Jogador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Partida {
    private final int idPartida;
    private final ModoPartida modo;
    private StatusPartida status = StatusPartida.AGUARDANDO_OPONENTE;
    private final LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private LocalDateTime dataHoraUltimaJogada;
    private int numeroTurnos;
    private String motivoEncerramento;
    private Jogador vencedor;
    private final LadoPartida ladoA;
    private final LadoPartida ladoB;
    private final List<Turno> turnos = new ArrayList<>();
    private final List<Participacao> participacoes = new ArrayList<>();

    public Partida(int idPartida, ModoPartida modo, LadoPartida ladoA, LadoPartida ladoB) {
        this.idPartida = idPartida;
        this.modo = modo;
        this.ladoA = ladoA;
        this.ladoB = ladoB;
        this.dataHoraInicio = LocalDateTime.now();
        this.participacoes.add(new Participacao(ladoA.getJogador(), ladoA.getDeck()));
        this.participacoes.add(new Participacao(ladoB.getJogador(), ladoB.getDeck()));
    }

    public int getIdPartida() { return idPartida; }
    public ModoPartida getModo() { return modo; }
    public StatusPartida getStatus() { return status; }
    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public LocalDateTime getDataHoraUltimaJogada() { return dataHoraUltimaJogada; }
    public int getNumeroTurnos() { return numeroTurnos; }
    public String getMotivoEncerramento() { return motivoEncerramento; }
    public Jogador getVencedor() { return vencedor; }
    public LadoPartida getLadoA() { return ladoA; }
    public LadoPartida getLadoB() { return ladoB; }
    public List<Turno> getTurnos() { return turnos; }
    public List<Participacao> getParticipacoes() { return participacoes; }

    public boolean ehRanqueada() {
        return modo == ModoPartida.RANQUEADA;
    }

    public LadoPartida oponenteDe(LadoPartida lado) {
        return lado == ladoA ? ladoB : ladoA;
    }

    public void iniciar() {
        if (status != StatusPartida.AGUARDANDO_OPONENTE) {
            return;
        }
        ladoA.distribuirMaoInicial();
        ladoB.distribuirMaoInicial();
        status = StatusPartida.EM_ANDAMENTO;
    }

    public boolean pausar() {
        if (status != StatusPartida.EM_ANDAMENTO) {
            return false;
        }
        status = StatusPartida.PAUSADA;
        return true;
    }

    public boolean retomar() {
        if (status != StatusPartida.PAUSADA) {
            return false;
        }
        status = StatusPartida.EM_ANDAMENTO;
        return true;
    }

    public void cancelar() {
        if (status.isFinalizador()) {
            return;
        }
        status = StatusPartida.CANCELADA;
        dataHoraFim = LocalDateTime.now();
    }

    public Turno iniciarNovoTurno(LadoPartida lado, int numeroTurnoDoLado) {
        numeroTurnos++;
        Turno turno = new Turno(numeroTurnos, lado.getJogador(), lado.calcularRecursosDoTurno(numeroTurnoDoLado));
        turnos.add(turno);
        dataHoraUltimaJogada = LocalDateTime.now();
        return turno;
    }

    public Turno obterTurnoCorrente() {
        return turnos.isEmpty() ? null : turnos.get(turnos.size() - 1);
    }

    public void marcarJogada() {
        dataHoraUltimaJogada = LocalDateTime.now();
    }

    public void encerrar(Jogador vencedor, String motivo) {
        if (status.isFinalizador()) {
            return;
        }
        this.vencedor = vencedor;
        this.motivoEncerramento = motivo;
        this.status = StatusPartida.ENCERRADA;
        this.dataHoraFim = LocalDateTime.now();
    }

    public Participacao participacaoDe(Jogador jogador) {
        for (Participacao p : participacoes) {
            if (p.getJogador() == jogador) {
                return p;
            }
        }
        return null;
    }
}
