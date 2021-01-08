package com.chengbo.frontlimit.model;

public class Marca {

    private String marcaid;
    private String nomeMarca;


    public Marca() {
    }

    public Marca(String marcaid, String nomeMarca) {
        this.marcaid = marcaid;
        this.nomeMarca = nomeMarca;
    }

    public String getMarcaid() {
        return marcaid;
    }

    public void setMarcaid(String marcaid) {
        this.marcaid = marcaid;
    }

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }

}
