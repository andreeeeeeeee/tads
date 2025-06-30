package com.edulivre.models;

import java.sql.Date;
import java.util.UUID;

public class Matricula {
  private int id;
  private UUID usuarioID;
  private UUID cursoID;
  private Date dataMatricula;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public UUID getUsuarioID() {
    return usuarioID;
  }

  public void setUsuarioID(UUID usuarioID) {
    this.usuarioID = usuarioID;
  }

  public UUID getCursoID() {
    return cursoID;
  }

  public void setCursoID(UUID cursoID) {
    this.cursoID = cursoID;
  }

  public Date getDataMatricula() {
    return dataMatricula;
  }

  public void setDataMatricula(Date dataMatricula) {
    this.dataMatricula = dataMatricula;
  }

}
