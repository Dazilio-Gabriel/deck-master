package br.faesa.deckmaster.model.carta;

import java.time.LocalDate;

public class Expansao {
    private final int idExpansao;
    private final String nome;
    private final String sigla;
    private final LocalDate dataLancamento;
    private int totalCartas;

    public Expansao(int idExpansao, String nome, String sigla, LocalDate dataLancamento) {
        this.idExpansao = idExpansao;
        this.nome = nome;
        this.sigla = sigla;
        this.dataLancamento = dataLancamento;
        this.totalCartas = 0;
    }

    public int getIdExpansao() { return idExpansao; }
    public String getNome() { return nome; }
    public String getSigla() { return sigla; }
    public LocalDate getDataLancamento() { return dataLancamento; }
    public int getTotalCartas() { return totalCartas; }
    public void incrementarTotalCartas() { this.totalCartas++; }
}
