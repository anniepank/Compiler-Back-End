package yapl.test.backend.rm;

import yapl.impl.BackendMIPS;
import yapl.interfaces.BackendAsmRM;

import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * BackendAsmRM test: proc with >4 arguments.
 */
public class Test13 {

    private static BackendAsmRM backend;

    public static void main(String[] args) throws Exception
    {
        PrintStream out = (args.length > 0)
                ? new PrintStream(new FileOutputStream(args[0])) : System.out;
        backend = new BackendMIPS(out);

        backend.enterProc("proc", 1);
        byte r1 = backend.allocReg();
        byte r2 = backend.allocReg();
        backend.loadWord(r1, backend.paramOffset(0), false);  // load parameter 0 from stack frame
        backend.loadWord(r2, backend.paramOffset(1), false);  // load parameter 1 from stack frame
        backend.add(r1,r1,r2);
        backend.loadWord(r2, backend.paramOffset(2), false);  // load parameter 2 from stack frame
        backend.add(r1,r1,r2);
        backend.loadWord(r2, backend.paramOffset(3), false);  // load parameter 3 from stack frame
        backend.add(r1,r1,r2);
        backend.loadWord(r2, backend.paramOffset(4), false);  // load parameter 4 from stack frame
        backend.add(r1,r1,r2);
        backend.freeReg(r1);
        backend.freeReg(r2);
        backend.returnFromProc("proc_end", r1);
        backend.exitProc("proc_end");

        backend.enterMain();
        int a=backend.allocStack(1,null);
        byte reg=backend.allocReg();

        backend.loadAddress(reg,0,false);

        backend.prepareProcCall(5);
        for (int i = 0; i < 5; i++) {
            backend.passArg(i, reg);
        }

        backend.callProc(reg, "proc");
        backend.prepareProcCall(1);
        backend.passArg(0, reg);
        backend.callProc((byte) -1, "writeint");
        backend.freeReg(reg);
        backend.exitMain("main_end");
    }
}
