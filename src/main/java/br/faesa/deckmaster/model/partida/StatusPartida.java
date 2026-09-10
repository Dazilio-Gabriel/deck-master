package br.faesa.deckmaster.model.partida;

public enum StatusPartida {
    AGUARDANDO_OPONENTE("Aguardando Oponente", false),
    EM_ANDAMENTO("Em Andamento", false),
    PAUSADA("Pausada", false),
    ENCERRADA("Encerrada", true),
    CANCELADA("Cancelada", true);

    private final String nome;
    private final boolean finalizador;

    StatusPartida(String nome, boolean finalizador) {
        this.nome = nome;
        this.finalizador = finalizador;
    }

    public String getNome() { return nome; }
    public boolean isFinalizador() { return finalizador; }
}
