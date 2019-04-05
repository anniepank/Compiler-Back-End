.text
writeint:
li $v0, 1
syscall
jr $ra
.globl main
main:
li $t0, 1
li $t1, 2
li $t2, 5
mul $t3, $t1, $t0

div $t2, $t3
mflo $t3
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

move $a0, $t3
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

li $a0, 4
li $v0, 9
syscall
la $t4, ($v0)
laWhile:
move $t3, $zero
lw $t5, 0($t4)
beq $t5,$zero,endLaWh
addi $t3, $zero, 1
addi $t4 ,$t4, 4
j   laWhile
endLaWh:

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

move $a0, $t3
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

div $t2, $t1
mfhi $t3
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

move $a0, $t3
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

lw $t1, 0($sp)

lw $t5, 0($sp)

seq $t3, $t1, $t5

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

move $a0, $t3
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
