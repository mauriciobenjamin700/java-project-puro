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

## Dicas sobre Java

Se você não sabe nada sobre java, ai vai algumas dicas

- `javac Main.java` : Compila seu arquivo Main.java
- `java Main` : Executa seu projeto java

## Informações Têcnicas

Para enviar o arquivo de texto a ser processado pelo servidor JAVA, você deve fazer uma requisição HTTP do tipo POST, onde o conteúdo será no formato `Content-Type: multipart/form-data`.

A rota que processa será `http://IP_DO_SERVIDOR/upload`

O retorno do processamento será:

```json
{
    "text": "Teste 01",
    "letters": 5,
    "digits": 2
}
```

Exlicando o retorno

- `text` : Chave contendo o texto lido pelo arquivo
- `letters` : Total de letras contadas no texto
- `digits` : Total de digitos contados no texto

Para saber mais, recomendo que tenha o `postman` instalado em sua maquina e carregue a nossa documentação de API contida [neste arquivo](./docs/java-project-puro.postman_collection.json).

## Como executar.

