package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator {
    double firstOperation = 0;
    double secondOperation = 0;
    String operator = "=";
    JTextField display;

    public static void main(String[] args) {
        Calculator gui = new Calculator();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame("Мой первый калькулятор");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300); // изменен размер фрейма

        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new GridLayout(4, 4, 10, 10));
        numberPanel.setBackground(Color.darkGray);

        String[] buttonLabels = {"1", "2", "3", "-", "4", "5", "6", "*", "7", "8", "9", "/", ".", "0", "C", "="};

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setPreferredSize(new Dimension(80, 50));
            button.addActionListener(new MyActionListener());
            numberPanel.add(button);
        }

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        display = new JTextField(20);
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(display, BorderLayout.CENTER);

        JButton plusButton = new JButton("+");
        plusButton.setPreferredSize(new Dimension(114, 50));
        plusButton.addActionListener(e -> {
            if (!display.getText().isEmpty()) {
                firstOperation = Double.parseDouble(display.getText());
                operator = "+";
                display.setText("");
            }
        });
        topPanel.add(plusButton, BorderLayout.EAST);

        topPanel.setBackground(Color.darkGray);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(numberPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String input = e.getActionCommand();

            if ("C".equals(input)) {
                display.setText("");
                firstOperation = 0;
                secondOperation = 0;
                operator = "=";
            }
            // Обработка кнопок операторов и "="
            else if ("=".equals(input) || "+".equals(input) || "-".equals(input) || "*".equals(input) || "/".equals(input)) {
                if (!display.getText().isEmpty()) {
                    secondOperation = Double.parseDouble(display.getText());

                    if (!"=".equals(operator)) {
                        double result = calculate();
                        display.setText(String.valueOf(result));
                        firstOperation = result;
                    } else {
                        firstOperation = secondOperation;
                    }

                    if ("=".equals(input)) {
                        operator = "=";
                    } else {
                        operator = input;
                        display.setText("");
                    }
                }
            }
            else {
                display.setText(display.getText() + input);
            }
        }

        private double calculate() {
            switch (operator) {
                case "+":
                    return firstOperation + secondOperation;
                case "-":
                    return firstOperation - secondOperation;
                case "*":
                    return firstOperation * secondOperation;
                case "/":
                    if (secondOperation != 0) {
                        return firstOperation / secondOperation;
                    } else {
                        display.setText("Ошибка: Деление на ноль");
                        firstOperation = 0;
                        secondOperation = 0;
                        operator = "=";
                        return 0;
                    }
                default:
                    return secondOperation;
            }
        }
    }
}