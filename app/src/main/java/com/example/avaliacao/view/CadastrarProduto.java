package com.example.avaliacao.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.avaliacao.R;
import com.example.avaliacao.dao.BancoDAO;
import com.example.avaliacao.dao.EstruturaBanco;

import java.util.ArrayList;
import java.util.List;

public class CadastrarProduto extends AppCompatActivity {
    private Boolean inclusao = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);

        TextView produto = findViewById(R.id.idProduto);
        EditText descProduto = findViewById(R.id.descricao);
        EditText apelidoProduto = findViewById(R.id.apelido);
        Spinner grupoProduto = findViewById(R.id.grupoProduto);
        EditText subGrupo = findViewById(R.id.subGrupo);
        EditText situacao = findViewById(R.id.situacao);
        EditText pesoLiquido = findViewById(R.id.pesoLiquido);
        EditText classFiscal = findViewById(R.id.classFiscal);
        EditText codBarras = findViewById(R.id.codBarras);
        EditText colecao = findViewById(R.id.colecao);
        Button botaoCadastrar = findViewById(R.id.Button);

        BancoDAO crud = new BancoDAO(getBaseContext());

        String empresaIdString = this.getIntent().getStringExtra("empresaId");
        String codigoProduto = this.getIntent().getStringExtra("codigoProduto");

        // Adicionar logs para verificar os valores recebidos
        Log.d("CadastrarProduto", "empresaIdString: " + empresaIdString);
        Log.d("CadastrarProduto", "codigoProduto: " + codigoProduto);

        if (empresaIdString == null || empresaIdString.isEmpty()) {
            Toast.makeText(this, "ID da empresa não fornecido.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        try {
            int empresaId = Integer.parseInt(empresaIdString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID da empresa inválido.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }


        List<String> grupoProdutoList = new ArrayList<>();
        Cursor gruposCursor = crud.carregarGruposProduto();
        if (gruposCursor != null && gruposCursor.moveToFirst()) {
            do {
                grupoProdutoList.add(gruposCursor.getString(gruposCursor.getColumnIndexOrThrow(EstruturaBanco.getDescricaoGrupo())));
            } while (gruposCursor.moveToNext());
            gruposCursor.close();
        }
        ArrayAdapter<String> grupoProdutoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, grupoProdutoList);
        grupoProdutoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grupoProduto.setAdapter(grupoProdutoAdapter);

        if (codigoProduto != null) {
            botaoCadastrar.setText("Atualizar");
            inclusao = false;

            Cursor cursor = crud.filtrarProdutos(codigoProduto);
            if (cursor != null && cursor.moveToFirst()) {
                produto.setText(cursor.getString(cursor.getColumnIndexOrThrow("produto")));
                descProduto.setText(cursor.getString(cursor.getColumnIndexOrThrow("descricaoProduto")));
                apelidoProduto.setText(cursor.getString(cursor.getColumnIndexOrThrow("apelidoProduto")));
                subGrupo.setText(cursor.getString(cursor.getColumnIndexOrThrow("subgrupoProduto")));
                situacao.setText(cursor.getString(cursor.getColumnIndexOrThrow("situacao")));
                pesoLiquido.setText(cursor.getString(cursor.getColumnIndexOrThrow("pesoLiquido")));
                classFiscal.setText(cursor.getString(cursor.getColumnIndexOrThrow("classificacaoFiscal")));
                codBarras.setText(cursor.getString(cursor.getColumnIndexOrThrow("codigoBarras")));
                colecao.setText(cursor.getString(cursor.getColumnIndexOrThrow("colecao")));
                setupSpinner(grupoProduto, cursor.getString(cursor.getColumnIndexOrThrow("grupoProduto")), grupoProdutoAdapter);
            }
        }

        botaoCadastrar.setOnClickListener(v -> {
            String descricao = descProduto.getText().toString();
            String apelido = apelidoProduto.getText().toString();
            String grupoProdutoSelecionado = grupoProduto.getSelectedItem().toString();
            String situacaoProduto = situacao.getText().toString();
            int subgrupo = Integer.parseInt(subGrupo.getText().toString());
            double peso = Double.parseDouble(pesoLiquido.getText().toString());
            String classificacao = classFiscal.getText().toString();
            String barras = codBarras.getText().toString();
            String col = colecao.getText().toString();

            if (descricao.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Por favor, insira uma descrição para o produto.", Toast.LENGTH_LONG).show();
                return;
            }

            String resultado;
            if (inclusao) {
                resultado = crud.inserirProduto(Integer.parseInt(empresaIdString), descricao, apelido, grupoProdutoSelecionado, situacaoProduto, subgrupo, peso, classificacao, barras, col);
            } else {
                resultado = crud.alterarProduto(Integer.parseInt(codigoProduto), descricao, apelido, grupoProdutoSelecionado, situacaoProduto, subgrupo, peso, classificacao, barras, Integer.parseInt(col));
            }

            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();

            // Retornar à tela de listagem
            Intent intent = new Intent(CadastrarProduto.this, ListagemProdutos.class);
            intent.putExtra("empresaId", String.valueOf(empresaIdString));
            startActivity(intent);
            finish();
        });


        Button buttonIrParaListagem = findViewById(R.id.buttonIrParaListagem);
        buttonIrParaListagem.setOnClickListener(v -> {
            Intent intent = new Intent(CadastrarProduto.this, ListagemProdutos.class);
            intent.putExtra("empresaId", String.valueOf(empresaIdString));
            startActivity(intent);
            finish();

        });
    }

    private void setupSpinner(Spinner spinner, String value, ArrayAdapter<String> adapter) {
        if (adapter != null && value != null) {
            int spinnerPosition = adapter.getPosition(value);
            spinner.setSelection(spinnerPosition);
        }
    }
}
