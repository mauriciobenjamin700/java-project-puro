# Usa uma imagem com JDK
FROM openjdk:17-slim

# Define o diretório de trabalho no container
WORKDIR /app

# Copia o código-fonte
COPY Main.java .

# Compila o código
RUN javac Main.java

# Expõe a porta 8000
EXPOSE 8000

# Comando pra rodar a aplicação
CMD ["java", "Main"]
