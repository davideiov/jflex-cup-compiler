package tree;

import nodes.ExprNode;
import nodes.leafs.*;
import nodes.ops.*;
import nodes.otherops.*;
import nodes.statements.*;
import tree.table.Entry;
import tree.table.FunEntry;
import tree.table.StackTables;
import tree.table.VarEntry;

import java.util.ArrayList;

public class SemanticVisitor implements Visitor{

    @Override
    public Object visit(ConstLeaf constLeaf) {
        return constLeaf.getConstType();
    }

    @Override
    public Object visit(IDLeaf id) {
        Entry e = StackTables.lookup(id.getId());
        if(e == null)
            throw new Error("Identificatore: " + id.getId() + " non definito!");

        if(e instanceof VarEntry) {
            id.setTypeOf(((VarEntry) e).getType());
            if(((VarEntry) e).isOut())
                id.setOutParam(true);
            return ((VarEntry) e).getType();
        }else {
            return ((FunEntry) e).getReturnType();
        }
    }

    @Override
    public Object visit(TypeLeaf typeLeaf) {
        return typeLeaf.getType();
    }

    @Override
    public Object visit(BinaryOP binaryOP) {

        ExprNode en1 = binaryOP.getExpr1(), en2 = binaryOP.getExpr2();
        String type1 = "", type2 = "", op = binaryOP.getType();

        if(en1 instanceof BinaryOP){
            type1 = String.valueOf(((BinaryOP) en1).accept(this));
        } else if (en1 instanceof UnaryOP){
            type1 = String.valueOf(((UnaryOP) en1).accept(this));
        } else if (en1 instanceof ConstLeaf){
            type1 = String.valueOf(((ConstLeaf) en1).accept(this));
        } else if (en1 instanceof IDLeaf){
            type1 = String.valueOf(((IDLeaf) en1).accept(this));
        } else if (en1 instanceof CallFunOP){
            type1 = String.valueOf(((CallFunOP) en1).accept(this));
        }

        if(en2 instanceof BinaryOP){
            type2 = String.valueOf(((BinaryOP) en2).accept(this));
        } else if (en2 instanceof UnaryOP){
            type2 = String.valueOf(((UnaryOP) en2).accept(this));
        } else if (en2 instanceof ConstLeaf){
            type2 = String.valueOf(((ConstLeaf) en2).accept(this));
        } else if (en2 instanceof IDLeaf){
            type2 = String.valueOf(((IDLeaf) en2).accept(this));
        } else if (en2 instanceof CallFunOP){
            type2 = String.valueOf(((CallFunOP) en2).accept(this));
        }

        if (op.equals("PlusOP") || op.equals("MinusOP") || op.equals("TimesOP") || op.equals("PowOP")){
            if (type1.equals("int") && type2.equals("int")) {
                binaryOP.setExprType("int");
                return "int";
            }else if (type1.equals("real") && type2.equals("int")) {
                binaryOP.setExprType("real");
                return "real";
            }else if (type1.equals("int") && type2.equals("real")) {
                binaryOP.setExprType("real");
                return "real";
            }else if (type1.equals("real") && type2.equals("real")) {
                binaryOP.setExprType("real");
                return "real";
            }else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        } else if(op.equals("DivOP")){
            if (type1.equals("int") && type2.equals("int")) {
                binaryOP.setExprType("real");
                return "real";
            }else if (type1.equals("real") && type2.equals("int")) {
                binaryOP.setExprType("real");
                return "real";
            }else if (type1.equals("int") && type2.equals("real")) {
                binaryOP.setExprType("real");
                return "real";
            }else if (type1.equals("real") && type2.equals("real")) {
                binaryOP.setExprType("real");
                return "real";
            }else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        } else if (op.equals("DivIntOP")){
            if (type1.equals("int") && type2.equals("int")) {
                binaryOP.setExprType("int");
                return "int";
            }else if (type1.equals("real") && type2.equals("int")) {
                binaryOP.setExprType("int");
                return "int";
            }else if (type1.equals("int") && type2.equals("real")) {
                binaryOP.setExprType("int");
                return "int";
            }else if (type1.equals("real") && type2.equals("real")) {
                binaryOP.setExprType("int");
                return "int";
            }else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        }else if (op.equals("StrConcatOP")){
            if (type1.equals("string")){
                if (type2.equals("int") || type2.equals("real") || type2.equals("bool") || type2.equals("string")) {
                    binaryOP.setExprType("string");
                    return "string";
                }
            } else if (type2.equals("string")) {
                if (type1.equals("int") || type1.equals("real") || type1.equals("bool") || type1.equals("string")) {
                    binaryOP.setExprType("string");
                    return "string";
                }
            }else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        } else if (op.equals("AndOP") || op.equals("OrOP")){
            if (type1.equals("bool") && type2.equals("bool")) {
                binaryOP.setExprType("bool");
                return "bool";
            }else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        } else if (op.equals("LtOP") || op.equals("LeOP") ||
                op.equals("GtOP") || op.equals("GeOP")){
            binaryOP.setExprType("bool");
            if (type1.equals("int") && type2.equals("int"))
                return "bool";
            else if (type1.equals("real") && type2.equals("int"))
                return "bool";
            else if (type1.equals("int") && type2.equals("real"))
                return "bool";
            else if (type1.equals("real") && type2.equals("real"))
                return "bool";
            else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        } else if (op.equals("EqOP") || op.equals("NeOP")) {
            binaryOP.setExprType("bool");
            if (type1.equals("int") && type2.equals("int"))
                return "bool";
            else if (type1.equals("real") && type2.equals("int"))
                return "bool";
            else if (type1.equals("int") && type2.equals("real"))
                return "bool";
            else if (type1.equals("real") && type2.equals("real"))
                return "bool";
            else if (type1.equals("string") && type2.equals("string"))
                return "bool";
            else if (type1.equals("bool") && type2.equals("bool"))
                return "bool";
            else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        }

        return null;
    }

