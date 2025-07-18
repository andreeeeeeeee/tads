import java.util.Date;
import java.util.UUID;

public class Cliente {
  private UUID id;
  private String nome;
  private String email;
  private Date dataNascimento;
  private String endereco;

  public Cliente() {
    this.id = UUID.randomUUID();
  }

  public Cliente(String nome, String email, Date dataNascimento, String endereco) {
    this.id = UUID.randomUUID();
    this.nome = nome;
    this.email = email;
    this.dataNascimento = dataNascimento;
    this.endereco = endereco;
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getDataNascimento() {
    return this.dataNascimento;
  }

  public String getDataString() {
    return String.format("%1$td/%1$tm/%1$tY", this.dataNascimento);
  }

  public void setDataNascimento(Date dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public String getEndereco() {
    return this.endereco;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

}
