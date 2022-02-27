package tree;

import nodes.ExprNode;
import nodes.leafs.ConstLeaf;
import nodes.leafs.IDLeaf;
import nodes.leafs.TypeLeaf;
import nodes.ops.BinaryOP;
import nodes.ops.UnaryOP;
import nodes.otherops.*;
import nodes.statements.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CodeGenerator implements Visitor{

    private static File FILE;
    private static FileWriter WRITER;
    private static int numTab;

    public CodeGenerator(String pathfile) throws IOException {
        FILE = new File(pathfile);
        if(!FILE.exists())
            FILE.createNewFile();
        WRITER = new FileWriter(FILE);

        //inserimento import necessari per c
        WRITER.write("#include <stdio.h>\n");
        WRITER.write("#include <stdlib.h>\n");
        WRITER.write("#include <math.h>\n");
        WRITER.write("#include <stdbool.h>\n");
        WRITER.write("#include <string.h>\n\n");

        WRITER.write("//procedure di supporto\n");
        WRITER.write("char * itos(int n) {\n");
        WRITER.write("\tchar * dest = malloc(sizeof(char)*16);\n");
        WRITER.write("\tsprintf(dest, \"%d\", n);\n");
        WRITER.write("\treturn dest;\n");
        WRITER.write("}\n\n");

        WRITER.write("char * dtos(double n) {\n");
        WRITER.write("\tchar * dest = malloc(sizeof(char)*16);\n");
        WRITER.write("\tsprintf(dest, \"%lf\", n);\n");
        WRITER.write("\treturn dest;\n");
        WRITER.write("}\n\n");

        WRITER.write("char * btos(bool b) {\n");
        WRITER.write("\tchar * dest = malloc(sizeof(char)*2);\n");
        WRITER.write("\tif(b == true)\n");
        WRITER.write("\t\tdest = \"true\";\n");
        WRITER.write("\telse\n");
        WRITER.write("\t\tdest = \"false\";\n");
        WRITER.write("\treturn dest;\n");
        WRITER.write("}\n\n");

        WRITER.write("char * strconcat(char * str1, char * str2) {\n");
        WRITER.write("\tchar * dest = malloc(sizeof(char)*256);\n");
        WRITER.write("\tstrcat(dest, str1);\n");
        WRITER.write("\tstrcat(dest, str2);\n");
        WRITER.write("\treturn dest;\n");
        WRITER.write("}\n\n");

        numTab = 0;

    }

    @Override
    public Object visit(ConstLeaf constLeaf) {
        try{
            if(constLeaf.getConstType().equals("string"))
                WRITER.write("\"" + constLeaf.getValue() + "\"");
            else
                WRITER.write(constLeaf.getValue());
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(IDLeaf id) {
        try{
            //System.out.println(id.getTypeOf() + " " + id.getId() + ": " + id.isOutParam());
            if(id.isOutParam() && !(id.getTypeOf().equals("string")))
                WRITER.write("*");
            WRITER.write(id.getId());
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(TypeLeaf typeLeaf) {
        try {
            String type = "";
            if (typeLeaf.getType().equals("int"))
                type = "int";
            else if (typeLeaf.getType().equals("real"))
                type = "double";
            else if (typeLeaf.getType().equals("string"))
                type = "char *";
            else if (typeLeaf.getType().equals("bool"))
                type = "bool";
            else
                type = "void";
            WRITER.write(type);
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(BinaryOP binaryOP) {
        try {
            ExprNode en1 = binaryOP.getExpr1(), en2 = binaryOP.getExpr2();
            String op = binaryOP.getType();

            if (op.equals("StrConcatOP")){

                WRITER.write("strconcat(");
                ExprToString(en1);
                WRITER.write(", ");
                ExprToString(en2);
                WRITER.write(")");
                return null;
            }

            if (op.equals("PowOP")){
                WRITER.write("pow(");
                if(en1 instanceof BinaryOP){
                    ((BinaryOP) en1).accept(this);
                } else if (en1 instanceof UnaryOP){
                    ((UnaryOP) en1).accept(this);
                } else if (en1 instanceof ConstLeaf){
                    ((ConstLeaf) en1).accept(this);
                } else if (en1 instanceof IDLeaf){
                    ((IDLeaf) en1).accept(this);
                } else if (en1 instanceof CallFunOP){
                    ((CallFunOP) en1).accept(this);
                }
                WRITER.write(", ");
                if(en2 instanceof BinaryOP){
                    ((BinaryOP) en2).accept(this);
                } else if (en2 instanceof UnaryOP){
                    ((UnaryOP) en2).accept(this);
                } else if (en2 instanceof ConstLeaf){
                    ((ConstLeaf) en2).accept(this);
                } else if (en2 instanceof IDLeaf){
                    ((IDLeaf) en2).accept(this);
                } else if (en2 instanceof CallFunOP){
                    ((CallFunOP) en2).accept(this);
                }
                WRITER.write(")");
                return null;
            }

            if(en1 instanceof IDLeaf && ((IDLeaf) en1).getTypeOf().equals("string") ||
                    en1 instanceof ConstLeaf && ((ConstLeaf) en1).getConstType().equals("string")){

                WRITER.write("strcmp(");
                if(en1 instanceof BinaryOP){
                    ((BinaryOP) en1).accept(this);
                } else if (en1 instanceof UnaryOP){
                    ((UnaryOP) en1).accept(this);
                } else if (en1 instanceof ConstLeaf){
                    ((ConstLeaf) en1).accept(this);
                } else if (en1 instanceof IDLeaf){
                    ((IDLeaf) en1).accept(this);
                } else if (en1 instanceof CallFunOP){
                    ((CallFunOP) en1).accept(this);
                }
                WRITER.write(", ");
                if(en2 instanceof BinaryOP){
                    ((BinaryOP) en2).accept(this);
                } else if (en2 instanceof UnaryOP){
                    ((UnaryOP) en2).accept(this);
                } else if (en2 instanceof ConstLeaf){
                    ((ConstLeaf) en2).accept(this);
                } else if (en2 instanceof IDLeaf){
                    ((IDLeaf) en2).accept(this);
                } else if (en2 instanceof CallFunOP){
                    ((CallFunOP) en2).accept(this);
                }
                WRITER.write(")");

                if (op.equals("EqOP"))
                    WRITER.write(" == ");
                else if (op.equals("NqOP"))
                    WRITER.write(" != ");
                WRITER.write(" 0");

                return null;
            }

            if(en1 instanceof BinaryOP){
                ((BinaryOP) en1).accept(this);
            } else if (en1 instanceof UnaryOP){
                ((UnaryOP) en1).accept(this);
            } else if (en1 instanceof ConstLeaf){
                ((ConstLeaf) en1).accept(this);
            } else if (en1 instanceof IDLeaf){
                ((IDLeaf) en1).accept(this);
            } else if (en1 instanceof CallFunOP){
                ((CallFunOP) en1).accept(this);
            }

            if (op.equals("PlusOP")){
                WRITER.write(" + ");
            } else if (op.equals("MinusOP")){
                WRITER.write(" - ");
            } else if(op.equals("TimesOP")){
                WRITER.write(" * ");
            } else if(op.equals("DivOP")){
                WRITER.write(" / ");
            } else if (op.equals("DivIntOP")){
                WRITER.write(" / ");
            } else if (op.equals("AndOP")){
                WRITER.write(" && ");
            } else if (op.equals("OrOP")){
                WRITER.write(" || ");
            } else if (op.equals("LtOP")){
                WRITER.write(" < ");
            } else if (op.equals("LeOP")){
                WRITER.write(" <= ");
            } else if (op.equals("GtOP")){
                WRITER.write(" > ");
            } else if (op.equals("GeOP")){
                WRITER.write(" >= ");
            } else if (op.equals("EqOP")){
                WRITER.write(" == ");
            } else if (op.equals("NeOP")) {
                WRITER.write(" != ");
            }

            if(en2 instanceof BinaryOP){
                ((BinaryOP) en2).accept(this);
            } else if (en2 instanceof UnaryOP){
                ((UnaryOP) en2).accept(this);
            } else if (en2 instanceof ConstLeaf){
                ((ConstLeaf) en2).accept(this);
            } else if (en2 instanceof IDLeaf){
                ((IDLeaf) en2).accept(this);
            } else if (en2 instanceof CallFunOP){
                ((CallFunOP) en2).accept(this);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(UnaryOP unaryOP) {
        try {
            String type = unaryOP.getType();
            ExprNode en1 = unaryOP.getExpr1();
            if (type.equals("UMinusOP")){
                WRITER.write(" -");
                if(en1 instanceof BinaryOP){
                    ((BinaryOP) en1).accept(this);
                } else if (en1 instanceof UnaryOP){
                    ((UnaryOP) en1).accept(this);
                } else if (en1 instanceof ConstLeaf){
                    ((ConstLeaf) en1).accept(this);
                } else if (en1 instanceof IDLeaf){
                    ((IDLeaf) en1).accept(this);
                } else if (en1 instanceof CallFunOP){
                    ((CallFunOP) en1).accept(this);
                }
            } else if (type.equals("NotOP")){
                WRITER.write(" !");
                if(en1 instanceof BinaryOP){
                    ((BinaryOP) en1).accept(this);
                } else if (en1 instanceof UnaryOP){
                    ((UnaryOP) en1).accept(this);
                } else if (en1 instanceof ConstLeaf){
                    ((ConstLeaf) en1).accept(this);
                } else if (en1 instanceof IDLeaf){
                    ((IDLeaf) en1).accept(this);
                } else if (en1 instanceof CallFunOP){
                    ((CallFunOP) en1).accept(this);
                }
            } else if (type.equals("ParOP")){
                WRITER.write("(");

                if(en1 instanceof BinaryOP){
                    ((BinaryOP) en1).accept(this);
                } else if (en1 instanceof UnaryOP){
                    ((UnaryOP) en1).accept(this);
                } else if (en1 instanceof ConstLeaf){
                    ((ConstLeaf) en1).accept(this);
                } else if (en1 instanceof IDLeaf){
                    ((IDLeaf) en1).accept(this);
                } else if (en1 instanceof CallFunOP){
                    ((CallFunOP) en1).accept(this);
                }

                WRITER.write(")");
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(AssignStatOP assignStatOP) {
        try {
            WRITER.write("\t".repeat(numTab));

            if(assignStatOP.getId().getTypeOf().equals("string")){
                WRITER.write("strcpy(");
                assignStatOP.getId().accept(this);
                WRITER.write(", ");
                ExprNode en1 = assignStatOP.getExpr();
                if(en1 instanceof BinaryOP){
                    ((BinaryOP) en1).accept(this);
                } else if (en1 instanceof UnaryOP){
                    ((UnaryOP) en1).accept(this);
                } else if (en1 instanceof ConstLeaf){
                    ((ConstLeaf) en1).accept(this);
                } else if (en1 instanceof IDLeaf){
                    ((IDLeaf) en1).accept(this);
                } else if (en1 instanceof CallFunOP){
                    ((CallFunOP) en1).accept(this);
                }
                WRITER.write(")");
                return null;
            }

            assignStatOP.getId().accept(this);

            WRITER.write(" = ");

            ExprNode en1 = assignStatOP.getExpr();
            if(en1 instanceof BinaryOP){
                ((BinaryOP) en1).accept(this);
            } else if (en1 instanceof UnaryOP){
                ((UnaryOP) en1).accept(this);
            } else if (en1 instanceof ConstLeaf){
                ((ConstLeaf) en1).accept(this);
            } else if (en1 instanceof IDLeaf){
                ((IDLeaf) en1).accept(this);
            } else if (en1 instanceof CallFunOP){
                ((CallFunOP) en1).accept(this);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(CallFunOP callFunOP) {
        try {
            WRITER.write("\t".repeat(numTab));
            WRITER.write(callFunOP.getId().getId() + "(");

            ArrayList<ExprNode> params = callFunOP.getList();
            if(params != null) {
                int i = 0;
                for (; i < params.size()-1; i++) {
                    ExprNode en1 = params.get(i);
                    if (en1 instanceof BinaryOP) {
                        ((BinaryOP) en1).accept(this);
                    } else if (en1 instanceof UnaryOP) {
                        ((UnaryOP) en1).accept(this);
                    } else if (en1 instanceof ConstLeaf) {
                        ((ConstLeaf) en1).accept(this);
                    } else if (en1 instanceof IDLeaf) {
                        if (((IDLeaf) en1).isOutParam() && !((IDLeaf) en1).getTypeOf().equals("string"))
                            WRITER.write("&");
                        WRITER.write(((IDLeaf) en1).getId());
                    } else if (en1 instanceof CallFunOP) {
                        ((CallFunOP) en1).accept(this);
                    }
                    WRITER.write(", ");
                }
                ExprNode en1 = params.get(i);
                if (en1 instanceof BinaryOP) {
                    ((BinaryOP) en1).accept(this);
                } else if (en1 instanceof UnaryOP) {
                    ((UnaryOP) en1).accept(this);
                } else if (en1 instanceof ConstLeaf) {
                    ((ConstLeaf) en1).accept(this);
                } else if (en1 instanceof IDLeaf) {
                    if (((IDLeaf) en1).isOutParam() && !((IDLeaf) en1).getTypeOf().equals("string"))
                        WRITER.write("&");
                    WRITER.write(((IDLeaf) en1).getId());
                } else if (en1 instanceof CallFunOP) {
                    ((CallFunOP) en1).accept(this);
                }
            }
            WRITER.write(")");

        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(ElseOP elseOP) {
        return null;
    }

    @Override
    public Object visit(IfOP ifOP) {
        try {
            WRITER.write("\t".repeat(numTab));
            WRITER.write("if(");

            ExprNode en = ifOP.getExpr();
            if(en instanceof BinaryOP){
                ((BinaryOP) en).accept(this);
            } else if (en instanceof UnaryOP){
                ((UnaryOP) en).accept(this);
            } else if (en instanceof ConstLeaf){
                ((ConstLeaf) en).accept(this);
            } else if (en instanceof IDLeaf){
                ((IDLeaf) en).accept(this);
            } else if (en instanceof CallFunOP){
                ((CallFunOP) en).accept(this);
            }
            WRITER.write("){\n");
            numTab++;
            ifOP.getBodyOP().accept(this);
            numTab--;
            WRITER.write("\t".repeat(numTab));
            WRITER.write("}\n");

            if(ifOP.getElseOP() != null){
                WRITER.write("\t".repeat(numTab));
                WRITER.write("else {\n");
                numTab++;
                ifOP.getElseOP().getBodyOP().accept(this);
                numTab--;
                WRITER.write("\t".repeat(numTab));
                WRITER.write("}");
            }

        } catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(ReadStatOP readStatOP) {
        try{
            ExprNode en = readStatOP.getExpr();
            if(en != null) {
                WRITER.write("\t".repeat(numTab));
                WRITER.write("printf(");

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

                WRITER.write(");\n");
            }
            ArrayList<IDLeaf> ids = readStatOP.getIdList();
            for (IDLeaf id: ids){
                if(id.getTypeOf().equals("int") || id.getTypeOf().equals("bool")) {
                    WRITER.write("\t".repeat(numTab));
                    WRITER.write("scanf(\" %d\", &");
                    id.accept(this);
                } else if (id.getTypeOf().equals("real")) {
                    WRITER.write("\t".repeat(numTab));
                    WRITER.write("scanf(\" %lf\", &");
                    id.accept(this);
                } else if (id.getTypeOf().equals("string")) {
                    WRITER.write("\t".repeat(numTab));
                    WRITER.write("scanf(\" %s\", ");
                    id.accept(this);
                }
                WRITER.write(");\n");
                WRITER.write("\t".repeat(numTab));
                WRITER.write("fflush(stdin);\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(WhileOP whileOP) {

        try{
            WRITER.write("\t".repeat(numTab));
            WRITER.write("while(");

            ExprNode en = whileOP.getExpr();
            if(en instanceof BinaryOP){
                ((BinaryOP) en).accept(this);
            } else if (en instanceof UnaryOP){
                ((UnaryOP) en).accept(this);
            } else if (en instanceof ConstLeaf){
                ((ConstLeaf) en).accept(this);
            } else if (en instanceof IDLeaf){
                ((IDLeaf) en).accept(this);
            } else if (en instanceof CallFunOP){
                ((CallFunOP) en).accept(this);
            }

            WRITER.write("){\n");
            numTab++;
            whileOP.getBodyOP().accept(this);
            numTab--;
            WRITER.write("\t".repeat(numTab));
            WRITER.write("}\n");
        } catch (IOException e){
          e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(WriteStatOP writeStatOP) {
        try{
            WRITER.write("\t".repeat(numTab));
            WRITER.write("printf(\"%s\", ");
            ExprNode en = writeStatOP.getExpr();
            if(en instanceof BinaryOP){
                ((BinaryOP) en).accept(this);
            } else if (en instanceof UnaryOP){
                ((UnaryOP) en).accept(this);
            } else if (en instanceof ConstLeaf){
                ((ConstLeaf) en).accept(this);
            } else if (en instanceof IDLeaf){
                ((IDLeaf) en).accept(this);
            } else if (en instanceof CallFunOP){
                ((CallFunOP) en).accept(this);
            }

            //per evitare ; ridondanti
            if (writeStatOP.getType().equals("WriteLn")){
                WRITER.write(");\n");
                WRITER.write("\t".repeat(numTab));
                WRITER.write("printf(\"\\n\")");
            } else if (writeStatOP.getType().equals("WriteB")){
                WRITER.write(");\n");
                WRITER.write("\t".repeat(numTab));
                WRITER.write("printf(\" \")");
            } else if (writeStatOP.getType().equals("WriteT")){
                WRITER.write(");\n");
                WRITER.write("\t".repeat(numTab));
                WRITER.write("printf(\"\\t\")");
            } else {
                WRITER.write(")");
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(FunOP funOP) {
        try {
            if (funOP.getTypeLeaf() != null) {
                if (funOP.getTypeLeaf().getType().equals("real"))
                    WRITER.write("double ");
                else if (funOP.getTypeLeaf().getType().equals("int"))
                    WRITER.write("int ");
                else if (funOP.getTypeLeaf().getType().equals("string"))
                    WRITER.write("char * ");
                else if (funOP.getTypeLeaf().getType().equals("bool"))
                    WRITER.write("bool ");
            } else
                WRITER.write("void ");

            WRITER.write(funOP.getId().getId() + "(");

            ArrayList<ParDeclOP> params = funOP.getParamDeclList();
            if(params != null) {
                int i = 0;
                for (; i < params.size() - 1; i++) {
                    params.get(i).accept(this);
                    WRITER.write(", ");
                }
                params.get(i).accept(this);
            }
            WRITER.write("){\n");
            numTab++;
            funOP.getBodyOP().accept(this);
            numTab--;
            WRITER.write("}\n\n");

        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(IdListInit idListInit) {
        try{
            ArrayList<IDLeaf> ids = idListInit.getIds();
            ArrayList<ExprNode> exprs = idListInit.getExprs();
            int i = 0;
            for (; i < ids.size() - 1; i++){
                ids.get(i).accept(this);
                ExprNode en = exprs.get(i);

                if(ids.get(i).getTypeOf().equals("string")) {
                    WRITER.write(" = malloc (sizeof(char) * 128);\n");

                    if(en != null) {
                        WRITER.write("\t".repeat(numTab));
                        WRITER.write("strcpy(");
                        ids.get(i).accept(this);
                        WRITER.write(", ");

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

                        WRITER.write(");\n");
                    }
                    WRITER.write("\t".repeat(numTab));
                    WRITER.write("char * ");
                } else {
                    if (en != null) {
                        WRITER.write(" = ");
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
                    WRITER.write(", ");
                }

            }
            ids.get(i).accept(this);
            ExprNode en = exprs.get(i);
            if(ids.get(i).getTypeOf().equals("string")) {
                WRITER.write(" = malloc (sizeof(char) * 128);\n");

                if(en != null) {
                    WRITER.write("\t".repeat(numTab));
                    WRITER.write("strcpy(");
                    ids.get(i).accept(this);
                    WRITER.write(", ");

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

                    WRITER.write(");\n");
                }
            } else {
                if (en != null) {
                    WRITER.write(" = ");
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
                WRITER.write(";\n");
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(IdListInitObbl idListInitObbl) {
        try{
            if (idListInitObbl.getList() != null){
                for(AssignStatOP a: idListInitObbl.getList()){
                    WRITER.write("\t".repeat(numTab));
                    if(a.getId().getTypeOf().equals("string")){
                        WRITER.write("char * ");
                        a.getId().accept(this);
                        WRITER.write(" = malloc (sizeof(char) * 128);\n");
                    } else if (a.getId().getTypeOf().equals("real")){
                        WRITER.write("double ");
                    } else if (a.getId().getTypeOf().equals("int")){
                        WRITER.write("int ");
                    } else if (a.getId().getTypeOf().equals("bool")){
                        WRITER.write("bool ");
                    }

                    a.accept(this);
                    WRITER.write(";\n");
                }
            }

            ArrayList<IDLeaf> ids = idListInitObbl.getIdList();
            ArrayList<ExprNode> exprs = idListInitObbl.getExprs();

            if(ids != null && exprs != null){
                for(int i = 0; i < ids.size(); i++) {
                    WRITER.write("\t".repeat(numTab));
                    if (ids.get(i).getTypeOf().equals("string")) {
                        WRITER.write("char * ");
                        ids.get(i).accept(this);
                        WRITER.write(" = malloc (sizeof(char) * 128);\n");
                        WRITER.write("\t".repeat(numTab));
                    } else if (ids.get(i).getTypeOf().equals("real")) {
                        WRITER.write("double ");
                    } else if (ids.get(i).getTypeOf().equals("int")) {
                        WRITER.write("int ");
                    } else if (ids.get(i).getTypeOf().equals("bool")) {
                        WRITER.write("bool ");
                    }

                    ids.get(i).accept(this);

                    ExprNode en = exprs.get(i);
                    WRITER.write(" = ");
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
                    WRITER.write(";\n");
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(BodyOP bodyOP) {
        try{
            ArrayList<VarDeclOP> varDeclList = bodyOP.getVarDeclList();
            if(varDeclList != null){
                for(VarDeclOP v: varDeclList){
                    v.accept(this);
                }
            }
            WRITER.write("\n");
            ArrayList<StatOP> statList = bodyOP.getStatList();
            if(statList != null){
                for(StatOP s: statList){
                    s.accept(this);
                    WRITER.write(";\n");
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(ParDeclOP parDeclOP) {
        try {
            parDeclOP.getType().accept(this);
            WRITER.write(" ");
            parDeclOP.getId().accept(this);
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(ProgramOP programOP) {
        try {
            WRITER.write("//dichiarazioni variabili globali\n");
            //gestione diversa in quanto in C le variabili globali devono essere costanti
            if (programOP.getVarDeclList() != null) {
                for (VarDeclOP v : programOP.getVarDeclList()) {
                    //caso in cui si dichiarano con var
                    if (v.getIdInitObblList() != null) {
                        ArrayList<AssignStatOP> assignList = v.getIdInitObblList().getList();
                        if (assignList != null){
                            for (AssignStatOP a: assignList){
                                WRITER.write("\t".repeat(numTab));
                                if(a.getId().getTypeOf().equals("string")){
                                    WRITER.write("char ");
                                    a.getId().accept(this);
                                    WRITER.write("[128] = ");
                                    ExprNode en = a.getExpr();

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
                                } else if (a.getId().getTypeOf().equals("real")){
                                    WRITER.write("double ");
                                    a.accept(this);
                                } else if (a.getId().getTypeOf().equals("int")){
                                    WRITER.write("int ");
                                    a.accept(this);
                                } else if (a.getId().getTypeOf().equals("bool")){
                                    WRITER.write("bool ");
                                    a.accept(this);
                                }
                                WRITER.write(";\n");
                            }
                        }

                    } else if (v.getIdListInit() != null) { //caso in cui si dichiarano col tipo
                        ArrayList<IDLeaf> idList = v.getIdListInit().getIds();
                        ArrayList<ExprNode> exprNodes = v.getIdListInit().getExprs();

                        if(idList != null){
                            int i = 0;
                            for(; i < idList.size(); i++) {
                                WRITER.write("\t".repeat(numTab));

                                if(idList.get(i).getTypeOf().equals("string")) {
                                    WRITER.write("char ");
                                    WRITER.write(idList.get(i).getId());
                                    WRITER.write("[128]");
                                } else if (idList.get(i).getTypeOf().equals("real")){
                                    WRITER.write("double ");
                                    WRITER.write(idList.get(i).getId());
                                } else if (idList.get(i).getTypeOf().equals("int")){
                                    WRITER.write("int ");
                                    WRITER.write(idList.get(i).getId());
                                } else if (idList.get(i).getTypeOf().equals("bool")){
                                    WRITER.write("bool ");
                                    WRITER.write(idList.get(i).getId());
                                }

                                if (exprNodes.get(i) != null){
                                    WRITER.write(" = ");
                                    ExprNode en = exprNodes.get(i);
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
                                WRITER.write(";\n");
                            }
                        }

                    }
                }
            }

            WRITER.write("\n//dichiarazioni funzioni\n");
            if (programOP.getFunOPList() != null)
                for (FunOP f : programOP.getFunOPList())
                    f.accept(this);

            WRITER.write("\n//main\n");
            WRITER.write("int main(){\n");
            numTab++;
            programOP.getBodyOP().accept(this);
            numTab--;
            WRITER.write("}");

            WRITER.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(StatOP statOP) {
        try {
            String typeStat = statOP.getTypeStat();
            if (typeStat.equals("IF")) {
                ((IfOP) statOP.getNode()).accept(this);
            } else if (typeStat.equals("WHILE")) {
                ((WhileOP) statOP.getNode()).accept(this);
            } else if (typeStat.equals("ReadStat")) {
                ((ReadStatOP) statOP.getNode()).accept(this);
            } else if (typeStat.equals("WriteStat")) {
                ((WriteStatOP) statOP.getNode()).accept(this);
            } else if (typeStat.equals("AssignStat")) {
                ((AssignStatOP) statOP.getNode()).accept(this);
            } else if (typeStat.equals("CallFun")) {
                ((CallFunOP) statOP.getNode()).accept(this);
            } else if (typeStat.equals("ExprStat")) {//return statement
                WRITER.write("\t".repeat(numTab));
                WRITER.write("return ");

                ExprNode en = (ExprNode) statOP.getNode();
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
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(VarDeclOP varDeclOP) {
        try {
            IdListInit idListInit = varDeclOP.getIdListInit();
            IdListInitObbl idListInitObbl = varDeclOP.getIdInitObblList();

            if (idListInit != null) {
                WRITER.write("\t".repeat(numTab));
                varDeclOP.getType().accept(this);
                WRITER.write(" ");
                idListInit.accept(this);
            } else if (idListInitObbl != null) {
                idListInitObbl.accept(this);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private void ExprToString(ExprNode en) throws IOException{
        if(en instanceof UnaryOP){
            UnaryOP unaryOP = (UnaryOP) en;
            String type = unaryOP.getExprType();
            if (type.equals("int"))
                WRITER.write("itos(");
            else if (type.equals("real"))
                WRITER.write("dtos(");
            else if (type.equals("bool"))
                WRITER.write("btos(");
            unaryOP.accept(this);
            if (!type.equals("string"))
                WRITER.write(")");
        } else if (en instanceof BinaryOP){
            BinaryOP binaryOP = (BinaryOP) en;
            String type = binaryOP.getExprType();
            if (type.equals("int"))
                WRITER.write("itos(");
            else if (type.equals("real"))
                WRITER.write("dtos(");
            else if (type.equals("bool"))
                WRITER.write("btos(");
            binaryOP.accept(this);
            if (!type.equals("string"))
                WRITER.write(")");
        } else if (en instanceof CallFunOP){
            CallFunOP callFunOP = (CallFunOP) en;
            if(callFunOP.getReturnType().equals("int")){
                WRITER.write("itos(");
            } else if(callFunOP.getReturnType().equals("real")){
                WRITER.write("dtos(");
            } else if(callFunOP.getReturnType().equals("bool")) {
                WRITER.write("btos(");
            }
            callFunOP.accept(this);
            if (!callFunOP.getReturnType().equals("string"))
                WRITER.write(")");
        } else if (en instanceof IDLeaf){
            String type = ((IDLeaf) en).getTypeOf();
            if (type.equals("int"))
                WRITER.write("itos(");
            else if (type.equals("real"))
                WRITER.write("dtos(");
            else if (type.equals("bool"))
                WRITER.write("btos(");
            ((IDLeaf) en).accept(this);
            if (!type.equals("string"))
                WRITER.write(")");
        } else if (en instanceof ConstLeaf){
            ConstLeaf constLeaf = (ConstLeaf) en;
            String type = constLeaf.getConstType();
            if (type.equals("int"))
                WRITER.write("itos(");
            else if (type.equals("real"))
                WRITER.write("dtos(");
            else if (type.equals("bool"))
                WRITER.write("btos(");
            constLeaf.accept(this);
            if (!type.equals("string"))
                WRITER.write(")");
        }
    }
}
