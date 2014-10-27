package compiler.ast.nodes;

import java.util.List;
import java.util.LinkedList;
import compiler.semantic.Table;
import compiler.semantic.SymbolTable;
import compiler.irt.IrtList;

/**
* 	Root representa cada scope en el programa este tendra una lista de declaraciones y se usara para todo el programa y para
*	cada bloque que aparesca en if while for y los metodos
*/
public class Root extends Node {

	/** 
	*	Listado con declaraciones
	*/
	public LinkedList<Node> declaraciones;
	
	/**
	*	Constructor sin parametros que innicializa la lista
	*/
	public Root(){
		declaraciones = new LinkedList<Node>();
	}
	
	/**
	*	Agrega nodos al bloque o root
	*	@param node Node con declaracion
	*/
	public void add(Node node){
		declaraciones.add(node);
	}

	/**
	*	Retorna la cantidad de elementos en el root
	* 	@return int con cantidad de nodos el el root
	*/
	public int size(){
		return declaraciones.size();
	}

	/**
	*	retona un Node la posicion i en el rango de [0 a cant-1]
	*	@param i numero de nodo 
	*/
	public Node getChild(int i){
		return declaraciones.get(i);
	}


	/**
	*	Imprime el ast sin padding
	*/
	public String print(){
		return print("");
	}

	
	/**
	*	{@inheritDoc}
	*/
	@Override
	public String print(String padding){
		String str = padding + "Root\n" ;
		for(Node n : declaraciones){
			if(n != null)
				str += n.print("\t" + padding);
		}
		return str;
	}
	
	/**
	*	{@inheritDoc}
	*/
	@Override
	public int getDotTree(int parent, int i, List<String> dec, List<String> rel){
		int nodoActual = i;
		dec.add("n" + ( ++i ) + "[label=\"Root\"];");
		if(parent != 0) 
			rel.add("n"+ parent + " -> n" + i );
		for (Node n : declaraciones ) {
			i = n.getDotTree(nodoActual+1, i, dec, rel);
		}
		return i;
	}

	/**
	*	{@inheritDoc}
	*/
	@Override
	public IrtList destruct(String parent, SymbolTable symbolTable) {
		IrtList irtList = new IrtList();
		for (Node node : declaraciones ) {
			if(node instanceof Declaracion) {
				// se podria ignorar dado que las instrucciones en este bloque solo podran ser field decl y para esos no se necesita realizar nada
			} else if(node instanceof Cond) {
				irtList.add(((Cond)node).destruct(parent, symbolTable));
			} else if(node instanceof Cycle) {
				irtList.add(((Cycle)node).destruct(parent, symbolTable));
			} else if(node instanceof Statement) {
				irtList.add(((Statement)node).destruct(parent, symbolTable));
			} else if(node instanceof Asign) {
				irtList.add(((Asign)node).destruct(parent, symbolTable));
			} else if(node instanceof MethodCall) {
				irtList.add(((Asign)node).destruct(parent, symbolTable));
			}
			// irtList.add(node.destruct());
		}
		return irtList;
	}
} 