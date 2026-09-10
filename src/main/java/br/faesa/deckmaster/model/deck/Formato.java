package br.faesa.deckmaster.model.deck;

public enum Formato {
    PADRAO("Padrao", 60, 250, 3),
    RAPIDO("Rapido", 20, 40, 2),
    LIVRE("Livre", 1, 500, 20);

    private final String nome;
    private final int minCartas;
    private final int maxCartas;
    private final int maxCopiasPorCarta;

    Formato(String nome, int minCartas, int maxCartas, int maxCopiasPorCarta) {
        this.nome = nome;
        this.minCartas = minCartas;
        this.maxCartas = maxCartas;
        this.maxCopiasPorCarta = maxCopiasPorCarta;
    }

    public String getNome() { return nome; }
    public int getMinCartas() { return minCartas; }
    public int getMaxCartas() { return maxCartas; }
    public int getMaxCopiasPorCarta() { return maxCopiasPorCarta; }

    public boolean validarQuantidade(int total) {
        return total >= minCartas && total <= maxCartas;
    }

    public boolean validarCopias(int quantidade) {
        return quantidade <= maxCopiasPorCarta;
    }
}
