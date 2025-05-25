import java.util.Date;

public class Tecnico extends Responsavel {

  public Tecnico(String nome, String cpf, String email, String telefone) {
    super(nome, cpf, email, telefone);
  }

  public Exame agendarExame(Date dataHora, String nome) {
    Exame exame = new Exame(nome, this);
    try {
      exame.agendar(dataHora);
    } catch (AgendaNaoDisponivelException e) {
      System.err.println(e.getMessage());
    }
    return exame;
  }
}
