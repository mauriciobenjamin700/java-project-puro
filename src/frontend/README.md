# Configurando a Interface Java no Ubuntu

Siga os passos a baixo fielmente

## 1. **JDK (Java Development Kit)**

Abra o terminal e execute:

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

Isso instalará o **JDK 17** (ou versão superior) que inclui o **Swing** e outras bibliotecas do Java.

### 2. **Configurar o ambiente Java**

Após a instalação, é uma boa prática configurar o **Java** corretamente no seu sistema, para garantir que você possa compilar e executar programas Java a partir de qualquer diretório no terminal.

Execute o seguinte comando para garantir que o JDK foi instalado corretamente:

```bash
java -version
```

Você deve ver algo como:

```bash
openjdk version "17.x.x"
OpenJDK Runtime Environment (build 17.x.x+...)
OpenJDK 64-Bit Server VM (build 17.x.x, mixed mode)
```

Isso indica que o **JDK** está instalado corretamente.

### 3. **Compilar e Executar o Código**

Agora, você pode compilar e executar o código no terminal. Aqui estão os passos:

#### Compilar o código Linux

```bash
javac -cp .:json-20240303.jar *.java
```
#### Compilar o código no Windows
```bash
javac -cp .;json-20240303.jar *.java

```

```bash
javac -cp ".;json-20240303.jar" -encoding UTF-8 *.java
```

#### Executar o código Linux

Depois de compilar, execute o programa com:

```bash
java -cp .:json-20240303.jar Main
```
#### Executar o código Windows

Depois de compilar, execute o programa com:

```bash
java -cp .;json-20240303.jar Main
```
```bash
java -cp ".;json-20240303.jar" Main
``

Isso abrirá a janela da interface gráfica do seu programa.

---

### 4. **Requisitos de Biblioteca**

O código usa apenas **Swing** (que já vem com o JDK), então você não precisa instalar bibliotecas externas. Certifique-se de que o **JDK** instalado inclui a biblioteca **Swing** (como no caso do OpenJDK 17). Isso é garantido, pois o Swing é parte da biblioteca padrão do JDK.

Caso dê erro experimente instalar as seguintes libs:O exemplo abaixo é para o openjdk21, ajuste para sua versão instalada do java

```bash
sudo apt update
sudo apt install openjdk-21-jdk openjdk-21-demo openjdk-21-source openjdk-21-doc libxext-dev libxrender-dev libxtst-dev libxi-dev libxt-dev

```

---

Com esses passos, você deve conseguir executar a aplicação GUI no seu Ubuntu 24.04 LTS sem problemas.
