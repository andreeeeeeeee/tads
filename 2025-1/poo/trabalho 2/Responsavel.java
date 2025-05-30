
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Responsavel extends Pessoa {
  private final List<AgendaDisponibilidade> agendas;

  public Responsavel(String nome, String cpf, String email, String telefone) {
    super(nome, cpf, email, telefone);
    this.agendas = new ArrayList<>();
  }

  public List<AgendaDisponibilidade> getAgendas() {
    return this.agendas;
  }

  public void abrirAgenda(Date data) {
    AgendaDisponibilidade agenda = new AgendaDisponibilidade(data);

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(data);

    for (int hora = 9; hora <= 17; hora++) {
      calendar.set(Calendar.HOUR_OF_DAY, hora);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);

      Horario horario = new Horario(calendar.getTime());
      agenda.addHorario(horario);
    }

    this.agendas.add(agenda);
  }

  public void getDisponibilidade(Date data) {
    SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdfDataHora = new SimpleDateFormat("HH\'h'");
    System.out.println("Horários disponíveis para " + sdfData.format(data) + ":");
    for (AgendaDisponibilidade agenda : this.agendas) {
      if (isMesmoDia(agenda.getData(), data)) {
        for (Horario horario : agenda.getHorarios()) {
          System.out.println(
              "- " + sdfDataHora.format(horario.getDataHora()) +
                  " (" + (horario.isOcupado() ? "Ocupado" : "Disponível") + ")");
        }
        return;
      }
    }
    System.out.println("Nenhuma agenda disponível para a data: " + data);
  }

  public boolean isHorarioDisponivel(Date dataHora) {
    for (AgendaDisponibilidade agenda : this.agendas) {
      if (isMesmoDia(agenda.getData(), dataHora)) {
        for (Horario horario : agenda.getHorarios()) {
          if (horario.getDataHora().equals(dataHora)) {
            return !horario.isOcupado();
          }
        }
      }
    }
    return false;
  }

  public boolean isMesmoDia(Date d1, Date d2) {
    java.util.Calendar c1 = java.util.Calendar.getInstance();
    java.util.Calendar c2 = java.util.Calendar.getInstance();
    c1.setTime(d1);
    c2.setTime(d2);
    return c1.get(java.util.Calendar.YEAR) == c2.get(java.util.Calendar.YEAR)
        && c1.get(java.util.Calendar.MONTH) == c2.get(java.util.Calendar.MONTH)
        && c1.get(java.util.Calendar.DAY_OF_MONTH) == c2.get(java.util.Calendar.DAY_OF_MONTH);
  }
}
