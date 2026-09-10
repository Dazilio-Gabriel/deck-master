package br.faesa.deckmaster.model.partida;

import br.faesa.deckmaster.model.carta.Carta;
import java.time.LocalDateTime;

public class Jogada {
    private final int sequencia;
    private final TipoJogada tipo;
    private final Carta carta;
    private final String alvo;
    private final LocalDateTime dataHora;
    private int danoCausado;

    public Jogada(int sequencia, TipoJogada tipo, Carta carta, String alvo) {
        this.sequencia = sequencia;
        this.tipo = tipo;
        this.carta = carta;
        this.alvo = alvo;
        this.dataHora = LocalDateTime.now();
    }

    public int getSequencia() { return sequencia; }
    public TipoJogada getTipo() { return tipo; }
    public Carta getCarta() { return carta; }
    public String getAlvo() { return alvo; }
    public LocalDateTime getDataHora() { return dataHora; }
    public int getDanoCausado() { return danoCausado; }
    public void setDanoCausado(int danoCausado) { this.danoCausado = danoCausado; }

    public String descrever() {
        StringBuilder sb = new StringBuilder(tipo.name());
        if (carta != null) {
            sb.append(" ").append(carta.getNome());
        }
        if (alvo != null) {
            sb.append(" -> ").append(alvo);
        }
        if (danoCausado > 0) {
            sb.append(" (").append(danoCausado).append(" de dano)");
        }
        return sb.toString();
    }
}
