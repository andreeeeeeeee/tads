
import java.util.ArrayList;
import java.util.List;

public class HistoricoConsultas {
  private final List<Consulta> consultas;

  public HistoricoConsultas() {
    this.consultas = new ArrayList<>();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Histórico de Consultas:\n");
    for (Consulta consulta : consultas) {
      sb.append(consulta.toString()).append("\n");
    }
    return sb.toString();
  }

  public List<Consulta> getConsultas() {
    return this.consultas;
  }

  public void addConsulta(Consulta consulta) {
    this.consultas.add(consulta);
  }

}
