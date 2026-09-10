package br.faesa.deckmaster.model.carta;

public class Criatura extends Carta {
    private final int ataque;
    private final int defesa;
    private final String tipoCriatura;

    public Criatura(int idCarta, String nome, String textoRegra, int custoMana,
                    Raridade raridade, Expansao expansao, int ataque, int defesa,
                    String tipoCriatura, Elemento... elementos) {
        super(idCarta, nome, textoRegra, custoMana, raridade, expansao, elementos);
        this.ataque = ataque;
        this.defesa = defesa;
        this.tipoCriatura = tipoCriatura;
    }

    public int getAtaque() { return ataque; }
    public int getDefesa() { return defesa; }
    public String getTipoCriatura() { return tipoCriatura; }

    public int calcularDanoContra(Criatura alvo) {
        if (alvo == null) {
            return ataque;
        }
        return Math.max(0, ataque - alvo.getDefesa());
    }

    @Override
    public String descrever() {
        return String.format("%s [Criatura %s %d/%d - custo %d - %s]",
                nome, tipoCriatura, ataque, defesa, custoMana, raridade.getNome());
    }
}
