parser grammar DecafParser;

options {
    tokenVocab=DecafLexer;
}

@parser::header{
  package compiler.parser;
}

// expresiones
start 			: class_decl_error EOF
				| class_decl EOF {System.out.println("start");};

statement		: location ASIG_OP expr PUNTO_COMA {System.out.println("asginacion");}
				| method_call PUNTO_COMA {System.out.println("llamado a metodo");}
				| KW_IF PARENTESIS_I expr PARENTESIS_D block (KW_ELSE block)? { System.out.println("if");}
				| KW_FOR PARENTESIS_I ID ASIG_OP expr COMA expr PARENTESIS_D block {System.out.println("for");}
				| KW_WHILE PARENTESIS_I expr PARENTESIS_D block {System.out.println("while");}
				| KW_RETURN (expr)? PUNTO_COMA {System.out.println("return");}
				| KW_BREAK PUNTO_COMA {System.out.println("break");}
				| KW_CONTINUE PUNTO_COMA{System.out.println("statement");};

expr 			: location {System.out.println("location");}
				| method_call {System.out.println("llamado a metodo");}
				| literal { System.out.println("literal");}
				| expr bin_op expr {System.out.println("operacion");}
				| MINUS expr {System.out.println("menos expr");}
				| NEGATION expr { System.out.println("negacion de expr");}
				| PARENTESIS_I expr PARENTESIS_D{System.out.println("(expr)");};

literal			: (INT_LITERAL | CHAR_LITERAL | BOOL_LITERAL ){System.out.println("literal");};

// expr 			: location {System.out.println("location");}
// 				| method_call {System.out.println("llamado a metodo");}
// 				| LITERAL { System.out.println("literal");}
// 				| op_expr {System.out.println("operacion");}
// 				| MINUS expr {System.out.println("menos expr");}
// 				| NEGATION expr { System.out.println("negacion de expr");}
// 				| PARENTESIS_I expr PARENTESIS_D{System.out.println("(expr)");};


// op_expr			: op_expr addOp op_expr { System.out.println("op_expr addOp op_term");}
// 				| op_expr {System.out.println("op_term");}
// 				| op_expr mulOp op_num {System.out.println("op_term mulOp op_num");}
// 				| op_num {System.out.println("op_num");};

// op_num			: INT_LITERAL
// 				| PARENTESIS_I op_expr PARENTESIS_D
// 				| location
// 				| method_call;

block			: block_error
				|(LLAVE_I (field_decl | statement)* LLAVE_D{System.out.println("block");});
block_error		: LLAVE_I field_decl* statement*{System.out.println("falto cerrar llave");};

bin_op			: addOp 
				| mulOp
				| REL_OP 
				| EQ_OP 
				| COND_OP {System.out.println("bin op");};

class_decl		: KW_CLASS ID LLAVE_I program LLAVE_D {System.out.println("declaracion de clase");};
class_decl_error: KW_CLASS ID LLAVE_I program {System.out.println("Falto cerrar llave en declaracion de clase");};
method_decl		: (type | KW_VOID) ID PARENTESIS_I ( (type ID) | (type ID COMA )+(type ID) )? PARENTESIS_D block{System.out.println("declaracion de metodo");};
field_decl		: field_decl_error
				| type ( (ID | ID CORCHETE_I INT_LITERAL CORCHETE_D)? | (ID COMA | ID CORCHETE_I INT_LITERAL CORCHETE_D COMA)+(ID | ID CORCHETE_I INT_LITERAL CORCHETE_D)) PUNTO_COMA{System.out.println("declaracion de variable");};

field_decl_error: type ( (ID | ID CORCHETE_I INT_LITERAL? bin_op? INT_LITERAL? CORCHETE_D)? | (ID COMA | ID CORCHETE_I INT_LITERAL? bin_op? INT_LITERAL? CORCHETE_D COMA)+(ID | ID CORCHETE_I INT_LITERAL? bin_op? INT_LITERAL? CORCHETE_D)) EQUAL? literal? {System.out.println("Falto ;");}
				| type ( (ID | ID CORCHETE_I INT_LITERAL? CORCHETE_D)? | (ID COMA | ID CORCHETE_I INT_LITERAL? CORCHETE_D COMA)+(ID | ID CORCHETE_I INT_LITERAL? CORCHETE_D)) EQUAL literal PUNTO_COMA {System.out.println("no se puede inicializar");/*no funciona*/ } 
				| type ( (ID CORCHETE_I INT_LITERAL bin_op INT_LITERAL CORCHETE_D)? | (ID COMA | ID CORCHETE_I INT_LITERAL bin_op INT_LITERAL CORCHETE_D COMA)+(ID CORCHETE_I INT_LITERAL bin_op INT_LITERAL CORCHETE_D)) PUNTO_COMA {System.out.println("Error en la declaracion del arreglo");/*no funciona*/}
				| type ( (ID CORCHETE_I CORCHETE_D)? | (ID COMA | ID CORCHETE_I CORCHETE_D COMA)+(ID CORCHETE_I CORCHETE_D)) PUNTO_COMA {System.out.println("Falto el tamaño del arreglo");};
				
type			: KW_INT | KW_BOOL;
method_call		: (method_name | KW_CALLOUT) PARENTESIS_I ( (expr)? | (expr COMA )+(expr) ) PARENTESIS_D
				| (method_name | KW_CALLOUT) PARENTESIS_I ( (callout_arg)? | (callout_arg COMA )+ (callout_arg) ) PARENTESIS_D;

callout_arg		: expr | STRING_LITERAL;

callout_decl	: KW_CALLOUT ID PUNTO_COMA {System.out.println("callout_decl");};

program			: program_error
				| callout_decl* field_decl* method_decl* {System.out.println("program");};
program_error	: callout_decl* method_decl field_decl {System.out.println("error las declaraciones van primero");};

location		: ID | (ID CORCHETE_I expr CORCHETE_D);
method_name		: ID;

addOp 			: PLUS  
				| MINUS;
mulOp 			: MULT
				| DIV;
