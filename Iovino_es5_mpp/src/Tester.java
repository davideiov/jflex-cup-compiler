import nodes.statements.ProgramOP;
import tree.CodeGenerator;
import tree.SemanticVisitor;
import tree.XMLTreeGenerator;

import java.io.File;
import java.io.FileReader;

public class Tester {

    public static void main(String[] args) throws Exception {
        System.out.println("--- COMPILATORE ---");

        String filepath = args[0];
        String filename = new File(args[0]).getName().replaceAll(".txt",".c");

        FileReader file = new FileReader(filepath);
        parser p = new parser(new Lexer(file));


        XMLTreeGenerator xml = new XMLTreeGenerator();
        SemanticVisitor semanticVisitor = new SemanticVisitor();
        CodeGenerator codeGenerator = new CodeGenerator("test_files" + File.separator + "c_out" + File.separator + filename);

        ProgramOP programOP = (ProgramOP) p.parse().value;

        programOP.accept(xml);
        xml.SaveTo("test_files" + File.separator + "AST" + File.separator + filename.replaceAll(".c",".xml"));
        programOP.accept(semanticVisitor);
        programOP.accept(codeGenerator);

        //istruzione system dependent
        //Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"gcc -lm " + "test_files" + File.separator + "c_out" + File.separator + filename + " \"");
    }
}
