package nodes.statements;

import nodes.ExprNode;
import nodes.otherops.*;
import tree.Visitor;

public class StatOP {

    private String typeStat;
    private Object node;

    public StatOP(IfOP op){
        this.typeStat = "IF";
        this.node = op;
    }

    public StatOP(WhileOP op){
        this.typeStat = "WHILE";
        this.node = op;
    }

    public StatOP(ReadStatOP op){
        this.typeStat = "ReadStat";
        this.node = op;
    }

    public StatOP(WriteStatOP op){
        this.typeStat = "WriteStat";
        this.node = op;
    }

    public StatOP(AssignStatOP op){
        this.typeStat = "AssignStat";
        this.node = op;
    }

    public StatOP(CallFunOP op){
        this.typeStat = "CallFun";
        this.node = op;
    }

    public StatOP(ExprNode op){
        this.typeStat = "ExprStat";
        this.node = op;
    }

    public String getTypeStat() {
        return typeStat;
    }

    public Object getNode() {
        return node;
    }

    @Override
    public String toString() {
        return "StatOP{" +
                "typeStat='" + typeStat + '\'' +
                ", node=" + node +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
