package yapl.test.backend.rm;

import yapl.impl.BackendMIPS;
import yapl.interfaces.BackendAsmRM;

import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * BackendAsmRM test: operations.
 */
public class Test11 {

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
        byte two = backend.allocReg();
        backend.loadConst(two,2);
        byte five = backend.allocReg();
        backend.addConst(five,zero,5);
        byte result=backend.allocReg();
        backend.isLess(result,two,five);
        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");


        backend.isEqual(result,two,two);
        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");

        backend.isLessOrEqual(result,one,two);
        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");

        backend.and(result,one,zero);
        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");

        backend.or(result,one,zero);
        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");

        backend.not(result,one);
        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");

        backend.boolValue(true);
        backend.branchIf(result,true,"exit");
        backend.not(result,one);
        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");
        backend.jump("exit");

        backend.or(result,one,zero);
        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");

        backend.emitLabel("exit","end");
        backend.freeReg(result);
        backend.exitMain("main_end");



    }
}
