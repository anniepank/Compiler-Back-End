.text
writeint:
li $v0, 1
syscall
jr $ra
swap:
sw	$fp, -20($sp)
move	$fp, $sp
subiu	$sp, $sp, 52
sw	$a0, 40($sp)
sw	$a1, 44($sp)
sw	$a2, 48($sp)
sw	$ra, 36($sp)
sw	$s0, 0($sp)
sw	$s1, 4($sp)
sw	$s2, 8($sp)
sw	$s3, 12($sp)
sw	$s4, 16($sp)
sw	$s5, 20($sp)
sw	$s6, 24($sp)
sw	$s7, 28($sp)

subiu	$sp, $sp, 4 # local variable tmp
lw	$t0, -12($fp)
lw	$t1, -8($fp)
addi $t4, $zero, 4
mul $t1, $t1, $t4

add $t0, $t0, $t1 # get an element of array 

lw $t0, 0($t0)

sw $t0, -56($fp)
lw	$t0, -12($fp)
lw	$t1, -8($fp)
lw	$t2, -12($fp)
lw	$t3, -4($fp)
addi $t4, $zero, 4
mul $t3, $t3, $t4

add $t2, $t2, $t3 # get an element of array 

lw $t2, 0($t2)

addi $t4, $zero, 4
mul $t1, $t1, $t4

add $t1, $t0, $t1 # get an element of array 

sw $t2, 0($t1)

lw	$t0, -12($fp)
lw	$t1, -4($fp)
lw	$t2, -56($fp)
addi $t4, $zero, 4
mul $t1, $t1, $t4

add $t1, $t0, $t1 # get an element of array 

sw $t2, 0($t1)

swap_end:
lw	$s0, 4($sp)
lw	$s1, 8($sp)
lw	$s2, 12($sp)
lw	$s3, 16($sp)
lw	$s4, 20($sp)
lw	$s5, 24($sp)
lw	$s6, 28($sp)
lw	$s7, 32($sp)
lw	$ra, 40($sp)
lw	$fp, 36($sp)
addiu	$sp, $sp, 56
jr	$ra

.data
_static_var0: .space 4 # global variable a
.text
.data
_string0:.asciiz " : "
.text
.globl main
main:
li $t0, 3
li $a0, 32
li $v0, 9
syscall
la $t0, ($v0)
sw $t0,_static_var0
lw	$t0,_static_var0
li $t1, 0
li $t2, 1
addi $t3, $zero, 4
mul $t1, $t1, $t3

add $t1, $t0, $t1 # get an element of array 

sw $t2, 0($t1)

lw	$t0,_static_var0
li $t1, 1
li $t2, 2
addi $t3, $zero, 4
mul $t1, $t1, $t3

add $t1, $t0, $t1 # get an element of array 

sw $t2, 0($t1)

lw	$t0,_static_var0
li $t1, 2
li $t2, 3
addi $t3, $zero, 4
mul $t1, $t1, $t3

add $t1, $t0, $t1 # get an element of array 

sw $t2, 0($t1)

lw	$t0,_static_var0
li $t1, 1
li $t2, 2
subiu	$sp, $sp, 64
sw	$v0, 0($sp)
sw	$v1, 4($sp)
sw	$t0, 8($sp)
sw	$t1, 12($sp)
sw	$t2, 16($sp)
sw	$t3, 20($sp)
sw	$t4, 24($sp)
sw	$t5, 28($sp)
sw	$t6, 32($sp)
sw	$t7, 36($sp)
sw	$t8, 40($sp)
sw	$t9, 44($sp)
sw	$a0, 48($sp)
sw	$a1, 52($sp)
sw	$a2, 56($sp)
sw	$a3, 60($sp)
subiu	$sp, $sp, 0

move $a0, $t0
move $a1, $t1
move $a2, $t2
jal swap
addiu	$sp, $sp, 0
lw	$t0, 8($sp)
lw	$t1, 12($sp)
lw	$t2, 16($sp)
lw	$t3, 20($sp)
lw	$t4, 24($sp)
lw	$t5, 28($sp)
lw	$t6, 32($sp)
lw	$t7, 36($sp)
lw	$t8, 40($sp)
lw	$t9, 44($sp)
lw	$a0, 48($sp)
lw	$a1, 52($sp)
lw	$a2, 56($sp)
lw	$a3, 60($sp)
lw	$v0, 0($sp)
lw	$v1, 4($sp)

lw	$t0,_static_var0
li $t1, 0
addi $t3, $zero, 4
mul $t1, $t1, $t3

