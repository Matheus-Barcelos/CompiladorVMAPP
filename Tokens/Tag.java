package Tokens;

public class Tag {
	public static final byte 
		//Definição de início do código
		APP=0,
		
		//Delimitadores de blocos
		START=1,
		STOP=2,
		THEN=3,
		END=4,
		UNTIL=5,
		DO=6,
		
		//Tipos
		INTEGER=7,
		REAL=8,
	
		//Blocos
		IF=9,
		ELSE=10,
		REPEAT=11,
		WHILE=12,
		READ=13,
		WRITE=14,
		ASSIGN=15,
	
		//Operações de comparação (Relop)
		EQ=100,
		GT=101,
		GE=102,
		LT=103,
		LE=104,
		NE=105,
		
		//Operações de adição (Addop)
		ADD=16,
		SUB=17,
		OR=18,
		
		//Operações de Multiplicação (Mulop)
		MUL=19,
		DIV=20,
		AND=21,
		
		NOT=33,
		
		//Constantes
		INT_CONST=22,
		FLOAT_CONST=23,
		LITERAL=24,
	
		//Identificador
		IDENTIFIER=25,
	
		//Pontuação
		ABPAR=40,
		FEPAR=41,
		VIR = 44,
		PTVIR = 59,
		EOF = -1;
}
	