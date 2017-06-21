package Tokens;

public class IntConst extends Token {
	public final int value;
	public IntConst(int value){
		super(Tag.INT_CONST);
		this.value=value;
	}
	
	public String toString(){
		return ""+this.value;
	}

}
