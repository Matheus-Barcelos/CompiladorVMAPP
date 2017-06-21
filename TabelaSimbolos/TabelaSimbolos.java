package TabelaSimbolos;
import Tokens.*;

import java.util.ArrayList;
import java.util.Hashtable;
public class TabelaSimbolos {
	
	private class Ambiente{
		protected Ambiente anterior;
		protected Hashtable<Token, Id> tabela;
		
		public Ambiente(Ambiente anterior){
			this.anterior = anterior;
			this.tabela = new Hashtable<Token,Id>();
			
		}
	}
	
	private Ambiente amb;
	
	public TabelaSimbolos(){
		this.amb = new Ambiente(null);
	}
	
	public boolean put(Token t, Id d){
		if(this.amb.tabela.containsKey(t))
			return false;
		this.amb.tabela.put(t, d);
		return true;
	}
	
	public Id get(Token w){
		for(Ambiente tb = this.amb; tb!=null; tb = tb.anterior){
			Id found = (Id) tb.tabela.get(w);
			if(found!=null)
				return found;
		}
		return null;
	}
	
	public void push(){
		Ambiente tmp = new Ambiente(this.amb);
		this.amb = tmp;
	}
	public void pop(){
		this.amb = this.amb.anterior;
		System.gc();
	}
	
	public ArrayList<Token> tokensInseridos(){
		ArrayList<Token> t = new ArrayList<Token>();
		for(Ambiente tb = this.amb; tb!=null; tb = tb.anterior){
			t.addAll(tb.tabela.keySet());
		}
		return t;
	}
	
	public void printTable(){
		ArrayList<Token> t = this.tokensInseridos();
		for (int i = 0; i<t.size(); i++){
			System.out.println(t.get(i).toString()+ " " + this.get(t.get(i)).toString());
		}
	}
}
