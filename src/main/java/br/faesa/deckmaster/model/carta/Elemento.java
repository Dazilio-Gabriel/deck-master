package br.faesa.deckmaster.model.carta;

public enum Elemento {
    FOGO("Fogo"), AGUA("Agua"), TERRA("Terra"), AR("Ar"), SOMBRA("Sombra");

    private final String nome;

    Elemento(String nome) { this.nome = nome; }

    public String getNome() { return nome; }
}
