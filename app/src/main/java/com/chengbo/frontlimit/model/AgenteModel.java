package com.chengbo.frontlimit.model;

import java.util.Date;

public class AgenteModel {

    private String agenteId;
    private String nome;
    private String genero;
    private String datanascimento;
    private String documentoIdentificacao;
    private String email;
    private String password;
    private String telemovel;
    private String morada;

    public AgenteModel() {
    }

    public AgenteModel(String agenteId, String nome, String genero, String datanascimento, String documentoIdentificacao, String email, String password, String telemovel, String morada) {
        this.agenteId = agenteId;
        this.nome = nome;
        this.genero = genero;
        this.datanascimento = datanascimento;
        this.documentoIdentificacao = documentoIdentificacao;
        this.email = email;
        this.password = password;
        this.telemovel = telemovel;
        this.morada = morada;
    }

    public String getAgenteId() {
        return agenteId;
    }

    public void setAgenteId(String agenteId) {
        this.agenteId = agenteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(String datanascimento) {
        this.datanascimento = datanascimento;
    }

    public String getDocumentoIdentificacao() {
        return documentoIdentificacao;
    }

    public void setDocumentoIdentificacao(String documentoIdentificacao) {
        this.documentoIdentificacao = documentoIdentificacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }
}
