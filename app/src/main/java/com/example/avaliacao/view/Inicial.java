package com.example.avaliacao.view;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.avaliacao.BO.Empresa;
import com.example.avaliacao.R;
import com.example.avaliacao.dao.BancoDAO;
import com.example.avaliacao.dao.EstruturaBanco;

import java.util.List;

public class Inicial extends AppCompatActivity {
    private BancoDAO crud;
    private ListView listEmpresas;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        crud = new BancoDAO(getBaseContext());
        listEmpresas = findViewById(R.id.listEmpresas);

        List<String> empresas = crud.obterListaEmpresas();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, empresas);
        listEmpresas.setAdapter(adapter);

        listEmpresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Recupera o nome da empresa selecionada com base na posição
                String empresaSelecionada = (String) parent.getItemAtPosition(position);

                // Encontra o ID da empresa baseado no nome (ajuste conforme o ID real)
                cursor = crud.carregarProdutosByEmpresaID(position + 1); // Ajuste conforme o ID real

                if (cursor != null && cursor.moveToFirst()) {
                    // Obtém o ID da empresa a partir do Cursor
                    int empresaId = cursor.getInt(cursor.getColumnIndexOrThrow(EstruturaBanco.getIdEmpresa())); // Use o nome correto da coluna

                    // Converte o int para String
                    String empresaIdString = String.valueOf(empresaId);

                    // Cria o Intent para a próxima Activity
                    Intent intent = new Intent(Inicial.this, ListagemProdutos.class);
                    intent.putExtra("empresaId", empresaIdString);
                    Log.d("Inicial", "empresaId: " + empresaIdString);
                    startActivity(intent);
                } else {
                    // Exibe uma mensagem caso não encontre produtos para a empresa
                    Toast.makeText(Inicial.this, "Nenhum produto encontrado para a empresa selecionada!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