    @Override
    public Object visit(UnaryOP unaryOP) {
        ExprNode en1 = unaryOP.getExpr1();
        String type = "";

        if(en1 instanceof BinaryOP){
            type = String.valueOf(((BinaryOP) en1).accept(this));
        } else if (en1 instanceof UnaryOP){
            type = String.valueOf(((UnaryOP) en1).accept(this));
        } else if (en1 instanceof ConstLeaf){
            type = String.valueOf(((ConstLeaf) en1).accept(this));
        } else if (en1 instanceof IDLeaf){
            type = String.valueOf(((IDLeaf) en1).accept(this));
        } else if (en1 instanceof CallFunOP){
            type = String.valueOf(((CallFunOP) en1).accept(this));
        }

        unaryOP.setExprType(type);

        if (unaryOP.getType().equals("UMinusOP")){
            if (type.equals("int"))
                return "int";
            else if (type.equals("real"))
                return "real";
            else
                throw new Error("Type error: " + unaryOP.getType() + " " + type);
        } else if (unaryOP.getType().equals("NotOP")){
            if (type.equals("bool"))
                return "bool";
            else
                throw new Error("Type error: " + unaryOP.getType() + " " + type);
        } else if (unaryOP.getType().equals("ParOP")){
            return type;
        }
        return null;
    }

    @Override
    public Object visit(AssignStatOP assignStatOP) {
        assignStatOP.setPointerToTable(StackTables.getScope());
        String type1 = String.valueOf(assignStatOP.getId().accept(this)), type2 = "";
        ExprNode en1 = assignStatOP.getExpr();

        if(en1 instanceof BinaryOP){
            type2 = String.valueOf(((BinaryOP) en1).accept(this));
        } else if (en1 instanceof UnaryOP){
            type2 = String.valueOf(((UnaryOP) en1).accept(this));
        } else if (en1 instanceof ConstLeaf){
            type2 = String.valueOf(((ConstLeaf) en1).accept(this));
        } else if (en1 instanceof IDLeaf){
            type2 = String.valueOf(((IDLeaf) en1).accept(this));
        } else if (en1 instanceof CallFunOP){
            type2 = String.valueOf(((CallFunOP) en1).accept(this));
        }

        if (type1.equals(type2))
            return "notype";
        else
            throw new Error("Type error: " + type1 + " = " + type2);
    }

