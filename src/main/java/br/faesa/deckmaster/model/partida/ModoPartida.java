package br.faesa.deckmaster.model.partida;

public enum ModoPartida {
    CASUAL("Casual"), RANQUEADA("Ranqueada");

    private final String nome;

    ModoPartida(String nome) { this.nome = nome; }

    public String getNome() { return nome; }
}
