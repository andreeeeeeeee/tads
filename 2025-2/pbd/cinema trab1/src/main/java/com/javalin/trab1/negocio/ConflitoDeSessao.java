package com.javalin.trab1.negocio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConflitoDeSessao {
  private int sessaoId1;
  private int sessaoId2;
  private int filmeId;
  private String tituloFilme;
  private int salaId1;
  private int salaId2;
  private LocalDateTime horario;

  public ConflitoDeSessao() {
  }

  public ConflitoDeSessao(int sessaoId1, int sessaoId2, int filmeId, String tituloFilme,
      int salaId1, int salaId2, LocalDateTime horario) {
    this.sessaoId1 = sessaoId1;
    this.sessaoId2 = sessaoId2;
    this.filmeId = filmeId;
    this.tituloFilme = tituloFilme;
    this.salaId1 = salaId1;
    this.salaId2 = salaId2;
    this.horario = horario;
  }

  public int getSessaoId1() {
    return sessaoId1;
  }

  public void setSessaoId1(int sessaoId1) {
    this.sessaoId1 = sessaoId1;
  }

  public int getSessaoId2() {
    return sessaoId2;
  }

  public void setSessaoId2(int sessaoId2) {
    this.sessaoId2 = sessaoId2;
  }

  public int getFilmeId() {
    return filmeId;
  }

  public void setFilmeId(int filmeId) {
    this.filmeId = filmeId;
  }

  public String getTituloFilme() {
    return tituloFilme;
  }

  public void setTituloFilme(String tituloFilme) {
    this.tituloFilme = tituloFilme;
  }

  public int getSalaId1() {
    return salaId1;
  }

  public void setSalaId1(int salaId1) {
    this.salaId1 = salaId1;
  }

  public int getSalaId2() {
    return salaId2;
  }

  public void setSalaId2(int salaId2) {
    this.salaId2 = salaId2;
  }

  public LocalDateTime getHorario() {
    return horario;
  }

  public void setHorario(LocalDateTime horario) {
    this.horario = horario;
  }

  public String getHorarioFormatado() {
    if (horario == null) {
      return "";
    }
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH'h'mm'm'");
    return horario.format(formatter);
  }

  @Override
  public String toString() {
    return "ConflitoDeSessao{" +
        "sessaoId1=" + sessaoId1 +
        ", sessaoId2=" + sessaoId2 +
        ", filmeId=" + filmeId +
        ", tituloFilme='" + tituloFilme + '\'' +
        ", salaId1=" + salaId1 +
        ", salaId2=" + salaId2 +
        ", horario=" + horario +
        '}';
  }
}
