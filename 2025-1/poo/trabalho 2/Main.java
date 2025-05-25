
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
  public static void main(String[] args) {
    try {

      final Medico medico = new Medico("Dr. Auzio Varella", "99999999999", "doutorauzio@yahoo.com", "123456789",
          "123456");
      final Paciente paciente = new Paciente("Coitadinho Inocêncio", "11111111111", "vereador@coitadolandia.gov.cl",
          "987654321", Sexo.MASCULINO, TipoSanguineo.O_POSITIVO);
      final Tecnico tecnico = new Tecnico("Jorge Ben", "55555555555", "email@email.com", "789456123");

      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date dataAgenda = sdf.parse("30/05/2025");
      medico.abrirAgenda(dataAgenda);
      tecnico.abrirAgenda(dataAgenda);

      System.out.println("Horários disponíveis para " + sdf.format(dataAgenda) + ":");
      medico.getAgendas().forEach(agenda -> {
        if (agenda.getData().equals(dataAgenda)) {
          agenda.getHorarios().forEach(horario -> {
            System.out.println("- " + horario.getDataHora() + " (Ocupado: " + horario.isOcupado() + ")");
          });
        }
      });

      Consulta consulta = medico.agendarConsulta(new Date(), TipoConsulta.CARDIOLOGICA, paciente);
      consulta.encerrar("Febre alta e dor de cabeça", "Hemograma completo", "Paracetamol 500mg");
      Exame exame = tecnico.agendarExame(new Date(), "Hemograma completo");
      exame.encerrar("Hemoglobina normal.");

      System.out.println("Laudo do exame: " + exame.getLaudo());
      Prontuario prontuario = consulta.getProntuario();
      System.out.println("Prontuário gerado: " + prontuario.getSintomas());
    } catch (ParseException e) {
      System.err.println(e.getMessage());
    }
  }
}
