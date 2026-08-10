package View;

import model.Operacao;
import service.Calculadora;
import service.Historico;

import javax.swing.*;
import java.awt.*;

public class TelaCalculadora extends JFrame {

    private JTextField display;
    private DefaultListModel<String> historicoModel;

    private final Calculadora calculadora;
    private final Historico historico;

    private double primeiroNumero;
    private Operacao operacaoAtual;

    private String entradaAtual = "0";
    private boolean novoNumero = true;


    public TelaCalculadora() {

        calculadora = new Calculadora();
        historico = new Historico();

        configurarJanela();
        criarInterface();

        setVisible(true);
    }


    // =========================================================
    // CONFIGURAÇÃO DA JANELA
    // =========================================================

    private void configurarJanela() {

        setTitle("Calculadora");

        setSize(750, 550);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setResizable(false);

        getContentPane().setBackground(
                new Color(18, 18, 18)
        );
    }


    // =========================================================
    // INTERFACE PRINCIPAL
    // =========================================================

    private void criarInterface() {

        setLayout(new BorderLayout());

        JPanel painelPrincipal =
                new JPanel(new BorderLayout());

        painelPrincipal.setBackground(
                new Color(18, 18, 18)
        );

        painelPrincipal.add(
                criarCalculadora(),
                BorderLayout.CENTER
        );

        painelPrincipal.add(
                criarHistorico(),
                BorderLayout.EAST
        );

        add(painelPrincipal);
    }


    // =========================================================
    // PAINEL DA CALCULADORA
    // =========================================================

