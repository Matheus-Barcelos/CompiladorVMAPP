import java.io.FileReader;
import TabelaSimbolos.TabelaSimbolos;
import java.io.FileNotFoundException;

public class Main {

	
	public static void main(String[] args) {
		try{
			
			FileReader ar_Fonte = new FileReader(args[0]);
			TabelaSimbolos tb = new TabelaSimbolos();
			AnalisadorLexico al = new AnalisadorLexico(ar_Fonte);
			AnalisadorSintatico sl = new AnalisadorSintatico(al,tb);
			AnalisadorSemantico sm = new AnalisadorSemantico(sl, tb);
			sl.analyse();
			sm.verify();
			tb.printTable();
		}
		catch(FileNotFoundException e){
			System.out.println("Arquivo fonte: " + args[0] + ", não encontrado.");
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			
		}

	}
	
	

}
