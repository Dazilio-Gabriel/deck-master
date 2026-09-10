# Deck Master

Jogo digital de cartas colecionáveis. Trabalho prático de **Projeto Orientado a Objetos** —
Centro Universitário FAESA, 2025.2.

## O que tem aqui

```
docs/                                   documentação do trabalho
  DeckMaster-ProjetoOO.md                arquivo-fonte (texto + diagramas Mermaid)
  trabalhoC1ProjetoOO.doc/.docx          Proposta de Trabalho Prático (entrega C1)
  trabalhoC1ProjetoOO-Especificacao.*    Especificação do Projeto do Sistema
src/main/java/br/faesa/deckmaster/      protótipo em Java
  model/        camada Model      — Componente de Domínio do Problema (CDP)
  controller/   camada Controller — Componente de Gerência de Tarefa (CGT)
  persistence/  camada Persistent — Componente de Gerência de Dados (CGD)
  app/          Main de console que roda a demonstração
```

## Rodar

Precisa de JDK 17 ou superior.

```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out br.faesa.deckmaster.app.Main
```

No Windows (PowerShell):

```powershell
$fontes = Get-ChildItem -Recurse -Filter *.java src\main\java | ForEach-Object { $_.FullName }
javac -d out $fontes
java -cp out br.faesa.deckmaster.app.Main
```

Com Maven:

```bash
mvn -q compile exec:java -Dexec.mainClass=br.faesa.deckmaster.app.Main
```

## O que a demonstração faz

1. Compra e abre pacotes, sorteando cartas pela probabilidade de cada raridade e convertendo
   cópias excedentes em fragmentos.
2. Monta um deck automaticamente a partir da coleção, respeitando as regras do formato.
3. Valida o deck e mostra erros e avisos.
4. Roda uma partida ranqueada completa entre dois lados, turno a turno.
5. Apura o resultado, atualiza totalizadores, experiência e cristais dos jogadores.

## Estado

O protótipo cobre o núcleo do modelo: coleção, deck, formato, partida por turnos, motor de regras
e sorteio de cartas. A persistência é em memória (`RepositorioMemoria`), com as interfaces já no
formato do CGD projetado — a troca por DAOs JDBC/PostgreSQL não altera o Model nem o Controller.

Fora do protótipo, por enquanto: interface gráfica (JavaFX), temporadas e ranking, segurança de
acesso e as rotinas de backup e histórico. Todas estão especificadas em `docs/`.
