import javax.swing.*;
import java.awt.*;

public class TelaBoasVindas extends JFrame {

    public TelaBoasVindas() {
        setTitle("Boas-Vindas");
        // Ajusta o tamanho da janela com base na resolução da tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (screenSize.width * 1.0), (int) (screenSize.height * 1.0)); // 100% da largura e altura da tela
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Bem-vindo ao Contador de Caracteres!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setBorder(null); // Remove qualquer borda ao redor da JLabel

        // Botão "Realizar Contagem" com tamanho individual
        JButton botaoContagem = new JButton("Realizar Contagem");
        botaoContagem.setFont(new Font("Arial", Font.PLAIN, 20)); // Fonte menor
        botaoContagem.setBackground(Color.GREEN); // Define a cor de fundo do botão
        botaoContagem.setForeground(Color.WHITE); // Define a cor do texto do botão
        botaoContagem.setPreferredSize(new Dimension(250, 60)); // Define o tamanho do botão
        botaoContagem.setFocusPainted(false); // Remove o retângulo de foco ao redor do texto do botão
        botaoContagem.addActionListener(e -> {
            dispose(); // Fecha a tela atual
            new TelaEnvioArquivo();
        });

        // Botão "Sair" com tamanho individual
        JButton botaoSair = new JButton("Sair");
        botaoSair.setFont(new Font("Arial", Font.PLAIN, 20)); // Fonte menor
        botaoSair.setBackground(Color.RED); // Define a cor de fundo do botão
        botaoSair.setForeground(Color.WHITE); // Define a cor do texto do botão
        botaoSair.setPreferredSize(new Dimension(250, 60)); // Define o tamanho do botão
        botaoSair.setFocusPainted(false); // Remove o retângulo de foco ao redor do texto do botão
        botaoSair.addActionListener(e -> {
            System.exit(0); // Encerra o programa
        });

        // Painel para os botões com FlowLayout para respeitar tamanhos individuais
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 0)); // Espaçamento horizontal de 40px
        painelBotoes.add(botaoContagem);
        painelBotoes.add(botaoSair);

        // Declara e inicializa o painel
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Centraliza o painel de botões
        painel.add(painelBotoes, gbc);

        // Painel principal com BoxLayout para alinhar os componentes verticalmente
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));

        // Adiciona espaçamento e componentes ao painel
        painelCentral.add(Box.createVerticalStrut(200)); // Espaçamento inicial da margem superior
        painelCentral.add(label);
        painelCentral.add(Box.createVerticalStrut(50)); // Espaçamento entre a label e os botões
        painelCentral.add(painelBotoes);

        // Centraliza os componentes no painel
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelBotoes.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(painelCentral, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaBoasVindas();
    }
}