package com.example.avaliacao.BO;

public class GrupoProduto {
    private int empresa;
    private int grupoProduto;
    private String descricaoGrupoProduto;
    private double percDesconto;
    private String tipoComplemento;


    public GrupoProduto(int empresa, int grupoProduto, String descricaoGrupoProduto, double percDesconto, String tipoComplemento) {
        this.empresa = empresa;
        this.grupoProduto = grupoProduto;
        this.descricaoGrupoProduto = descricaoGrupoProduto;
        this.percDesconto = percDesconto;
        this.tipoComplemento = tipoComplemento;
    }


    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public int getGrupoProduto() {
        return grupoProduto;
    }

    public void setGrupoProduto(int grupoProduto) {
        this.grupoProduto = grupoProduto;
    }

    public String getDescricaoGrupoProduto() {
        return descricaoGrupoProduto;
    }

    public void setDescricaoGrupoProduto(String descricaoGrupoProduto) {
        this.descricaoGrupoProduto = descricaoGrupoProduto;
    }

    public double getPercDesconto() {
        return percDesconto;
    }

    public void setPercDesconto(double percDesconto) {
        this.percDesconto = percDesconto;
    }

    public String getTipoComplemento() {
        return tipoComplemento;
    }

    public void setTipoComplemento(String tipoComplemento) {
        this.tipoComplemento = tipoComplemento;
    }
}