    @Override
    public Object visit(CallFunOP callFunOP) {
        callFunOP.getId().accept(this);

        ArrayList<ExprNode> list = callFunOP.getList();
        if(list != null) {
            for (ExprNode en : list) {

                if (en instanceof BinaryOP) {
                    ((BinaryOP) en).accept(this);
                } else if (en instanceof UnaryOP) {
                    ((UnaryOP) en).accept(this);
                } else if (en instanceof ConstLeaf) {
                    ((ConstLeaf) en).accept(this);
                } else if (en instanceof IDLeaf) {
                    ((IDLeaf) en).accept(this);
                } else if (en instanceof CallFunOP) {
                    ((CallFunOP) en).accept(this);
                }
            }
        }
        FunEntry fun = (FunEntry) StackTables.lookup(callFunOP.getId().getId());
        callFunOP.setReturnType(fun.getReturnType());

        ArrayList<ExprNode> params = callFunOP.getList();

        ArrayList<String> paramsType = fun.getParamsType();
        ArrayList<String> paramsMode = fun.getParamsMode();

        int i = 0;
        if(params != null) {
            for (ExprNode en : params) {
                String parType = "";
                String mode = "false";
                if (en instanceof BinaryOP) {
                    parType = String.valueOf(((BinaryOP) en).accept(this));
                } else if (en instanceof UnaryOP) {
                    parType = String.valueOf(((UnaryOP) en).accept(this));
                } else if (en instanceof ConstLeaf) {
                    parType = String.valueOf(((ConstLeaf) en).accept(this));
                } else if (en instanceof IDLeaf) {
                    parType = String.valueOf(((IDLeaf) en).accept(this));
                    if (((IDLeaf) en).isOutParam())
                        mode = "true";
                    else
                        mode = "false";
                } else if (en instanceof CallFunOP) {
                    parType = String.valueOf(((CallFunOP) en).accept(this));
                }

                if (!(parType.equals(paramsType.get(i)) && mode.equals(paramsMode.get(i))))
                    throw new Error("Type error: metodo [" + fun.getName() + "], parametri non compatibili con la dichiarazione");
                i++;
            }
        }
        return fun.getReturnType();
    }

    @Override
    public Object visit(ElseOP elseOP) {
        return elseOP.getBodyOP().accept(this);
    }

    @Override
    public Object visit(IfOP ifOP) {
        String condType = "";
        ArrayList<String> bodyType = null, elseType = null;
        ExprNode en = ifOP.getExpr();

        if(en instanceof BinaryOP){
            condType = String.valueOf(((BinaryOP) en).accept(this));
        } else if (en instanceof UnaryOP){
            condType = String.valueOf(((UnaryOP) en).accept(this));
        } else if (en instanceof ConstLeaf){
            condType = String.valueOf(((ConstLeaf) en).accept(this));
        } else if (en instanceof IDLeaf){
            condType = String.valueOf(((IDLeaf) en).accept(this));
        } else if (en instanceof CallFunOP){
            condType = String.valueOf(((CallFunOP) en).accept(this));
        }

        if(ifOP.isFirstVisit()){
            ifOP.setFirstVisit(false);

            StackTables.newScope();
            bodyType = (ArrayList<String>) ifOP.getBodyOP().accept(this);
            ifOP.setPointerToTable(StackTables.getScope());
            StackTables.removeScope();

            if(ifOP.getElseOP() != null) {
                StackTables.newScope();
                elseType = (ArrayList<String>) ifOP.getElseOP().accept(this);
                ifOP.getElseOP().setPointerToTable(StackTables.getScope());
                StackTables.removeScope();
            }
        }

        if (ifOP.getElseOP() != null){
            if (condType.equals("bool")) {
                bodyType.addAll(elseType);
                return bodyType;
            }else
                throw new Error("Type error: if " + condType + " then " + bodyType + " else " + elseType);
        } else {
            if (condType.equals("bool")) {
                return bodyType;
            }else
                throw new Error("Type error: if " + condType + " then " + bodyType);
        }
    }

