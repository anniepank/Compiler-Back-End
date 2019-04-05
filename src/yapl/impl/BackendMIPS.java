package yapl.impl;

import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import yapl.interfaces.BackendAsmRM;

import java.io.PrintStream;
import java.util.Arrays;

public class BackendMIPS implements BackendAsmRM {
    private static final String FILE_HEADER = ".text\n" +
            "writeint:\n" +
            "li $v0, 1\n" +
            "syscall\n" +
            "jr $ra";
    private static final String STRING_LABEL = "_string";
    private static final String STATIC_MEMORY_LABEL = "_static_var";
    private int[] registers = new int[31];
    private int stringIndex;
    private int staticMemory;

    private static final int STATIC_CALLEE_FRAME_SIZE = 40;
    private static final int STATIC_CALLER_FRAME_SIZE = 64;
    private static final int MAX_ARRAY_DIMENTION = 10;
    private int numberOfArguments = 0;
    private int numberOfCurrentFunctionCallArguments = 0;
    private int numberOfLocalBytes = 0;
    private int[] previousFrameRegisters = new int[31];
    private int[] arraydim = new int[MAX_ARRAY_DIMENTION];

    /*
    *  L3 L2 L1... S0 S1 S2.. S7 FP RA A0 A1 A2 A3 | [A4 A5...] V0 V1 T0 T1 .. T9 A0 A1 A2 A3
    * [SP CALLEE STACK ---------------------------] [ FP CALLER STACK -----------------------]
    *
    * */

    private PrintStream out;

    public BackendMIPS() {
        this(System.out);
    }

    public BackendMIPS(PrintStream printStream) {
        this.stringIndex = 0;
        this.staticMemory = 0;
        this.out = printStream;
        out.println(FILE_HEADER);
    }

    @Override
    public int wordSize() {
        // word-size in MIPS is 32 bit
        // 4 bytes
        return 4;
    }

