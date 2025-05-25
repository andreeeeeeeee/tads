import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Medico extends Responsavel {
  private String crm;
  private final List<String> especialidades;

  public Medico(String nome, String cpf, String email, String telefone, String crm) {
    super(nome, cpf, email, telefone);
    this.crm = crm;
    this.especialidades = new ArrayList<>();
  }

  @Override
  public String toString() {
    String retorno = getNome() + " - ";
    for (String especialidade : getEspecialidades()) {
      retorno += especialidade + ", ";
    }
    return retorno;
  }

  public String getCrm() {
    return this.crm;
  }

  public void setCrm(String crm) {
    this.crm = crm;
  }

  public List<String> getEspecialidades() {
    return this.especialidades;
  }

  public void addEspecialidade(String especialidade) {
    this.especialidades.add(especialidade);
  }

  public Consulta agendarConsulta(Date dataHora, TipoConsulta tipoConsulta, Paciente paciente) {
    Consulta consulta = new Consulta(tipoConsulta, this, paciente);
    try {
      consulta.agendar(dataHora);
    } catch (AgendaNaoDisponivelException e) {
      System.err.println(e.getMessage());
    }
    return consulta;
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