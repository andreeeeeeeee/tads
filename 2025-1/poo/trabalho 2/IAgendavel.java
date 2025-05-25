
import java.util.Date;

public interface IAgendavel {
  public void agendar(Date dataHora) throws AgendaNaoDisponivelException;
}
