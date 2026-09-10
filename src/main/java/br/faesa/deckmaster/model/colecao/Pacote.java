package br.faesa.deckmaster.model.colecao;

import br.faesa.deckmaster.model.carta.Expansao;

public class Pacote {
    private final int idPacote;
    private final String nome;
    private final int precoCristais;
    private final int quantidadeCartas;
    private final Expansao expansao;
    private boolean ativo = true;

    public Pacote(int idPacote, String nome, int precoCristais, int quantidadeCartas, Expansao expansao) {
        this.idPacote = idPacote;
        this.nome = nome;
        this.precoCristais = precoCristais;
        this.quantidadeCartas = quantidadeCartas;
        this.expansao = expansao;
    }

    public int getIdPacote() { return idPacote; }
    public String getNome() { return nome; }
    public int getPrecoCristais() { return precoCristais; }
    public int getQuantidadeCartas() { return quantidadeCartas; }
    public Expansao getExpansao() { return expansao; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
