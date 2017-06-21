
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import Tokens.*;

public class AnalisadorLexico {
	private FileReader ar_Fonte;
	private Hashtable<String, Word> tabela;
	private char ch;
	protected int line;
	
	public AnalisadorLexico(FileReader ar_Fonte){
		this.ar_Fonte = ar_Fonte;
		this.tabela = new Hashtable<String, Word>();
		this.ch = ' ';
		this.line = 1;
		
		reserve(Word.App);
		reserve(Word.If);
		reserve(Word.Else);
		reserve(Word.Start);
		reserve(Word.Stop);
		reserve(Word.Until);
		reserve(Word.Do);
		reserve(Word.Then);
		reserve(Word.End);
		reserve(Word.Repeat);
		reserve(Word.While);
		reserve(Word.Write);
		reserve(Word.Read);
		reserve(Word.integer);
		reserve(Word.real);
	}
	
	private void reserve(Word w){
		this.tabela.put(w.toString(), w);
	}
	
	private void readch() throws IOException{
		this.ch = (char) this.ar_Fonte.read();		
	}
	
	private boolean readch(char c) throws IOException{
		this.ch = (char) this.ar_Fonte.read();
		if (ch != c) return false;
		else{
			ch = ' ';
			return true;
		}
		
	}
	
	public Token scan() throws IOException, Exception{
		for(;;readch()){
			if(ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') continue;
			else if(ch == '%'){
				do{
					readch();
				}while(ch!='\n');
				this.line ++;
			}
			else if(ch == '\n') this.line ++;
			else break;
		}
		
		switch(ch){
			case '&': if(readch('&')) return Word.and;
						else new Token((byte)'&'); 
			case '|': if(readch('|')) return Word.or;
						else new Token((byte)'|');
			case '+': ch = ' ';
					  return Word.add;
			case '-': ch = ' ';
				      return Word.sub;
			case '*': ch=' ';
					  return Word.mult;
			case '/': ch = ' ';
					  return Word.div;
			case '=': ch = ' ';
					  return Word.eq;
			case '>': if(readch('=')) return Word.ge;
					  else return Word.gt;
			case '<': if(readch('=')) return Word.le;
					  else return Word.lt;
			case '!': if(readch('=')) return Word.ne;
					  else return Token.Not;
			case ':': if(readch('=')) return Word.as;
					  else return new Token((byte)':');
			case '(': ch = ' ';
				      return Token.Abpar;
			case ')': ch = ' ';
				      return Token.Fepar;
			case ',': ch = ' ';
		      		  return Token.Vir;
			case ';': ch = ' ';
		      		  return Token.Ptvir;
		}
		
		if(Character.isDigit(this.ch)){
			int ivalue=0;
			do{
				ivalue = 10*ivalue + Character.digit(this.ch,10);
				readch();
			}while(Character.isDigit(this.ch));
			if(ch == '.'){
				float alpha = 0.1f;
				float fvalue = (float) ivalue;
				readch();
				do{
					fvalue = fvalue + alpha*Character.digit(this.ch,10);
					alpha *= 0.1f;
					readch();
				}while(Character.isDigit(this.ch));
				return new FloatConst(fvalue);
			}
			return new IntConst(ivalue);
		}
		
		if(Character.isLetter(ch)){
			StringBuffer sb = new StringBuffer();
			do{
				ch = Character.toLowerCase(ch);
				sb.append(ch);
				readch();
			}while(Character.isLetterOrDigit(ch)||ch=='_');
			String s = sb.toString();
			Word w = this.tabela.get(s);
			if(w!=null)
				return w;
			w = new Word(s,Tag.IDENTIFIER);
			this.tabela.put(s, w);
			return w;
		}
		
		if(this.ch == '{'){
			StringBuffer sb = new StringBuffer();
			readch();
			int linha = this.line;
			while(this.ch!='}'&&this.ch!=(char)(-1)){
				sb.append(this.ch);
				readch();
			}
			if(this.ch==(char)(-1))
				throw new Exception("Erro na linha "+linha+": Fim do Literal não encontrado");
			else{
				String s = sb.toString();
				this.ch = ' ';
				return new Word(s,Tag.LITERAL);
			}
		}
		
		Token t = new Token((byte)ch);
		ch = ' ';
		return t;
		
	}
}
