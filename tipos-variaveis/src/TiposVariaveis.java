public class TiposVariaveis {
    public static void main(String[] args) throws Exception {
        double salarioMinimo = 1100.00;

        short idade = 18;
        int populacao = idade;
        short populacao2 = (short) populacao; // (short) é um cast semelhante ao Number() do JS

        // Variaveis VS Constantes

        int numero = 10;

        numero = 20;

        System.out.println("Exemplo de variavel " + numero);

        final double VALOR_PI = 3.14;

        System.out.println("Exemplo de constantes " + VALOR_PI);
    }
}
