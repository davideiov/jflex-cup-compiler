package tree.table;

import java.util.ArrayList;

public class FunEntry extends Entry{

    private ArrayList<String> paramsName;
    private ArrayList<String> paramsMode;
    private ArrayList<String> paramsType;
    private String returnType;

    public FunEntry(String name, ArrayList<String> paramsName, ArrayList<String> paramsMode, ArrayList<String> paramsType, String returnType){
        super(name, "fun");
        this.paramsName = paramsName;
        this.paramsType = paramsType;
        this.paramsMode = paramsMode;
        this.returnType = returnType;
    }

    public ArrayList<String> getParamsName() {
        return paramsName;
    }

    public void setParamsName(ArrayList<String> paramsName) {
        this.paramsName = paramsName;
    }

    public ArrayList<String> getParamsMode() {
        return paramsMode;
    }

    public void setParamsMode(ArrayList<String> paramsMode) {
        this.paramsMode = paramsMode;
    }

    public ArrayList<String> getParamsType() {
        return paramsType;
    }

    public void setParamsType(ArrayList<String> paramsType) {
        this.paramsType = paramsType;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return "FunEntry{" +
                "paramsName=" + paramsName +
                ", paramsMode=" + paramsMode +
                ", paramsType=" + paramsType +
                ", returnType='" + returnType + '\'' +
                '}';
    }
}
