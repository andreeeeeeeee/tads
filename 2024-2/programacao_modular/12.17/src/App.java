import java.util.HashMap;
import java.util.Map;

class App {
    public static void main(String[] args) {
        int horasSemanais = 20;
        String nivel = "Junior II";
        Map<String, String> ponto = new HashMap<String, String>();
        ponto.put("Entrada 1", "14:16:22");
        ponto.put("Saída 1", "17:16:22");
        ponto.put("Entrada 2", "18:17:10");
        ponto.put("Saída 2", "22:10:15");

        int segundosTrabalhados = Controle.segundosTrabalhados(ponto);
        // System.out.println(segundosTrabalhados);

        int segundosExtras = Controle.segundosExtras(ponto, horasSemanais);
        int segundosNormais = Controle.segundosNormais(horasSemanais);
        // System.out.println(segundosExtras);

        float valorPagamento = Controle.valorPagamento(ponto, nivel, horasSemanais);
        // System.out.println(valorPagamento);

        System.out.println("Quantas horas trabalhadas:");
        System.out.println(Controle.formataSegundos(segundosTrabalhados));

        System.out.println("Quantas dentro do contrato:");
        System.out.println(Controle.formataSegundos(segundosNormais));

        System.out.println("Quantas horas extras:");
        System.out.println(Controle.formataSegundos(segundosExtras));

        System.out.println("Quanto pagar:");
        System.out.printf("R$ %.2f\n",valorPagamento);
    }
}