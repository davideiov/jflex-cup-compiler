package nodes.ops;

import nodes.ExprNode;
import tree.Visitor;

public class UnaryOP extends ExprNode{

    private String type;
    private ExprNode expr1;
    private String exprType;

    public UnaryOP(String type, ExprNode expr1){
        super.type = "UnaryOP";
        this.type = type;
        this.expr1 = expr1;
    }

    public String getExprType() {
        return exprType;
    }

    public void setExprType(String exprType) {
        this.exprType = exprType;
    }

    public String getType() {
        return type;
    }

    public ExprNode getExpr1() {
        return expr1;
    }

    @Override
    public String toString() {
        return "UnaryOP{" +
                "type='" + type + '\'' +
                ", expr1=" + expr1 +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
