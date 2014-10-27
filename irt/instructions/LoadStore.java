package compiler.irt.instructions;

import compiler.irt.Register;

public class LoadStore extends Instruction {

	protected String type;
	protected Register rs;
	protected Register rd;


	/**
	*	Guarda el valor que debe guardarse para un Store
	*/
	protected String value;

	protected int offset;

	/**
	*	LI
	*/
	public LoadStore(String type, Register r, String value) {
		this.type = type;
		this.rs = r;
		this.value = value;
	}

	/**
	*	SW, lw
	*/
	public LoadStore(String type, Register rs, int offset, Register rd ) {
		this.type 	= type;
		this.rs 	= rs; 
		this.offset	= offset; 
		this.rd 	= rd; 

	}

	public Register getRD() {
		return rs;
	}
	
	@Override
	public String toString() {
		switch (type) {
			case "li":
				return type + " " + rs + " " + value;
			case "sw": case "lw":
				return type + " " + rs + " " + offset + "(" + rd + ")";
		}
		return "";
	}
}
