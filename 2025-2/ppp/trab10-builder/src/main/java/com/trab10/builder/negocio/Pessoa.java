package com.trab10.builder.negocio;

public class Pessoa {
  private String nome;
  private String sobrenome;
  private String email;
  private String telefone;
  private String endereco;
  private String dataNascimento;
  private String cargo;
  private String documento;
  private String cidade;
  private String estado;

  private Pessoa(Builder builder) {
    this.nome = builder.nome;
    this.sobrenome = builder.sobrenome;
    this.email = builder.email;
    this.telefone = builder.telefone;
    this.endereco = builder.endereco;
    this.dataNascimento = builder.dataNascimento;
    this.cargo = builder.cargo;
    this.documento = builder.documento;
    this.cidade = builder.cidade;
    this.estado = builder.estado;
  }

  public String getNome() {
    return nome;
  }

  public String getSobrenome() {
    return sobrenome;
  }

  public String getEmail() {
    return email;
  }

  public String getTelefone() {
    return telefone;
  }

  public String getEndereco() {
    return endereco;
  }

  public String getDataNascimento() {
    return dataNascimento;
  }

  public String getCargo() {
    return cargo;
  }

  public String getDocumento() {
    return documento;
  }

  public String getCidade() {
    return cidade;
  }

  public String getEstado() {
    return estado;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("=== Dados da Pessoa ===\n");
    if (nome != null)
      sb.append("Nome: ").append(nome).append("\n");
    if (sobrenome != null)
      sb.append("Sobrenome: ").append(sobrenome).append("\n");
    if (email != null)
      sb.append("Email: ").append(email).append("\n");
    if (telefone != null)
      sb.append("Telefone: ").append(telefone).append("\n");
    if (endereco != null)
      sb.append("Endereço: ").append(endereco).append("\n");
    if (cidade != null)
      sb.append("Cidade: ").append(cidade).append("\n");
    if (estado != null)
      sb.append("Estado: ").append(estado).append("\n");
    if (dataNascimento != null)
      sb.append("Data de Nascimento: ").append(dataNascimento).append("\n");
    if (cargo != null)
      sb.append("Cargo: ").append(cargo).append("\n");
    if (documento != null)
      sb.append("Documento: ").append(documento).append("\n");
    sb.append("======================");
    return sb.toString();
  }

  public static class Builder {
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String endereco;
    private String dataNascimento;
    private String cargo;
    private String documento;
    private String cidade;
    private String estado;

    public Builder nome(String nome) {
      this.nome = nome;
      return this;
    }

    public Builder sobrenome(String sobrenome) {
      this.sobrenome = sobrenome;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder telefone(String telefone) {
      this.telefone = telefone;
      return this;
    }

    public Builder endereco(String endereco) {
      this.endereco = endereco;
      return this;
    }

    public Builder dataNascimento(String dataNascimento) {
      this.dataNascimento = dataNascimento;
      return this;
    }

    public Builder cargo(String cargo) {
      this.cargo = cargo;
      return this;
    }

    public Builder documento(String documento) {
      this.documento = documento;
      return this;
    }

    public Builder cidade(String cidade) {
      this.cidade = cidade;
      return this;
    }

    public Builder estado(String estado) {
      this.estado = estado;
      return this;
    }

    public Pessoa build() {
      if (nome == null || nome.trim().isEmpty())
        throw new IllegalArgumentException("O nome é obrigatório!");

      return new Pessoa(this);
    }
  }
}
