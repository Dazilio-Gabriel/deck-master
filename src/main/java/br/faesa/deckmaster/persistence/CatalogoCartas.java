package br.faesa.deckmaster.persistence;

import br.faesa.deckmaster.model.carta.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CatalogoCartas {
    private final Expansao expansao;
    private final List<Carta> cartas = new ArrayList<>();

    public CatalogoCartas() {
        this.expansao = new Expansao(1, "Alvorecer dos Reinos", "ADR", LocalDate.of(2025, 3, 1));
        montarCatalogo();
    }

    public Expansao getExpansao() { return expansao; }
    public List<Carta> getCartas() { return cartas; }

    public List<Carta> listarPorRaridade(Raridade raridade) {
        List<Carta> filtradas = new ArrayList<>();
        for (Carta c : cartas) {
            if (c.getRaridade() == raridade) {
                filtradas.add(c);
            }
        }
        return filtradas;
    }

    // catalogo inicial do prototipo
    private void montarCatalogo() {
        int id = 1;
        cartas.add(new Criatura(id++, "Recruta de Vitoria", "Sem efeito.", 1, Raridade.COMUM, expansao, 2, 1, "Humano", Elemento.TERRA));
        cartas.add(new Criatura(id++, "Lobo das Brumas", "Sem efeito.", 2, Raridade.COMUM, expansao, 3, 2, "Fera", Elemento.AR));
        cartas.add(new Criatura(id++, "Guarda de Pedra", "Sem efeito.", 3, Raridade.COMUM, expansao, 2, 5, "Elemental", Elemento.TERRA));
        cartas.add(new Criatura(id++, "Salamandra Ignea", "Sem efeito.", 3, Raridade.INCOMUM, expansao, 4, 3, "Fera", Elemento.FOGO));
        cartas.add(new Criatura(id++, "Sereia do Abismo", "Sem efeito.", 4, Raridade.INCOMUM, expansao, 4, 4, "Aquatico", Elemento.AGUA));
        cartas.add(new Criatura(id++, "Cavaleiro Sombrio", "Sem efeito.", 5, Raridade.RARA, expansao, 6, 5, "Humano", Elemento.SOMBRA));
        cartas.add(new Criatura(id++, "Golem de Obsidiana", "Sem efeito.", 6, Raridade.RARA, expansao, 7, 7, "Elemental", Elemento.TERRA, Elemento.FOGO));
        cartas.add(new Criatura(id++, "Fenix Rubra", "Sem efeito.", 7, Raridade.EPICA, expansao, 9, 6, "Fera", Elemento.FOGO, Elemento.AR));
        cartas.add(new Criatura(id++, "Leviata Ancestral", "Sem efeito.", 8, Raridade.EPICA, expansao, 10, 9, "Aquatico", Elemento.AGUA));
        cartas.add(new Criatura(id++, "Dragao Rubro", "Sem efeito.", 9, Raridade.LENDARIA, expansao, 12, 10, "Dragao", Elemento.FOGO, Elemento.SOMBRA));

        cartas.add(new Feitico(id++, "Faisca", "dano direto", 1, Raridade.COMUM, expansao, "DANO", true, 2, Elemento.FOGO));
        cartas.add(new Feitico(id++, "Bola de Fogo", "dano direto", 3, Raridade.COMUM, expansao, "DANO", true, 5, Elemento.FOGO));
        cartas.add(new Feitico(id++, "Mare Curativa", "cura", 2, Raridade.COMUM, expansao, "CURA", true, 4, Elemento.AGUA));
        cartas.add(new Feitico(id++, "Vendaval", "dano direto", 4, Raridade.INCOMUM, expansao, "DANO", true, 7, Elemento.AR));
        cartas.add(new Feitico(id++, "Ritual Sombrio", "cura", 4, Raridade.INCOMUM, expansao, "CURA", false, 8, Elemento.SOMBRA));
        cartas.add(new Feitico(id++, "Meteoro", "dano direto", 6, Raridade.RARA, expansao, "DANO", true, 10, Elemento.FOGO, Elemento.TERRA));
        cartas.add(new Feitico(id++, "Julgamento", "dano direto", 8, Raridade.EPICA, expansao, "DANO", true, 14, Elemento.SOMBRA));
        cartas.add(new Feitico(id++, "Renascer", "cura", 7, Raridade.RARA, expansao, "CURA", false, 12, Elemento.AGUA, Elemento.TERRA));

        cartas.add(new Terreno(id++, "Montanha", "Gera fogo.", Raridade.COMUM, expansao, "FOGO", 1, Elemento.FOGO));
        cartas.add(new Terreno(id++, "Lago Profundo", "Gera agua.", Raridade.COMUM, expansao, "AGUA", 1, Elemento.AGUA));
        cartas.add(new Terreno(id++, "Planicie", "Gera terra.", Raridade.COMUM, expansao, "TERRA", 1, Elemento.TERRA));
        cartas.add(new Terreno(id++, "Pico Ventoso", "Gera ar.", Raridade.COMUM, expansao, "AR", 1, Elemento.AR));
        cartas.add(new Terreno(id++, "Cripta", "Gera sombra.", Raridade.INCOMUM, expansao, "SOMBRA", 1, Elemento.SOMBRA));
        cartas.add(new Terreno(id, "Nexo Arcano", "Gera qualquer recurso.", Raridade.RARA, expansao, "ARCANO", 2, Elemento.AR, Elemento.SOMBRA));
    }
}
