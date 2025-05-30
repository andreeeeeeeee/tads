
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
  public static void main(String[] args) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date dtNascimento = sdf.parse("01/01/2000");

      final Medico medico = new Medico(
          "Dr. Auzio Varella",
          "99999999999",
          "doutorauzio@yahoo.com",
          "123456789",
          "123456");
      final Paciente paciente = new Paciente(
          "Coitadinho Inocêncio",
          "11111111111",
          dtNascimento,
          "vereador@coitadolandia.gov.cl",
          "987654321",
          Sexo.MASCULINO,
          TipoSanguineo.O_POSITIVO);
      final Tecnico tecnico = new Tecnico(
          "Roger Machado",
          "55555555555",
          "email@email.com",
          "789456123");

      System.out.println("Cadastro de profissionais e paciente:");
      System.out.println(medico);
      System.out.println(paciente);
      System.out.println(tecnico);
      System.out.println();

      Date dataAgenda = sdf.parse("30/05/2025");
      medico.abrirAgenda(dataAgenda);
      tecnico.abrirAgenda(dataAgenda);

      medico.getDisponibilidade(dataAgenda);
      System.out.println();

      Consulta consulta = medico.agendarConsulta(
          medico.getAgendas().get(0).getHorarios().get(0).getDataHora(),
          TipoConsulta.CARDIOLOGICA,
          paciente);
      System.out.println("Consulta agendada: " + consulta);
      System.out.println();
      medico.getDisponibilidade(dataAgenda);
      System.out.println();

      consulta.encerrar("Febre alta e dor de cabeça", "Hemograma completo", "Paracetamol 500mg");
      System.out.println("Prontuário gerado: " + consulta.getProntuario());
      System.out.println(consulta);
      System.out.println(consulta.getPaciente().getHistoricoConsultas());
      System.out.println();

      Exame exame = tecnico.agendarExame(
          tecnico.getAgendas().get(0).getHorarios().get(0).getDataHora(),
          "Hemograma completo");
      System.out.println("Exame agendado: " + exame);
      System.out.println();
      tecnico.getDisponibilidade(dataAgenda);
      System.out.println();
      System.out.println("Teste de agendamento para horário ocupado:");
      tecnico.agendarExame(
          tecnico.getAgendas().get(0).getHorarios().get(0).getDataHora(),
          "Hemograma completo");

      System.out.println();
      exame.encerrar("Hemoglobina normal.");
      System.out.println("Laudo do exame: " + exame.getLaudo());
      System.out.println(exame);
      System.out.println();

    } catch (ParseException e) {
      System.err.println(e.getMessage());
    }
  }
}
