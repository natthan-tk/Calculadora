package ProjetoCalculadora;

// Importando bibliotecas necessárias para a criação da interface gráfica e manipulação de eventos
import javax.swing.*; // Biblioteca Swing, usada para interfaces gráficas
import java.awt.*; // Biblioteca AWT, usada para layouts, estilos e propriedades gráficas
import java.awt.event.*; // Biblioteca para manipulação de eventos (como cliques de botões)


// Classe principal da calculadora, que estende JFrame para criar a janela da aplicação
public class Calculadora extends JFrame implements ActionListener {

    // Atributos da classe Calculadora
    private JTextField display; // Tela de exibição dos números e resultados
    private JLabel operacaoLabel; // Rótulo que exibe a operação em andamento (ex: "30 + 10")
    private JButton[] botoes; // Array de botões da calculadora
    private String[] rotulos = { // Array com os rótulos dos botões
        "AC", "%", "<-", "÷",
        "7", "8", "9", "x",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "00", "0", ".", "=",
    };
    private String operacaoExtensa = ""; // Armazena a operação por extenso (ex: "30 + 10")
    private String operador = ""; // Armazena o operador atual (ex: "+", "-", "x", "÷")
    private String operadorAnterior = ""; // Armazena o operador anterior
    

    // Variáveis para armazenar os valores durante os cálculos
    private double valor1 = 0; // Primeiro valor (ex: 30 em "30 + 10")
    private double valor2 = 0; // Segundo valor (ex: 10 em "30 + 10")
    private boolean novoValor = true; // Flag para indicar se o próximo número será um novo valor

