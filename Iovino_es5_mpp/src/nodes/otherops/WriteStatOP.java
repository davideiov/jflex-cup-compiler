package nodes.otherops;

import nodes.ExprNode;
import tree.Visitor;

public class WriteStatOP {

    private String type;
    private ExprNode expr;

    public WriteStatOP(String type, ExprNode expr){
        this.type = type;
        this.expr = expr;
    }

    public String getType() {
        return type;
    }

    public ExprNode getExpr() {
        return expr;
    }

    @Override
    public String toString() {
        return "WriteStatOP{" +
                "type='" + type + '\'' +
                ", expr=" + expr +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
