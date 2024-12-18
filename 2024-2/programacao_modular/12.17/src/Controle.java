import java.util.Map;

public class Controle {
    static Map<String, Integer> valores = Map.of(
            "Junior I", 25,
            "Junior II", 30,
            "Pleno I", 45,
            "Pleno II", 55,
            "Senior I", 70,
            "Senior II", 100);

    public static int valorHora(String nivel) {
        return valores.get(nivel);
    }

    public static int segundosTrabalhados(Map<String, String> ponto) {
        final int HORAS_DIA = 24 * 60 * 60;
        int segundosTrabalhados = 0;

        for (int i = 1; i <= ponto.size() / 2; i++) {
            String saida = ponto.get("Saída " + i);
            String entrada = ponto.get("Entrada " + i);

            int saidaPrimeiroPonto = saida.indexOf(':');
            int saidaSegundoPonto = saida.lastIndexOf(':');

            int horaSaida = Integer.valueOf(saida.substring(0, saidaPrimeiroPonto));
            int minutoSaida = Integer.valueOf(saida.substring(saidaPrimeiroPonto + 1, saidaSegundoPonto));
            int segundoSaida = Integer.valueOf(saida.substring(saidaSegundoPonto + 1));

            int entradaPrimeiroPonto = entrada.indexOf(':');
            int entradaSegundoPonto = entrada.lastIndexOf(':');

            int horaEntrada = Integer.valueOf(entrada.substring(0, entradaPrimeiroPonto));
            int minutoEntrada = Integer.valueOf(entrada.substring(entradaPrimeiroPonto + 1, entradaSegundoPonto));
            int segundoEntrada = Integer.valueOf(entrada.substring(entradaSegundoPonto + 1));

            int segundosTotaisSaida = horaSaida * 3600 + minutoSaida * 60 + segundoSaida;
            int segundosTotaisEntrada = horaEntrada * 3600 + minutoEntrada * 60 + segundoEntrada;

            if (segundosTotaisSaida >= segundosTotaisEntrada) {
                segundosTrabalhados += segundosTotaisSaida - segundosTotaisEntrada;
            } else {
                segundosTrabalhados += HORAS_DIA - segundosTotaisEntrada + segundosTotaisSaida;
            }
        }

        return segundosTrabalhados;
    }

    public static int segundosNormais(int horasSemanais) {
        return horasSemanais / 5 * 3600;
    }

    public static int segundosExtras(Map<String, String> ponto, int horasSemanais) {
        int segundosTrabalhados = segundosTrabalhados(ponto);
        int segundosNormais = segundosNormais(horasSemanais);

        int segundosExtras = segundosTrabalhados - segundosNormais;

        return segundosExtras;
    }

    public static float valorPagamento(
            Map<String, String> ponto,
            String nivel,
            int horasSemanais) {
        int segundosNormais = segundosNormais(horasSemanais);
        int segundosExtras = segundosExtras(ponto, horasSemanais);
        float valorHora = (float) (valorHora(nivel) / 3600f);

        float pagamentoNormais = segundosNormais * valorHora;
        float pagamentoExtras = segundosExtras * valorHora * 2;

        return pagamentoExtras + pagamentoNormais;
    }

    public static String formataSegundos(int segundos) {
        int hora = segundos / 3600;
        int minuto = segundos % 3600 / 60;
        int segundo = segundos % 3600 % 60;

        return String.format("%02d:%02d:%02d", hora, minuto, segundo);
    }
}
