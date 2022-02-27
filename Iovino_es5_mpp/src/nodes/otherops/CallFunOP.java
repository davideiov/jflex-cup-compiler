package nodes.otherops;

import nodes.ExprNode;
import nodes.leafs.IDLeaf;
import tree.Visitor;

import java.util.ArrayList;

public class CallFunOP extends ExprNode {

    private IDLeaf id;
    private ArrayList<ExprNode> list;
    private String returnType;

    public CallFunOP(IDLeaf id, ArrayList<ExprNode> list){
        this.id = id;
        this.list = list;
        super.type = "CALLFUN";
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public IDLeaf getId() {
        return id;
    }

    public ArrayList<ExprNode> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "<" + super.type + ", " + id + ", " + list + ">";
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
