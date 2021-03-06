package newlang4;

import newlang3.LexicalAnalyzer;
import newlang5.Function;
import newlang5.PrintFunction;

import java.util.HashMap;
import java.util.Map;

public class Environment {
	Map library;		//Basicから呼び出せる関数の表
	Map<String, Node> var_map;
	LexicalAnalyzer input;

	public Environment(LexicalAnalyzer my_input) {
		input = my_input;
		library = new HashMap();
		library.put("PRINT", new PrintFunction() );		//PRINTはあらかじめ用意
		var_map = new HashMap<>();
	}

	public LexicalAnalyzer getInput() {
		return input;
	}
	//引数ー関数名
	//NULLだったら関数未定義
	public Function getFunction(String fname) {
		return (Function) library.get(fname);
	}

	//変数そのものはvar_mapに格納
	//変数の記録はここで行われる．
	public VariableNode getVariable(String vname) {
		VariableNode v;
		v = (VariableNode) var_map.get(vname);
		if (v == null) {
			v = new VariableNode(vname);
			var_map.put(vname, v);
		}
		return v;
	}
}
