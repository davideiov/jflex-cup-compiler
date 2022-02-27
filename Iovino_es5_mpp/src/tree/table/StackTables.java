package tree.table;

import java.util.HashMap;
import java.util.Stack;

public class StackTables {

    private static final Stack<SymbolTable> STACK = new Stack<>();

    public static void newScope(){
        STACK.push(new SymbolTable());
    }

    public static SymbolTable getScope(){
        return STACK.lastElement();
    }

    public static void removeScope(){
        STACK.pop();
    }

    public static void addID(Entry entry){
        if (entry instanceof VarEntry){
            STACK.lastElement().addID((VarEntry) entry);
        } else if (entry instanceof FunEntry){
            STACK.lastElement().addFun((FunEntry) entry);
        }
    }

    public static Entry lookup(String name){
        for (int i = STACK.size()-1 ; i >= 0; i--) {
            HashMap<String, Entry> currentTable = STACK.get(i).getScope();
            if (currentTable != null) {
                if (currentTable.containsKey(name)) {
                    return currentTable.get(name);
                }
            }
        }
        return null;
    }
}
