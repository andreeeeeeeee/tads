
import java.util.Date;

public class Horario {
  private Date dataHora;
  private boolean ocupado;

  public Horario(Date dataHora) {
    this.dataHora = dataHora;
    this.ocupado = false;
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