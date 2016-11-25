package applav.dcc.br.ufla.dcc.cadastro_comercial.controle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Cliente;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Funcionario;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Login;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Pessoa;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Roupa;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Venda;

/**
 * Created by João Paulo on 26/07/2016.
 */


public class BD_ControleDAO implements IBD_ControleDAO {

    private SQLiteDatabase db;
    private BD banco;

    public BD_ControleDAO(Context context) {
        banco = new BD(context);
    }

    @Override
    public String insereRoupa(Roupa roupa){// throws Exception {
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BD.KEY_referencia, roupa.getReferencia());
        valores.put(BD.KEY_nomeRoupa, roupa.getNome());
        valores.put(BD.KEY_codigoRoupa, roupa.getCodigo());
        valores.put(BD.KEY_preco, roupa.getPreco());
        valores.put(BD.KEY_cor, roupa.getCor());
        valores.put(BD.KEY_tamanho, roupa.getTamanho());
        valores.put(BD.KEY_material, roupa.getMaterial());
        valores.put(BD.KEY_detalhes, roupa.getDetalhes());

        resultado = db.insert(BD.TAB_ROUPA, null, valores);
        db.close();
        if (resultado == -1)
           return "Falha ao inserir roupa!";
        else
            return "Roupa inserida com sucesso!";

    }

    @Override
    public int insereVenda(String CPFfunc,String CPFcliente,String dataVenda, ArrayList<String> codigoRoupa) {
        ContentValues valores;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BD.KEY_CPFfuncionarioVenda, CPFfunc);
        valores.put(BD.KEY_CPFclienteVenda, CPFcliente);
        valores.put(BD.KEY_DataVenda, dataVenda);
        db.insert(BD.TAB_VENDA, null, valores);
        db.close();
        db = banco.getReadableDatabase();
        String selectQuery = "SELECT  MAX(idVenda) FROM " + BD.TAB_VENDA;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int IDvenda=0;

        if (cursor.moveToFirst()) {
            do {
                IDvenda = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        db.close();

        db = banco.getWritableDatabase();
        ContentValues valores2;
        valores2 = new ContentValues();

        for(String s : codigoRoupa){
            valores2.put(BD.KEY_idVenda, IDvenda);
            valores2.put(BD.KEY_codigoRoupaVenda, s);
            db.insert(BD.TAB_VENDA_CLIENTE, null, valores2);
        }
        db.close();

        return IDvenda;
    }


    @Override
    public void loginPadrao(){
        ContentValues valores;
        valores = new ContentValues();
        db = banco.getWritableDatabase();
        valores.put(BD.KEY_usuario,"admin");
        valores.put(BD.KEY_senha,"admin");
        Login admin = new Login ("admin", "admin");
        Funcionario funcionario = new Funcionario("Administrador","","123.456.789-11", "123456", 0, 0, "admin@sistemas.ufla.br", "10/04/1996", "10/04/2016",admin, 1);
        insereFunc(funcionario);
    }


    @Override
    public String insereFunc(Funcionario funcionario) {
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BD.KEY_cpf, funcionario.getCPF());
        valores.put(BD.KEY_nome, funcionario.getNome());
        valores.put(BD.KEY_sobrenome, funcionario.getSobrenome());
        valores.put(BD.KEY_dataNascimento,funcionario.getDataNascimento());
        valores.put(BD.KEY_email, funcionario.getEmail());
        valores.put(BD.KEY_telefone, funcionario.getTelefone());
        valores.put(BD.KEY_salario, funcionario.getSalario());
        valores.put(BD.KEY_comissao, funcionario.getComissao());
        valores.put(BD.KEY_nivel, funcionario.getNivel());
        valores.put(BD.KEY_dataInicio, funcionario.getDataInicio());

        ContentValues valores1;
        long resultado1;
        valores1 = new ContentValues();
        valores1.put(BD.KEY_CPFLogin,funcionario.getCPF());
        valores1.put(BD.KEY_usuario,funcionario.getLogin().getUsuario());
        valores1.put(BD.KEY_senha,funcionario.getLogin().getSenha());

        resultado1 = db.insert(BD.TAB_LOGIN,null,valores1);

        resultado = db.insert(BD.TAB_FUNC, null, valores);
        db.close();

        if (resultado == -1 && resultado1 == -1)
            return "Erro ao inserir funcionario";
        else
            return "Funcionario inserido com sucesso";

    }

    @Override
    public void insereFuncBackup(Pessoa pessoa) {
        ContentValues valores;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BD.KEY_cpf,pessoa.getCPF());
        valores.put(BD.KEY_nome, pessoa.getNome());
        valores.put(BD.KEY_sobrenome, pessoa.getSobrenome());
        db.insert(BD.TAB_FUNCbackUP, null, valores);
        db.close();
    }


    public void insereCliBackup(Pessoa pessoa) {
        ContentValues valores;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BD.KEY_cpfCliente, pessoa.getCPF());
        valores.put(BD.KEY_nomeCliente, pessoa.getNome());
        valores.put(BD.KEY_sobrenomeCliente, pessoa.getSobrenome());
        db.insert(BD.TAB_CLIENTEbackUP, null, valores);
        db.close();
    }

    public void insereRoupaBackup(Roupa roupa) {
        ContentValues valores;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BD.KEY_nomeRoupa, roupa.getNome());
        valores.put(BD.KEY_codigoRoupa,roupa.getCodigo());
        valores.put(BD.KEY_preco, roupa.getPreco());
        db.insert(BD.TAB_ROUPAbackUP, null, valores);
        db.close();
    }

    @Override
    public String insereCli(Cliente cliente) {
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BD.KEY_cpf, cliente.getCPF());
        valores.put(BD.KEY_nome,cliente.getNome() );
        valores.put(BD.KEY_sobrenome, cliente.getSobrenome());
        valores.put(BD.KEY_dataNascimento,cliente.getDataNascimento());
        valores.put(BD.KEY_email, cliente.getEmail());
        valores.put(BD.KEY_telefone, cliente.getTelefone());
        valores.put(BD.KEY_ptsfidelidade,0);

        resultado = db.insert(BD.TAB_CLIENTE, null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir cliente";
        else
            return "Cliente inserido com sucesso";

    }


    @Override
    public ArrayList<Pessoa> carregaFunc() {
        ArrayList<Pessoa> funcionarios = new ArrayList<Pessoa>();

        String selectQuery = "SELECT  nome,sobrenome,cpf,telefone,salario,comissao,email,dataNascimento,dataInicio,nivel FROM " + BD.TAB_FUNC;
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Pessoa pessoa  = new Funcionario(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getFloat(4),cursor.getFloat(5),cursor.getString(6)
                        ,cursor.getString(7),cursor.getString(8),null,cursor.getInt(9));
                funcionarios.add(pessoa);
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return funcionarios;
    }

    @Override
    public ArrayList<Pessoa> carregaCli() {
        ArrayList<Pessoa> clientes = new ArrayList<Pessoa>();

        String selectQuery = "SELECT  nome,sobrenome,cpf,telefone,email,dataNascimento FROM " + BD.TAB_CLIENTE;
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Pessoa pessoa  = new Cliente(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
                clientes.add(pessoa);
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return clientes;
    }
    @Override
    public ArrayList<Roupa> carregaRoupas() {
        ArrayList<Roupa> roupas = new ArrayList<Roupa>();

        String selectQuery = "SELECT  referencia,nome,codigo,preco,cor,tamanho,material,detalhes FROM " + BD.TAB_ROUPA;
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Roupa roupa = new Roupa(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getFloat(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
                roupas.add(roupa);
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return roupas;
    }

    @Override
    public ArrayList<Roupa> getRoupasVenda(int id){
        String selectQuery = "SELECT codigo FROM " + BD.TAB_VENDA_CLIENTE + " WHERE " + BD.KEY_idVenda + " = " + id;
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Roupa> roupas = new ArrayList<Roupa>();

        if (cursor.moveToFirst()) {
            do {
                Roupa r = procuraRoupa(cursor.getString(0));
                if(r == null){
                    r = procuraRoupaBackup(cursor.getString(0));
                }
               roupas.add(r);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return roupas;

    }

    @Override
    public ArrayList<Venda> carregaVendas() {
        ArrayList<Venda> vendas = new ArrayList<Venda>();
        String selectQuery = "SELECT  idVenda,cpf,cpfCliente,dataVenda FROM " + BD.TAB_VENDA;
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Funcionario funcionario= procuraFunc(cursor.getString(1));
               if(funcionario == null){
                    funcionario = procuraFuncBackUp(cursor.getString(1));
                }
                Cliente cliente = procuraCliente(cursor.getString(2));
                if(cliente == null){
                    cliente = procuraClienteBackUp(cursor.getString(2));
                }
                ArrayList<Roupa> roupasVenda= getRoupasVenda(cursor.getInt(0));
                Venda venda = new Venda(cursor.getInt(0),funcionario,cliente,roupasVenda,cursor.getString(3));
                vendas.add(venda);
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return vendas;
    }

    @Override
    public Venda procuraVenda(int IDVenda) {
        String selectQuery = "SELECT  idVenda,cpf,cpfCliente,dataVenda FROM " + BD.TAB_VENDA + " WHERE " + BD.KEY_idVenda + " = " + IDVenda;
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Venda venda = null;
        if (cursor.moveToFirst()) {
            do {
                Funcionario funcionario = procuraFunc(cursor.getString(1));
                if(funcionario == null){
                    funcionario = procuraFuncBackUp(cursor.getString(1));
                }
                Cliente cliente = procuraCliente(cursor.getString(2));
                if(cliente == null){
                    cliente = procuraClienteBackUp(cursor.getString(2));
                }
                ArrayList<Roupa> roupasVenda = getRoupasVenda(cursor.getInt(0));
                venda = new Venda(cursor.getInt(0),funcionario,cliente,roupasVenda,cursor.getString(3));
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return venda;
    }

    @Override
    public Roupa procuraRoupa(String codigo) {
        String selectQuery = "SELECT  referencia,nome,codigo,preco,cor,tamanho,material,detalhes FROM " + BD.TAB_ROUPA + " WHERE " + BD.KEY_codigoRoupa + " = '" + codigo+"'";
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Roupa roupa = null;

        if (cursor.moveToFirst()) {
            do {
                roupa = new Roupa(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getFloat(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return roupa;
    }

    @Override
    public Roupa procuraRoupaBackup(String codigo) {
        String selectQuery = "SELECT  nome,codigo,preco FROM " + BD.TAB_ROUPAbackUP + " WHERE " + BD.KEY_codigoRoupa + " = '" + codigo+"'";
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Roupa roupa = null;

        if (cursor.moveToFirst()) {
            do {
                roupa = new Roupa(cursor.getString(0),cursor.getString(1),cursor.getFloat(2));
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return roupa;
    }

    @Override
    public double somaPrecos (int idVenda){
        String selectQuery = "SELECT SUM(preco) FROM " + BD.TAB_ROUPA + " NATURAL JOIN " + BD.TAB_VENDA_CLIENTE + " WHERE " + BD.KEY_idVenda + " = " + idVenda;
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        double soma = 0;
        if (cursor.moveToFirst()) {
            do {
                soma = cursor.getFloat(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return soma;
    }

    @Override
    public double somaPrecosBackup (int idVenda){
        String selectQuery = "SELECT SUM(preco) FROM " + BD.TAB_ROUPAbackUP + " NATURAL JOIN " + BD.TAB_VENDA_CLIENTE + " WHERE " + BD.KEY_idVenda + " = " + idVenda;
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        double soma = 0;
        if (cursor.moveToFirst()) {
            do {
                soma = cursor.getFloat(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return soma;
    }

    @Override
    public Funcionario procuraFunc(String CPF) {
        String selectQuery = "SELECT  nome,sobrenome,cpf,telefone,salario,comissao,email,dataNascimento,dataInicio,nivel FROM " + BD.TAB_FUNC + " WHERE " + BD.KEY_cpf+ " = '"+ CPF +"'";
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Funcionario funcionario = null;
        if (cursor.moveToFirst()) {
            do {
                funcionario  = new Funcionario(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getFloat(4),cursor.getFloat(5),cursor.getString(6)
                        ,cursor.getString(7),cursor.getString(8),null,cursor.getInt(9));
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return funcionario;
    }
    @Override
    public Funcionario procuraFuncBackUp(String CPF) {
        String selectQuery = "SELECT  nome,sobrenome,cpf FROM " + BD.TAB_FUNCbackUP + " WHERE " + BD.KEY_cpf+ " = '"+ CPF +"'";
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Funcionario funcionario = null;
        if (cursor.moveToFirst()) {
            do {
                funcionario  = new Funcionario(cursor.getString(0),cursor.getString(1),cursor.getString(2));
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return funcionario;
    }


    @Override
    public Funcionario procuraFuncLogin(String Usuario) {
        String selectQuery = "SELECT  nome,sobrenome,cpf,telefone,salario,comissao,email,dataNascimento,dataInicio,nivel FROM "
                + BD.TAB_LOGIN+ " NATURAL JOIN "+ BD.TAB_FUNC + " WHERE " + BD.KEY_usuario+ " = '"+ Usuario +"'";
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Funcionario funcionario = null;
        if (cursor.moveToFirst()) {
            do {
                funcionario  = new Funcionario(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getFloat(4),cursor.getFloat(5),cursor.getString(6)
                        ,cursor.getString(7),cursor.getString(8),null,cursor.getInt(9));
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return funcionario;
    }

    @Override
    public Cliente procuraCliente(String CPF) {
        String selectQuery = "SELECT  nome,sobrenome,cpf,telefone,email,dataNascimento,ptsFidelidade FROM " + BD.TAB_CLIENTE + " WHERE " + BD.KEY_cpfCliente + " = '" + CPF + "'";
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Cliente cliente = null;
        if (cursor.moveToFirst()) {
            do {
                cliente = new Cliente(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                cursor.close();
                db.close();
            }while (cursor.moveToNext());
        }
        return cliente;
    }

    @Override
    public Cliente procuraClienteBackUp(String CPF) {
        String selectQuery = "SELECT  nome,sobrenome,cpf FROM " + BD.TAB_CLIENTEbackUP + " WHERE " + BD.KEY_cpfCliente + " = '" + CPF + "'";
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Cliente cliente = null;
        if (cursor.moveToFirst()) {
            do {
                cliente = new Cliente(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                cursor.close();
                db.close();
            }while (cursor.moveToNext());
        }
        return cliente;
    }


    @Override
    public ArrayList<String> carregaVendaRoupa() {
        ArrayList<String> labels = new ArrayList<String>();

        String selectQuery = "SELECT  codigo,nome FROM " + BD.TAB_ROUPA;

        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0)+" :   " + cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    @Override
    public int tentaLogar(Login login){
        db = banco.getReadableDatabase();
        String buscaUsuario = "SELECT " + banco.KEY_CPFLogin + " FROM " + banco.TAB_LOGIN + " WHERE " + banco.KEY_usuario + " = '" + login.getUsuario() +
                "' and " + banco.KEY_senha + " = '" + login.getSenha() + "'";

        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(buscaUsuario, null);
        String cpf = "erro";
        if (cursor.moveToFirst()) {
            do {
                cpf = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        // closing connection
        int nivel = 0;
        if(cpf == "erro"){
            return -1;
        }else{
            String buscaFunc = "SELECT " + banco.KEY_nivel + " FROM " + banco.TAB_FUNC + " WHERE " + banco.KEY_cpf + " = '" + cpf+ "'";
            cursor = db.rawQuery(buscaFunc, null);
            if (cursor.moveToFirst()) {
                do {
                    nivel = cursor.getInt(0);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return nivel;
    }

    public void ExcluiTodosFUNC() {
        String where = "idFuncionario NOT NULL";
        db = banco.getReadableDatabase();
        db.delete(BD.TAB_FUNC, where, null);
        db.close();
    }

    @Override
    public String deletaFunc(String cpf){
        String where = BD.KEY_cpf + " = " + "'" + cpf + "'";
        Pessoa pessoa = procuraFunc(cpf);
        insereFuncBackup(pessoa);
        db = banco.getReadableDatabase();
        if(cpf != null) {
            db.delete(BD.TAB_FUNC, where, null);
            db.close();
        }
        return "Sucesso!";
    }

    @Override
    public String deletaLogin(String cpf) {
        String where = BD.KEY_cpf + " = " + "'" + cpf + "'";
        db = banco.getReadableDatabase();
            if (cpf != null) {
                db.delete(BD.TAB_LOGIN, where, null);
                db.close();
            }
        return "Sucesso!";
    }

    @Override
    public String deletaCliente(String cpf){
        String where = BD.KEY_cpf + " = " + "'" + cpf + "'";
        Pessoa pessoa = procuraCliente(cpf);
        insereCliBackup(pessoa);
        db = banco.getReadableDatabase();
        if(cpf != null) {
            db.delete(BD.TAB_CLIENTE, where, null);
            db.close();
        }
        return "Sucesso!";
    }

    @Override
    public String deletaRoupa(String codigo){
        String where = BD.KEY_codigoRoupa + " = " + "'" + codigo + "'";
        Roupa roupa = procuraRoupa(codigo);
        insereRoupaBackup(roupa);
        db = banco.getReadableDatabase();
            if(codigo != null) {
                db.delete(BD.TAB_ROUPA, where, null);
                db.close();
            }
        return "Sucesso!";
    }

    @Override
    public String alteraFunc(Funcionario funcionario){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = BD.KEY_cpf + "='" + funcionario.getCPF()+"'";

        valores = new ContentValues();
        valores.put(BD.KEY_nome, funcionario.getNome());
        valores.put(BD.KEY_sobrenome, funcionario.getSobrenome());
        valores.put(BD.KEY_dataNascimento,funcionario.getSobrenome());
        valores.put(BD.KEY_email, funcionario.getEmail());
        valores.put(BD.KEY_telefone, funcionario.getTelefone());
        valores.put(BD.KEY_salario, funcionario.getSalario());
        valores.put(BD.KEY_comissao, funcionario.getComissao());
        valores.put(BD.KEY_nivel, funcionario.getNivel());
        int resultado = db.update(BD.TAB_FUNC,valores,where,null);
        db.close();
        if (resultado == -1)
            return "Erro ao alterar funcionário";
        else
            return "Funcionário alterado com sucesso";
    }

    @Override
    public String alteraCli(Cliente cliente){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = BD.KEY_cpf + "='" + cliente.getCPF()+"'";

        valores = new ContentValues();
        valores.put(BD.KEY_nome, cliente.getNome());
        valores.put(BD.KEY_sobrenome, cliente.getSobrenome());
        valores.put(BD.KEY_dataNascimento,cliente.getDataNascimento());
        valores.put(BD.KEY_email, cliente.getEmail());
        valores.put(BD.KEY_telefone, cliente.getTelefone());
        valores.put(BD.KEY_ptsfidelidade, cliente.getPtsFidelidade());
        int resultado = db.update(BD.TAB_CLIENTE,valores,where,null);
        db.close();

        if (resultado == -1)
            return "Erro ao alterar cliente";
        else
            return "Cliente alterado com sucesso";
    }

    @Override
    public String alteraRoupa(Roupa roupa){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = BD.KEY_codigoRoupa + "='" + roupa.getCodigo()+"'";
        valores = new ContentValues();
        valores.put(BD.KEY_nomeRoupa, roupa.getNome());
        valores.put(BD.KEY_preco, roupa.getPreco());
        valores.put(BD.KEY_cor, roupa.getCor());
        valores.put(BD.KEY_tamanho, roupa.getTamanho());
        valores.put(BD.KEY_material, roupa.getMaterial());
        valores.put(BD.KEY_detalhes,roupa.getDetalhes());
        int resultado = db.update(BD.TAB_ROUPA,valores,where,null);
        db.close();
        if (resultado == -1)
            return "Erro ao alterar roupa";
        else
            return "Roupa alterada com sucesso";
    }
/*

    public void excluiVendaFunc (String CPF){

        String where = BD.KEY_CPFfuncionarioVenda + " = " + "'" + CPF + "'";
        db = banco.getReadableDatabase();
        if(CPF != null) {
            db.delete(BD.TAB_VENDA, where, null);
            db.close();
        }
    }

    public void excluiVendaCliente (String CPF){

        String where = BD.KEY_CPFclienteVenda + " = " + "'" + CPF + "'";
        db = banco.getReadableDatabase();
        if(CPF != null) {
            db.delete(BD.TAB_VENDA, where, null);
            db.close();
        }
    }
    */
}