    // Construtor da classe Calculadora
        public Calculadora() { 
            // Configurações da janela principal
            setTitle(Calculadora.class.getSimpleName()); // Define o Título da Janela
            setSize(450, 500); // Define o Tamanho da Janela
            setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao fechar a janela
            setLocationRelativeTo(null); // Centraliza a janela na tela
            setResizable(false); // Não permite redimensionar a janela
            setLayout(new BorderLayout()); // Define o layout como BorderLayout (norte, sul, leste, oeste e centro)
            setAlwaysOnTop(true); // Mantém a janela sempre no topo


    // Rótulo para exibir a operação em andamento
        operacaoLabel = new JLabel("");
        operacaoLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Define a fonte do texto
        operacaoLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Alinha o texto à direita
        operacaoLabel.setForeground(Color.LIGHT_GRAY); // Define a cor do texto
        operacaoLabel.setPreferredSize(new Dimension(300, 40)); // Define o tamanho do rótulo
        operacaoLabel.setBackground(Color.BLACK); // Cor de fundo desejada
        operacaoLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1)); // Borda verde
        operacaoLabel.setForeground(Color.GREEN); // Define a cor de fundo do rótulo
        operacaoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Adiciona uma borda ao rótulo
        operacaoLabel.setOpaque(true); // Necessário para mostrar a cor de fundo


    // Definindo a tela da calculadora 
              display = new JTextField(); // Cria a tela da calculadora
              display.setEditable(true); // Barrinha de edição notavel
              display.setCaretColor(Color.GREEN); // Define a cor da barrinha 
              display.setFont(new Font("Arial", Font.PLAIN, 24)); // Define a fonte da tela
              display.setHorizontalAlignment(SwingConstants.RIGHT); // Alinha o texto á direita
              display.setBackground(Color.BLACK); // Define a cor de fundo da tela
              display.setForeground(Color.GREEN); // Define a cor do texto da tela
              display.setPreferredSize(new Dimension(300, 120)); // Define o tamanho da tela, Largura 300px, altura 120px (ou mais)

    // Agrupando operacaoLabel + display, o rótulo e a tela em um painel superior
              JPanel painelSuperior = new JPanel();
              painelSuperior.setLayout(new BoxLayout(painelSuperior, BoxLayout.Y_AXIS)); // Layout em coluna
              painelSuperior.add(operacaoLabel); // Adiciona o rótulo ao painel
              painelSuperior.add(display); // Adiciona a tela ao painel
              painelSuperior.setBackground(Color.BLACK); // Define o fundo do painel
              add(painelSuperior, BorderLayout.NORTH); // Adiciona o painel no topo da janela

    // Definindo os botões da calculadora
              botoes = new JButton[rotulos.length]; // Cria um array de botões com o mesmo tamanho dos rótulos
              JPanel painelBotos = new JPanel(); // Cria o painel para os botões
              painelBotos.setLayout(new GridLayout(5,4)); // Define o layout dos botões (5 linhas e 4 colunas)
                for (int i = 0; i < rotulos.length; i++) { // Para cada rótulo de botão
                    final int index = i;// Copia o índice atual para uma variável final
                    botoes[i] = new JButton(); // Cria o botão
                    botoes[i].setText(rotulos[i]); // Define o texto do botão
                    botoes[i].setFont(new Font("Arial", Font.PLAIN,24)); // Define a fonte do botão
                    botoes[i].setBackground(Color.BLACK); // Define a cor de fundo do botão
                    botoes[i].setForeground(Color.GREEN); // Define a cor do texto do botão
                    botoes[i].setFocusable(false); // Remove o foco do botão
                    botoes[i].addActionListener(this); // Adiciona o evento de clique no botão
                    painelBotos.add(botoes[i]); // Adiciona o botão no painel


                    // Adicionando efeitos de cor ao pressionar e liberar os botões
                    botoes[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            botoes[index].setBackground(Color.BLUE);  // Cor do botão quando pressionado
                        }
                    
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            botoes[index].setBackground(Color.BLACK); // Cor do botão quando solto
                        }
                    });
                }
              add(painelBotos, BorderLayout.CENTER); // Adicione o painel de botões ao centro da janela
          }

         
          public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand(); // Obtém o comando do botão pressionado
        
            // Tratando a entrada de números ou ponto decimal
            if (comando.matches("[0-9\\.]") || comando.equals("00")) {
                if (novoValor) {
                    display.setText(comando); // Começa novo número
                    novoValor = false;
                } else {
                    display.setText(display.getText() + comando); // Continua digitando
                }
        
                operacaoExtensa += comando; // Adiciona número à operação por extenso
                operacaoLabel.setText(operacaoExtensa); // Atualiza exibição
            }
        
            // Tratando operadores
            else if (comando.equals("+") || comando.equals("-") || comando.equals("x") || comando.equals("÷")) {
                String textoAtual = display.getText(); // Obtém o texto atual da tela
                textoAtual = textoAtual.replace(",", "."); // Substitui vírgula por ponto

                double numeroAtual = Double.parseDouble(display.getText()); // Obtém o número atual da tela
        
                if (!operador.isEmpty()) {
                    valor2 = numeroAtual;
                    switch (operador) {
                        case "+": valor1 = valor1 + valor2; break;
                        case "-": valor1 = valor1 - valor2; break;
                        case "x": valor1 = valor1 * valor2; break;
                        case "÷": valor1 = valor1 / valor2; break;
                    }
                } else {
                    valor1 = numeroAtual;
                }
        
                setOperador(comando);
                operadorAnterior = comando;
                operacaoExtensa += " " + comando + " "; // Adiciona operador com espaços
                operacaoLabel.setText(operacaoExtensa); // Exibe a operação por extenso
                display.setText(String.valueOf(valor1));
                novoValor = true;
            }
        
            // Tratando o símbolo de vírgula
            else if (comando.equals(",")) {
                String texto = display.getText();
                if (!texto.contains(",")) {
                    display.setText(texto + ",");
                }


            // Também adiciona a vírgula na operação por extenso
                 if (!operacaoExtensa.contains(",")) {
                    operacaoExtensa += ","; // Adiciona vírgula na operação por extenso
                    operacaoLabel.setText(operacaoExtensa); // Atualiza a operação por extenso
    }
}
            
        
            // Backspace
            else if (comando.equals("<-")) {
                String texto = display.getText();
                if (!texto.isEmpty()) {
                    display.setText(texto.substring(0, texto.length() - 1));
                }

                if (!operacaoExtensa.isEmpty()) {
                    operacaoExtensa = operacaoExtensa.substring(0, operacaoExtensa.length() - 1);
                    operacaoLabel.setText(operacaoExtensa);
                }
            }
            
        
            // Tratando o cálculo
            else if (comando.equals("=")) {
                valor2 = Double.parseDouble(display.getText());
        
                // Se o operador for %, calcula com base no operador anterior
                if (operador.equals("%")) {
                    switch (operadorAnterior) {
                        case "+": valor2 = valor1 * valor2 / 100; break;
                        case "-": valor2 = valor1 * valor2 / 100; break;
                        case "x": valor2 = valor2 / 100; break;
                        case "÷": valor2 = valor2 / 100; break;
                    }
                }
        
                double resultado = 0;
                switch (operador) {
                    case "+": resultado = valor1 + valor2; break;
                    case "-": resultado = valor1 - valor2; break;
                    case "x": resultado = valor1 * valor2; break;
                    case "÷": resultado = valor1 / valor2; break;
                    case "%": resultado = valor1 + valor2; break;
                }
        
                operacaoExtensa += " = " + resultado;
                operacaoLabel.setText(operacaoExtensa);
                display.setText(String.valueOf(resultado));
                novoValor = true;
        
                // Reinicia para continuar operando com o resultado
                operador = "";
                operadorAnterior = "";
                operacaoExtensa = "";
                valor1 = resultado;
            }
        
            // Tratando porcentagem
            else if (comando.equals("%")) {
                operadorAnterior = operador;
                setOperador("%");
                novoValor = true;
            }
        
            // Limpeza total (AC)
            else if (comando.equals("AC")) {
                display.setText("");
                operacaoLabel.setText("");
                valor1 = valor2 = 0;
                operador = "";
                novoValor = true;
                operacaoExtensa = "";
            }
        }
        

private void setOperador(String operador) { // This method declaration is now correctly placed
    this.operador = operador;
}

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        new Calculadora().setVisible(true);
    });
}
}

        
