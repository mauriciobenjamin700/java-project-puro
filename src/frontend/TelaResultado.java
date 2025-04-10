import javax.swing.*;
import java.awt.*;
import org.json.JSONObject;

public class TelaResultado extends JFrame {

    public TelaResultado(String respostaJson) {
        setTitle("Resultado");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (screenSize.width * 1.0), (int) (screenSize.height * 1.0));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String texto = "Não recebido";
        String letrasValor = "0";
        String numerosValor = "0";

        try {
            JSONObject json = new JSONObject(respostaJson);
            texto = json.getString("text");
            letrasValor = String.valueOf(json.getInt("letters"));
            numerosValor = String.valueOf(json.getInt("digits"));
        } catch (Exception e) {
            System.out.println("Erro ao processar JSON: " + e.getMessage());
        }

        JLabel textoLabel = new JLabel("Texto analisado: " + texto, SwingConstants.CENTER);
        textoLabel.setFont(new Font("Arial", Font.BOLD, 30));

        JLabel letras = new JLabel("Quantidade de letras: " + letrasValor, SwingConstants.CENTER);
        letras.setFont(new Font("Arial", Font.BOLD, 40));

        JLabel numeros = new JLabel("Quantidade de números: " + numerosValor, SwingConstants.CENTER);
        numeros.setFont(new Font("Arial", Font.BOLD, 40));

        JButton botaoVoltar = new JButton("Nova contagem");
        botaoVoltar.setFont(new Font("Arial", Font.BOLD, 20));
        botaoVoltar.setPreferredSize(new Dimension(250, 50));
        botaoVoltar.setBackground(new Color(255, 140, 0));
        botaoVoltar.setForeground(Color.BLACK);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.addActionListener(e -> {
            dispose();
            new TelaEnvioArquivo();
        });

        JButton botaoSair = new JButton("Voltar para tela inicial");
        botaoSair.setFont(new Font("Arial", Font.BOLD, 20));
        botaoSair.setPreferredSize(new Dimension(250, 50));
        botaoSair.setBackground(new Color(173, 216, 230));
        botaoSair.setForeground(Color.BLACK);
        botaoSair.setFocusPainted(false);
        botaoSair.addActionListener(e -> {
            dispose();
            new TelaBoasVindas();
        });

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painel.add(textoLabel, gbc);

        gbc.gridy = 1;
        painel.add(letras, gbc);

        gbc.gridy = 2;
        painel.add(numeros, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(40, 10, 10, 10);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        painelBotoes.add(botaoVoltar);
        painelBotoes.add(botaoSair);

        painel.add(painelBotoes, gbc);

        add(painel);
        setVisible(true);
    }
}
