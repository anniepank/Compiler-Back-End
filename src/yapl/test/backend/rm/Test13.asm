.text
writeint:
li $v0, 1
syscall
jr $ra
proc:
sw	$fp, -12($sp)
move	$fp, $sp
subiu	$sp, $sp, 44
sw	$a0, 40($sp)
sw	$ra, 36($sp)
sw	$s0, 0($sp)
sw	$s1, 4($sp)
sw	$s2, 8($sp)
sw	$s3, 12($sp)
sw	$s4, 16($sp)
sw	$s5, 20($sp)
sw	$s6, 24($sp)
sw	$s7, 28($sp)

lw	$t0, -4($fp)
lw	$t1, 0($fp)
addu $t0, $t0, $t1
lw	$t1, 4($fp)
addu $t0, $t0, $t1
lw	$t1, 8($fp)
addu $t0, $t0, $t1
lw	$t1, 12($fp)
addu $t0, $t0, $t1
move	$v0, $t0
j	proc_end

proc_end:
lw	$s0, 0($sp)
lw	$s1, 4($sp)
lw	$s2, 8($sp)
lw	$s3, 12($sp)
lw	$s4, 16($sp)
lw	$s5, 20($sp)
lw	$s6, 24($sp)
lw	$s7, 28($sp)
lw	$ra, 36($sp)
lw	$fp, 32($sp)
addiu	$sp, $sp, 44
jr	$ra

.globl main
main:
subiu	$sp, $sp, 4
la	$t0, 0($fp)
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
subiu	$sp, $sp, 4

move $a0, $t0
move $a1, $t0
move $a2, $t0
move $a3, $t0
sw	$t0, 0($sp)
jal proc
addiu	$sp, $sp, 4
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
move $t0, $v0
lw	$v0, 0($sp)
lw	$v1, 4($sp)

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
