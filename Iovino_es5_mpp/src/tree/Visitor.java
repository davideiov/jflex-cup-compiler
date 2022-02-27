package tree;

import nodes.leafs.*;
import nodes.statements.*;
import nodes.otherops.*;
import nodes.ops.*;


public interface Visitor {

    Object visit(ConstLeaf constLeaf);
    Object visit(IDLeaf id);
    Object visit(TypeLeaf typeLeaf);

    Object visit(BinaryOP binaryOP);
    Object visit(UnaryOP unaryOP);

    Object visit(AssignStatOP assignStatOP);
    Object visit(CallFunOP callFunOP);
    Object visit(ElseOP elseOP);
    Object visit(IfOP ifOP);
    Object visit(ReadStatOP readStatOP);
    Object visit(WhileOP whileOP);
    Object visit(WriteStatOP writeStatOP);

    Object visit(FunOP funOP);
    Object visit(IdListInit idListInit);
    Object visit(IdListInitObbl idListInitObbl);
    Object visit(BodyOP bodyOP);
    Object visit(ParDeclOP parDeclOP);
    Object visit(ProgramOP programOP);
    Object visit(StatOP statOP);
    Object visit(VarDeclOP varDeclOP);
}
