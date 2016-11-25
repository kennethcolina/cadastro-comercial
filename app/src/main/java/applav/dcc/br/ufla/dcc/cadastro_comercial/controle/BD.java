package applav.dcc.br.ufla.dcc.cadastro_comercial.controle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by João Paulo on 26/07/2016.
 */


public class BD extends SQLiteOpenHelper{

        public static final String NOME_BANCO = "banco.db";
        public static final int VERSAO = 1;
        public static final String TAB_FUNC = "Funcionario";
        public static final String TAB_LOGIN = "Login";
        public static final String TAB_ROUPA = "Roupa";
        public static final String TAB_CLIENTE = "Cliente";
        public static final String TAB_VENDA = "Venda";
        public static final String TAB_VENDA_CLIENTE = "VendaCliente";
        public static final String TAB_ROUPAbackUP = "RoupaBackUp";
        public static final String TAB_CLIENTEbackUP = "ClienteBackUp";
        public static final String TAB_FUNCbackUP = "FuncionaioBackUp";

        public static final String KEY_idRoupa = "idRoupa";
        public static final String KEY_referencia = "referencia";
        public static final String KEY_nomeRoupa = "nome";
        public static final String KEY_codigoRoupa = "codigo";
        public static final String KEY_preco = "preco";
        public static final String KEY_cor = "cor";
        public static final String KEY_tamanho = "tamanho";
        public static final String KEY_material = "material";
        public static final String KEY_detalhes = "detalhes";

        public static final String KEY_idFuncionario = "idFuncionario";
        public static final String KEY_cpf = "cpf";
        public static final String KEY_nome = "nome";
        public static final String KEY_sobrenome = "sobrenome";
        public static final String KEY_dataNascimento = "dataNascimento";
        public static final String KEY_email = "email";
        public static final String KEY_telefone = "telefone";
        public static final String KEY_salario = "salario";
        public static final String KEY_comissao = "comissao";
        public static final String KEY_nivel = "nivel";
        public static final String KEY_dataInicio = "dataInicio";

        public static final String KEY_idCliente = "idCliente";
        public static final String KEY_cpfCliente = "cpf";
        public static final String KEY_nomeCliente = "nome";
        public static final String KEY_sobrenomeCliente = "sobrenome";
        public static final String KEY_dataNascimentoCliente = "dataNascimento";
        public static final String KEY_emailCliente = "email";
        public static final String KEY_telefoneCliente = "telefone";
        public static final String KEY_ptsfidelidade = "ptsFidelidade";

        public static final String KEY_CPFLogin = "cpf";
        public static final String KEY_usuario = "usuario";
        public static final String KEY_senha = "senha";

        public static final String KEY_idVenda = "idVenda";
        public static final String KEY_CPFfuncionarioVenda = "cpf";
        public static final String KEY_codigoRoupaVenda = "codigo";
        public static final String KEY_CPFclienteVenda = "cpfCliente";
        public static final String KEY_DataVenda = "dataVenda";


        public BD(Context context){
        super(context, NOME_BANCO, null, VERSAO);
    }


        @Override
        public void onCreate(SQLiteDatabase db) {
                String sqlRoupa = "CREATE TABLE "+TAB_ROUPA+"("
                + KEY_idRoupa + " integer not null primary key autoincrement,"
                + KEY_referencia + " text not null,"
                + KEY_nomeRoupa + " text not null,"
                + KEY_codigoRoupa + " text not null unique,"
                + KEY_preco + " float,"
                + KEY_cor + " text,"
                + KEY_tamanho + " text,"
                + KEY_material + " text,"
                + KEY_detalhes + " text"
                +");";

                 db.execSQL(sqlRoupa);

                String sqlFunc = "CREATE TABLE "+TAB_FUNC+"("
                + KEY_idFuncionario + " integer not null primary key autoincrement,"
                + KEY_cpf + " text not null unique,"
                + KEY_nome + " text,"
                + KEY_sobrenome + " text,"
                + KEY_dataNascimento + " text,"
                + KEY_email + " text,"
                + KEY_telefone + " text,"
                + KEY_salario + " float,"
                + KEY_comissao + " float,"
                + KEY_nivel + " integer,"
                + KEY_dataInicio + " text"
                +");";

                db.execSQL(sqlFunc);

               String sqlCli = "CREATE TABLE "+TAB_CLIENTE+"("
                + KEY_idCliente + " integer not null primary key autoincrement,"
                + KEY_cpfCliente + " text unique not null,"
                + KEY_nomeCliente + " text,"
                + KEY_sobrenomeCliente + " text,"
                + KEY_dataNascimentoCliente + " text,"
                + KEY_emailCliente + " text,"
                + KEY_telefoneCliente + " text,"
                + KEY_ptsfidelidade + " int"
                +");";
                db.execSQL(sqlCli);

               String sqlLogin = "CREATE TABLE "+TAB_LOGIN+"("
                    + KEY_CPFLogin + " text not null,"
                    + KEY_usuario + " text not null,"
                    + KEY_senha + " text not null,"
                    + "PRIMARY KEY"+ "(" + KEY_CPFLogin + "," + KEY_usuario + "),"
                    + "FOREIGN KEY (cpf) "
                    + "REFERENCES Funcionario (cpf)"
                    +");";
                db.execSQL(sqlLogin);

                String sqlVenda = "CREATE TABLE "+TAB_VENDA+"("
                + KEY_idVenda + " integer not null primary key autoincrement,"
                + KEY_CPFfuncionarioVenda + " integer not null,"
                + KEY_CPFclienteVenda + " integer not null,"
                + KEY_DataVenda + " date not null,"
                + "FOREIGN KEY (cpfCliente)"
                + "REFERENCES Cliente (cpf),"
                + "FOREIGN KEY (cpf)"
                + "REFERENCES Funcionario (cpf)"
                +");";
            db.execSQL(sqlVenda);

            String sqlVendaRoupa = "CREATE TABLE "+TAB_VENDA_CLIENTE+"("
                    + KEY_idVenda + " integer not null,"
                    + KEY_codigoRoupaVenda + " integer not null,"
                    + "FOREIGN KEY (idVenda)"
                    + "REFERENCES Venda (idVenda)"
                    + "FOREIGN KEY (codigo)"
                    + "REFERENCES Roupa (codigo)"
                    +");";

            db.execSQL(sqlVendaRoupa);

            String sqlClienteBackup = "CREATE TABLE "+TAB_CLIENTEbackUP+"("
                    + KEY_cpfCliente + " text not null primary key,"
                    + KEY_nomeCliente + " text,"
                    + KEY_sobrenomeCliente + " text"
                    +");";
            db.execSQL(sqlClienteBackup);


            String sqlFuncBackup = "CREATE TABLE "+TAB_FUNCbackUP+"("
                    + KEY_cpf + " text not null primary key,"
                    + KEY_nome + " text,"
                    + KEY_sobrenome + " text"
                    +");";

            db.execSQL(sqlFuncBackup);

            String sqlRoupaBackup = "CREATE TABLE "+TAB_ROUPAbackUP+"("
                    + KEY_nomeRoupa + " text not null,"
                    + KEY_codigoRoupa + " text not null primary key,"
                    + KEY_preco + " float"
                    +");";

            db.execSQL(sqlRoupaBackup);


    }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + " ");
        onCreate(db);
    }

    }

