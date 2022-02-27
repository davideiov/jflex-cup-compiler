package nodes.statements;

import nodes.leafs.IDLeaf;
import nodes.leafs.TypeLeaf;
import tree.Visitor;
import tree.table.SymbolTable;

import java.util.ArrayList;

public class FunOP {

    private IDLeaf id;
    private ArrayList<ParDeclOP> paramDeclList;
    private TypeLeaf typeLeaf;
    private BodyOP bodyOP;

    private SymbolTable pointerToTable;
    private boolean firstVisit;

    public FunOP(IDLeaf id, ArrayList<ParDeclOP> paramDeclList, TypeLeaf typeLeaf, BodyOP bodyOP){
        this.id = id;
        this.paramDeclList = paramDeclList;
        this.typeLeaf = typeLeaf;
        this.bodyOP = bodyOP;
        firstVisit = true;
    }

    public FunOP(IDLeaf id, ArrayList<ParDeclOP> paramDeclList, BodyOP bodyOP){
        this.id = id;
        this.paramDeclList = paramDeclList;
        this.typeLeaf = null;
        this.bodyOP = bodyOP;
        firstVisit = true;
    }

    public IDLeaf getId() {
        return id;
    }

    public ArrayList<ParDeclOP> getParamDeclList() {
        return paramDeclList;
    }

    public TypeLeaf getTypeLeaf() {
        return typeLeaf;
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
        return "FunOP{" +
                "id=" + id +
                ", paramDeclList=" + paramDeclList +
                ", typeLeaf=" + typeLeaf +
                ", bodyOP=" + bodyOP +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
