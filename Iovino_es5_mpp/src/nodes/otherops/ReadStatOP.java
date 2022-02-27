package nodes.otherops;

import nodes.ExprNode;
import nodes.leafs.IDLeaf;
import tree.Visitor;

import java.util.ArrayList;

public class ReadStatOP {

    private ArrayList<IDLeaf> idList;
    private ExprNode expr;

    public ReadStatOP(ArrayList<IDLeaf> idList, ExprNode expr){
        this.idList = idList;
        if(expr != null)
            this.expr = expr;
        else
            this.expr = null;
    }

    public ArrayList<IDLeaf> getIdList() {
        return idList;
    }

    public ExprNode getExpr() {
        return expr;
    }

    @Override
    public String toString() {
        return "ReadStatOP{" +
                "idList=" + idList +
                ", expr=" + expr +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
