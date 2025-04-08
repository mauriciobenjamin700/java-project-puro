# Atividade 4 – THREADS, MIDDLEWARE, PARALELISMO, DOCKER

Objetivo: Implementar um sistema distribuído todo em Java puro usando threads e containers Docker, seguindo uma arquitetura mestre-escravo.

## Funcionalidades

- Cliente (Notebook 1):
  - Envia um texto que contém letras e números em um arquivo txt.
  - Interface gráfica (GUI) em Java (Swing/JavaFX)
  - Apenas envia requisições HTTP (Rest) e exibe resultados (não processa dados).

- Servidores (Notebook 2):
  - Mestre (Container 1) - Recebe requisições e dispara 2 threads paralelas:
  - Cada thread é responsável por se comunicar com um escravo e antes de enviar o arquivo deve perguntar para o escravo se ele está disponível.
  - Quando o mestre receber o resultado dos dois escravos, ele combina a resposta e a devolve para o cliente.

- Escravos (Containers 2 e 3):
  - Escravo 1: Calcula quantidade de letras (endpoint /letras).
  - Escravo 2: Calcula quantidade de números (endpoint /números).
