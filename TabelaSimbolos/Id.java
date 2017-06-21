package TabelaSimbolos;

public class Id {
	public byte Tipo;
	public int Endereco;
	
	public Id(byte t, int ende){
		this.Tipo = t;
		this.Endereco = ende;
	}
	
	public String toString(){
		if(this.Tipo == 7){
			return "Integer "+this.Endereco;
		}
		if(this.Tipo == 8){
			return "Real "+this.Endereco;
		}
		if(this.Tipo == 0)
			return "App "+this.Endereco;
		return null;
	}
	
}
