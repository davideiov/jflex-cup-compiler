package nodes.statements;

import nodes.ExprNode;
import nodes.leafs.IDLeaf;
import nodes.leafs.TypeLeaf;
import tree.Visitor;

import java.util.ArrayList;

public class VarDeclOP {
    private TypeLeaf type;
    private IdListInit idListInit;

    private IdListInitObbl idInitObblList;

    public VarDeclOP(TypeLeaf type, IdListInit idListInit){
        this.type = type;
        this.idListInit = idListInit;
    }

    public VarDeclOP(IdListInitObbl idInitObblList){
        this.idInitObblList = idInitObblList;
    }

    public TypeLeaf getType() {
        return type;
    }

    public IdListInit getIdListInit() {
        return idListInit;
    }

    public IdListInitObbl getIdInitObblList() {
        return idInitObblList;
    }

    @Override
    public String toString() {
        return "VarDeclOP{" +
                "type=" + type +
                ", idListInit=" + idListInit +
                ", idInitObblList=" + idInitObblList +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
