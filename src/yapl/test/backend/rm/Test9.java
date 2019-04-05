package yapl.test.backend.rm;

import yapl.impl.BackendMIPS;
import yapl.interfaces.BackendAsmRM;

import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * BackendAsmRM test: allocate static memory.
 * Takes about 3 minutes to run
 */
public class Test9 {

    private static BackendAsmRM backend;

    public static void main(String[] args) throws Exception
    {
        PrintStream out = new PrintStub(System.out);
        backend = new BackendMIPS(out);

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            backend.allocStringConstant(""+i);
            backend.allocStaticData(4,null);
        }
        boolean passed1=false;
        try {
            backend.allocStringConstant(""+Integer.MAX_VALUE);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            passed1=true;
        }
        boolean passed2=false;
        try {
            backend.allocStaticData(4,null);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            passed2=true;
        }
        if(!passed1||!passed2){
            throw new Exception();
        }
        out = (args.length > 0)
                ? new PrintStream(new FileOutputStream(args[0])) : System.out;
        backend = new BackendMIPS(out);
        backend.enterMain();
        int addr = backend.allocStringConstant("Hello world!");
        backend.writeString(addr);
        backend.exitMain("main_end");

    }
}
