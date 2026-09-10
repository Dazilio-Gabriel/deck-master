package br.faesa.deckmaster.model.partida;

import br.faesa.deckmaster.model.conta.Jogador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Turno {
    private final int numero;
    private final Jogador jogadorAtivo;
    private final LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private int recursosDisponiveis;
    private final List<Jogada> jogadas = new ArrayList<>();

    public Turno(int numero, Jogador jogadorAtivo, int recursosDisponiveis) {
        this.numero = numero;
        this.jogadorAtivo = jogadorAtivo;
        this.recursosDisponiveis = recursosDisponiveis;
        this.dataHoraInicio = LocalDateTime.now();
    }

    public int getNumero() { return numero; }
    public Jogador getJogadorAtivo() { return jogadorAtivo; }
    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public int getRecursosDisponiveis() { return recursosDisponiveis; }
    public List<Jogada> getJogadas() { return Collections.unmodifiableList(jogadas); }

    public boolean consumirRecursos(int custo) {
        if (custo > recursosDisponiveis) {
            return false;
        }
        recursosDisponiveis -= custo;
        return true;
    }

    public void adicionarRecursos(int quantidade) {
        recursosDisponiveis += quantidade;
    }

    public void registrarJogada(Jogada jogada) {
        jogadas.add(jogada);
    }

    public void encerrar() {
        this.dataHoraFim = LocalDateTime.now();
    }
}