add $t0, $t0, $t1 # get an element of array 

lw $t0, 0($t0)

subiu	$sp, $sp, 64
sw	$v0, 0($sp)
sw	$v1, 4($sp)
sw	$t0, 8($sp)
sw	$t1, 12($sp)
sw	$t2, 16($sp)
sw	$t3, 20($sp)
sw	$t4, 24($sp)
sw	$t5, 28($sp)
sw	$t6, 32($sp)
sw	$t7, 36($sp)
sw	$t8, 40($sp)
sw	$t9, 44($sp)
sw	$a0, 48($sp)
sw	$a1, 52($sp)
sw	$a2, 56($sp)
sw	$a3, 60($sp)
subiu	$sp, $sp, 0

move $a0, $t0
jal writeint
addiu	$sp, $sp, 0
lw	$t0, 8($sp)
lw	$t1, 12($sp)
lw	$t2, 16($sp)
lw	$t3, 20($sp)
lw	$t4, 24($sp)
lw	$t5, 28($sp)
lw	$t6, 32($sp)
lw	$t7, 36($sp)
lw	$t8, 40($sp)
lw	$t9, 44($sp)
lw	$a0, 48($sp)
lw	$a1, 52($sp)
lw	$a2, 56($sp)
lw	$a3, 60($sp)
lw	$v0, 0($sp)
lw	$v1, 4($sp)

la $a0, _string0
li $v0, 4
syscall
lw	$t0,_static_var0
li $t1, 1
addi $t3, $zero, 4
mul $t1, $t1, $t3

add $t0, $t0, $t1 # get an element of array 

lw $t0, 0($t0)

subiu	$sp, $sp, 64
sw	$v0, 0($sp)
sw	$v1, 4($sp)
sw	$t0, 8($sp)
sw	$t1, 12($sp)
sw	$t2, 16($sp)
sw	$t3, 20($sp)
sw	$t4, 24($sp)
sw	$t5, 28($sp)
sw	$t6, 32($sp)
sw	$t7, 36($sp)
sw	$t8, 40($sp)
sw	$t9, 44($sp)
sw	$a0, 48($sp)
sw	$a1, 52($sp)
sw	$a2, 56($sp)
sw	$a3, 60($sp)
subiu	$sp, $sp, 0

move $a0, $t0
jal writeint
addiu	$sp, $sp, 0
lw	$t0, 8($sp)
lw	$t1, 12($sp)
lw	$t2, 16($sp)
lw	$t3, 20($sp)
lw	$t4, 24($sp)
lw	$t5, 28($sp)
lw	$t6, 32($sp)
lw	$t7, 36($sp)
lw	$t8, 40($sp)
lw	$t9, 44($sp)
lw	$a0, 48($sp)
lw	$a1, 52($sp)
lw	$a2, 56($sp)
lw	$a3, 60($sp)
lw	$v0, 0($sp)
lw	$v1, 4($sp)

la $a0, _string0
li $v0, 4
syscall
lw	$t0,_static_var0
li $t1, 2
addi $t3, $zero, 4
mul $t1, $t1, $t3

add $t0, $t0, $t1 # get an element of array 

lw $t0, 0($t0)

subiu	$sp, $sp, 64
sw	$v0, 0($sp)
sw	$v1, 4($sp)
sw	$t0, 8($sp)
sw	$t1, 12($sp)
sw	$t2, 16($sp)
sw	$t3, 20($sp)
sw	$t4, 24($sp)
sw	$t5, 28($sp)
sw	$t6, 32($sp)
sw	$t7, 36($sp)
sw	$t8, 40($sp)
sw	$t9, 44($sp)
sw	$a0, 48($sp)
sw	$a1, 52($sp)
sw	$a2, 56($sp)
sw	$a3, 60($sp)
subiu	$sp, $sp, 0

move $a0, $t0
jal writeint
addiu	$sp, $sp, 0
lw	$t0, 8($sp)
lw	$t1, 12($sp)
lw	$t2, 16($sp)
lw	$t3, 20($sp)
lw	$t4, 24($sp)
lw	$t5, 28($sp)
lw	$t6, 32($sp)
lw	$t7, 36($sp)
lw	$t8, 40($sp)
lw	$t9, 44($sp)
lw	$a0, 48($sp)
lw	$a1, 52($sp)
lw	$a2, 56($sp)
lw	$a3, 60($sp)
lw	$v0, 0($sp)
lw	$v1, 4($sp)

li $v0, 10
syscall
main_end:
