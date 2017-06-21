package Tokens;

public class Word extends Token {

	private String lexeme = "";
	
	//Operações
	public static final Word and = new Word("&&",Tag.AND);
	public static final Word mult = new Word("*",Tag.MUL);
	public static final Word div = new Word("/",Tag.DIV);
	
	public static final Word or = new Word("||",Tag.OR);
	public static final Word add = new Word("+",Tag.ADD);
	public static final Word sub = new Word("-",Tag.SUB);
	
	public static final Word eq = new Word("=",Tag.EQ);
	public static final Word gt = new Word(">",Tag.GT);
	public static final Word lt = new Word("<",Tag.LT);
	public static final Word ge = new Word(">=",Tag.GE);
	public static final Word le = new Word("<=",Tag.LE);
	public static final Word ne = new Word("!=",Tag.NE);
	
	public static final Word as = new Word(":=",Tag.ASSIGN);
	
	//Palavras Reservadas
	public static final Word App = new Word("app",Tag.APP);
	public static final Word If = new Word("if",Tag.IF);
	public static final Word Else = new Word("else",Tag.ELSE);
	public static final Word Start = new Word("start",Tag.START);
	public static final Word Stop = new Word("stop",Tag.STOP);
	public static final Word Until = new Word("until",Tag.UNTIL);
	public static final Word Do = new Word("do",Tag.DO);
	public static final Word Then = new Word("then",Tag.THEN);
	public static final Word End = new Word("end",Tag.END);
	public static final Word Repeat = new Word("repeat",Tag.REPEAT);
	public static final Word While = new Word("while",Tag.WHILE);
	public static final Word Write = new Word("write",Tag.WRITE);
	public static final Word Read = new Word("read",Tag.READ);
	public static final Word integer = new Word("int",Tag.INTEGER);
	public static final Word real = new Word("real",Tag.REAL);
	
	
	public Word(String lex, byte tag){
		super(tag);
		this.lexeme = lex;
	}
	
	public String toString(){
		return this.lexeme;
	}
}
