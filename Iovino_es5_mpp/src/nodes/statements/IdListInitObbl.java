package nodes.statements;

import nodes.ExprNode;
import nodes.leafs.ConstLeaf;
import nodes.leafs.IDLeaf;
import nodes.otherops.AssignStatOP;
import tree.Visitor;

import java.util.ArrayList;
import java.util.HashMap;

public class IdListInitObbl {

    private ArrayList<AssignStatOP> list;

    private ArrayList<IDLeaf> idList;
    private ArrayList<ExprNode> exprs;

    public IdListInitObbl(ArrayList<IDLeaf> idList, ArrayList<ExprNode> exprs){
        this.idList = idList;
        this.exprs = exprs;
        list = new ArrayList<>();
    }

    public IdListInitObbl(IDLeaf id, ConstLeaf value){
        list = new ArrayList<>();
        list.add(new AssignStatOP(id, value));
    }

    public void AddElement(IDLeaf id, ConstLeaf value){
        list.add(new AssignStatOP(id, value));
    }

    public ArrayList<AssignStatOP> getList() {
        return list;
    }

    public ArrayList<ExprNode> getExprs() {
        return exprs;
    }

    public ArrayList<IDLeaf> getIdList() {
        return idList;
    }

    @Override
    public String toString() {
        return "IdListInitObbl{" +
                "list=" + list +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
