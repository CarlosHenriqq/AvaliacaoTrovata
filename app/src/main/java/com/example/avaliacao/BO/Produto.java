package com.example.avaliacao.BO;

public class Produto {
    private int empresa;
    private String produto;
    private String descricaoProduto;
    private String apelidoProduto;
    private int grupoProduto;
    private int subgrupoProduto;
    private String situacao;
    private double pesoLiquido;
    private String classificacaoFiscal;
    private String codigoBarras;
    private String colecao;

    public Produto(int empresa, String produto, String descricaoProduto, String apelidoProduto, int grupoProduto, int subgrupoProduto, String situacao, double pesoLiquido, String classificacaoFiscal, String codigoBarras, String colecao) {
        this.empresa = empresa;
        this.produto = produto;
        this.descricaoProduto = descricaoProduto;
        this.apelidoProduto = apelidoProduto;
        this.grupoProduto = grupoProduto;
        this.subgrupoProduto = subgrupoProduto;
        this.situacao = situacao;
        this.pesoLiquido = pesoLiquido;
        this.classificacaoFiscal = classificacaoFiscal;
        this.codigoBarras = codigoBarras;
        this.colecao = colecao;
    }


    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public String getApelidoProduto() {
        return apelidoProduto;
    }

    public void setApelidoProduto(String apelidoProduto) {
        this.apelidoProduto = apelidoProduto;
    }

    public int getGrupoProduto() {
        return grupoProduto;
    }

    public void setGrupoProduto(int grupoProduto) {
        this.grupoProduto = grupoProduto;
    }

    public int getSubgrupoProduto() {
        return subgrupoProduto;
    }

    public void setSubgrupoProduto(int subgrupoProduto) {
        this.subgrupoProduto = subgrupoProduto;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public double getPesoLiquido() {
        return pesoLiquido;
    }

    public void setPesoLiquido(double pesoLiquido) {
        this.pesoLiquido = pesoLiquido;
    }

    public String getClassificacaoFiscal() {
        return classificacaoFiscal;
    }

    public void setClassificacaoFiscal(String classificacaoFiscal) {
        this.classificacaoFiscal = classificacaoFiscal;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getColecao() {
        return colecao;
    }

    public void setColecao(String colecao) {
        this.colecao = colecao;
    }
}