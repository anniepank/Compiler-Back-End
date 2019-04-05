package yapl.test.backend.rm;

import yapl.impl.BackendMIPS;
import yapl.interfaces.BackendAsmRM;

import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * BackendAsmRM test: operations mul, div, mod and array,length.
 */
public class Test10 {

    public static void main(String[] args) throws Exception
    {
        PrintStream out = (args.length > 0)
                ? new PrintStream(new FileOutputStream(args[0])) : System.out;
        BackendMIPS backend = new BackendMIPS(out);
        backend.enterMain();
        byte zero=backend.zeroReg();
        byte one=backend.allocReg();
        backend.loadConst(one,1);
        byte two = backend.allocReg();
        backend.loadConst(two,2);
        byte five = backend.allocReg();
        backend.loadConst(five,5);
        byte result=backend.allocReg();
        backend.mul(result,two,one);
        backend.div(result,five,result);

        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");

        byte array = backend.allocReg();
        backend.allocArray(array);
        backend.arrayLength(result,array);
        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");

        backend.mod(result,five,two);
        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");

        backend.getregisterA(4);
        byte check = backend.allocReg();
        backend.loadWordReg(two,(byte) 29);
        backend.loadWordReg(check, (byte) 29,0);
        backend.isEqual(result,two,check);
        backend.prepareProcCall(1);
        backend.passArg(0, result);
        backend.callProc((byte) -1, "writeint");

        backend.exitMain("main_end");

    }
}
