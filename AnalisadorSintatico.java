import java.io.IOException;

import Tokens.*;
import TabelaSimbolos.*;

public class AnalisadorSintatico { 
	private Token prox;
	private AnalisadorLexico lexico;
	private TabelaSimbolos tb;
	private int Endereco = 0;
	private String MsgErro = "";
	private boolean erro = false;
	
	public AnalisadorSintatico(AnalisadorLexico lexico, TabelaSimbolos t)throws IOException, Exception{
		this.prox = null;
		this.lexico = lexico;
		this.tb = t;
		this.prox = this.lexico.scan();
	}
	
	private void eat(byte tag) throws IOException, Exception{
		if(tag != this.prox.tag){
			this.MsgErro+="Erro linha "+this.lexico.line+": símbolo "+this.prox.toString()+" não esperado\n";
			this.erro = true;
		}
		this.prox = this.lexico.scan();
	}

	
	private void constant()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.INT_CONST:
				eat(Tag.INT_CONST);
				break;
			case Tag.FLOAT_CONST:
				eat(Tag.FLOAT_CONST);
				break;
		}
	}
	
	private void mulop()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.MUL:
				eat(Tag.MUL);
				break;
			case Tag.DIV:
				eat(Tag.DIV);
				break;
			case Tag.AND:
				eat(Tag.AND);
				break;
			
		}
	}
	
	private void addop()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.ADD:
				eat(Tag.ADD);
				break;
			case Tag.SUB:
				eat(Tag.SUB);
				break;
			case Tag.OR:
				eat(Tag.OR);
				break;
		}
	}
	
	private void relop()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.EQ:
				eat(Tag.EQ);
				break;
			case Tag.NE:
				eat(Tag.NE);
				break;
			case Tag.GE:
				eat(Tag.GE);
				break;
			case Tag.GT:
				eat(Tag.GT);
				break;
			case Tag.LE:
				eat(Tag.LE);
				break;
			case Tag.LT:
				eat(Tag.LT);
				break;
		}
	}
	
	private void factor()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.INT_CONST:
			case Tag.FLOAT_CONST:
				constant();
				break;
			case Tag.IDENTIFIER:
				eat(Tag.IDENTIFIER);
				break;
			case Tag.ABPAR:
				eat(Tag.ABPAR);
				expression();
				eat(Tag.FEPAR);
				break;
		}
	}
	
	private void factor_a()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.SUB:
				eat(Tag.SUB);
				factor();
				break;
			case Tag.NOT:
				eat(Tag.NOT);
				factor();
				break;
			case Tag.IDENTIFIER:
			case Tag.INT_CONST:
			case Tag.FLOAT_CONST:
			case Tag.ABPAR:
				factor();
				break;
		}
	}
	
	
	private void term_loop()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.MUL:
			case Tag.DIV:
			case Tag.AND:
				mulop();
				term();
				break;
			case Tag.PTVIR:
			case Tag.FEPAR:
			case Tag.OR:
			case Tag.SUB:
			case Tag.ADD:
			case Tag.NE:
			case Tag.LE:
			case Tag.LT:
			case Tag.GE:
			case Tag.GT:
			case Tag.EQ:
			case Tag.DO:
			case Tag.THEN:
			case Tag.STOP:
			case Tag.ELSE:
			case Tag.END:
			case Tag.UNTIL:
				return;
			default:
				this.MsgErro+="Erro linha "+ this.lexico.line+": Símbolo \""+this.prox.toString()+"\" encontrado, esperado \"*\", \"/\", \"&&\", \"+\", \"-\" ou \"||\"\n";
				this.erro = true;
				while(this.prox.tag != Tag.ADD && this.prox.tag != Tag.SUB &&
						this.prox.tag != Tag.OR && this.prox.tag != Tag.STOP &&
						this.prox.tag != Tag.END && this.prox.tag != Tag.ELSE &&
						this.prox.tag != Tag.UNTIL && this.prox.tag != Tag.THEN &&
						this.prox.tag != Tag.DO && this.prox.tag != Tag.EQ &&
						this.prox.tag != Tag.NE && this.prox.tag != Tag.GE &&
						this.prox.tag != Tag.GT && this.prox.tag != Tag.LE &&
						this.prox.tag != Tag.LT && this.prox.tag != Tag.FEPAR &&
						this.prox.tag != Tag.PTVIR && this.prox.tag != Tag.MUL &&
						this.prox.tag != Tag.DIV && this.prox.tag != Tag.AND){
					this.prox = this.lexico.scan();
				}
				term_loop();
			
		}
	}
	
	private void term()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.SUB:
			case Tag.NOT:
			case Tag.INT_CONST:
			case Tag.FLOAT_CONST:
			case Tag.IDENTIFIER:
			case Tag.ABPAR:
				factor_a();
				term_loop();
		}
		
	}
	
	private void simple_expr_loop()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.ADD:
			case Tag.SUB:
			case Tag.OR:
				addop();
				simple_expr();
				break;
			case Tag.STOP:
			case Tag.END:
			case Tag.ELSE:
			case Tag.UNTIL:
			case Tag.THEN:
			case Tag.DO:
			case Tag.EQ:
			case Tag.NE:
			case Tag.GE:
			case Tag.GT:
			case Tag.LE:
			case Tag.LT:
			case Tag.FEPAR:
			case Tag.PTVIR:
				return;
			default:
				this.MsgErro+="Erro linha "+ this.lexico.line+": Símbolo \""+this.prox.toString()+"\" encontrado, esperado \"+\", \"-\" ou \"||\"\n";
				this.erro = true;
				while(this.prox.tag != Tag.ADD && this.prox.tag != Tag.SUB &&
						this.prox.tag != Tag.OR && this.prox.tag != Tag.STOP &&
						this.prox.tag != Tag.END && this.prox.tag != Tag.ELSE &&
						this.prox.tag != Tag.UNTIL && this.prox.tag != Tag.THEN &&
						this.prox.tag != Tag.DO && this.prox.tag != Tag.EQ &&
						this.prox.tag != Tag.NE && this.prox.tag != Tag.GE &&
						this.prox.tag != Tag.GT && this.prox.tag != Tag.LE &&
						this.prox.tag != Tag.LT && this.prox.tag != Tag.FEPAR &&
						this.prox.tag != Tag.PTVIR){
					this.prox = this.lexico.scan();
				}
				simple_expr_loop();
		}
	}
	
	private void simple_expr()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.SUB:
			case Tag.NOT:
			case Tag.INT_CONST:
			case Tag.FLOAT_CONST:
			case Tag.IDENTIFIER:
			case Tag.ABPAR:
				term();
				simple_expr_loop();
				break;
		}
	}
	
	private void expression_loop()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.EQ:
			case Tag.GT:
			case Tag.GE:
			case Tag.LT:
			case Tag.LE:
			case Tag.NE:
				relop();
				expression();
				break;
			case Tag.STOP:
			case Tag.ELSE:
			case Tag.END:
			case Tag.UNTIL:
			case Tag.FEPAR:
			case Tag.THEN:
			case Tag.PTVIR:
				return;
				
		}
	}
	
	private void expression()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.SUB:
			case Tag.NOT:
			case Tag.INT_CONST:
			case Tag.FLOAT_CONST:
			case Tag.IDENTIFIER:
			case Tag.ABPAR:
				simple_expr();
				expression_loop();
				break;
		}
	}
	
	private void writable()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.SUB:
			case Tag.NOT:
			case Tag.INT_CONST:
			case Tag.FLOAT_CONST:
			case Tag.IDENTIFIER:
			case Tag.ABPAR:
				simple_expr();
				break;
			case Tag.LITERAL:
				eat(Tag.LITERAL);
		}
	}
	
	private void write_stmt()throws IOException, Exception{
			eat(Tag.WRITE);
			eat(Tag.ABPAR);
			writable();
			eat(Tag.FEPAR);
	}
	
	private void read_stmt()throws IOException, Exception{
			eat(Tag.READ);
			eat(Tag.ABPAR);
			eat(Tag.IDENTIFIER);
			eat(Tag.FEPAR);
	}
	
	private void stmt_prefix()throws IOException, Exception{
		
			eat(Tag.WHILE);
			condition();
			eat(Tag.DO);
		
	}
	
	private void while_stmt()throws IOException, Exception{
				stmt_prefix();
				stmt_list();
				eat(Tag.END);
	}
	
	private void stmt_suffix()throws IOException, Exception{
		if(this.prox.tag == Tag.UNTIL){
			eat(Tag.UNTIL);
			condition();
		}
		else{
			while(this.prox.tag != Tag.UNTIL && this.prox.tag != Tag.EOF){
				this.prox = this.lexico.scan();
			}
			if(this.prox.tag == Tag.UNTIL){
				eat(Tag.UNTIL);
				condition();
			}
			else{
				this.erro=true;
			}
		}
		
	}
	
	private void repeat_stmt()throws IOException, Exception{
		eat(Tag.REPEAT);
		stmt_list();
		stmt_suffix();
	}
	
	private void condition()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.SUB:
			case Tag.NOT:
			case Tag.INT_CONST:
			case Tag.FLOAT_CONST:
			case Tag.IDENTIFIER:
			case Tag.ABPAR:
				expression();
				break;
		}
	}
	
	private void else_stmt()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.ELSE:
				eat(Tag.ELSE);
				stmt_list();
				eat(Tag.END);
				break;
			case Tag.END:
				eat(Tag.END);
				break;
			case Tag.EOF:
				break;
			default:
				this.MsgErro += "Erro linha "+this.lexico.line+": Esperado terminador de bloco \"end\" ou \"else\" encontrado \""+this.prox.toString()+"\"\n";
				this.erro = true;
				while(this.prox.tag != Tag.END && this.prox.tag != Tag.ELSE && this.prox.tag != Tag.EOF){
					this.prox = this.lexico.scan();
				}
				else_stmt();
				
		}
	}
	
	private void if_stmt()throws IOException, Exception{
		eat(Tag.IF);
		condition();
		eat(Tag.THEN);
		stmt_list();
		else_stmt();
	}

	private void assign_stmt()throws IOException, Exception{
		eat(Tag.IDENTIFIER);
		eat(Tag.ASSIGN);
		simple_expr();
	}
	
	private void stmt()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.IF:
				if_stmt();
				break;
			case Tag.WHILE:
				while_stmt();
				break;
			case Tag.REPEAT:
				repeat_stmt();
				break;
			case Tag.READ:
				read_stmt();
				break;
			case Tag.WRITE:
				write_stmt();
				break;
			case Tag.IDENTIFIER:
				assign_stmt();
				break;
			default:
				this.MsgErro+="Erro linha "+this.lexico.line+": Operação inválida \""+this.prox.toString()+"\"\n";
				this.erro = true;
				while(this.prox.tag != Tag.STOP && this.prox.tag != Tag.PTVIR &&
						this.prox.tag != Tag.END && this.prox.tag != Tag.ELSE &&
						this.prox.tag != Tag.UNTIL && this.prox.tag != Tag.IF &&
						this.prox.tag != Tag.WHILE && this.prox.tag != Tag.REPEAT &&
						this.prox.tag != Tag.READ && this.prox.tag != Tag.WRITE &&
						this.prox.tag != Tag.IDENTIFIER && this.prox.tag != Tag.EOF){
					this.prox = this.lexico.scan();
				}
				switch(this.prox.tag){
				case Tag.IF:
					if_stmt();
					break;
				case Tag.WHILE:
					while_stmt();
					break;
				case Tag.REPEAT:
					repeat_stmt();
					break;
				case Tag.READ:
					read_stmt();
					break;
				case Tag.WRITE:
					write_stmt();
					break;
				case Tag.IDENTIFIER:
					assign_stmt();
					break;
				case Tag.END:
				case Tag.ELSE:
				case Tag.UNTIL:
				case Tag.STOP:
				case Tag.EOF:
					return;
				}
		}
	}
	
	private void stmt_list_loop()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.PTVIR:
				eat(Tag.PTVIR);
				stmt_list();
				break;
			case Tag.STOP:
			case Tag.END:
			case Tag.UNTIL:
			case Tag.ELSE:
				return;
			case Tag.EOF:
				this.MsgErro+="Erro linha "+this.lexico.line+": Não foi encontrado o terminador \"stop\"\n";
				this.erro = true;
				break;
			default:
				this.MsgErro+="Erro linha "+ this.lexico.line+": Esperado um separador \";\" ou fim de programa \"stop\", encontrado \""+this.prox.toString()+"\"\n";
				this.erro=true;
				while(this.prox.tag != Tag.PTVIR && this.prox.tag != Tag.STOP &&
						this.prox.tag != Tag.ELSE && this.prox.tag != Tag.END &&
						this.prox.tag != Tag.UNTIL && this.prox.tag != Tag.EOF){
					this.prox = this.lexico.scan();
				}
				if(this.prox.tag == Tag.PTVIR){
					eat(Tag.PTVIR);
					stmt_list();
				}
				else if(this.prox.tag == Tag.EOF){
					this.MsgErro+="Erro linha "+this.lexico.line+": Não foi encontrado o terminador \"stop\"\n";
					this.erro = true;
				}
				else return;
		}
	}
	
	private void stmt_list()throws IOException, Exception{
		stmt();
		stmt_list_loop();
	}
	
	private byte type()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.INTEGER:
				eat(Tag.INTEGER);
				return Tag.INTEGER;
			case Tag.REAL:
				eat(Tag.REAL);
				return Tag.REAL;
			default:
				return -1;
		}
	}
	
	private void ident_list_loop(byte t)throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.VIR:
				eat(Tag.VIR);
				ident_list(t);
				break;
			case Tag.PTVIR:
			case Tag.START:
				return;
			case Tag.EOF:
				return;
			default:
				this.MsgErro+="Erro linha "+this.lexico.line+": Esperado um separador \",\" ou \";\" ou início de programa \"start\", encontrado \""+this.prox.toString()+"\"\n";
				this.erro = true;
				while(this.prox.tag != Tag.PTVIR && this.prox.tag != Tag.START&&this.prox.tag!=Tag.VIR && this.prox.tag != Tag.EOF){
					this.prox = this.lexico.scan();
				}
				ident_list_loop(t);
		}
		
		
	}
	
	private void ident_list(byte t)throws IOException, Exception{
		if(this.prox.tag == Tag.IDENTIFIER){
			if(this.tb.put(this.prox, new Id(t,this.Endereco))){
				this.Endereco += 4;
			}
			else{
				this.MsgErro += "Erro linha "+this.lexico.line+": Identificador \""+this.prox.toString()+"\" já foi declarado\n";
				this.erro = true;
			}
			eat(Tag.IDENTIFIER);
			ident_list_loop(t);
			
		}
		else{
			this.MsgErro+="Erro linha "+this.lexico.line+": Identificador para variável inválido \""+this.prox.toString()+"\"\n";
			this.erro = true;
			while(this.prox.tag != Tag.PTVIR && this.prox.tag != Tag.START && this.prox.tag != Tag.EOF){
				this.prox = this.lexico.scan();
			}
			if(this.prox.tag==Tag.EOF)
				return;
			return;
		}
	}
	
	private void decl()throws IOException, Exception{
		if(this.prox.tag == Tag.REAL || this.prox.tag == Tag.INTEGER){
			byte t = type();
			ident_list(t);
		}
		else{
			this.MsgErro+="Erro linha "+this.lexico.line +"Tipo inválido\n";
			this.erro = true;
			while( this.prox.tag != Tag.START && this.prox.tag != Tag.PTVIR && this.prox.tag != Tag.EOF ){
				this.prox = this.lexico.scan();
			}
			return;
		}
	}
	
	private void decl_list_loop()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.PTVIR:
				eat(Tag.PTVIR);
				decl_list();
				break;
			case Tag.START:
				return;
			case Tag.EOF:
				return;
			default:
				this.MsgErro+="Erro linha "+this.lexico.line+": Esperado \";\" ou \"start\", encontrado \""+this.prox.toString()+"\"\n";
				this.erro = true;
				while(this.prox.tag != Tag.PTVIR &&
						this.prox.tag != Tag.START &&
						this.prox.tag != Tag.EOF){
					this.prox = this.lexico.scan();
				}
				decl_list_loop();
		}
	}
	
	private void decl_list()throws IOException, Exception{
		switch(this.prox.tag){
			case Tag.INTEGER:
			case Tag.REAL:
				decl();
				decl_list_loop();
				break;
			case Tag.EOF:
				return;
			default:
				this.MsgErro+="Erro linha "+ this.lexico.line + ": Tipo \""+this.prox.toString()+"\" inválido.\n";
				this.erro = true;
				while(this.prox.tag != Tag.INTEGER &&
						this.prox.tag != Tag.REAL &&
						this.prox.tag != Tag.START &&
						this.prox.tag != Tag.EOF){
					this.prox = this.lexico.scan();
				}
				if(this.prox.tag == Tag.START){
					return;
				}
				else{
					decl_list();
				}
		}
		
		
	}
	
	private void body()throws IOException, Exception{
		switch(this.prox.tag){
		case Tag.START : eat(Tag.START);
						 stmt_list();
						 eat(Tag.STOP);
						 break;
		case Tag.INTEGER:
		case Tag.REAL:
						decl_list();
						eat(Tag.START);
						stmt_list();
						eat(Tag.STOP);
						break;
		default:
			this.MsgErro+="Erro linha "+this.lexico.line+": Símbolo \""+this.prox.toString()+"\" não esperado. Esperada um declaração ou start\n";
			this.erro = true;
			while(this.prox.tag != Tag.START && 
					this.prox.tag != Tag.INTEGER && 
					this.prox.tag != Tag.REAL&&
					this.prox.tag != Tag.EOF){
				this.prox = this.lexico.scan();
			}
			if(this.prox.tag==Tag.EOF){
				return;
			}
			else body();
			
		}
	}
	
	private void Program()throws IOException, Exception{
		if(this.prox.tag == Tag.APP){
			eat(Tag.APP);
			if(this.prox.tag == Tag.IDENTIFIER){
				this.tb.put(this.prox, new Id(Tag.APP,this.Endereco));
				eat(Tag.IDENTIFIER);
				body();
			}
			else{
				this.MsgErro += "Erro linha "+this.lexico.line+": Identificador para o programa não encontrado\n";
				this.erro = true;
				while(this.prox.tag != Tag.INTEGER && this.prox.tag != Tag.REAL && this.prox.tag != Tag.START && this.prox.tag != Tag.EOF){
					this.prox = this.lexico.scan();
				}
				body();
			}
		}
		else{
			this.MsgErro+="Erro linha "+this.lexico.line+": Símbolo inicial app não encontrado.\n";
			this.erro = true;
		}
	}
	
	
	public void analyse()throws ParserException,IOException, Exception{
		float t1 = System.nanoTime();
		Program();
		t1 = System.nanoTime()-t1;
		
		if(this.erro){
			this.MsgErro+="Fim do arquivo atingido, erros foram encontrados. Tempo de análise: "+t1/10e6+" ms";
			throw new ParserException(this.MsgErro);			
		}
		else{
			System.out.println("Compilação terminada com sucesso em "+t1/10e6+" ms");
		}
	}
	
}
