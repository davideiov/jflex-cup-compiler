package tree;


import nodes.ExprNode;
import nodes.leafs.*;
import nodes.ops.*;
import nodes.otherops.*;
import nodes.statements.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class XMLTreeGenerator implements Visitor{

    private Document document;

    public XMLTreeGenerator() throws ParserConfigurationException{
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();
    }

    public void SaveTo(String filepath) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(new File(filepath)));
    }

    @Override
    public Object visit(ConstLeaf constLeaf) {
        Element ConstLeafElement = document.createElement("Const");
        ConstLeafElement.appendChild(document.createTextNode(constLeaf.getConstType()));
        ConstLeafElement.appendChild(document.createTextNode(constLeaf.getValue())); //.replaceAll("\'","").replaceAll("\"",""));
        return ConstLeafElement;
    }

    @Override
    public Object visit(IDLeaf idLeaf) {
        Element IDLeafElement = document.createElement("ID");
        Element e1 = document.createElement(idLeaf.getId());
        IDLeafElement.appendChild(e1);
        return IDLeafElement;
    }

    @Override
    public Object visit(TypeLeaf typeLeaf) {
        Element typeLeafElement = document.createElement("Type");
        Element e1 = document.createElement(typeLeaf.getType());
        typeLeafElement.appendChild(e1);
        return typeLeafElement;
    }

    @Override
    public Object visit(BinaryOP binaryOP) {
        Element element = document.createElement(binaryOP.getType());
        ExprNode en1 = binaryOP.getExpr1(), en2 = binaryOP.getExpr2();
        Element e1 = null, e2 = null;

        if(en1 instanceof BinaryOP){
            e1 = (Element) ((BinaryOP) en1).accept(this);
        } else if (en1 instanceof UnaryOP){
            e1 = (Element) ((UnaryOP) en1).accept(this);
        } else if (en1 instanceof ConstLeaf){
            e1 = (Element) ((ConstLeaf) en1).accept(this);
        } else if (en1 instanceof IDLeaf){
            e1 = (Element) ((IDLeaf) en1).accept(this);
        } else if (en1 instanceof CallFunOP){
            e1 = (Element) ((CallFunOP) en1).accept(this);
        }

        if(en2 instanceof BinaryOP){
            e2 = (Element) ((BinaryOP) en2).accept(this);
        } else if (en2 instanceof UnaryOP){
            e2 = (Element) ((UnaryOP) en2).accept(this);
        } else if (en2 instanceof ConstLeaf){
            e2 = (Element) ((ConstLeaf) en2).accept(this);
        } else if (en2 instanceof IDLeaf){
            e2 = (Element) ((IDLeaf) en2).accept(this);
        } else if (en2 instanceof CallFunOP){
            e2 = (Element) ((CallFunOP) en2).accept(this);
        }

        element.appendChild(e1);
        element.appendChild(e2);
        return element;
    }

    @Override
    public Object visit(UnaryOP unaryOP) {
        Element element = document.createElement(unaryOP.getType());
        ExprNode en1 = unaryOP.getExpr1();
        Element e1 = null;

        if(en1 instanceof BinaryOP){
            e1 = (Element) ((BinaryOP) en1).accept(this);
        } else if (en1 instanceof UnaryOP){
            e1 = (Element) ((UnaryOP) en1).accept(this);
        } else if (en1 instanceof ConstLeaf){
            e1 = (Element) ((ConstLeaf) en1).accept(this);
        } else if (en1 instanceof IDLeaf){
            e1 = (Element) ((IDLeaf) en1).accept(this);
        } else if (en1 instanceof CallFunOP){
            e1 = (Element) ((CallFunOP) en1).accept(this);
        }

        element.appendChild(e1);
        return element;
    }

    @Override
    public Object visit(AssignStatOP assignStatOP) {
        Element element = document.createElement("AssignStatOP");

        ExprNode en1 = assignStatOP.getExpr();
        Element e1 = (Element) assignStatOP.getId().accept(this);
        Element e2 = null;

        if(en1 instanceof BinaryOP){
            e2 = (Element) ((BinaryOP) en1).accept(this);
        } else if (en1 instanceof UnaryOP){
            e2 = (Element) ((UnaryOP) en1).accept(this);
        } else if (en1 instanceof ConstLeaf){
            e2 = (Element) ((ConstLeaf) en1).accept(this);
        } else if (en1 instanceof IDLeaf){
            e2 = (Element) ((IDLeaf) en1).accept(this);
        } else if (en1 instanceof CallFunOP){
            e2 = (Element) ((CallFunOP) en1).accept(this);
        }

        element.appendChild(e1);
        element.appendChild(e2);

        return element;
    }

    @Override
    public Object visit(CallFunOP callFunOP) {
        Element element = document.createElement("CallFunOP");

        ArrayList<ExprNode> list = callFunOP.getList();

        Element e1 = (Element) callFunOP.getId().accept(this);
        Element e2 = document.createElement("ExprList");

        if(list != null) {
            for (ExprNode en : list) {
                Element el = null;

                if (en instanceof BinaryOP) {
                    el = (Element) ((BinaryOP) en).accept(this);
                } else if (en instanceof UnaryOP) {
                    e1 = (Element) ((UnaryOP) en).accept(this);
                } else if (en instanceof ConstLeaf) {
                    el = (Element) ((ConstLeaf) en).accept(this);
                } else if (en instanceof IDLeaf) {
                    el = (Element) ((IDLeaf) en).accept(this);
                } else if (en instanceof CallFunOP) {
                    el = (Element) ((CallFunOP) en).accept(this);
                }

                e2.appendChild(el);
            }
        }
        element.appendChild(e1);
        element.appendChild(e2);

        return element;
    }

    @Override
    public Object visit(ElseOP elseOP) {
        Element element = document.createElement("ElseOP");
        Element e = (Element) elseOP.getBodyOP().accept(this);
        element.appendChild(e);
        return element;
    }

    @Override
    public Object visit(IfOP ifOP) {
        Element element = document.createElement("IfOP");
        ExprNode en = ifOP.getExpr();

        Element e1 = null;

        if(en instanceof BinaryOP){
            e1 = (Element) ((BinaryOP) en).accept(this);
        } else if (en instanceof UnaryOP){
            e1 = (Element) ((UnaryOP) en).accept(this);
        } else if (en instanceof ConstLeaf){
            e1 = (Element) ((ConstLeaf) en).accept(this);
        } else if (en instanceof IDLeaf){
            e1 = (Element) ((IDLeaf) en).accept(this);
        } else if (en instanceof CallFunOP){
            e1 = (Element) ((CallFunOP) en).accept(this);
        }

        Element e2 = (Element) ifOP.getBodyOP().accept(this);

        Element e3 = (ifOP.getElseOP() != null) ? (Element) ifOP.getElseOP().accept(this) : document.createElement("ElseOP")  ;

        element.appendChild(e1);
        element.appendChild(e2);
        element.appendChild(e3);

        return element;
    }

    @Override
    public Object visit(ReadStatOP readStatOP) {
        Element element = document.createElement("ReadStatOP");
        ExprNode en = readStatOP.getExpr();
        ArrayList<IDLeaf> idList = readStatOP.getIdList();

        Element e1 = document.createElement("IDList");

        for (IDLeaf id: idList){
            e1.appendChild((Element) id.accept(this));
        }
        element.appendChild(e1);

        if (en != null) {
            Element e2 = null;

            if (en instanceof BinaryOP) {
                e2 = (Element) ((BinaryOP) en).accept(this);
            } else if (en instanceof UnaryOP) {
                e2 = (Element) ((UnaryOP) en).accept(this);
            } else if (en instanceof ConstLeaf) {
                e2 = (Element) ((ConstLeaf) en).accept(this);
            } else if (en instanceof IDLeaf) {
                e2 = (Element) ((IDLeaf) en).accept(this);
            } else if (en instanceof CallFunOP) {
                e2 = (Element) ((CallFunOP) en).accept(this);
            }

            for (IDLeaf i : idList) {
                Element e = (Element) i.accept(this);
                e2.appendChild(e);
            }


            element.appendChild(e2);
        }
        return element;
    }

    @Override
    public Object visit(WhileOP whileOP) {
        Element element = document.createElement("WhileOP");
        ExprNode en = whileOP.getExpr();

        Element e1 = null;

        if(en instanceof BinaryOP){
            e1 = (Element) ((BinaryOP) en).accept(this);
        } else if (en instanceof UnaryOP){
            e1 = (Element) ((UnaryOP) en).accept(this);
        } else if (en instanceof ConstLeaf){
            e1 = (Element) ((ConstLeaf) en).accept(this);
        } else if (en instanceof IDLeaf){
            e1 = (Element) ((IDLeaf) en).accept(this);
        } else if (en instanceof CallFunOP){
            e1 = (Element) ((CallFunOP) en).accept(this);
        }

        Element e2 = (Element) whileOP.getBodyOP().accept(this);

        element.appendChild(e1);
        element.appendChild(e2);

        return element;
    }

    @Override
    public Object visit(WriteStatOP writeStatOP) {
        Element element = document.createElement("WriteStatOP");
        ExprNode en = writeStatOP.getExpr();
        Element e1 = document.createElement(writeStatOP.getType());
        Element e2 = null;

        if(en instanceof BinaryOP){
            e2 = (Element) ((BinaryOP) en).accept(this);
        } else if (en instanceof UnaryOP){
            e2 = (Element) ((UnaryOP) en).accept(this);
        } else if (en instanceof ConstLeaf){
            e2 = (Element) ((ConstLeaf) en).accept(this);
        } else if (en instanceof IDLeaf){
            e2 = (Element) ((IDLeaf) en).accept(this);
        } else if (en instanceof CallFunOP){
            e2 = (Element) ((CallFunOP) en).accept(this);
        }

        element.appendChild(e1);
        element.appendChild(e2);

        return element;
    }

    @Override
    public Object visit(FunOP funOP) {
        Element element = document.createElement("FunOP");

        ArrayList<ParDeclOP> paramDeclList = funOP.getParamDeclList();

        Element name = (Element) funOP.getId().accept(this);
        Element params = document.createElement("ParDeclList");;
        Element type = document.createElement("returnType");;
        Element body = (Element) funOP.getBodyOP().accept(this);

        if(paramDeclList == null){
            params.appendChild(document.createElement("null"));
        } else {
            for (ParDeclOP p: paramDeclList){
                Element e = (Element) p.accept(this);
                params.appendChild(e);
            }
        }

        if(funOP.getTypeLeaf() != null)
            type = (Element) funOP.getTypeLeaf().accept(this);
        else
            type.appendChild(document.createElement("null"));

        element.appendChild(name);
        element.appendChild(params);
        element.appendChild(type);
        element.appendChild(body);

        return element;
    }

    @Override
    public Object visit(IdListInit idListInit) {
        Element element = document.createElement("IDListInit");

        ArrayList<IDLeaf> ids = idListInit.getIds();
        ArrayList<ExprNode> exprs = idListInit.getExprs();

        for (int i = 0; i < ids.size(); i++) {
            Element e1 = (Element) ids.get(i).accept(this);
            element.appendChild(e1);

            ExprNode en = exprs.get(i);
            if (en != null) {
                Element e2 = null;

                if (en instanceof BinaryOP) {
                    e2 = (Element) ((BinaryOP) en).accept(this);
                } else if (en instanceof UnaryOP) {
                    e2 = (Element) ((UnaryOP) en).accept(this);
                } else if (en instanceof ConstLeaf) {
                    e2 = (Element) ((ConstLeaf) en).accept(this);
                } else if (en instanceof IDLeaf) {
                    e2 = (Element) ((IDLeaf) en).accept(this);
                } else if (en instanceof CallFunOP) {
                    e2 = (Element) ((CallFunOP) en).accept(this);
                }
                element.appendChild(e2);
            }

        }

        return element;
    }

    @Override
    public Object visit(IdListInitObbl idListInitObbl) {
        Element element = document.createElement("IDListInitObbl");

        ArrayList<AssignStatOP> list = idListInitObbl.getList();

        ArrayList<IDLeaf> ids = idListInitObbl.getIdList();
        ArrayList<ExprNode> exprs = idListInitObbl.getExprs();

        if(list != null)
            for (AssignStatOP a: list) {
                Element e = (Element) a.accept(this);
                element.appendChild(e);
            }
        if(ids != null && exprs != null){
            for(int i = 0; i < ids.size(); i++){
                Element e = (Element) ids.get(i).accept(this);
                element.appendChild(e);
                ExprNode en = exprs.get(i);
                if (en != null) {
                    Element e2 = null;

                    if (en instanceof BinaryOP) {
                        e2 = (Element) ((BinaryOP) en).accept(this);
                    } else if (en instanceof UnaryOP) {
                        e2 = (Element) ((UnaryOP) en).accept(this);
                    } else if (en instanceof ConstLeaf) {
                        e2 = (Element) ((ConstLeaf) en).accept(this);
                    } else if (en instanceof IDLeaf) {
                        e2 = (Element) ((IDLeaf) en).accept(this);
                    } else if (en instanceof CallFunOP) {
                        e2 = (Element) ((CallFunOP) en).accept(this);
                    }
                    element.appendChild(e2);
                }
            }
        }

        return element;
    }

    @Override
    public Object visit(BodyOP bodyOP) {
        Element bodyOPElement = document.createElement("BodyOP");

        ArrayList<VarDeclOP> varDeclList = bodyOP.getVarDeclList();
        ArrayList<StatOP> statList = bodyOP.getStatList();

        Element e1 = document.createElement("VarDeclList");
        Element e2 = document.createElement("StatList");

        if(varDeclList != null) {
            for (VarDeclOP v : varDeclList) {
                Element e = (Element) v.accept(this);
                e1.appendChild(e);
            }
        }
        if(statList != null) {
            for (StatOP s : statList) {
                Element e = (Element) s.accept(this);
                e2.appendChild(e);
            }
        }

        bodyOPElement.appendChild(e1);
        bodyOPElement.appendChild(e2);

        return bodyOPElement;
    }

    @Override
    public Object visit(ParDeclOP parDeclOP) {
        Element parDeclOPElement = document.createElement("ParDeclOP");
        Element e1 = null;
        if(parDeclOP.isOut()) {
            e1 = document.createElement("out");
        } else {
            e1 = document.createElement("in");
        }
        Element e2 = (Element) parDeclOP.getType().accept(this);
        Element e3 = (Element) parDeclOP.getId().accept(this);

        parDeclOPElement.appendChild(e1);
        parDeclOPElement.appendChild(e2);
        parDeclOPElement.appendChild(e3);

        return parDeclOPElement;
    }

    @Override
    public Object visit(ProgramOP programOP) {
        Element programOPElement = document.createElement("ProgramOP");

        ArrayList<VarDeclOP> varDeclList = programOP.getVarDeclList();
        ArrayList<FunOP> funOPList = programOP.getFunOPList();

        Element e1 = document.createElement("VarDeclList");
        Element e2 = document.createElement("FunList");
        Element e3 = (Element) programOP.getBodyOP().accept(this);

        if(varDeclList != null) {
            for (VarDeclOP v : varDeclList) {
                Element e = (Element) v.accept(this);
                e1.appendChild(e);
            }
        }
        if (funOPList != null) {
            for (FunOP f : funOPList) {
                Element e = (Element) f.accept(this);
                e2.appendChild(e);
            }
        }
        programOPElement.appendChild(e1);
        programOPElement.appendChild(e2);
        programOPElement.appendChild(e3);

        document.appendChild(programOPElement);


        return document;
    }

    @Override
    public Object visit(StatOP statOP) {
        Element statOPElement = document.createElement("StatOP");
        Element child = null;

        String typeStat = statOP.getTypeStat();
        if (typeStat.equals("IF")){
            child = (Element) ((IfOP) statOP.getNode()).accept(this);
        } else if (typeStat.equals("WHILE")){
            child = (Element) ((WhileOP) statOP.getNode()).accept(this);
        } else if (typeStat.equals("ReadStat")){
            child = (Element) ((ReadStatOP) statOP.getNode()).accept(this);
        } else if (typeStat.equals("WriteStat")){
            child = (Element) ((WriteStatOP) statOP.getNode()).accept(this);
        } else if (typeStat.equals("AssignStat")){
            child = (Element) ((AssignStatOP) statOP.getNode()).accept(this);
        } else if (typeStat.equals("CallFun")){
            child = (Element) ((CallFunOP) statOP.getNode()).accept(this);
        } else if (typeStat.equals("ExprStat")) {
            statOPElement.appendChild(document.createTextNode("ReturnOP"));
            ExprNode en = (ExprNode) statOP.getNode();
            child = null;

            if(en instanceof BinaryOP){
                child = (Element) ((BinaryOP) en).accept(this);
            } else if (en instanceof UnaryOP){
                child = (Element) ((UnaryOP) en).accept(this);
            } else if (en instanceof ConstLeaf){
                child = (Element) ((ConstLeaf) en).accept(this);
            } else if (en instanceof IDLeaf){
                child = (Element) ((IDLeaf) en).accept(this);
            } else if (en instanceof CallFunOP){
                child = (Element) ((CallFunOP) en).accept(this);
            }
        }

        statOPElement.appendChild(child);
        return statOPElement;
    }

    @Override
    public Object visit(VarDeclOP varDeclOP) {
        Element varDeclOPElement = document.createElement("VarDeclOP");

        if(varDeclOP.getType() != null){
            Element e1 = (Element) varDeclOP.getType().accept(this);
            Element e2 = (Element) varDeclOP.getIdListInit().accept(this);

            varDeclOPElement.appendChild(e1);
            varDeclOPElement.appendChild(e2);
        } else {
            Element e1 = (Element) varDeclOP.getIdInitObblList().accept(this);
            varDeclOPElement.appendChild(e1);
        }

        return varDeclOPElement;
    }
}
