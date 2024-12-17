package com.example.avaliacao.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.avaliacao.R;
import com.example.avaliacao.dao.BancoDAO;

import java.util.List;

public class ListagemEmpresas extends AppCompatActivity {
    private BancoDAO crud;
    private ListView listEmpresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_empresas);
        Intent intent = getIntent();
        String empresaIdString = intent.getStringExtra("empresaId");
        crud = new BancoDAO(getBaseContext());
        listEmpresas = findViewById(R.id.listEmpresas);

        // Carregar lista de empresas
        List<String> empresas = crud.obterListaEmpresas();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, empresas);
        listEmpresas.setAdapter(adapter);

        // Ouvir eventos de clique para alteração, exclusão e visualização de detalhes
        listEmpresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Criar intent para abrir a tela de cadastro de empresa e passar o ID
                Intent intent = new Intent(ListagemEmpresas.this, CadastrarEmpresa.class);
                intent.putExtra("empresaIdString", empresaIdString);
                startActivity(intent);
            }
        });

    }};
