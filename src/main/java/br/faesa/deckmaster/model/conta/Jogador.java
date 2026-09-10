package br.faesa.deckmaster.model.conta;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Jogador {
    private final int idJogador;
    private final String apelido;
    private final String nomeCompleto;
    private final String email;
    private final LocalDate dataNascimento;
    private final LocalDateTime dataCadastro;
    private int saldoCristais;
    private int saldoFragmentos;
    private int nivel = 1;
    private int experiencia;
    private int totalPartidas;
    private int totalVitorias;

    public Jogador(int idJogador, String apelido, String nomeCompleto, String email,
                   LocalDate dataNascimento, int cristaisIniciais) {
        this.idJogador = idJogador;
        this.apelido = apelido;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.dataCadastro = LocalDateTime.now();
        this.saldoCristais = cristaisIniciais;
    }

    public int getIdJogador() { return idJogador; }
    public String getApelido() { return apelido; }
    public String getNomeCompleto() { return nomeCompleto; }
    public String getEmail() { return email; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public int getSaldoCristais() { return saldoCristais; }
    public int getSaldoFragmentos() { return saldoFragmentos; }
    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }
    public int getTotalPartidas() { return totalPartidas; }
    public int getTotalVitorias() { return totalVitorias; }

    public void creditarCristais(int quantidade) {
        if (quantidade > 0) {
            saldoCristais += quantidade;
        }
    }

    public boolean debitarCristais(int quantidade) {
        if (quantidade <= 0 || saldoCristais < quantidade) {
            return false;
        }
        saldoCristais -= quantidade;
        return true;
    }

    public void creditarFragmentos(int quantidade) {
        if (quantidade > 0) {
            saldoFragmentos += quantidade;
        }
    }

    public boolean debitarFragmentos(int quantidade) {
        if (quantidade <= 0 || saldoFragmentos < quantidade) {
            return false;
        }
        saldoFragmentos -= quantidade;
        return true;
    }

    public void registrarResultado(boolean venceu) {
        totalPartidas++;
        if (venceu) {
            totalVitorias++;
        }
    }

    public double calcularPercentualVitorias() {
        if (totalPartidas == 0) {
            return 0.0;
        }
        return (totalVitorias * 100.0) / totalPartidas;
    }

    public void ganharExperiencia(int pontos) {
        experiencia += pontos;
        while (experiencia >= nivel * 100) {
            experiencia -= nivel * 100;
            nivel++;
        }
    }

    @Override
    public String toString() { return apelido; }
}
