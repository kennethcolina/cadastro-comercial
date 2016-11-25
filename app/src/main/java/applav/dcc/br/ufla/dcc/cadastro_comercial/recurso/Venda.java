package applav.dcc.br.ufla.dcc.cadastro_comercial.recurso;

import java.util.ArrayList;

/**
 * Created by Roberto on 04/08/2016.
 */
public class Venda {

    private int idVenda;
    private Funcionario funcionario;
    private Cliente cliente;
    private ArrayList<Roupa> roupas;
    private String dataVenda;
    private double total;

    public Venda(int idVenda,Funcionario funcionario, Cliente cliente, ArrayList<Roupa> roupas, String dataVenda) {
        this.idVenda = idVenda;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.roupas = roupas;
        this.dataVenda = dataVenda;
        total = 0;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }
    public int getIdVenda (){
        return idVenda;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList<Roupa> getRoupas() {
        return roupas;
    }

    public String getDataVenda() {
        return dataVenda;
    }
}
