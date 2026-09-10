package br.faesa.deckmaster.controller;

import br.faesa.deckmaster.model.carta.Carta;
import br.faesa.deckmaster.model.carta.Terreno;
import br.faesa.deckmaster.model.colecao.Colecao;
import br.faesa.deckmaster.model.colecao.ItemColecao;
import br.faesa.deckmaster.model.conta.Jogador;
import br.faesa.deckmaster.model.deck.Deck;
import br.faesa.deckmaster.model.deck.Formato;
import br.faesa.deckmaster.model.deck.ItemDeck;
import br.faesa.deckmaster.model.deck.ResultadoValidacao;

import java.util.List;

public class CtrlManterDeck {
    private int proximoId = 1;

    public Deck criar(Jogador jogador, String nome, Formato formato) {
        return new Deck(proximoId++, nome, jogador, formato);
    }

    public boolean adicionarCarta(Deck deck, Colecao colecao, Carta carta, int quantidade) {
        int possuidas = colecao.quantidadeDe(carta);
        if (deck.quantidadeDe(carta) + quantidade > possuidas) {
            return false;
        }
        return deck.adicionarCarta(carta, quantidade);
    }

    public boolean removerCarta(Deck deck, Carta carta, int quantidade) {
        return deck.removerCarta(carta, quantidade);
    }

    public ResultadoValidacao validar(Deck deck, Colecao colecao) {
        ResultadoValidacao resultado = new ResultadoValidacao();
        Formato formato = deck.getFormato();
        int total = deck.getQuantidadeTotalCartas();

        if (!formato.validarQuantidade(total)) {
            resultado.adicionarErro(String.format(
                    "O formato %s exige entre %d e %d cartas; o deck tem %d.",
                    formato.getNome(), formato.getMinCartas(), formato.getMaxCartas(), total));
        }

        int terrenos = 0;
        for (ItemDeck item : deck.listarItens()) {
            if (!formato.validarCopias(item.getQuantidade())) {
                resultado.adicionarErro(String.format(
                        "A carta %s aparece %d vezes; o maximo do formato e %d.",
                        item.getCarta().getNome(), item.getQuantidade(), formato.getMaxCopiasPorCarta()));
            }
            if (colecao.quantidadeDe(item.getCarta()) < item.getQuantidade()) {
                resultado.adicionarErro(String.format(
                        "Voce nao possui %d copias de %s.",
                        item.getQuantidade(), item.getCarta().getNome()));
            }
            if (item.getCarta() instanceof Terreno) {
                terrenos += item.getQuantidade();
            }
        }

        if (total > 0 && terrenos * 100 / total < 30) {
            resultado.adicionarAviso("Menos de 30% do deck e terreno; pode faltar recurso nos primeiros turnos.");
        }
        return resultado;
    }

    // monta um deck completo com o que o jogador tem na colecao
    public Deck montarAutomatico(Jogador jogador, Colecao colecao, Formato formato, String nome) {
        Deck deck = criar(jogador, nome, formato);
        List<ItemColecao> itens = colecao.listarItens();

        for (ItemColecao item : itens) {
            if (!(item.getCarta() instanceof Terreno)) {
                continue;
            }
            int qtd = Math.min(item.getQuantidade(), formato.getMaxCopiasPorCarta());
            adicionarCarta(deck, colecao, item.getCarta(), qtd);
        }
        for (ItemColecao item : itens) {
            if (item.getCarta() instanceof Terreno) {
                continue;
            }
            if (deck.getQuantidadeTotalCartas() >= formato.getMinCartas()) {
                break;
            }
            int qtd = Math.min(item.getQuantidade(), formato.getMaxCopiasPorCarta());
            adicionarCarta(deck, colecao, item.getCarta(), qtd);
        }
        return deck;
    }
}
