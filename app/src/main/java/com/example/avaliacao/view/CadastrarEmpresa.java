package com.example.avaliacao.view;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.avaliacao.BO.Empresa;
import com.example.avaliacao.R;
import com.example.avaliacao.dao.BancoDAO;

import java.net.ResponseCache;


import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class CadastrarEmpresa extends AppCompatActivity {

    private EditText edtNomeFantasia, edtRazaoSocial, edtEndereco, edtBairro, edtCEP, edtCidade, edtTelefone, edtCNPJ, edtFax, edtIE;
    private Button btnCadastrar;
    private BancoDAO bancoDAO;
    private String empresaId; // ID da empresa para edição

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_empresa);

        inicializarComponentes();

        bancoDAO = new BancoDAO(getBaseContext());
        Intent intent = getIntent();

        // Verifica se está no modo de edição
        if (intent.hasExtra("empresaId")) {
            empresaId = intent.getStringExtra("empresaId");
            carregarDadosEmpresa(empresaId);
            btnCadastrar.setText("Atualizar");
        }

        btnCadastrar.setOnClickListener(view -> {
            if (empresaId != null) {
                atualizarEmpresa(empresaId);
            } else {
                cadastrarEmpresa();
            }
        });

        // Listener para preenchimento automático de endereço com base no CEP

    }

    private void inicializarComponentes() {
        edtNomeFantasia = findViewById(R.id.edtNomeFantasia);
        edtRazaoSocial = findViewById(R.id.edtRazaoSocial);
        edtEndereco = findViewById(R.id.edtEndereco);
        edtBairro = findViewById(R.id.edtBairro);
        edtCEP = findViewById(R.id.edtCEP);
        edtCidade = findViewById(R.id.edtCidade);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtCNPJ = findViewById(R.id.edtCNPJ);
        edtFax = findViewById(R.id.edtFax);
        edtIE = findViewById(R.id.edtIE);
        btnCadastrar = findViewById(R.id.btnCadastrar);
    }

    private void carregarDadosEmpresa(String empresaId) {
        Empresa empresa = (Empresa) bancoDAO.obterListaEmpresas();
        if (empresa != null) {
            edtNomeFantasia.setText(empresa.getNomeFantasia());
            edtRazaoSocial.setText(empresa.getRazaoSocial());
            edtEndereco.setText(empresa.getEndereco());
            edtBairro.setText(empresa.getBairro());
            edtCEP.setText(empresa.getCep());
            edtCidade.setText(empresa.getCidade());
            edtTelefone.setText(empresa.getTelefone());
            edtCNPJ.setText(empresa.getCnpj());
            edtFax.setText(empresa.getFax());
            edtIE.setText(empresa.getIe());
        }
    }

    private void cadastrarEmpresa() {
        Empresa novaEmpresa = criarObjetoEmpresa();
        bancoDAO.inserirEmpresa(novaEmpresa);
        Toast.makeText(this, "Empresa cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
        redirecionarParaListagem();
    }

    private void atualizarEmpresa(String empresaId) {
        Empresa empresaAtualizada = criarObjetoEmpresa();
        empresaAtualizada.setId(Integer.parseInt(empresaId));
        bancoDAO.alterarEmpresa(empresaAtualizada);
        Toast.makeText(this, "Empresa atualizada com sucesso!", Toast.LENGTH_SHORT).show();
        redirecionarParaListagem();
    }

    private Empresa criarObjetoEmpresa() {
        return new Empresa(
                edtNomeFantasia.getText().toString(),
                edtRazaoSocial.getText().toString(),
                edtEndereco.getText().toString(),
                edtBairro.getText().toString(),
                edtCEP.getText().toString(),
                edtCidade.getText().toString(),
                edtTelefone.getText().toString(),
                edtCNPJ.getText().toString(),
                edtFax.getText().toString(),
                edtIE.getText().toString()
        );
    }

    private void redirecionarParaListagem() {
        Intent intent = new Intent(CadastrarEmpresa.this, ListagemEmpresas.class);
        startActivity(intent);
        finish();
    }};


