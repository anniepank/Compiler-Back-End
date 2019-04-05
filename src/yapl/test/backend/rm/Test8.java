package yapl.test.backend.rm;

import yapl.impl.BackendMIPS;
import yapl.interfaces.BackendAsmRM;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * BackendAsmRM test: operations.
 */
public class Test8 {

    private static BackendAsmRM backend;

    public static void main(String[] args) throws Exception
    {
        PrintStream out = (args.length > 0)
                ? new PrintStream(new FileOutputStream(args[0])) : System.out;
        backend = new BackendMIPS(out);

        backend.enterMain();
        byte zero=backend.zeroReg();
        byte one=backend.allocReg();
        backend.loadConst(one,1);
        byte result=backend.allocReg();
        backend.comment("this is a comment");
        backend.add(result,one,zero);
        backend.neg(result,result);
        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");
        backend.exitMain("main_end");
    }
}
