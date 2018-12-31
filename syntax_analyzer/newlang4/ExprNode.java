package newlang4;

import newlang3.LexicalType;
import newlang3.LexicalUnit;

import java.util.*;
//
//<expr>	::=
//  <expr> <ADD> <expr>
//	| <expr> <SUB> <expr>
//	| <expr> <MUL> <expr>
//	| <expr> <DIV> <expr>
//	| <SUB> <expr>
//	| <LP> <expr> <RP>
//	| <NAME>
//	| <INTVAL>
//	| <DOUBLEVAL>
//	| <LITERAL>
//	| <call_func>

public class ExprNode extends Node {
    List<LexicalType> operators = new ArrayList<>();
    List<Node> operands = new ArrayList<>();
    private Node left;
    private Node right;

    static final Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(
            LexicalType.NAME,
            LexicalType.LP,
            LexicalType.INTVAL,
            LexicalType.DOUBLEVAL,
            LexicalType.LITERAL));

    private ExprNode(Environment env){
        super(env);
        type = NodeType.EXPR;
    }

    public static boolean isMatch(LexicalType type) {
        return first.contains(type);
    }
    public static Node getHandler(LexicalType type, Environment env){
        return new ExprNode(env);
    }

    public void parse() throws Exception{
        while(true){
//      <Operand>
            LexicalUnit lu = env.getInput().get();
            switch (lu.getType()){
                case NAME:
                    env.getInput().unget(lu);
                    Node vn = VariableNode.getHandler(lu.getType(),env);
                    operands.add(vn);
                    vn.parse();

                    break;
                case LP:
                    break;
                case INTVAL:
                case DOUBLEVAL:
                case LITERAL:
                    Node cn = ConstNode.getHandler(lu,env);
                    operands.add(cn);
                    cn.parse();
                    break;
                default:
                    throw new Exception("Expr Node Parse Error: ");
            }

            lu = env.getInput().get();
            if(OPERATORS.contains(lu.getType())){
                operators.add(lu.getType());
            }
            else{
                env.getInput().unget(lu);
                break;
            }
        }

    }

    @Override
    public String toString(){
        String tmp ="";
        if(!operators.isEmpty()){
            tmp += operators;
        }
        tmp += operands.toString();
        return tmp;
    }
}
