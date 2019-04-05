.text
writeint:
li $v0, 1
syscall
jr $ra
.globl main
main:
.data
_static_var0: .space 5 # five
.text
li $t1, 5
sw $t1,_static_var0
la $t0,_static_var0
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
