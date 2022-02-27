package nodes.otherops;

import nodes.ExprNode;
import nodes.leafs.IDLeaf;
import tree.Visitor;
import tree.table.SymbolTable;

public class AssignStatOP {

    private IDLeaf id;
    private ExprNode expr;

    private SymbolTable pointerToTable;

    public AssignStatOP(IDLeaf id, ExprNode expr){
        this.id = id;
        this.expr = expr;
    }

    public IDLeaf getId() {
        return id;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public SymbolTable getPointerToTable() {
        return pointerToTable;
    }

    public void setPointerToTable(SymbolTable pointerToTable) {
        this.pointerToTable = pointerToTable;
    }

    @Override
    public String toString() {
        return "AssignStatOP{" +
                "id=" + id +
                ", expr=" + expr +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
