import javax.swing.*;
import java.io.File;
import java.awt.*;

public class TelaEnvioArquivo extends JFrame {
    private JTextField campoArquivo;

    public TelaEnvioArquivo() {
        setTitle("Enviar Arquivo");

        // Ajusta o tamanho da janela com base na resolução da tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (screenSize.width * 1.0), (int) (screenSize.height * 1.0)); // 100% da largura e altura da tela
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Selecione um arquivo .txt:", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30)); // Aumenta o tamanho da fonte
        label.setForeground(Color.BLACK); // Define a cor do texto da label
        label.setBorder(null); // Remove qualquer borda ao redor da JLabel

        campoArquivo = new JTextField();
        campoArquivo.setEditable(false);
        campoArquivo.setFont(new Font("Arial", Font.PLAIN, 20)); // Define o tamanho da fonte do texto
        campoArquivo.setPreferredSize(new Dimension(500, 50)); // Ajusta o tamanho da caixa de texto

        JButton botaoEscolher = new JButton("Escolher Arquivo");
        botaoEscolher.setFont(new Font("Arial", Font.BOLD, 20)); // Define o tamanho da fonte do botão
        botaoEscolher.setPreferredSize(new Dimension(200, 50)); // Define o tamanho do botão
        botaoEscolher.setBackground(Color.LIGHT_GRAY); // Define a cor de fundo do botão
        botaoEscolher.setForeground(Color.BLACK); // Define a cor do texto do botão
        botaoEscolher.setFocusPainted(false); // Remove o retângulo de foco ao redor do texto do botão
        botaoEscolher.addActionListener(e -> escolherArquivo());

        // Painel para o campo de texto e o botão
        JPanel painelCampo = new JPanel(new BorderLayout());
        painelCampo.setPreferredSize(new Dimension(500, 50)); // Ajusta o tamanho total do painel
        painelCampo.add(campoArquivo, BorderLayout.CENTER); // Adiciona o campo de texto ao centro
        painelCampo.add(botaoEscolher, BorderLayout.EAST); // Adiciona o botão à direita

        JButton botaoEnviar = new JButton("Enviar");
        botaoEnviar.setFont(new Font("Arial", Font.PLAIN, 20)); // Define o tamanho da fonte do botão
        botaoEnviar.setBackground(Color.GREEN); // Define a cor de fundo do botão
        botaoEnviar.setForeground(Color.WHITE); // Define a cor do texto do botão
        botaoEnviar.setPreferredSize(new Dimension(150, 50)); // Define o tamanho do botão
        botaoEnviar.setFocusPainted(false); // Remove o retângulo de foco ao redor do texto do botão
        botaoEnviar.addActionListener(e -> {
            dispose();
            new TelaResultado(); // Apenas simula envio e mostra resultado
        });

        // Configuração do layout principal
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margens internas para espaçamento

        // Adiciona o label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(label, gbc);

        // Adiciona o painel com o campo de texto e o botão
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(painelCampo, gbc);

        // Adiciona o botão "Enviar"
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Centraliza o botão abaixo dos outros componentes
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(botaoEnviar, gbc);

        add(painel);
        setVisible(true);
    }

    private void escolherArquivo() {
        JFileChooser chooser = new JFileChooser();
        int opcao = chooser.showOpenDialog(this);
        if (opcao == JFileChooser.APPROVE_OPTION) {
            File arquivo = chooser.getSelectedFile();
            campoArquivo.setText(arquivo.getAbsolutePath());
        }
    }
}