package com.example.avaliacao.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.avaliacao.R;
import com.example.avaliacao.dao.BancoDAO;
import com.example.avaliacao.dao.EstruturaBanco;

public class ListagemProdutos extends AppCompatActivity {
    private ListView listaProdutos;
    private BancoDAO crud;
    private Cursor cursor;
    private SimpleCursorAdapter adaptador;
    private EditText searchField;
    private Button btnOrdenarDescricao, btnOrdenarCodigo, btnVoltar, btnCadastrar,btnEmpresas;
    private Spinner spinnerGrupos, spinnerTipoComplemento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_produtos);

        crud = new BancoDAO(getBaseContext());
        Intent intent = getIntent();
        String empresaIdString = intent.getStringExtra("empresaId");

        // Verificar se a string foi recebida
        if (empresaIdString == null || empresaIdString.isEmpty()) {
            Toast.makeText(getApplicationContext(), "ID da empresa não fornecido.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        try {
            int empresaId = Integer.parseInt(empresaIdString);

            // Carregar produtos pela ID da empresa
            cursor = crud.carregarProdutosByEmpresaID(empresaId);
            if (cursor == null) {
                Toast.makeText(getApplicationContext(), "Nenhum produto encontrado para esta empresa.", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "ID da empresa inválido.", Toast.LENGTH_SHORT).show();
            finish();
        }



        listaProdutos = findViewById(R.id.listView);
        searchField = findViewById(R.id.searchField);
        btnOrdenarDescricao = findViewById(R.id.btnOrdenarDescricao);
        btnOrdenarCodigo = findViewById(R.id.btnOrdenarCodigo);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnEmpresas= findViewById(R.id.btnEmpresas);
        spinnerGrupos = findViewById(R.id.spinnerGrupos);
        spinnerTipoComplemento = findViewById(R.id.spinnerTipoComplemento);

        String[] nomeCampos = new String[]{
                EstruturaBanco.getProduto(),
                EstruturaBanco.getDescricaoProduto(),
                EstruturaBanco.getApelidoProduto(),
                EstruturaBanco.getSituacao(),
                //EstruturaBanco.getGrupoProduto(),
                //EstruturaBanco.getTipoComplemento()
        };

        int[] idViews = new int[]{
                R.id.idProduto,
                R.id.descricao,
                R.id.apelido,
                R.id.situacao,
                //R.id.grupoProduto,
                //R.id.complemento
        };

        adaptador = new SimpleCursorAdapter(
                this,
                R.layout.activity_item_produto,
                cursor,
                nomeCampos,
                idViews,
                0
        );
        listaProdutos.setAdapter(adaptador);



        btnVoltar.setOnClickListener(v -> {
            Intent voltarIntent = new Intent(ListagemProdutos.this, Inicial.class);
            startActivity(voltarIntent);
            finish();
        });

        btnCadastrar.setOnClickListener(v -> {
            Intent cadastrarProdutoIntent = new Intent(ListagemProdutos.this, CadastrarProduto.class);
            cadastrarProdutoIntent.putExtra("empresaId", empresaIdString);
            startActivity(cadastrarProdutoIntent);
            finish();
        });
        btnEmpresas.setOnClickListener(v -> {
            Intent verEmpresasIntent = new Intent(ListagemProdutos.this, ListagemEmpresas.class);
            startActivity(verEmpresasIntent);
            finish();
        });


        // Ordenar por Descrição
        btnOrdenarDescricao.setOnClickListener(v -> {
            cursor = crud.carregarProdutosOrdenado(Integer.parseInt(empresaIdString), EstruturaBanco.getDescricaoProduto());
            adaptador.changeCursor(cursor);
        });

        // Ordenar por Código
        btnOrdenarCodigo.setOnClickListener(v -> {
            cursor = crud.carregarProdutosOrdenado(Integer.parseInt(empresaIdString), EstruturaBanco.getProduto());
            adaptador.changeCursor(cursor);
        });

        // Filtrar produtos com base no campo de pesquisa
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cursor = crud.filtrarProdutos(s.toString());
                adaptador.changeCursor(cursor);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Filtrar produtos por grupo de produto
        spinnerGrupos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String grupoProduto = (String) parent.getItemAtPosition(position);
                cursor = crud.filtrarProdutosPorGrupo(grupoProduto);
                adaptador.changeCursor(cursor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Filtrar produtos por tipo de complemento
        spinnerTipoComplemento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tipoComplemento = (String) parent.getItemAtPosition(position);
                cursor = crud.filtrarProdutosPorTipoComplemento(tipoComplemento);
                adaptador.changeCursor(cursor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        listaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                String empresaId = cursor.getString(cursor.getColumnIndexOrThrow(EstruturaBanco.getIdEmpresa()));
                String codigoProduto = cursor.getString(cursor.getColumnIndexOrThrow(EstruturaBanco.getProduto()));
                Log.d("ListagemProdutos", "empresaId: " + empresaId);
                Log.d("ListagemProdutos", "codigoProduto: " + codigoProduto);
                Intent intent = new Intent(ListagemProdutos.this, CadastrarProduto.class);
                intent.putExtra("empresaId", empresaId);
                intent.putExtra("codigoProduto", codigoProduto);
                cursor.close();
                startActivity(intent);
            }
        });
    }
};

