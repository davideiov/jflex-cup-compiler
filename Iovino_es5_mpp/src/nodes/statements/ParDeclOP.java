package nodes.statements;

import nodes.leafs.IDLeaf;
import nodes.leafs.TypeLeaf;
import tree.Visitor;

public class ParDeclOP {

    private boolean isOut;
    private TypeLeaf type;
    private IDLeaf id;

    public ParDeclOP(boolean isOut, TypeLeaf type, IDLeaf id){
        this.isOut = isOut;
        this.type = type;
        this.id = id;
    }

    public boolean isOut() {
        return isOut;
    }

    public TypeLeaf getType() {
        return type;
    }

    public IDLeaf getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ParDeclOP{" +
                "isOut=" + isOut +
                ", type=" + type +
                ", id=" + id +
                '}';
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
