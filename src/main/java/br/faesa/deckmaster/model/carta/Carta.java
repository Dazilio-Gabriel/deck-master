package br.faesa.deckmaster.model.carta;

import java.util.ArrayList;
import java.util.List;

public abstract class Carta {
    protected final int idCarta;
    protected final String nome;
    protected final String textoRegra;
    protected final int custoMana;
    protected final Raridade raridade;
    protected final Expansao expansao;
    protected final List<Elemento> elementos = new ArrayList<>();
    protected boolean ativa = true;

    protected Carta(int idCarta, String nome, String textoRegra, int custoMana,
                    Raridade raridade, Expansao expansao, Elemento... elementos) {
        this.idCarta = idCarta;
        this.nome = nome;
        this.textoRegra = textoRegra;
        this.custoMana = custoMana;
        this.raridade = raridade;
        this.expansao = expansao;
        for (Elemento e : elementos) {
            this.elementos.add(e);
        }
        if (expansao != null) {
            expansao.incrementarTotalCartas();
        }
    }

    public int getIdCarta() { return idCarta; }
    public String getNome() { return nome; }
    public String getTextoRegra() { return textoRegra; }
    public int getCustoMana() { return custoMana; }
    public Raridade getRaridade() { return raridade; }
    public Expansao getExpansao() { return expansao; }
    public List<Elemento> getElementos() { return elementos; }
    public boolean isAtiva() { return ativa; }
    public void setAtiva(boolean ativa) { this.ativa = ativa; }

    public int calcularValorFragmentos() {
        return raridade.getValorFragmentos();
    }

    public boolean ehJogavel(int recursosDisponiveis) {
        return ativa && recursosDisponiveis >= custoMana;
    }

    public abstract String descrever();

    @Override
    public String toString() {
        return nome + " (" + custoMana + ")";
    }
}