    @Override
    public Object visit(ReadStatOP readStatOP) {
        for(IDLeaf id: readStatOP.getIdList())
            id.accept(this);

        String type = "";
        ExprNode en = readStatOP.getExpr();
        if(en instanceof BinaryOP){
            type = String.valueOf(((BinaryOP) en).accept(this));
        } else if (en instanceof UnaryOP){
            type = String.valueOf(((UnaryOP) en).accept(this));
        } else if (en instanceof ConstLeaf){
            type = String.valueOf(((ConstLeaf) en).accept(this));
        } else if (en instanceof IDLeaf){
            type = String.valueOf(((IDLeaf) en).accept(this));
        } else if (en instanceof CallFunOP){
            type = String.valueOf(((CallFunOP) en).accept(this));
        }

        if (type.equals("string") || type.equals(""))
            return "notype";
        else
            throw new Error("Type error: ReadStatOP " + type);
    }

    @Override
    public Object visit(WhileOP whileOP) {
        String condType = "";
        ArrayList<String> bodyType = null;
        ExprNode en = whileOP.getExpr();

        if(en instanceof BinaryOP){
            condType = String.valueOf(((BinaryOP) en).accept(this));
        } else if (en instanceof UnaryOP){
            condType = String.valueOf(((UnaryOP) en).accept(this));
        } else if (en instanceof ConstLeaf){
            condType = String.valueOf(((ConstLeaf) en).accept(this));
        } else if (en instanceof IDLeaf){
            condType = String.valueOf(((IDLeaf) en).accept(this));
        } else if (en instanceof CallFunOP){
            condType = String.valueOf(((CallFunOP) en).accept(this));
        }

        if(whileOP.isFirstVisit()) {
            StackTables.newScope();
            bodyType = (ArrayList<String>) whileOP.getBodyOP().accept(this);
            whileOP.setPointerToTable(StackTables.getScope());
            StackTables.removeScope();
        }

        if (condType.equals("bool"))
            return bodyType;
        else
            throw new Error("Type error: while condition: " + condType);
    }

    @Override
    public Object visit(WriteStatOP writeStatOP) {

        ExprNode en = writeStatOP.getExpr();
        String type = "";
        if(en instanceof BinaryOP){
            type = String.valueOf(((BinaryOP) en).accept(this));
        } else if (en instanceof UnaryOP){
            type = String.valueOf(((UnaryOP) en).accept(this));
        } else if (en instanceof ConstLeaf){
            type = String.valueOf(((ConstLeaf) en).accept(this));
        } else if (en instanceof IDLeaf){
            type = String.valueOf(((IDLeaf) en).accept(this));
        } else if (en instanceof CallFunOP){
            type = String.valueOf(((CallFunOP) en).accept(this));
        }

        if (type.equals("string"))
            return "notype";
        else
            throw new Error("Type error: WriteStatOP ha un expr {" + en + "} di tipo " + type);
    }

    @Override
    public Object visit(FunOP funOP) {
        //creazione di una FunEntry ed aggiunta alla symbol table
        String name = funOP.getId().getId();
        ArrayList<String> paramsName = new ArrayList<>();
        ArrayList<String> paramsMode = new ArrayList<>();
        ArrayList<String> paramsType = new ArrayList<>();
        String returnType = "void";
        if (funOP.getTypeLeaf() != null)
            returnType = funOP.getTypeLeaf().getType();

        if(funOP.getParamDeclList() != null) {
            for (ParDeclOP p : funOP.getParamDeclList()) {
                paramsName.add(p.getId().getId());
                paramsMode.add(String.valueOf(p.isOut()));
                paramsType.add(p.getType().getType());
                p.getId().setTypeOf(p.getType().getType());
            }
        }
        FunEntry funEntry = new FunEntry(name, paramsName, paramsMode, paramsType, returnType);
        StackTables.addID(funEntry);

        //creazione scope per il corpo della funzione
        if(funOP.isFirstVisit()){
            funOP.setFirstVisit(false);
            StackTables.newScope();
            funOP.setPointerToTable(StackTables.getScope());

            //aggiunta dei parametri della funzione alla symbol table
            for(int i = 0; i < paramsName.size(); i++){
                if (paramsMode.get(i) == "true")
                    StackTables.addID(new VarEntry(paramsName.get(i), paramsType.get(i), true));
                else
                    StackTables.addID(new VarEntry(paramsName.get(i), paramsType.get(i), false));
            }

            ArrayList<String> returnBody = (ArrayList<String>) funOP.getBodyOP().accept(this);

            for (String s: returnBody) {
                if (!returnType.equals(s) && !returnType.equals("void"))
                    throw new Error("Type error: metodo " + funOP.getId().getId() + " ritorna " + returnBody + " ma dovrebbe restituire un " + returnType);
                else if (returnType.equals("void")){
                    if (!returnType.equals(s))
                        throw new Error("Type error: metodo " + funOP.getId().getId() + " ritorna " + returnBody + " ma dovrebbe restituire un " + returnType);
                }
            }
            StackTables.removeScope();
        }
        return "notype";
    }

