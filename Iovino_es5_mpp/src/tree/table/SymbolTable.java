package tree.table;

import java.util.HashMap;

public class SymbolTable {

    private HashMap<String, Entry> scope;

    public SymbolTable(){
        scope = new HashMap<String, Entry>();
    }

    public HashMap<String, Entry> getScope(){
        return scope;
    }

    public void addID(VarEntry varEntry){
        if(scope.containsKey(varEntry.getName()))
            throw new Error("Variabile " + varEntry.getName() + " già dichiarata precedentemente!");
        else{
            scope.put(varEntry.getName(), varEntry);
        }
    }

    public void addFun(FunEntry funEntry){
        if(scope.containsKey(funEntry.getName())) {
            throw new Error("Funzione " + funEntry.getName() + " già dichiarata precedentemente!");
        }else{
            scope.put(funEntry.getName(), funEntry);
        }
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "scope=" + scope +
                '}';
    }
}
