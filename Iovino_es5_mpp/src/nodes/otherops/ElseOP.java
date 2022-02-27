package nodes.otherops;

import nodes.statements.BodyOP;
import nodes.statements.StatOP;
import nodes.statements.VarDeclOP;
import tree.Visitor;
import tree.table.SymbolTable;

import java.util.ArrayList;

public class ElseOP {

    private BodyOP bodyOP;

    private SymbolTable pointerToTable;

    public ElseOP(BodyOP bodyOP){
        this.bodyOP = bodyOP;
    }

    public BodyOP getBodyOP() {
        return bodyOP;
    }

    public SymbolTable getPointerToTable() {
        return pointerToTable;
    }

    public void setPointerToTable(SymbolTable pointerToTable) {
        this.pointerToTable = pointerToTable;
    }

    @Override
    public String toString() {
        return "ElseOP{" +
                "bodyOP=" + bodyOP +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
