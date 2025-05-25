
import java.util.Date;

public class Horario {
  private Date dataHora; // Representa o horário específico
  private boolean ocupado; // Flag para indicar se o horário está ocupado

  public Horario(Date dataHora) {
    this.dataHora = dataHora;
    this.ocupado = false; // Inicialmente, o horário está disponível
  }

  public Date getDataHora() {
    return this.dataHora;
  }

  public void setDataHora(Date dataHora) {
    this.dataHora = dataHora;
  }

  public boolean isOcupado() {
    return this.ocupado;
  }

  public void setOcupado(boolean ocupado) {
    this.ocupado = ocupado;
  }
}