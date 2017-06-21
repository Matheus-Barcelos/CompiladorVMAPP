package Tokens;

public class Token {
	public final byte tag;
	
	public static final Token Not = new Token(Tag.NOT);
	public static final Token Abpar = new Token(Tag.ABPAR);
	public static final Token Fepar = new Token(Tag.FEPAR);
	public static final Token Vir = new Token(Tag.VIR);
	public static final Token Ptvir = new Token(Tag.PTVIR);
	
	public Token(byte tag){
		this.tag=tag;
	}
	
	public String toString(){
		return ""+(char)this.tag;
	}
}
