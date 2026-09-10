package br.faesa.deckmaster.controller;

import br.faesa.deckmaster.model.carta.Carta;
import br.faesa.deckmaster.model.carta.Criatura;
import br.faesa.deckmaster.model.carta.Feitico;
import br.faesa.deckmaster.model.carta.Terreno;
import br.faesa.deckmaster.model.partida.*;

import java.util.ArrayList;
import java.util.List;

public class MotorRegrasPartida {

    public boolean validarJogada(Turno turno, Carta carta) {
        return carta != null && carta.ehJogavel(turno.getRecursosDisponiveis());
    }

    public Jogada aplicarCarta(Partida partida, LadoPartida lado, Turno turno, Carta carta, int sequencia) {
        if (!validarJogada(turno, carta)) {
            return null;
        }
        turno.consumirRecursos(carta.getCustoMana());
        LadoPartida oponente = partida.oponenteDe(lado);
        Jogada jogada;

        if (carta instanceof Terreno) {
            lado.posicionar((Terreno) carta);
            jogada = new Jogada(sequencia, TipoJogada.POSICIONAR_TERRENO, carta, null);
        } else if (carta instanceof Criatura) {
            lado.invocar((Criatura) carta);
            jogada = new Jogada(sequencia, TipoJogada.INVOCAR_CRIATURA, carta, null);
        } else {
            Feitico feitico = (Feitico) carta;
            lado.descartar(feitico);
            jogada = new Jogada(sequencia, TipoJogada.LANCAR_FEITICO, carta,
                    "CURA".equals(feitico.getEfeito()) ? lado.getJogador().getApelido()
                                                       : oponente.getJogador().getApelido());
            if ("CURA".equals(feitico.getEfeito())) {
                lado.curar(feitico.getValorEfeito());
            } else {
                oponente.receberDano(feitico.getValorEfeito());
                jogada.setDanoCausado(feitico.getValorEfeito());
            }
        }
        turno.registrarJogada(jogada);
        partida.marcarJogada();
        return jogada;
    }

    public Jogada resolverCombate(Partida partida, LadoPartida lado, Turno turno, int sequencia) {
        LadoPartida oponente = partida.oponenteDe(lado);
        int poder = lado.calcularPoderDeAtaque();
        if (poder <= 0) {
            return null;
        }
        int bloqueio = 0;
        List<Criatura> abatidas = new ArrayList<>();
        for (Criatura defensora : oponente.getCampo()) {
            if (bloqueio + defensora.getDefesa() > poder) {
                break;
            }
            bloqueio += defensora.getDefesa();
            abatidas.add(defensora);
        }
        oponente.getCampo().removeAll(abatidas);

        int danoDireto = poder - bloqueio;
        oponente.receberDano(danoDireto);

        Jogada jogada = new Jogada(sequencia, TipoJogada.ATACAR, null, oponente.getJogador().getApelido());
        jogada.setDanoCausado(danoDireto);
        turno.registrarJogada(jogada);
        partida.marcarJogada();
        return jogada;
    }

    public boolean verificarFimDePartida(Partida partida) {
        return partida.getLadoA().estaDerrotado() || partida.getLadoB().estaDerrotado();
    }
}
