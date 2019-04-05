package yapl.test.backend.rm;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import yapl.impl.BackendMIPS;
import yapl.interfaces.BackendAsmRM;

/**
 * BackendAsmRM test: registers.
 */
public class Test7 {

    public static void main(String[] args) throws Exception
    {
        BackendAsmRM backend = new BackendMIPS();
        PrintStream out = (args.length > 0)
                ? new PrintStream(new FileOutputStream(args[0])) : System.out;
        backend = new BackendMIPS(out);
        ArrayList<Byte> regArrayList=new ArrayList<>();

        backend.enterMain();
        for (int i = 0; i < 32; i++) {
            ((BackendMIPS)backend).getStrregister(i);
        }
        boolean passed=false;
        try {
            ((BackendMIPS)backend).getStrregister(32);
        }catch (NullPointerException e){
            passed=true;
        }
        if (!passed){
            throw new Exception();
        }

        for (int i = 0; i < 10; i++) {//should have 10 usable registers
            byte reg=backend.allocReg();
            if(reg>=0) {
                regArrayList.add(reg);
            }else {
                throw new Exception();
            }
        }
        if (backend.allocReg()!=(byte)-1){//all registers are allocated
            throw new Exception();
        }
        for (Byte b :
                regArrayList) {
            backend.freeReg(b);
        }

        int addr = backend.allocStringConstant("Hello world!");
        backend.writeString(addr);
        backend.exitMain("main_end");
    }
}
