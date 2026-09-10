package br.faesa.deckmaster.model.carta;

public enum Raridade {
    COMUM("Comum", 0.60, 5),
    INCOMUM("Incomum", 0.25, 20),
    RARA("Rara", 0.10, 100),
    EPICA("Epica", 0.04, 400),
    LENDARIA("Lendaria", 0.01, 1600);

    private final String nome;
    private final double probabilidadeSorteio;
    private final int valorFragmentos;

    Raridade(String nome, double probabilidadeSorteio, int valorFragmentos) {
        this.nome = nome;
        this.probabilidadeSorteio = probabilidadeSorteio;
        this.valorFragmentos = valorFragmentos;
    }

    public String getNome() { return nome; }
    public double getProbabilidadeSorteio() { return probabilidadeSorteio; }
    public int getValorFragmentos() { return valorFragmentos; }
}
