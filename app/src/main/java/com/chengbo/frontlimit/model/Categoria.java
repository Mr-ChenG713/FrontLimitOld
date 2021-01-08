package com.chengbo.frontlimit.model;

public class Categoria {

    private String categoriaid;
    private String nomeCategoria;

    public Categoria() {
    }

    public Categoria(String categoriaid, String nomeCategoria) {
        this.categoriaid = categoriaid;
        this.nomeCategoria = nomeCategoria;
    }

    public String getCategoriaid() {
        return categoriaid;
    }

    public void setCategoriaid(String categoriaid) {
        this.categoriaid = categoriaid;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}
