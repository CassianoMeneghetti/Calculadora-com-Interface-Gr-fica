package Historico;

import java.util.ArrayList;
import java.util.List;

public class Historico {

    private List<String> operacoes = new ArrayList<>();

    public void adicionar(String operacao) {
        operacoes.add(operacao);
    }

    public List<String> listar(){
        return operacoes;
    }

    public void limpar(){
        operacoes.clear();
    }

}
