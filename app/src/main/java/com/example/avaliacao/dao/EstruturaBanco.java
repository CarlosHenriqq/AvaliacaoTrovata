package com.example.avaliacao.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EstruturaBanco extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "trovata.db";
    private static final int VERSAO = 9;


    private static final String TABELA_EMPRESA = "empresa";
    private static final String ID_EMPRESA = "_id";
    private static final String NOME_FANTASIA = "nomeFantasia";
    private static final String RAZAO_SOCIAL = "razaoSocial";
    private static final String ENDERECO = "endereco";
    private static final String BAIRRO = "bairro";
    private static final String CEP = "cep";
    private static final String CIDADE = "cidade";
    private static final String TELEFONE = "telefone";
    private static final String CNPJ = "cnpj";
    private static final String FAX = "fax";
    private static final String IE = "ie";


    private static final String TABELA_PRODUTO = "produto";
    private static final String EMPRESA_PRODUTO = "empresa";
    private static final String PRODUTO = "produto";
    private static final String DESCRICAO_PRODUTO = "descricaoProduto";
    private static final String APELIDO_PRODUTO = "apelidoProduto";
    private static final String GRUPO_PRODUTO = "grupoProduto";
    private static final String SUBGRUPO_PRODUTO = "subgrupoProduto";
    private static final String SITUACAO = "situacao";
    private static final String PESO_LIQUIDO = "pesoLiquido";
    private static final String CLASSIFICACAO_FISCAL = "classificacaoFiscal";
    private static final String CODIGO_BARRAS = "codigoBarras";
    private static final String COLECAO = "colecao";


    private static final String TABELA_GRUPO_PRODUTO = "grupo_produto";
    private static final String EMPRESA_GRUPO = "empresa";
    private static final String GRUPO = "grupoProduto";
    private static final String DESCRICAO_GRUPO = "descricaoGrupoProduto";
    private static final String PERC_DESCONTO = "percDesconto";
    private static final String TIPO_COMPLEMENTO = "tipoComplemento";

    public EstruturaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    public static String getNomeBanco() {
        return NOME_BANCO;
    }

    // Empresa
    public static String getTabelaEmpresa() {
        return TABELA_EMPRESA;
    }

    public static String getIdEmpresa() {
        return ID_EMPRESA;
    }

    public static String getNomeFantasia() {
        return NOME_FANTASIA;
    }

    public static String getRazaoSocial() {
        return RAZAO_SOCIAL;
    }

    public static String getEndereco() {
        return ENDERECO;
    }

    public static String getBairro() {
        return BAIRRO;
    }

    public static String getCep() {
        return CEP;
    }

    public static String getCidade() {
        return CIDADE;
    }

    public static String getTelefone() {
        return TELEFONE;
    }

    public static String getCnpj() {
        return CNPJ;
    }

    public static String getFax() {
        return FAX;
    }

    public static String getIe() {
        return IE;
    }

    // Produto
    public static String getTabelaProduto() {
        return TABELA_PRODUTO;
    }

    public static String getEmpresaProduto() {
        return EMPRESA_PRODUTO;
    }

    public static String getProduto() {
        return PRODUTO;
    }

    public static String getDescricaoProduto() {
        return DESCRICAO_PRODUTO;
    }

    public static String getApelidoProduto() {
        return APELIDO_PRODUTO;
    }

    public static String getGrupoProduto() {
        return GRUPO_PRODUTO;
    }

    public static String getSubgrupoProduto() {
        return SUBGRUPO_PRODUTO;
    }

    public static String getSituacao() {
        return SITUACAO;
    }

    public static String getPesoLiquido() {
        return PESO_LIQUIDO;
    }

    public static String getClassificacaoFiscal() {
        return CLASSIFICACAO_FISCAL;
    }

    public static String getCodigoBarras() {
        return CODIGO_BARRAS;
    }

    public static String getColecao() {
        return COLECAO;
    }

  //grupoProduto
    public static String getTabelaGrupoProduto() {
        return TABELA_GRUPO_PRODUTO;
    }

    public static String getEmpresaGrupo() {
        return EMPRESA_GRUPO;
    }

    public static String getGrupo() {
        return GRUPO;
    }

    public static String getDescricaoGrupo() {
        return DESCRICAO_GRUPO;
    }

    public static String getPercDesconto() {
        return PERC_DESCONTO;
    }

    public static String getTipoComplemento() {
        return TIPO_COMPLEMENTO;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlEmpresa = "CREATE TABLE " + TABELA_EMPRESA + " ("

                + ID_EMPRESA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOME_FANTASIA + " TEXT NOT NULL, "
                + RAZAO_SOCIAL + " TEXT NOT NULL, "
                + ENDERECO + " TEXT, "
                + BAIRRO + " TEXT, "
                + CEP + " TEXT, "
                + CIDADE + " TEXT, "
                + TELEFONE + " TEXT, "
                + CNPJ + " TEXT, "
                + FAX + " TEXT, "
                + IE + " TEXT)";
        db.execSQL(sqlEmpresa);


        String sqlProduto = "CREATE TABLE " + TABELA_PRODUTO + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMPRESA_PRODUTO + " INTEGER NOT NULL, "
                + PRODUTO + " TEXT NOT NULL, "
                + DESCRICAO_PRODUTO + " TEXT, "
                + APELIDO_PRODUTO + " TEXT, "
                + GRUPO_PRODUTO + " INTEGER, "
                + SUBGRUPO_PRODUTO + " INTEGER, "
                + SITUACAO + " TEXT, "
                + PESO_LIQUIDO + " REAL, "
                + CLASSIFICACAO_FISCAL + " TEXT, "
                + CODIGO_BARRAS + " TEXT, "
                + COLECAO + " TEXT, "
                + "FOREIGN KEY (" + EMPRESA_PRODUTO + ") REFERENCES " + TABELA_EMPRESA + "(" + ID_EMPRESA + "))";
        db.execSQL(sqlProduto);


        String sqlGrupoProduto = "CREATE TABLE " + TABELA_GRUPO_PRODUTO + " ("
                + EMPRESA_GRUPO + " INTEGER NOT NULL, "
                + GRUPO + " INTEGER NOT NULL, "
                + DESCRICAO_GRUPO + " TEXT, "
                + PERC_DESCONTO + " REAL, "
                + TIPO_COMPLEMENTO + " TEXT, "
                + "CONSTRAINT PK_GRUPO_PRODUTO PRIMARY KEY (" + EMPRESA_GRUPO + ", " + GRUPO + "))";
        db.execSQL(sqlGrupoProduto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_EMPRESA);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PRODUTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_GRUPO_PRODUTO);
        onCreate(db);
    }
}
