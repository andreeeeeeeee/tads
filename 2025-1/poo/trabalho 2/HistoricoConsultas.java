
import java.util.ArrayList;
import java.util.List;

public class HistoricoConsultas {
  private final List<Consulta> consultas;

  public HistoricoConsultas() {
    this.consultas = new ArrayList<>();
  }

  public List<Consulta> getConsultas() {
    return this.consultas;
  }

  public void addConsulta(Consulta consulta) {
    this.consultas.add(consulta);
  }

}
