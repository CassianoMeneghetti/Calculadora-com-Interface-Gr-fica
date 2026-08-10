package service;

import model.Operacao;

public class Calculadora {

    public double calcular(
            double numero1,
            double numero2,
            Operacao operacao
    ) {

        switch (operacao) {

            case SOMA:
                return numero1 + numero2;

            case SUBTRACAO:
                return numero1 - numero2;

            case MULTIPLICACAO:
                return numero1 * numero2;

            case DIVISAO:

                if (numero2 == 0) {
                    throw new ArithmeticException(
                            "Não é possível dividir por zero."
                    );
                }

                return numero1 / numero2;

            default:
                throw new IllegalArgumentException(
                        "Operação inválida."
                );
        }
    }
}