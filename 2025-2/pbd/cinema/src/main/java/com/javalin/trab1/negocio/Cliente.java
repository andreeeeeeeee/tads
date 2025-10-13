package com.javalin.trab1.negocio;

public class Cliente {
  private String cpf;
  private String nome;
  private String email;
  private String telefone;

  public Cliente() {
  }

  public Cliente(String cpf, String nome) {
    this.cpf = cpf;
    this.nome = nome;
  }

  public Cliente(String cpf, String nome, String email, String telefone) {
    this.cpf = cpf;
    this.nome = nome;
    this.email = email;
    this.telefone = telefone;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  @Override
  public String toString() {
    return "Cliente{" +
        "cpf='" + cpf + '\'' +
        ", nome='" + nome + '\'' +
        ", email='" + email + '\'' +
        ", telefone='" + telefone + '\'' +
        '}';
  }
}
