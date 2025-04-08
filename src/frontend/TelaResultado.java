import javax.swing.*;
import java.awt.*;

public class TelaResultado extends JFrame {

    public TelaResultado() {
        setTitle("Resultado");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (screenSize.width * 1.0), (int) (screenSize.height * 1.0)); // 100% da largura e altura da tela
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Textos com acentos
        JLabel letras = new JLabel("Quantidade de letras: Valor retornado pelo servidor", SwingConstants.CENTER);
        letras.setFont(new Font("Arial", Font.BOLD, 40));
        
        JLabel numeros = new JLabel("Quantidade de numeros: Valor retornado pelo servidor", SwingConstants.CENTER);
        numeros.setFont(new Font("Arial", Font.BOLD, 40));

        JButton botaoVoltar = new JButton("Nova contagem");
        botaoVoltar.setFont(new Font("Arial", Font.BOLD, 20));
        botaoVoltar.setPreferredSize(new Dimension(250, 50));
        botaoVoltar.setBackground(Color.YELLOW);
        botaoVoltar.setForeground(Color.BLACK);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.addActionListener(e -> {
            dispose();
            new TelaEnvioArquivo();
        });

        JButton botaoSair = new JButton("Voltar para tela inicial");
        botaoSair.setFont(new Font("Arial", Font.BOLD, 20));
        botaoSair.setPreferredSize(new Dimension(250, 50));
        botaoSair.setBackground(Color.LIGHT_GRAY);
        botaoSair.setForeground(Color.BLACK);
        botaoSair.setFocusPainted(false);
        botaoSair.addActionListener(e -> {
            dispose();
            new TelaBoasVindas();
        });

        // Configuração do layout principal
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Ajuste das margens das labels
        gbc.insets = new Insets(10, 10, 20, 10); // Margem superior maior para mover a primeira label para baixo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(letras, gbc);

        // Ajuste do espaçamento entre as labels
        gbc.insets = new Insets(30, 10, 30, 10); // Espaçamento maior entre as labels
        gbc.gridy = 1;
        painel.add(numeros, gbc);

        // Ajuste do espaçamento entre as labels e os botões
        gbc.insets = new Insets(50, 10, 10, 10); // Espaçamento entre as labels e os botões
        gbc.gridy = 2;

        // Painel para os botões com espaçamento ajustado
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); // Espaçamento horizontal entre os botões
        painelBotoes.add(botaoVoltar);
        painelBotoes.add(botaoSair);

        gbc.gridwidth = 2; // Centraliza o painel de botões
        painel.add(painelBotoes, gbc);

        add(painel);
        setVisible(true);
    }

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        new TelaResultado();
    }
}