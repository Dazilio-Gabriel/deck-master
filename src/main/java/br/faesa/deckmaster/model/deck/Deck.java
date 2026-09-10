package br.faesa.deckmaster.model.deck;

import br.faesa.deckmaster.model.carta.Carta;
import br.faesa.deckmaster.model.conta.Jogador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Deck {
    private final int idDeck;
    private String nome;
    private final Jogador jogador;
    private final Formato formato;
    private final LocalDateTime dataCriacao;
    private LocalDateTime dataUltimaAlteracao;
    private boolean ativo = true;
    private int quantidadeTotalCartas;
    private final Map<Integer, ItemDeck> itens = new LinkedHashMap<>();

    public Deck(int idDeck, String nome, Jogador jogador, Formato formato) {
        this.idDeck = idDeck;
        this.nome = nome;
        this.jogador = jogador;
        this.formato = formato;
        this.dataCriacao = LocalDateTime.now();
        this.dataUltimaAlteracao = this.dataCriacao;
    }

    public int getIdDeck() { return idDeck; }
    public String getNome() { return nome; }
    public Jogador getJogador() { return jogador; }
    public Formato getFormato() { return formato; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataUltimaAlteracao() { return dataUltimaAlteracao; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public int getQuantidadeTotalCartas() { return quantidadeTotalCartas; }

    public List<ItemDeck> listarItens() {
        return new ArrayList<>(itens.values());
    }

    public int quantidadeDe(Carta carta) {
        ItemDeck item = itens.get(carta.getIdCarta());
        return item == null ? 0 : item.getQuantidade();
    }

    public boolean adicionarCarta(Carta carta, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }
        if (!formato.validarCopias(quantidadeDe(carta) + quantidade)) {
            return false;
        }
        if (quantidadeTotalCartas + quantidade > formato.getMaxCartas()) {
            return false;
        }
        ItemDeck item = itens.get(carta.getIdCarta());
        if (item == null) {
            itens.put(carta.getIdCarta(), new ItemDeck(carta, quantidade));
        } else {
            item.incrementar(quantidade);
        }
        quantidadeTotalCartas += quantidade;
        dataUltimaAlteracao = LocalDateTime.now();
        return true;
    }

    public boolean removerCarta(Carta carta, int quantidade) {
        ItemDeck item = itens.get(carta.getIdCarta());
        if (item == null || !item.decrementar(quantidade)) {
            return false;
        }
        if (item.getQuantidade() == 0) {
            itens.remove(carta.getIdCarta());
        }
        quantidadeTotalCartas -= quantidade;
        dataUltimaAlteracao = LocalDateTime.now();
        return true;
    }

    public void renomear(String nome) {
        this.nome = nome;
        this.dataUltimaAlteracao = LocalDateTime.now();
    }

    // expande o deck em cartas individuais para embaralhar na partida
    public List<Carta> expandir() {
        List<Carta> cartas = new ArrayList<>();
        for (ItemDeck item : itens.values()) {
            for (int i = 0; i < item.getQuantidade(); i++) {
                cartas.add(item.getCarta());
            }
        }
        return cartas;
    }

    @Override
    public String toString() {
        return nome + " (" + quantidadeTotalCartas + " cartas / " + formato.getNome() + ")";
    }
}
