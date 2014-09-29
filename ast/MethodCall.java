package compiler.ast;

import java.util.LinkedList;
import java.util.List;
import compiler.semantic.*;

public class MethodCall extends Node {
	
	public String nameMethod;
	public LinkedList<Node> expresiones;

	/* para method_decl */
	public MethodCall(String name, LinkedList<Node> ex){
		this.nameMethod = name;
		this.expresiones = ex;
	}

	public String checkMethodCall(Table tb, SymbolTable st){
		boolean p = false;
		String resultado = "";
		LinkedList<String> lista = new LinkedList<String>();
		Table tabla = null;
		for (Node n : this.expresiones){
			if (n instanceof Exp){
				Exp ex = (Exp)n;
				lista.add(ex.checkExp(tb,st));
			}else if (n instanceof Literal){
				Literal lit = (Literal)n;
				lista.add(lit.checkLiteral(tb,st));
			}else if (n instanceof BinOp){
				BinOp bin = (BinOp)n;
				lista.add(bin.checkBinOp(tb,st));
			}
		}
		
		for (Table t : st.listaTablas){
			if (t.name.equals("ROOT")){
				tabla = t;
			}
		}
		if (tabla.tabla.containsKey(this.nameMethod) == true){
			resultado = tabla.tabla.get(this.nameMethod).tipo;
			//System.out.println("----> " + lista.toString());
			//System.out.println("----> " + tabla.tabla.get(this.nameMethod).tiposparametros.toString());
			if (lista.size() != tabla.tabla.get(this.nameMethod).tiposparametros.size()){
				System.out.println("Error en el numero de argumentos");
			}
			if (!lista.equals(tabla.tabla.get(this.nameMethod).tiposparametros)){
				System.out.println("parametro invalido en la llamada a " + this.nameMethod);
			}
		}else{
			System.out.println(this.nameMethod + "es un metodo no declarado");
		}
		return resultado;
	}
	
	@Override
	public String print(String padding) {
		String str = padding + "Llamada " +  nameMethod + "\n";
		if(expresiones != null){
			for ( Node e : expresiones ) {
				str += e.print(padding + "\t");
			}
		}
		return str;
	}
	
	@Override
	public String toString(){
		return "Method Call";
	}

	public int getDotTree(int parent, int i, List<String> dec, List<String> rel) {
		int nodoActual = i;

		dec.add("n" + ( ++i ) + "[label=\"" + nameMethod + "\"];");
		rel.add("n" + parent + " -> n" + i);		
		return i;
	}
}
