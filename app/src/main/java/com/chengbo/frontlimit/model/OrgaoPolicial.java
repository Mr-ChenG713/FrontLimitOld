package com.chengbo.frontlimit.model;

public class OrgaoPolicial {

    private String oragaoPolicialid;
    private String nomeOrgaoPolicial;

    public OrgaoPolicial() {
    }

    public OrgaoPolicial(String oragaoPolicialid, String nomeOrgaoPolicial) {
        this.oragaoPolicialid = oragaoPolicialid;
        this.nomeOrgaoPolicial = nomeOrgaoPolicial;
    }

    public String getOragaoPolicialid() {
        return oragaoPolicialid;
    }

    public void setOragaoPolicialid(String oragaoPolicialid) {
        this.oragaoPolicialid = oragaoPolicialid;
    }

    public String getNomeOrgaoPolicial() {
        return nomeOrgaoPolicial;
    }

    public void setNomeOrgaoPolicial(String nomeOrgaoPolicial) {
        this.nomeOrgaoPolicial = nomeOrgaoPolicial;
    }

}
