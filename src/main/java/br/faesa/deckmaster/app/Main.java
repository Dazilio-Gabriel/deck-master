package br.faesa.deckmaster.app;

import br.faesa.deckmaster.controller.*;
import br.faesa.deckmaster.model.carta.Carta;
import br.faesa.deckmaster.model.colecao.Colecao;
import br.faesa.deckmaster.model.colecao.Pacote;
import br.faesa.deckmaster.model.conta.Jogador;
import br.faesa.deckmaster.model.deck.Deck;
import br.faesa.deckmaster.model.deck.Formato;
import br.faesa.deckmaster.model.deck.ResultadoValidacao;
import br.faesa.deckmaster.model.partida.ModoPartida;
import br.faesa.deckmaster.model.partida.Partida;
import br.faesa.deckmaster.persistence.CatalogoCartas;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random random = new Random(20250910L);
        CatalogoCartas catalogo = new CatalogoCartas();
        SorteadorCartas sorteador = new SorteadorCartas(catalogo, random);
        CtrlComprarAbrirPacote ctrlLoja = new CtrlComprarAbrirPacote(sorteador);
        CtrlManterDeck ctrlDeck = new CtrlManterDeck();
        CtrlRealizarPartida ctrlPartida = new CtrlRealizarPartida(new MotorRegrasPartida(), random);

        titulo("DECK MASTER - prototipo de console");

        Jogador jogador = new Jogador(1, "Dazilio", "Gabriel Fanchiotti", "gabriel@exemplo.com",
                LocalDate.of(2003, 5, 12), 3000);
        Jogador rival = new Jogador(2, "RivalBot", "Oponente Automatico", "bot@exemplo.com",
                LocalDate.of(2000, 1, 1), 3000);

        Colecao colecao = new Colecao(jogador);
        Colecao colecaoRival = new Colecao(rival);

        Pacote pacote = new Pacote(1, "Pacote Alvorecer", 100, 5, catalogo.getExpansao());

        titulo("1. COMPRA E ABERTURA DE PACOTES");
        abrirPacotes(ctrlLoja, jogador, colecao, pacote, 18, true);
        abrirPacotes(ctrlLoja, rival, colecaoRival, pacote, 18, false);

        titulo("2. MONTAGEM DO DECK");
        Deck deck = ctrlDeck.montarAutomatico(jogador, colecao, Formato.PADRAO, "Furia Elfica");
        Deck deckRival = ctrlDeck.montarAutomatico(rival, colecaoRival, Formato.PADRAO, "Legiao Sombria");
        System.out.println("Deck montado: " + deck);
        System.out.println("Deck do rival: " + deckRival);

        titulo("3. VALIDACAO DO DECK");
        ResultadoValidacao validacao = ctrlDeck.validar(deck, colecao);
        System.out.println(validacao);
        if (!validacao.isValido()) {
            System.out.println("\nDeck sem cartas suficientes para o formato Padrao. Rodando no formato Rapido.");
            deck = ctrlDeck.montarAutomatico(jogador, colecao, Formato.RAPIDO, "Furia Elfica");
            deckRival = ctrlDeck.montarAutomatico(rival, colecaoRival, Formato.RAPIDO, "Legiao Sombria");
            System.out.println(ctrlDeck.validar(deck, colecao));
        }

        titulo("4. PARTIDA RANQUEADA");
        Partida partida = ctrlPartida.iniciar(jogador, deck, rival, deckRival, ModoPartida.RANQUEADA);
        ctrlPartida.executarAutomatico(partida, System.out::println);

        titulo("5. RESULTADO");
        System.out.printf("Situacao da partida: %s (%s)%n",
                partida.getStatus().getNome(), partida.getMotivoEncerramento());
        System.out.printf("Vencedor: %s%n", partida.getVencedor().getApelido());
        imprimirPerfil(jogador);
        imprimirPerfil(rival);
    }

    private static void abrirPacotes(CtrlComprarAbrirPacote ctrl, Jogador jogador, Colecao colecao,
                                     Pacote pacote, int quantidade, boolean detalhar) {
        List<Carta> obtidas = ctrl.comprarEAbrir(jogador, colecao, pacote, quantidade);
        System.out.printf("%s abriu %d pacotes e obteve %d cartas (%d distintas). Cristais restantes: %d%n",
                jogador.getApelido(), quantidade, obtidas.size(),
                colecao.totalCartasDistintas(), jogador.getSaldoCristais());
        if (detalhar) {
            int mostrar = Math.min(8, obtidas.size());
            for (int i = 0; i < mostrar; i++) {
                System.out.println("   " + obtidas.get(i).descrever());
            }
            System.out.println("   ...");
            System.out.printf("   Total na colecao: %d cartas | fragmentos: %d%n",
                    colecao.totalCartas(), jogador.getSaldoFragmentos());
        }
    }

    private static void imprimirPerfil(Jogador j) {
        System.out.printf("%s | nivel %d | %d/%d vitorias (%.0f%%) | %d cristais%n",
                j.getApelido(), j.getNivel(), j.getTotalVitorias(), j.getTotalPartidas(),
                j.calcularPercentualVitorias(), j.getSaldoCristais());
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=".repeat(Math.max(10, texto.length())));
        System.out.println(texto);
        System.out.println("=".repeat(Math.max(10, texto.length())));
    }
}
