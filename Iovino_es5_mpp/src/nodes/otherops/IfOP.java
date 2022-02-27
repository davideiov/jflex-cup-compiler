package nodes.otherops;

import nodes.ExprNode;
import nodes.statements.BodyOP;
import nodes.statements.StatOP;
import nodes.statements.VarDeclOP;
import tree.Visitor;
import tree.table.SymbolTable;

import java.util.ArrayList;

public class IfOP {

    private ExprNode expr;
    private BodyOP bodyOP;
    private ElseOP elseOP;

    private SymbolTable pointerToTable;
    private boolean firstVisit;

    public IfOP(ExprNode expr, BodyOP bodyOP, ElseOP elseop){
        this.elseOP = elseop;
        this.expr = expr;
        this.bodyOP = bodyOP;
        firstVisit = true;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public BodyOP getBodyOP(){ return bodyOP; }

    public ElseOP getElseOP() {
        return elseOP;
    }

    public SymbolTable getPointerToTable() {
        return pointerToTable;
    }

    public void setPointerToTable(SymbolTable pointerToTable) {
        this.pointerToTable = pointerToTable;
    }

    public boolean isFirstVisit() {
        return firstVisit;
    }

    public void setFirstVisit(boolean firstVisit) {
        this.firstVisit = firstVisit;
    }

    @Override
    public String toString() {
        return "IfOP{" +
                "expr=" + expr +
                ", bodyOP=" + bodyOP +
                ", elseop=" + elseOP +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
