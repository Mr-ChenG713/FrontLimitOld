package com.chengbo.frontlimit.model;

public class Pais {

    private String paisid;
    private String nomePais;

    public Pais() {
    }

    public Pais(String paisid, String nomePais) {
        this.paisid = paisid;
        this.nomePais = nomePais;
    }

    public String getPaisid() {
        return paisid;
    }

    public void setPaisid(String paisid) {
        this.paisid = paisid;
    }

    public String getNomePais() {
        return nomePais;
    }

    public void setNomePais(String nomePais) {
        this.nomePais = nomePais;
    }
}
