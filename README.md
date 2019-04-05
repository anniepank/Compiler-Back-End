# Back-end for a compiler
This project is an implementation of a back-end for a future compiler

##Goup members
Pankova Hanna, Platter Hellmuth, Wen Boda

## Installation
Download the package

## Testing
Open package folder in terminal and run
```bash
ant
```

## Code coverage
Code coverage was performed with IntelliJ IDEA code coverage runner
### Total statistic
Number of covered methods is **38 out of 53** (71.7%)   
Number of covered lines is **207 out of 297** (69.7%)

Actual information can be found in coverage/Test{num}/BackendMIPS.html

### Uncovered methods ussing test cases 1-6
* BackendMIPS()
* allocReg() do not allocate registers 24-25
* zeroReg()
* throw new IllegalStateException("Too many smth-allocations!"). Maybe we should get rid of it
* allocStack(int bytes, String comment) in case of allocating value not multiple 4
* loadAddress(byte reg, int addr, boolean isStatic)
* arrayLength(byte dest, byte baseAddr)
* neg(byte regDest, byte regX)
* add(byte regDest, byte regX, byte regY)
* div(byte regDest, byte regX, byte regY)
* mod(byte regDest, byte regX, byte regY)
* isLess(byte regDest, byte regX, byte regY)
* isLessOrEqual(byte regDest, byte regX, byte regY)
* isEqual
* not
* and
* or
* branchIf() in valid case (not else)
* passArg(int arg, byte reg) in else case
* syscall(int v0, String register)
* syscall(int v0, int n, String comment)
* getregisterA(int arg) to use not the first register a0, but for ex. a1
* getStrregister(int index) almost all cases

## Code coverage after adding tests (test cases 1-13)
Number of covered methods is **53 out of 53** (100%)  
Number of covered lines is **297 out of 297** (100%)