    @Override
    public Object visit(IdListInit idListInit) {
        return null;
    }

    @Override
    public Object visit(IdListInitObbl idListInitObbl) {
        return null;
    }

    @Override
    public Object visit(BodyOP bodyOP) {
        bodyOP.setPointerToTable(StackTables.getScope());
        if(bodyOP.getVarDeclList() != null)
            for (VarDeclOP v: bodyOP.getVarDeclList())
                v.accept(this);

        ArrayList<String> returnsType = new ArrayList<String>();
        if(bodyOP.getStatList() != null) {
            for (StatOP s : bodyOP.getStatList()) {
                //in maniera tale da non aggiungere il tipo di ritorno di una chiamata a funzione
                if (!(s.getTypeStat().equals("CallFun"))) {
                    Object obj = s.accept(this);
                    if (obj instanceof ArrayList) {
                        returnsType.addAll((ArrayList<String>) obj);
                    } else if (!String.valueOf(obj).equals("notype")) {
                        returnsType.add(String.valueOf(obj));
                    }
                } else
                    s.accept(this);
                /* implementazione funzionanante ma non al 100% quando ci sono più return
                if (s.getTypeStat().equals("ExprStat")) { //statement return
                    returnsType.add(String.valueOf(s.accept(this)));
                } else {
                    s.accept(this);
                }*/
            }
        }
        return returnsType;
    }

    @Override
    public Object visit(ParDeclOP parDeclOP) {
        return null;
    }

    @Override
    public Object visit(ProgramOP programOP) {
        if(programOP.isFirstVisit()){
            programOP.setFirstVisit(false);
            StackTables.newScope();
            programOP.setPointerToTable(StackTables.getScope());

            if (programOP.getVarDeclList() != null)
                for(VarDeclOP v: programOP.getVarDeclList())
                    v.accept(this);

            if (programOP.getFunOPList() != null)
                for(FunOP f: programOP.getFunOPList())
                    f.accept(this);

            StackTables.newScope();

            ArrayList<String> returnsType = (ArrayList<String>) programOP.getBodyOP().accept(this);

            if (returnsType.size() != 0){
                for(String s: returnsType){
                    if(!s.equals("void"))
                        throw new Error("Il main non può ritornare un valore!");
                }
            }

            StackTables.removeScope();
            StackTables.removeScope();
        }

        return "notype";
    }

    @Override
    public Object visit(StatOP statOP) {

        String typeStat = statOP.getTypeStat();
        if (typeStat.equals("IF")){
            return ((IfOP) statOP.getNode()).accept(this);
        } else if (typeStat.equals("WHILE")){
            return ((WhileOP) statOP.getNode()).accept(this);
        } else if (typeStat.equals("ReadStat")){
            return ((ReadStatOP) statOP.getNode()).accept(this);
        } else if (typeStat.equals("WriteStat")){
            return ((WriteStatOP) statOP.getNode()).accept(this);
        } else if (typeStat.equals("AssignStat")){
            return ((AssignStatOP) statOP.getNode()).accept(this);
        } else if (typeStat.equals("CallFun")){
            return ((CallFunOP) statOP.getNode()).accept(this);
        } else if (typeStat.equals("ExprStat")) {//return statement
            ExprNode en = (ExprNode) statOP.getNode();
            if(en instanceof BinaryOP){
                return ((BinaryOP) en).accept(this);
            } else if (en instanceof UnaryOP){
                return ((UnaryOP) en).accept(this);
            } else if (en instanceof ConstLeaf){
                return ((ConstLeaf) en).accept(this);
            } else if (en instanceof IDLeaf){
                return ((IDLeaf) en).accept(this);
            } else if (en instanceof CallFunOP){
                return ((CallFunOP) en).accept(this);
            }
        }

        return null;
    }

