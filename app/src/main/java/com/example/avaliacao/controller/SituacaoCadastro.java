package com.example.avaliacao.controller;

public enum SituacaoCadastro {
    A("Ativo"),
    C("Cancelado"),
    B("Bloqueado"),
    I("Inativo");

    private String situacao;

    SituacaoCadastro(String pValor) {
        this.situacao = pValor;
    }

    public String getValor() {
        return situacao;
    }

    // Método para converter uma string para o enum correspondente
    public static SituacaoCadastro fromValue(String valor) {
        for (SituacaoCadastro situacao : SituacaoCadastro.values()) {
            if (situacao.getValor().equalsIgnoreCase(valor)) {
                return situacao;
            }
        }
        throw new IllegalArgumentException("Valor não encontrado: " + valor);
    }
}
