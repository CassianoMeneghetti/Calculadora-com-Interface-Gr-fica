public class Calculadora {

    private double primeiroNumero;
    private double segundoNumero;

    public double somar(){
        return primeiroNumero + segundoNumero;
    }

    public double subtrair(){
        return primeiroNumero - segundoNumero;
    }

    public double dividir(){

        if (segundoNumero == 0){
            throw new ArithmeticException(
                    "Não é possível dividir por zero"
            );
        }

        return primeiroNumero / segundoNumero;
    }

    public double multiplicar(){
        return primeiroNumero * segundoNumero;
    }
}