    @Override
    public Object visit(VarDeclOP varDeclOP) {
        IdListInit idListInit = varDeclOP.getIdListInit();
        IdListInitObbl idListInitObbl = varDeclOP.getIdInitObblList();

        //scoping
        if(idListInit != null){
            ArrayList<IDLeaf> ids = idListInit.getIds();
            for (IDLeaf id: ids) {
                StackTables.addID(new VarEntry(id.getId(), varDeclOP.getType().getType(), false));
            }
        } else if (idListInitObbl != null){
            ArrayList<AssignStatOP> list = idListInitObbl.getList();

            ArrayList<IDLeaf> ids = idListInitObbl.getIdList();
            ArrayList<ExprNode> exprs = idListInitObbl.getExprs();

            if(list != null){
                for (AssignStatOP assign: list){
                    assign.getId().setTypeOf(assign.getExpr().type);
                    StackTables.addID(new VarEntry(assign.getId().getId(), assign.getExpr().type, false));
                }
            }

        }

        //type checking
        String type = "";
        if(idListInit != null){
            ArrayList<IDLeaf> ids = idListInit.getIds();
            ArrayList<ExprNode> exprs = idListInit.getExprs();
            for (int i = 0; i < ids.size(); i++) {
                type = String.valueOf(ids.get(i).accept(this));
                if (!varDeclOP.getType().getType().equals(type))
                    throw new Error("Type error: " + varDeclOP.getType().getType() + " " + ids.get(i).getId() + " = " + type);

                ExprNode en = exprs.get(i);
                if (en != null){
                    String exprType = "";
                    if(en instanceof BinaryOP){
                        exprType = String.valueOf(((BinaryOP) en).accept(this));
                    } else if (en instanceof UnaryOP){
                        exprType = String.valueOf(((UnaryOP) en).accept(this));
                    } else if (en instanceof ConstLeaf){
                        exprType = String.valueOf(((ConstLeaf) en).accept(this));
                    } else if (en instanceof IDLeaf){
                        exprType = String.valueOf(((IDLeaf) en).accept(this));
                    } else if (en instanceof CallFunOP){
                        exprType = String.valueOf(((CallFunOP) en).accept(this));
                    }

                    if (!exprType.equals(varDeclOP.getType().getType()))
                        throw new Error("Type error: variabile " + ids.get(i).getId() + " è di tipo " + varDeclOP.getType().getType() + " ma dovrebbe essere di tipo " + exprType);
                }
            }
        } else if (idListInitObbl != null){
            ArrayList<AssignStatOP> list = idListInitObbl.getList();
            ArrayList<IDLeaf> ids = idListInitObbl.getIdList();
            ArrayList<ExprNode> exprs = idListInitObbl.getExprs();

            if(list != null) {
                for (AssignStatOP assign : list) {
                    type = String.valueOf(assign.accept(this));
                    if (!type.equals("notype"))
                        throw new Error("Type error: " + assign.getId().getId() + " = " + type);
                }
            }
            if(ids != null && exprs != null){
                for(int i = 0; i < ids.size(); i++){

                    String exprType = "";
                    ExprNode en = exprs.get(i);

                    if(en instanceof BinaryOP){
                        exprType = String.valueOf(((BinaryOP) en).accept(this));
                    } else if (en instanceof UnaryOP){
                        exprType = String.valueOf(((UnaryOP) en).accept(this));
                    } else if (en instanceof ConstLeaf){
                        exprType = String.valueOf(((ConstLeaf) en).accept(this));
                    } else if (en instanceof IDLeaf){
                        exprType = String.valueOf(((IDLeaf) en).accept(this));
                    } else if (en instanceof CallFunOP){
                        exprType = String.valueOf(((CallFunOP) en).accept(this));
                    }

                    //aggiunta alla table dopo aver scoperto il tipo
                    ids.get(i).setTypeOf(exprType);
                    StackTables.addID(new VarEntry(ids.get(i).getId(), exprType, false));
                }
            }
        }
        return "notype";
    }
}
