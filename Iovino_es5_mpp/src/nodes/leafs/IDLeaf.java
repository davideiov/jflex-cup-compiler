package nodes.leafs;

import nodes.ExprNode;
import tree.Visitor;

public class IDLeaf extends ExprNode {
    private String id;
    private boolean isOutParam;

    private String typeOf;

    public IDLeaf(String id){
        this.id = id;
        isOutParam = false;
        super.type = "IDLeaf";
    }

    public IDLeaf(String id, boolean bool){
        this.id = id;
        isOutParam = bool;
        super.type = "IDLeaf";
    }

    public String getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(String typeOf) {
        this.typeOf = typeOf;
    }

    public boolean isOutParam() {
        return isOutParam;
    }

    public void setOutParam(boolean outParam) {
        isOutParam = outParam;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString(){
        return "<ID, " + id + ">";
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