    private JPanel criarCalculadora() {

        JPanel painel =
                new JPanel(new BorderLayout(10, 10));

        painel.setBackground(
                new Color(18, 18, 18)
        );

        painel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        10
                )
        );


        // TÍTULO

        JLabel titulo =
                new JLabel("CALCULADORA");

        titulo.setForeground(Color.WHITE);

        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        painel.add(
                titulo,
                BorderLayout.NORTH
        );


        // DISPLAY

        display =
                new JTextField("0");

        display.setEditable(false);

        display.setHorizontalAlignment(
                JTextField.RIGHT
        );

        display.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        36
                )
        );

        display.setForeground(Color.WHITE);

        display.setBackground(
                new Color(30, 30, 30)
        );

        display.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );


        JPanel painelDisplay =
                new JPanel(new BorderLayout());

        painelDisplay.setBackground(
                new Color(18, 18, 18)
        );

        painelDisplay.add(
                display,
                BorderLayout.CENTER
        );


        // BOTÕES

        JPanel painelBotoes =
                new JPanel(
                        new GridLayout(
                                5,
                                4,
                                8,
                                8
                        )
                );

        painelBotoes.setBackground(
                new Color(18, 18, 18)
        );


        String[] botoes = {

                "AC", "←", "%", "÷",

                "7", "8", "9", "×",

                "4", "5", "6", "-",

                "1", "2", "3", "+",

                "0", ".", "=", ""

        };


        for (String texto : botoes) {

            if (texto.isEmpty()) {

                painelBotoes.add(
                        new JPanel()
                );

                continue;
            }

            painelBotoes.add(
                    criarBotao(texto)
            );
        }


        JPanel centro =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        centro.setBackground(
                new Color(18, 18, 18)
        );

        centro.add(
                painelDisplay,
                BorderLayout.CENTER
        );

        centro.add(
                painelBotoes,
                BorderLayout.SOUTH
        );


        painel.add(
                centro,
                BorderLayout.CENTER
        );


        return painel;
    }


    // =========================================================
    // CRIAÇÃO DOS BOTÕES
    // =========================================================

    private JButton criarBotao(String texto) {

        JButton botao =
                new JButton(texto);

        botao.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        botao.setFocusPainted(false);

        botao.setBorderPainted(false);

        botao.setOpaque(true);


        // OPERADORES

        if (
                texto.equals("+") ||
                        texto.equals("-") ||
                        texto.equals("×") ||
                        texto.equals("÷") ||
                        texto.equals("=")
        ) {

            botao.setBackground(
                    new Color(255, 140, 0)
            );

            botao.setForeground(Color.WHITE);

        }

        // BOTÕES ESPECIAIS

        else if (
                texto.equals("AC") ||
                        texto.equals("←") ||
                        texto.equals("%")
        ) {

            botao.setBackground(
                    new Color(65, 65, 65)
            );

            botao.setForeground(
                    new Color(220, 220, 220)
            );

        }

        // NÚMEROS

        else {

            botao.setBackground(
                    new Color(45, 45, 45)
            );

            botao.setForeground(Color.WHITE);
        }


        botao.addActionListener(e -> {

            tratarBotao(
                    e.getActionCommand()
            );

        });


        return botao;
    }


    // =========================================================
    // TRATAMENTO DOS BOTÕES
    // =========================================================

    private void tratarBotao(String botao) {

        // NÚMEROS

        if (botao.matches("[0-9]")) {

            adicionarNumero(botao);

            return;
        }


        // PONTO

        if (botao.equals(".")) {

            adicionarPonto();

            return;
        }


        // OPERAÇÕES

        if (
                botao.equals("+") ||
                        botao.equals("-") ||
                        botao.equals("×") ||
                        botao.equals("÷")
        ) {

            selecionarOperacao(botao);

            return;
        }


        // IGUAL

        if (botao.equals("=")) {

            calcular();

            return;
        }


        // LIMPAR

        if (botao.equals("AC")) {

            limpar();

            return;
        }


        // APAGAR

        if (botao.equals("←")) {

            apagar();

            return;
        }


        // PORCENTAGEM

        if (botao.equals("%")) {

            porcentagem();
        }
    }


    // =========================================================
    // ADICIONAR NÚMERO
    // =========================================================

    private void adicionarNumero(String numero) {

        if (novoNumero) {

            entradaAtual = numero;

            novoNumero = false;

        } else {

            entradaAtual += numero;
        }


        atualizarDisplay();
    }


    // =========================================================
    // ADICIONAR PONTO
    // =========================================================

    private void adicionarPonto() {

        if (novoNumero) {

            entradaAtual = "0.";

            novoNumero = false;

        } else if (!entradaAtual.contains(".")) {

            entradaAtual += ".";
        }


        atualizarDisplay();
    }


    // =========================================================
    // SELECIONAR OPERAÇÃO
    // =========================================================

    private void selecionarOperacao(
            String operador
    ) {

        // Se já existe uma operação e o usuário
        // digitou outro número, calcula primeiro.

        if (
                operacaoAtual != null &&
                        !novoNumero
        ) {

            calcular();
        }


        primeiroNumero =
                Double.parseDouble(
                        entradaAtual
                );


        switch (operador) {

            case "+":
                operacaoAtual =
                        Operacao.SOMA;
                break;

            case "-":
                operacaoAtual =
                        Operacao.SUBTRACAO;
                break;

            case "×":
                operacaoAtual =
                        Operacao.MULTIPLICACAO;
                break;

            case "÷":
                operacaoAtual =
                        Operacao.DIVISAO;
                break;
        }


        novoNumero = true;


        // MOSTRA:
        // 6 +

        atualizarDisplay();
    }


    // =========================================================
    // CALCULAR
    // =========================================================

    private void calcular() {

        if (operacaoAtual == null) {
            return;
        }


        double segundoNumero;

        try {

            segundoNumero =
                    Double.parseDouble(
                            entradaAtual
                    );

        } catch (NumberFormatException erro) {

            return;
        }


        double resultado;


        try {

            resultado =
                    calculadora.calcular(
                            primeiroNumero,
                            segundoNumero,
                            operacaoAtual
                    );

        } catch (ArithmeticException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

            limpar();

            return;
        }


        String simbolo =
                obterSimbolo(
                        operacaoAtual
                );


        // MONTA O HISTÓRICO

        String registro =
                formatar(primeiroNumero)
                        + " "
                        + simbolo
                        + " "
                        + formatar(segundoNumero)
                        + " = "
                        + formatar(resultado);


        // SALVA NO HISTÓRICO

        historico.adicionar(
                registro
        );

        historicoModel.addElement(
                registro
        );


        // MOSTRA RESULTADO

        entradaAtual =
                formatar(resultado);

        display.setText(
                entradaAtual
        );


        // PREPARA PARA PRÓXIMA OPERAÇÃO

        primeiroNumero = resultado;

        novoNumero = true;

        operacaoAtual = null;
    }


    // =========================================================
    // ATUALIZAR DISPLAY
    // =========================================================

    private void atualizarDisplay() {

        if (operacaoAtual == null) {

            display.setText(
                    entradaAtual
            );

            return;
        }


        String simbolo =
                obterSimbolo(
                        operacaoAtual
                );


        if (novoNumero) {

            // Exemplo:
            // 6 +

            display.setText(
                    formatar(primeiroNumero)
                            + " "
                            + simbolo
            );

        } else {

            // Exemplo:
            // 6 + 5

            display.setText(
                    formatar(primeiroNumero)
                            + " "
                            + simbolo
                            + " "
                            + entradaAtual
            );
        }
    }


    // =========================================================
    // OBTER SÍMBOLO
    // =========================================================

    private String obterSimbolo(
            Operacao operacao
    ) {

        if (operacao == null) {
            return "";
        }


        switch (operacao) {

            case SOMA:
                return "+";

            case SUBTRACAO:
                return "-";

            case MULTIPLICACAO:
                return "×";

            case DIVISAO:
                return "÷";

            default:
                return "";
        }
    }


    // =========================================================
    // LIMPAR
    // =========================================================

    private void limpar() {

        entradaAtual = "0";

        primeiroNumero = 0;

        operacaoAtual = null;

        novoNumero = true;

        display.setText("0");
    }


    // =========================================================
    // APAGAR
    // =========================================================

    private void apagar() {

        if (novoNumero) {
            return;
        }


        if (entradaAtual.length() > 1) {

            entradaAtual =
                    entradaAtual.substring(
                            0,
                            entradaAtual.length() - 1
                    );

        } else {

            entradaAtual = "0";
        }


        atualizarDisplay();
    }


    // =========================================================
    // PORCENTAGEM
    // =========================================================

    private void porcentagem() {

        try {

            double numero =
                    Double.parseDouble(
                            entradaAtual
                    );

            numero = numero / 100;

            entradaAtual =
                    formatar(numero);

            novoNumero = false;

            atualizarDisplay();

        } catch (NumberFormatException erro) {

            entradaAtual = "0";
        }
    }


    // =========================================================
    // FORMATAR NÚMERO
    // =========================================================

    private String formatar(double numero) {

        if (numero == (long) numero) {

            return String.valueOf(
                    (long) numero
            );
        }


        return String.valueOf(numero);
    }


    // =========================================================
    // HISTÓRICO
    // =========================================================

    private JPanel criarHistorico() {

        JPanel painel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );


        painel.setPreferredSize(
                new Dimension(230, 0)
        );


        painel.setBackground(
                new Color(25, 25, 25)
        );


        painel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        10,
                        20,
                        20
                )
        );


        JLabel titulo =
                new JLabel("HISTÓRICO");


        titulo.setForeground(
                Color.WHITE
        );


        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );


        painel.add(
                titulo,
                BorderLayout.NORTH
        );


        // LISTA DO HISTÓRICO

        historicoModel =
                new DefaultListModel<>();


        JList<String> lista =
                new JList<>(
                        historicoModel
                );


        lista.setBackground(
                new Color(30, 30, 30)
        );


        lista.setForeground(
                new Color(220, 220, 220)
        );


        lista.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );


        lista.setSelectionBackground(
                new Color(255, 140, 0)
        );


        JScrollPane scroll =
                new JScrollPane(
                        lista
                );


        scroll.setBorder(null);


        painel.add(
                scroll,
                BorderLayout.CENTER
        );


        // BOTÃO LIMPAR HISTÓRICO

        JButton limpar =
                new JButton(
                        "Limpar histórico"
                );


        limpar.setFocusPainted(false);


        limpar.setForeground(
                Color.WHITE
        );


        limpar.setBackground(
                new Color(50, 50, 50)
        );


        limpar.addActionListener(e -> {

            historico.limpar();

            historicoModel.clear();

        });


        painel.add(
                limpar,
                BorderLayout.SOUTH
        );


        return painel;
    }
}