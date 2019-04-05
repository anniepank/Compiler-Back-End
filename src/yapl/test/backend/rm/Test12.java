package yapl.test.backend.rm;

import yapl.impl.BackendMIPS;
import yapl.interfaces.BackendAsmRM;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class Test12 {

    public static void main(String[] args) throws FileNotFoundException {
        PrintStream out = (args.length > 0)
                ? new PrintStream(new FileOutputStream(args[0])) : System.out;
        BackendAsmRM backend = new BackendMIPS(out);

        backend.enterMain();

        int offset = backend.allocStaticData((byte)5, "five");
        byte result = backend.allocReg();

        byte five=backend.allocReg();
        backend.loadConst(five,5);
        backend.storeWord(five,offset,true);
        backend.freeReg(five);
        backend.loadAddress(result, offset, true);
        backend.loadWordReg(result,result);

        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");
        backend.exitMain("main_end");

    }
}
