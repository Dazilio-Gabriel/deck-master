package br.faesa.deckmaster.model.carta;

public class Terreno extends Carta {
    private final String recursoGerado;
    private final int quantidadeRecurso;

    public Terreno(int idCarta, String nome, String textoRegra,
                   Raridade raridade, Expansao expansao,
                   String recursoGerado, int quantidadeRecurso, Elemento... elementos) {
        super(idCarta, nome, textoRegra, 0, raridade, expansao, elementos);
        this.recursoGerado = recursoGerado;
        this.quantidadeRecurso = quantidadeRecurso;
    }

    public String getRecursoGerado() { return recursoGerado; }
    public int produzirRecurso() { return quantidadeRecurso; }

    @Override
    public String descrever() {
        return String.format("%s [Terreno +%d %s]", nome, quantidadeRecurso, recursoGerado);
    }
}
