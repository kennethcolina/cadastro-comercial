package applav.dcc.br.ufla.dcc.cadastro_comercial.controle;

import android.database.SQLException;

import java.util.ArrayList;

import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Cliente;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Funcionario;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Login;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Pessoa;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Roupa;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Venda;

/**
 * Created by kenneth on 11/08/16.
 */
public interface IBD_ControleDAO {

    //Login
    public void loginPadrao();
    public int tentaLogar(Login login);
    public String deletaLogin(String cpf);

    // Pessoa
    public void insereFuncBackup(Pessoa pessoa);
    //Funcionario
    public String insereFunc(Funcionario funcionario);
    public ArrayList<Pessoa> carregaFunc();
    //public Funcionario procuraFunc(Funcionario funcionario);
    public Funcionario procuraFunc(String CPF);
    //public Funcionario procuraFuncLogin(Funcionario funcionario);
    public Funcionario procuraFuncLogin(String Usuario);
    public String deletaFunc(String cpf);
    public String alteraFunc(Funcionario funcionario);
    public Funcionario procuraFuncBackUp(String CPF);

    //Cliente
    public String insereCli(Cliente cliente);
    public ArrayList<Pessoa> carregaCli();
    //public Cliente procuraCliente(Cliente cliente);
    public Cliente procuraCliente(String CPF);
    public String deletaCliente(String cpf);
    public String alteraCli(Cliente cliente);
    public void insereCliBackup(Pessoa pessoa);
    public Cliente procuraClienteBackUp(String CPF);

    //Roupa
    public String insereRoupa(Roupa roupa);
    public ArrayList<Roupa> carregaRoupas();
    public ArrayList<Roupa> getRoupasVenda(int id);
    public ArrayList<Venda> carregaVendas();
    public Roupa procuraRoupa(String codigo);
    public String deletaRoupa(String codigo);
    public String alteraRoupa(Roupa roupa);
    public void insereRoupaBackup(Roupa roupa);
    public Roupa procuraRoupaBackup(String codigo);


    //Venda
    public int insereVenda(String CPFfunc,String CPFcliente,String dataVenda, ArrayList<String> codigoRoupa);
    public ArrayList<String> carregaVendaRoupa();
    public Venda procuraVenda(int IDVenda);
    public double somaPrecos (int idVenda);
    public double somaPrecosBackup (int idVenda);
}