    @Override
    public int boolValue(boolean value) {
        if (value == true) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public byte allocReg() {
        // $t0 -$t9(8-15, 24-25) can be used for variables
        int i = 8;
        while (i <= 15) {
            if (registers[i] == 0) {
                registers[i] = 1;
                return (byte) i;
            }
            i++;
        }
        i = 24;
        while (i <= 25) {
            if (registers[i] == 0) {
                registers[i] = 1;
                return (byte) i;
            }
            i++;
        }
        return -1;
    }



    @Override
    public void freeReg(byte reg) {
        registers[reg] = 0;
    }

    @Override
    public byte zeroReg() {
        return 0;
    }

    @Override
    public void comment(String comment) {
        String res = "# " + comment;
        this.out.println(res);
    }

    @Override
    public void emitLabel(String label, String comment) {
        String mipsInstructions = label+":";
        if (!(comment==null)){
            mipsInstructions+= " # " + comment;
        }
        this.out.println(mipsInstructions);
    }

    @Override
    public int allocStaticData(int bytes, String comment) {

        String mipsInstructions = ".data\n" + STATIC_MEMORY_LABEL + this.staticMemory + ": .space " + bytes;
        if (!(comment==null)) {
            mipsInstructions += " # " + comment;
        }
        mipsInstructions += "\n.text";
        this.out.println(mipsInstructions);
        if (this.staticMemory == Integer.MAX_VALUE) {
            throw new IllegalStateException("Too many static-data-allocations!");
        }
        return this.staticMemory++;
    }

    @Override
    public int allocStringConstant(String string) {
        String label = STRING_LABEL + this.stringIndex;
        String mipsInstructions =
                ".data\n" +
                        label + ":.asciiz \"" + string + "\"\n" +//save string at label
                        ".text";
        this.out.println(mipsInstructions);
        if (this.stringIndex == Integer.MAX_VALUE) {
            throw new IllegalStateException("Too many String-allocations!");
        }
        return this.stringIndex++;
    }

    @Override
    public int allocStack(int bytes, String comment) {
        if (bytes % 4 != 0) {
            bytes = bytes / 4 * 4 + 4;
        }
        numberOfLocalBytes += bytes;
        String mipsInstructions = "subiu\t$sp, $sp, " + bytes;
        if(!(comment==null)) {
            mipsInstructions += " # " + comment;
        }
        this.out.println(mipsInstructions);
        return -(numberOfArguments * 4 + STATIC_CALLEE_FRAME_SIZE + numberOfLocalBytes);
    }

    @Override
    public void allocHeap(byte destReg, int bytes) {
        String mipsInstructions = syscall(9, bytes);
        mipsInstructions += "\nla " + getStrregister(destReg) + ", ($v0)";
        this.out.println(mipsInstructions);
//        storeWordReg((byte) 2, destReg);
    }

    @Override
    public void storeArrayDim(int dim, byte lenReg) {
        arraydim[dim] = lenReg;
    }

    @Override
    public void allocArray(byte destReg) {
        int length = 1;
        for (int i = 0; i < arraydim.length; i++) {
            if (arraydim[i] == 0) {
                break;
            } else {
                length = length * arraydim[i];
            }
        }
        length = length * 4;
        String mipsInstructions = "";
        byte a0=getregisterA(0);

        mipsInstructions += "li "+getStrregister(a0)+", " + length + "\n"
                + "li $v0, 9\n" +
                "syscall\n";
        mipsInstructions += "la " + getStrregister(destReg) + ", ($v0)";
        this.out.println(mipsInstructions);
        freeReg(a0);
    }

    @Override
    public void loadConst(byte reg, int value) {
        String mipsInstructions = "li " + getStrregister(reg) + ", " + value;
        this.out.println(mipsInstructions);
    }

    @Override
    public void loadAddress(byte reg, int addr, boolean isStatic) {
        String mipsInstructions;
        if (!isStatic) {
            mipsInstructions = "la\t" + getStrregister(reg) + ", " + addr + "($fp)";
        } else {
            mipsInstructions = "la " + getStrregister(reg) + "," + STATIC_MEMORY_LABEL + addr;
        }
        this.out.println(mipsInstructions);
    }

    /*
    * if isStatic == false, then addr is an offset relative to FP
    * else it is a memory address
    * */
    @Override
    public void loadWord(byte reg, int addr, boolean isStatic) {
        String mipsInstructions;
        if (!isStatic) {
            mipsInstructions = "lw\t" + getStrregister(reg) + ", " + addr + "($fp)";
        } else {
            mipsInstructions = "lw\t" + getStrregister(reg) + "," + STATIC_MEMORY_LABEL + addr;
        }
        this.out.println(mipsInstructions);
    }

    @Override
    public void storeWord(byte reg, int addr, boolean isStatic) {
        String mipsInstructions;
        if (!isStatic) {
            mipsInstructions = "sw " + getStrregister(reg) + ", " + addr + "($fp)";
        } else {
            mipsInstructions = "sw " + getStrregister(reg) + "," + STATIC_MEMORY_LABEL + addr;
        }
        this.out.println(mipsInstructions);
    }

    @Override
    public void loadWordReg(byte reg, byte addrReg) {
        String mipsInstructions = "lw " + getStrregister(reg) + ", 0(" + getStrregister(addrReg) + ")\n";
        this.out.println(mipsInstructions);
    }

    @Override
    public void loadWordReg(byte reg, byte addrReg, int offset) {
        String mipsInstructions = "lw " + getStrregister(reg) + ", " + offset + "(" + getStrregister(addrReg) + ")" + "\n";
        this.out.println(mipsInstructions);
    }

    @Override
    public void storeWordReg(byte reg, int addrReg) {
        String mipsInstructions = "sw " + getStrregister(reg) + ", 0(" + getStrregister(addrReg) + ")\n";
        this.out.println(mipsInstructions);
    }

    @Override
    public void arrayOffset(byte dest, byte baseAddr, byte index) {
        int re = allocReg();
        String mipsInstructions = "addi " + getStrregister(re) + ", $zero, 4";
        this.out.println(mipsInstructions);
        mul(index, index, (byte) re);
        String mipsInstruction = "add " + getStrregister(dest) + ", "
                + getStrregister(baseAddr) + ", " + getStrregister(index) + " # get an element of array " + "\n";
//        mipsInstructions+="la " + getStrregister(baseAddr)
//                +", "+"("+getStrregister(baseAddr)+")";
        freeReg((byte) re);

        this.out.println(mipsInstruction);
    }

    @Override
    public void arrayLength(byte dest, byte baseAddr) {
        int term = allocReg();
        String strDest = getStrregister(dest);
        String strBaseAddr = getStrregister(baseAddr);
        String strTerm = getStrregister(term);
        String mipsInstructions =
                "laWhile:\n" +
                        "move " + strDest+", $zero\n"+
                        "lw " + strTerm+ ", 0(" + strBaseAddr + ")\n" +
                        "beq " + strTerm + ",$zero,endLaWh\n" +
                        "addi " + strDest + ", " + "$zero" + ", 1\n" +
                        "addi " + strBaseAddr + " ," + strBaseAddr + ", 4\n" +
                        "j   laWhile\n" +
                        "endLaWh:\n";
        freeReg((byte) term);
        this.out.println(mipsInstructions);
    }

    @Override
    public void writeString(int addr) {
        String label = STRING_LABEL + addr;
        byte a0=getregisterA(0);
        String mipsInstructions = "la "+getStrregister(a0)+", " + label + "\n" +//load from label
                syscall(4);
        this.out.println(mipsInstructions);
        freeReg(a0);
    }

    @Override
    public void neg(byte regDest, byte regX) {
        String mipsInstructions = "subu " + getStrregister(regDest) + ", "
                + getStrregister(zeroReg()) + ", " + getStrregister(regX);
        this.out.println(mipsInstructions);
    }

    @Override
    public void add(byte regDest, byte regX, byte regY) {
        String mipsInstructions = "addu " + getStrregister(regDest) + ", "
                + getStrregister(regX) + ", " + getStrregister(regY);
        this.out.println(mipsInstructions);
    }

    @Override
    public void addConst(byte regDest, byte regX, int value) {
        String mipsInstructions = "addi " + getStrregister(regDest) + ", "
                + getStrregister(regX) + ", " + value;
        this.out.println(mipsInstructions);
    }

    @Override
    public void sub(byte regDest, byte regX, byte regY) {
        String mipsInstructions = "sub " + getStrregister(regDest) + ", "
                + getStrregister(regX) + ", " + getStrregister(regY);
        this.out.println(mipsInstructions);
    }

    @Override
    public void mul(byte regDest, byte regX, byte regY) {
        String mipsInstructions = "mul " + getStrregister(regDest) + ", " + getStrregister(regX) + ", " + getStrregister(regY) + "\n";
        this.out.println(mipsInstructions);
    }

    @Override
    public void div(byte regDest, byte regX, byte regY) {
        String mipsInstructions = "div " + getStrregister(regX) + ", " + getStrregister(regY) + "\n";
        mipsInstructions = mipsInstructions + "mflo " + getStrregister(regDest);
        this.out.println(mipsInstructions);
    }

    @Override
    public void mod(byte regDest, byte regX, byte regY) {
        String mipsInstructions = "div " + getStrregister(regX) + ", " + getStrregister(regY) + "\n";
        mipsInstructions = mipsInstructions + "mfhi " + getStrregister(regDest);
        this.out.println(mipsInstructions);
    }

    @Override
    public void isLess(byte regDest, byte regX, byte regY) {
        String mipsInstructions = "slt " + getStrregister(regDest) + ", " +
                getStrregister(regX) + ", " + getStrregister(regY) + "\n";

        this.out.println(mipsInstructions);
    }

    @Override
    public void isLessOrEqual(byte regDest, byte regX, byte regY) {
        String mipsInstructions = "sle " + getStrregister(regDest) + ", " +
                getStrregister(regX) + ", " + getStrregister(regY) + "\n";
        this.out.println(mipsInstructions);
    }

    @Override
    public void isEqual(byte regDest, byte regX, byte regY) {
        String mipsInstructions = "seq " + getStrregister(regDest) + ", " +
                getStrregister(regX) + ", " + getStrregister(regY) + "\n";
        this.out.println(mipsInstructions);
    }

    @Override
    public void not(byte regDest, byte regSrc) {
        String mipsInstructions = "not " + getStrregister(regDest) + ", " +
                getStrregister(regSrc) + "\n";
        this.out.println(mipsInstructions);
    }

    @Override
    public void and(byte regDest, byte regX, byte regY) {
        String mipsInstructions = "and " + getStrregister(regDest) + ", " +
                getStrregister(regX) + ", " + getStrregister(regY) + "\n";
        this.out.println(mipsInstructions);
    }

    @Override
    public void or(byte regDest, byte regX, byte regY) {
        String mipsInstructions = "or " + getStrregister(regDest) + ", " +
                getStrregister(regX) + ", " + getStrregister(regY) + "\n";
        this.out.println(mipsInstructions);
    }

    @Override
    public void branchIf(byte reg, boolean value, String label) {
        if (value) {
            out.println("bne\t" + getStrregister(reg) + ", $zero, " + label);
        } else {
            out.println("beq\t" + getStrregister(reg) + ", $zero, " + label);
        }
    }

    @Override
    public void jump(String label) {
        this.out.println("j\t" + label);
    }

    @Override
    public void enterMain() {
        String mipsInstructions = ".globl main\n" +
                "main:";
        out.println(mipsInstructions);
    }

    @Override
    public void exitMain(String label) {
        String mipsInstructions = this.syscall(10) +
                "\n" + label + ":";
        this.out.println(mipsInstructions);
    }

    @Override
    public void enterProc(String label, int nParams) {
        this.numberOfArguments = nParams;
        String mipsInstructions = label + ":\n";
        //save old $fp to -12($sp) <=> 32($sp)(later when $sp -= 44) <=> after args and $ra
        mipsInstructions += "sw\t$fp, -" + (numberOfArguments * 4 + 8) + "($sp)\n";
        mipsInstructions += "move\t$fp, $sp\n";
        mipsInstructions += "subiu\t$sp, $sp, " + (STATIC_CALLEE_FRAME_SIZE + numberOfArguments * 4) + "\n";
        //args are saved after $sp
        for (int i = 0; i < numberOfArguments; i++) {
            mipsInstructions += "sw\t$a" + i + ", " + (STATIC_CALLEE_FRAME_SIZE + i * 4) + "($sp)\n";
        }
        mipsInstructions += "sw\t$ra, " + (STATIC_CALLEE_FRAME_SIZE - 4) + "($sp)\n";
        for (int i = 0; i < 8; i++) {
            mipsInstructions += "sw\t$s" + i + ", " + (i * 4) + "($sp)\n";
        }

        // free saved registers

        for (int i = 0; i < registers.length; i++) {
            previousFrameRegisters[i] = registers[i];
        }
        for (int i = 2; i <= 15; i++) {
            registers[i] = 0;
        }

        for (int i = 24; i <= 25; i++) {
            registers[i] = 0;
        }
        this.out.println(mipsInstructions);
    }

    @Override
    public void exitProc(String label) {
        String mipsInstructions = label + ":\n";
        for (int i = 0; i < 8; i++) {
            mipsInstructions += "lw\t$s" + i + ", " + (numberOfLocalBytes + i * 4) + "($sp)\n";
        }
        mipsInstructions += "lw\t$ra, " + (numberOfLocalBytes + STATIC_CALLEE_FRAME_SIZE - 4) + "($sp)\n";
        mipsInstructions += "lw\t$fp, " + (numberOfLocalBytes + STATIC_CALLEE_FRAME_SIZE - 8) + "($sp)\n";
        mipsInstructions += "addiu\t$sp, $sp, " + (numberOfLocalBytes + STATIC_CALLEE_FRAME_SIZE + numberOfArguments * 4) + "\n";
        mipsInstructions += "jr\t$ra\n";

        // return to previous state

        for (int i = 0; i < registers.length; i++) {
            registers[i] = previousFrameRegisters[i];
        }

        this.out.println(mipsInstructions);

    }

    @Override
    public void returnFromProc(String label, byte reg) {
        String mipsInstructions = "";
        mipsInstructions += "move\t$v0, " + getStrregister(reg) + "\n";
        mipsInstructions += "j\t" + label + "\n";
        this.out.println(mipsInstructions);
    }

    /*addi    $sp, $sp, -12
    sw 	$ra, 8($sp)
	sw 	$s0, 4($sp)
	sw 	$s1, 0($sp)
	move	$s0, $a0
	*/
    @Override
    public void prepareProcCall(int numArgs) {

        numberOfCurrentFunctionCallArguments = numArgs;
        String mipsInstructions = "subiu\t$sp, $sp, " + STATIC_CALLER_FRAME_SIZE + "\n";

        for (int i = 0; i < 2; i++) {
            mipsInstructions += "sw\t$v" + i + ", " + (i * 4) + "($sp)\n";
        }

        for (int i = 0; i < 10; i++) {
            mipsInstructions += "sw\t$t" + i + ", " + (8 + i * 4) + "($sp)\n";
        }

        for (int i = 0; i < 4; i++) {
            mipsInstructions += "sw\t$a" + i + ", " + (8 + 10 * 4 + i * 4) + "($sp)\n";
        }

        // saving space for arguments 4, 5, ..
        mipsInstructions += "subiu\t$sp, $sp, " + Math.max(numberOfCurrentFunctionCallArguments - 4, 0) * 4 + "\n";

        this.out.println(mipsInstructions);
    }

    @Override
    public void passArg(int arg, byte reg) {
        if (arg < 4) {
            String mipsInstructions = "move " + getStrregister(getregisterA(arg)) + ", " + getStrregister(reg);
            this.out.println(mipsInstructions);

        } else {
            this.out.println("sw\t" + getStrregister(reg) + ", " + (arg - 4) * 4 + "($sp)");
        }
    }

    @Override
    public void callProc(byte reg, String name) {
        String mipsInstructions = "jal " + name + "\n";

        //restore values that were saved in prepareProc
        // sp was on a4
        mipsInstructions += "addiu\t$sp, $sp, " + Math.max(numberOfCurrentFunctionCallArguments - 4, 0) * 4 + "\n";


        for (int i = 0; i < 10; i++) {
            mipsInstructions += "lw\t$t" + i + ", " + (8 + i * 4) + "($sp)\n";
        }

        for (int i = 0; i < 4; i++) {
            mipsInstructions += "lw\t$a" + i + ", " + (8 + 10 * 4 + i * 4) + "($sp)\n";
        }


        if (reg != -1) {
            mipsInstructions = mipsInstructions + "move " + getStrregister(reg) + ", " + "$v0\n";
        }

        //TODO: discuss what is going to be if reg == v0|v1

        for (int i = 0; i < 2; i++) {
            mipsInstructions += "lw\t$v" + i + ", " + (i * 4) + "($sp)\n";
        }


        out.println(mipsInstructions);
        for (int i = 0; i < 4; i++) {
            freeReg((byte)(i+4));
        }
    }

    @Override
    public int paramOffset(int index) {
        return 4 * (-numberOfArguments + index);
    }

    public String syscall(int v0) {
        String mipsInstructions = "li $v0, " + v0 + "\n" +
                "syscall";
        return mipsInstructions;
    }


    public String syscall(int v0, int n) {
        String mipsInstructions = "li $v0, " + v0 + "\n" +
                "li $a0, " + n + "\n" +
                "syscall";
        return mipsInstructions;
    }

    public byte getregisterA(int arg) {
        // $a0-$a3(4-7)
        for (int i = 4+arg; i < 8; i++) {
            if (registers[i] == 0) {
                registers[i] = 1;
                return (byte) i;
            }
        }
        return -1;
    }

    public String getStrregister(int index) {
        if (index == 0) {
            return "$zero";
        } else if (index == 1) {
            return "$at";
        } else if (index <= 3 && index >= 2) {
            return "$v" + (index - 2);
        } else if (index >= 4 && index <= 7) {
            return "$a" + (index - 4);
        } else if (index >= 8 && index <= 15) {
            return "$t" + (index - 8);
        } else if (index >= 16 && index <= 23) {
            return "$s" + (index - 16);
        } else if (index >= 24 && index <= 25) {
            return "$t" + (index - 16);
        } else if (index >= 26 && index <= 27) {
            return "$k" + (index - 26);
        } else if (index == 28) {
            return "$gp";
        } else if (index == 29) {
            return "$sp";
        } else if (index == 30) {
            return "$fp";
        } else if (index == 31) {
            return "$ra";
        } else {
            throw new NullPointerException("No register");
        }

    }
}
