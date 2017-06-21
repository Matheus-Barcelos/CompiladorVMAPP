import java.io.IOException;

import AnalisadorLexico.*;
import AnalisadorSintatico.*;
import TabelaSimbolos.*;
import java.util.*:

public class Tipo {
	public static final int inteiro = 1;
	public static final int string = 2;
	public static final int error = 4;
	public static final int vazio = 5;
	   
	private int tipo;
	private static Hashtable tipos;

	public Tipo(int tipo) {
		this.tipo = tipo;
	}      
	    
	public static void inicializarTipos(){
		tipos = new Hashtable();
	    tipos.put(inteiro, new Tipo(inteiro));
	    tipos.put(string, new Tipo(string));
	    tipos.put(error, new Tipo(error));
	    tipos.put(vazio, new Tipo(vazio));
    }
	    
	public static Tipo inteiro(){
        return (Tipo)tipos.get(inteiro);
    }
    
    public static Tipo string(){
        return (Tipo)tipos.get(string);
    }
	    
    public static Tipo erro(){
        return (Tipo)tipos.get(error);
    }
    
    public static Tipo vazio(){
        return (Tipo)tipos.get(vazio);
    }
	    
    public boolean isInteiro(){
        return inteiro == tipo;
    }
	    
    public boolean isString(){
        return string == tipo;
    }
	    
    public boolean isErro(){
        return error == tipo;
    }
	    
    public boolean isVazio(){
        return vazio == tipo;
    }
	    
    public int getTipo(){
        return tipo;
	}
	    
    public boolean isTipo(Tipo tipo){
        return this.tipo == tipo.getTipo();
	}
}

public class Regra {
	public static final int declaracao = 1;
    public static final int comando = 2;
    public static final int expressao = 3;
    public static final int escrita = 4;
    public static final int leitura = 5;
    //Pra por na tabela de símbolos
    private ArrayList<Id> listaDeclaracoes;
    private int regra;
    private Tipo tipo;
    private boolean operacaoLogica;
    private boolean operacao;

    public Regra(int regra) {
        this.regra = regra;
        this.tipo = Tipo.vazio();
    }
    
    public Tipo getTipo(){
        return tipo;
    }
    
    public static Regra declaracao(){
        Regra r = new Regra(declaracao);
        r.listaDeclaracoes = new ArrayList<Id>();
        return r;
    }
    
    public static Regra comando (Tipo tipo){
        Regra r = new Regra(comando);
        r.tipo = tipo;
        return r;
    }
    
    public static Regra expressao (Tipo tipo){
        Regra r = new Regra(expressao);
        r.tipo = tipo;
        return r;
    }
    
    public static Regra escrita (){
        Regra r = new Regra(escrita);
        r.tipo = Tipo.vazio();
        return r;
    }
    
    public static Regra leitura (){
        Regra r = new Regra(leitura);
        r.tipo = Tipo.vazio();
        return r;
    }
    
    public void addId(Id id){
        listaDeclaracoes.add(id);
    }
    
    public void setTiposDeclaracoes(AnalisadorSintatico sintatico){ 
    	//setar os tipos de todos os ids armazenados
        if(tipo.isErro()) return;
        for(Id id: listaDeclaracoes){
            id.setTipo(tipo);
            //sintatico.tabaleSimbolos.put(id);
        }
    }
    
    public boolean verificarTipo(Tipo tipo){
        if(this.tipo.isTipo(Tipo.valorLogico()) && this.operacaoLogica) return true;
        if(this.tipo.isTipo(Tipo.vazio())) return true;
        return this.tipo.isTipo(tipo);
    }
    
    public boolean isDeclaracao(){
        return regra == declaracao;
    }
    
    public boolean isComando(){
        return regra == comando;
    }
    
    public boolean isExpressao(){
        return regra == expressao;
    }
    
    public boolean isEscrita(){
        return regra == escrita;
    }
    
    public boolean isLeitura(){
        return regra == leitura;
    }
    
    public void setTipo(Tipo tipo){
        if(!this.tipo.isErro() && !tipo.isVazio()){
            this.tipo = tipo;
        }
    }

    public void operacaoLogica(){
        this.operacaoLogica = true;
    }
    
    public void operacao(){
        this.operacao = true;
    }
    
    public boolean isOperacaoLogica(){
        return operacaoLogica;
    }
    
    public boolean isOperacao(){
        return operacao;
    }
    
    public void verificaLeitura(Regra regra, int numeroLinha){
        if(!regra.verificarTipo(Tipo.inteiro())){
            this.setTipo(Tipo.erro());
            System.out.println("ERRO: Comando READ espera por um argumento do tipo INTEIRO. (Linha:"+numeroLinha+")");
        }else{
            this.setTipo(regra.getTipo());
        }
    }
    
    public void verificaWhileIf(int numeroLinha){
        if(!this.verificarTipo(Tipo.valorLogico())){
            this.setTipo(Tipo.erro());
            System.out.println("ERRO: Esperada expressao logica. (Linha:"+numeroLinha+")");
        }
    }
    
    public void verficaCondicao(){
        if(this.operacaoLogica){
            this.tipo = Tipo.valorLogico();
        }else{
            this.tipo = Tipo.erro();
        }
    }
    
    public void verificaNumero(AnalisadorSintatico sintatico){
    	if(this.isExpressao()){
            if(this.verificarTipo(Tipo.inteiro())){
                this.setTipo(Tipo.inteiro());
            }else{
                this.setTipo(Tipo.erro());
                System.out.println("ERRO: Tipos incompatíveis de operandos. (Linha:"+sintatico.analisadorLexico.getNumeroLinha()+")");
            }
        }else{ //caso do read e da atribuição
            this.setTipo(Tipo.inteiro());
        }
    }
}

public class AnalisadorSemantico {
	private AnalisadorLexico lexico;
	private AnalisadorSintatico sintatico;
	private TabelaSimbolos tb;
		
	public AnalisadorSemantico(AnalisadorSintatico sintatico, TabelaSimbolos t)throws IOException, Exception{
		this.sintatico = sintatico;
		this.tb = t;
	}
	
}
	

	
