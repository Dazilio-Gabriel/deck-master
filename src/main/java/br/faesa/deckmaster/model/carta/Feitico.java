package br.faesa.deckmaster.model.carta;

public class Feitico extends Carta {
    private final String efeito;
    private final boolean instantaneo;
    private final int valorEfeito;

    public Feitico(int idCarta, String nome, String textoRegra, int custoMana,
                   Raridade raridade, Expansao expansao, String efeito,
                   boolean instantaneo, int valorEfeito, Elemento... elementos) {
        super(idCarta, nome, textoRegra, custoMana, raridade, expansao, elementos);
        this.efeito = efeito;
        this.instantaneo = instantaneo;
        this.valorEfeito = valorEfeito;
    }

    public String getEfeito() { return efeito; }
    public boolean isInstantaneo() { return instantaneo; }
    public int getValorEfeito() { return valorEfeito; }

    @Override
    public String descrever() {
        return String.format("%s [Feitico %s %d - custo %d - %s]",
                nome, efeito, valorEfeito, custoMana, raridade.getNome());
    }
}
