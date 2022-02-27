package nodes.leafs;

import nodes.ExprNode;
import tree.Visitor;

public class ConstLeaf extends ExprNode{
    private String constType;
    private String value;

    public ConstLeaf(String constType, String value){
        this.constType = constType;
        this.value = value;
        super.type = constType;
    }

    public String getConstType() {
        return constType;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "{"+ constType +", " + value + "}";
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
