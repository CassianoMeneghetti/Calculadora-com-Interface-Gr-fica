package service;

import java.util.ArrayList;
import java.util.List;

public class Historico {

    private final List<String> operacoes;

    public Historico() {
        operacoes = new ArrayList<>();
    }

    public void adicionar(String operacao) {
        operacoes.add(operacao);
    }

    public List<String> listar() {
        return new ArrayList<>(operacoes);
    }

    public void limpar() {
        operacoes.clear();
    }
}