package nodes.statements;

import nodes.ExprNode;
import nodes.leafs.IDLeaf;
import tree.Visitor;

import java.util.ArrayList;
import java.util.HashMap;

public class IdListInit {

    private ArrayList<IDLeaf> ids;
    private ArrayList<ExprNode> exprs;

    public IdListInit(IDLeaf id, ExprNode expr){
        ids = new ArrayList<>();
        exprs = new ArrayList<>();

        ids.add(id);
        exprs.add(expr);
    }

    public void AddElement(IDLeaf id, ExprNode expr){
        ids.add(id);
        exprs.add(expr);
    }

    public ArrayList<IDLeaf> getIds() {
        return ids;
    }

    public ArrayList<ExprNode> getExprs() {
        return exprs;
    }

    @Override
    public String toString() {
        return "IdListInit{" +
                "ids=" + ids +
                ", exprs=" + exprs +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
