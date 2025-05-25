
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgendaDisponibilidade {
  private Date data;
  private final List<Horario> horarios;

  public AgendaDisponibilidade(Date data) {
    this.data = data;
    this.horarios = new ArrayList<>();
  }

  public Date getData() {
    return this.data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public List<Horario> getHorarios() {
    return this.horarios;
  }

  public void addHorario(Horario horario) {
    this.horarios.add(horario);
  }
}
