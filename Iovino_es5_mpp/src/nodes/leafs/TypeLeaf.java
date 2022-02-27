package nodes.leafs;

import tree.Visitor;

public class TypeLeaf {
    private String type;

    public TypeLeaf(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "<TypeLeaf, " + type + ">";
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
