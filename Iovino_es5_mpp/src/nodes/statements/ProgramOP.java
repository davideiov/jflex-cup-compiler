package nodes.statements;

import java.util.ArrayList;
import tree.Visitor;
import tree.table.SymbolTable;

public class ProgramOP {
    private ArrayList<VarDeclOP> varDeclList;
    private ArrayList<FunOP> funOPList;
    private BodyOP bodyOP;

    private SymbolTable pointerToTable;
    private boolean firstVisit;

    public ProgramOP(ArrayList<VarDeclOP> varDeclList, ArrayList<FunOP> funOPList, BodyOP bodyOP) {
        this.varDeclList = varDeclList;
        this.funOPList = funOPList;
        this.bodyOP = bodyOP;
        firstVisit = true;
    }

    public ArrayList<VarDeclOP> getVarDeclList() {
        return varDeclList;
    }

    public ArrayList<FunOP> getFunOPList() {
        return funOPList;
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

    public boolean isFirstVisit() {
        return firstVisit;
    }

    public void setFirstVisit(boolean firstVisit) {
        this.firstVisit = firstVisit;
    }

    @Override
    public String toString() {
        return "ProgramOP{" +
                "varDeclList=" + varDeclList +
                ", funOPList=" + funOPList +
                ", bodyOP=" + bodyOP +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
