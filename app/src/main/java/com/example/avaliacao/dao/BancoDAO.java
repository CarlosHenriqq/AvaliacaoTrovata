package com.example.avaliacao.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.avaliacao.BO.Empresa;

import java.util.ArrayList;
import java.util.List;

public class BancoDAO {
    private SQLiteDatabase db;
    private EstruturaBanco banco;

    public BancoDAO(Context context) {
        banco = new EstruturaBanco(context);
    }

    public void inserirEmpresa(Empresa empresa) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_ID", empresa.getId());
        values.put("nomeFantasia", empresa.getNomeFantasia());
        values.put("razaoSocial", empresa.getRazaoSocial());
        values.put("ENDERECO", empresa.getEndereco());
        values.put("BAIRRO", empresa.getBairro());
        values.put("CEP", empresa.getCep());
        values.put("CIDADE", empresa.getCidade());
        values.put("TELEFONE", empresa.getTelefone());
        values.put("CNPJ", empresa.getCnpj());
        values.put("FAX", empresa.getFax());
        values.put("IE", empresa.getIe());

        db.insert("EMPRESA", null, values);
        db.close();
    }


    public String inserirProduto(int empresaId, String descricao, String apelido, String grupoProdutoSelecionado, String situacao, int subgrupoProduto, double pesoLiquido, String classificacaoFiscal, String codigoBarras, String colecao) {
        ContentValues valores = new ContentValues();
        valores.put("empresa", empresaId); // Associar o produto à empresa específica
        valores.put("descricaoProduto", descricao);
        valores.put("apelidoProduto", apelido);
        valores.put("grupoProduto", grupoProdutoSelecionado);
        valores.put("situacao", situacao);
        valores.put("subgrupoProduto", subgrupoProduto);
        valores.put("pesoLiquido", pesoLiquido);
        valores.put("classificacaoFiscal", classificacaoFiscal);
        valores.put("codigoBarras", codigoBarras);
        valores.put("colecao", colecao);


        SQLiteDatabase db = banco.getWritableDatabase();
        String mensagem;

        try {
            long resultado = db.insert("produto", null, valores);
            mensagem = (resultado == -1) ? "Erro ao inserir produto" : "Produto inserido com sucesso";
        } catch (Exception e) {
            mensagem = "Erro: " + e.getMessage();
        } finally {
            db.close();
        }

        return mensagem;
    }


    public String inserirGrupoProduto(int empresa, int grupoProduto, String descricaoGrupoProduto, double percDesconto, int tipoComplemento) {
        ContentValues valores = new ContentValues();
        valores.put("empresa", empresa);
        valores.put("grupoProduto", grupoProduto);
        valores.put("descricaoGrupoProduto", descricaoGrupoProduto);
        valores.put("percDesconto", percDesconto);
        valores.put("tipoComplemento", tipoComplemento);

        SQLiteDatabase db = banco.getWritableDatabase();
        String mensagem;

        try {
            long resultado = db.insert("grupo_produto", null, valores);
            mensagem = (resultado == -1) ? "Erro ao inserir grupo de produto" : "Grupo de produto inserido com sucesso";
        } catch (Exception e) {
            mensagem = "Erro: " + e.getMessage();
        } finally {
            db.close();
        }

        return mensagem;
    }

    public List<String> obterListaEmpresas() {
        List<String> empresas = new ArrayList<>();
        SQLiteDatabase db = banco.getReadableDatabase();
        String query = "SELECT razaoSocial || ' - ' || cidade AS empresaInfo FROM Empresa";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String empresaInfo = cursor.getString(cursor.getColumnIndexOrThrow("empresaInfo"));
                empresas.add(empresaInfo);
            }
            cursor.close();
        }
        db.close();
        return empresas;
    }
    public Cursor carregarProdutosByEmpresaID(int empresaId) {
        SQLiteDatabase db = this.banco.getReadableDatabase();


        String query = "SELECT * FROM produto  WHERE empresa = ?";


        return db.rawQuery(query, new String[] { String.valueOf(empresaId) });
    }


    /*public Cursor carregarProdutos() {
        db = banco.getReadableDatabase();
        String[] campos = {"empresa", "produto", "descricaoProduto", "situacao"};

        return db.query(
                "produto",
                campos,
                null,
                null,
                null,
                null,
                "descricaoProduto ASC"
        );
    }*/

    public Cursor carregarGruposProduto() {
        db = banco.getReadableDatabase();
        String[] campos = {"empresa", "grupoProduto", "descricaoGrupoProduto"};

        return db.query(
                "grupo_produto",
                campos,
                null,
                null,
                null,
                null,
                "descricaoGrupoProduto ASC"
        );
    }

    public String alterarEmpresa(Empresa empresa) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nomeFantasia", empresa.getNomeFantasia());
        values.put("razaoSocial", empresa.getRazaoSocial());
        values.put("ENDERECO", empresa.getEndereco());
        values.put("BAIRRO", empresa.getBairro());
        values.put("CEP", empresa.getCep());
        values.put("CIDADE", empresa.getCidade());
        values.put("TELEFONE", empresa.getTelefone());
        values.put("CNPJ", empresa.getCnpj());
        values.put("FAX", empresa.getFax());
        values.put("IE", empresa.getIe());

        String whereClause = "id=?";
        String[] whereArgs = new String[] {String.valueOf(empresa.getId())};

        int count = db.update("EMPRESA", values, whereClause, whereArgs);
        db.close();

        return count > 0 ? "Empresa atualizada com sucesso" : "Erro ao atualizar empresa";
    }
    public String alterarProduto(int id, String descricao, String apelido, String grupoProdutoSelecionado, String situacao, int subgrupoProduto, double pesoLiquido, String classificacaoFiscal, String codigoBarras, int colecao) {
        ContentValues valores = new ContentValues();
        valores.put("descricaoProduto", descricao);
        valores.put("apelidoProduto", apelido);
        valores.put("grupoProduto", grupoProdutoSelecionado);
        valores.put("situacao", situacao);
        valores.put("subgrupoProduto", subgrupoProduto);
        valores.put("pesoLiquido", pesoLiquido);
        valores.put("classificacaoFiscal", classificacaoFiscal);
        valores.put("codigoBarras", codigoBarras);
        valores.put("colecao", colecao);


        SQLiteDatabase db = banco.getWritableDatabase();
        String mensagem;

        try {
            int resultado = db.update("produto", valores, "_id = ?", new String[]{String.valueOf(id)});
            mensagem = (resultado == -1) ? "Erro ao atualizar produto" : "Produto atualizado com sucesso";
        } catch (Exception e) {
            mensagem = "Erro: " + e.getMessage();
        } finally {
            db.close();
        }

        return mensagem;
    }

    public Cursor carregarProdutosOrdenado(int empresaId, String ordem) {
        SQLiteDatabase db = banco.getReadableDatabase();
        String[] campos = {
                "_id",
                EstruturaBanco.getEmpresaProduto(),
                EstruturaBanco.getProduto(),
                EstruturaBanco.getDescricaoProduto(),
                EstruturaBanco.getApelidoProduto(),
                EstruturaBanco.getSituacao()
        };
        String filtro = EstruturaBanco.getEmpresaProduto() + " = ?";
        String[] argumentos = {String.valueOf(empresaId)};
        if (ordem.equals(EstruturaBanco.getProduto())) {
            ordem = "CAST(" + ordem + " AS INTEGER)"; }
        return db.query(EstruturaBanco.getTabelaProduto(), campos, filtro, argumentos, null, null, ordem + " ASC");
    }


    public Cursor filtrarProdutos(String texto) {
        SQLiteDatabase db = banco.getReadableDatabase();
        String[] campos = {
                "_id",
                EstruturaBanco.getEmpresaProduto(),
                EstruturaBanco.getProduto(),
                EstruturaBanco.getDescricaoProduto(),
                EstruturaBanco.getApelidoProduto(),
                EstruturaBanco.getSituacao(),
                EstruturaBanco.getGrupoProduto(),
                EstruturaBanco.getSubgrupoProduto(),
                EstruturaBanco.getPesoLiquido(),
                EstruturaBanco.getClassificacaoFiscal(),
                EstruturaBanco.getCodigoBarras(),
                EstruturaBanco.getColecao(),

        };
        String filtro = "descricaoProduto LIKE ? OR apelidoProduto LIKE ? OR produto LIKE ? " +
                "OR grupoProduto LIKE ? OR subgrupoProduto LIKE ? OR codigoBarras LIKE ?";
        String[] argumentos = {
                "%" + texto + "%", "%" + texto + "%", "%" + texto + "%",
                "%" + texto + "%", "%" + texto + "%", "%" + texto + "%"
        };
        return db.query(EstruturaBanco.getTabelaProduto(), campos, filtro, argumentos, null, null, "descricaoProduto ASC");
    }

    public Cursor filtrarProdutosPorGrupo(String grupoProduto) {
        db = banco.getReadableDatabase();
        String[] campos = {
                "_id",
                EstruturaBanco.getEmpresaProduto(),
                EstruturaBanco.getProduto(),
                EstruturaBanco.getDescricaoProduto(),
                EstruturaBanco.getApelidoProduto(),
                EstruturaBanco.getSituacao()
        };
        String filtro = EstruturaBanco.getGrupoProduto() + " = ?";
        String[] argumentos = {grupoProduto};
        return db.query(EstruturaBanco.getTabelaProduto(), campos, filtro, argumentos, null, null, EstruturaBanco.getDescricaoProduto() + " ASC");
    }

    public Cursor filtrarProdutosPorTipoComplemento(String tipoComplemento) {
        db = banco.getReadableDatabase();
        String[] campos = {
                "_id",
                EstruturaBanco.getEmpresaProduto(),
                EstruturaBanco.getProduto(),
                EstruturaBanco.getDescricaoProduto(),
                EstruturaBanco.getApelidoProduto(),
                EstruturaBanco.getSituacao()
        };
        String filtro = EstruturaBanco.getTipoComplemento() + " = ?";
        String[] argumentos = {tipoComplemento};
        return db.query(EstruturaBanco.getTabelaProduto(), campos, filtro, argumentos, null, null, EstruturaBanco.getDescricaoProduto() + " ASC");
    }
    public void excluirEmpresa(int empresaId) {
        SQLiteDatabase db = banco.getWritableDatabase();
        db.delete("Empresa", "id = ?", new String[]{String.valueOf(empresaId)});
        db.close();
    }
}