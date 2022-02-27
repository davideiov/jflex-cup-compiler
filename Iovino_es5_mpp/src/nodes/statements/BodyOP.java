package nodes.statements;

import tree.Visitor;
import tree.table.SymbolTable;

import java.util.ArrayList;

public class BodyOP {

    private ArrayList<VarDeclOP> varDeclList;
    private ArrayList<StatOP> statList;

    private SymbolTable pointerToTable;
    private boolean firstVisit;

    public BodyOP(ArrayList<VarDeclOP> varDeclList, ArrayList<StatOP> statList){
        this.varDeclList = varDeclList;
        this.statList = statList;
        firstVisit = true;
    }

    public ArrayList<VarDeclOP> getVarDeclList() {
        return varDeclList;
    }

    public ArrayList<StatOP> getStatList() {
        return statList;
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
        return "BodyOP{" +
                "varDeclList=" + varDeclList +
                ", statList=" + statList +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
