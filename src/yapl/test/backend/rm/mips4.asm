.text
writeint:
li $v0, 1
move $a0, $t0
syscall
jr $ra
.data
_string0:.asciiz "True"
.text
.data
_string1:.asciiz "False"
.text
.data
_string2:.asciiz " : "
.text
writeboolean:
sw	$fp, -12($sp)
move	$fp, $sp
sub	$sp, $sp, 44
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
beq	$t0, $zero, L1
move $t0, $zero

la $a0, _string0
li $v0, 4
syscall
j	writeboolean_end
L1: # write 'False'
la $a0, _string1
li $v0, 4
syscall
writeboolean_end:
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
add	$sp, $sp, 44
jr	$ra

.globl main
main:
sub	$sp, $sp, 64
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
sub	$sp, $sp, 0

li $t0, 1
move $a0, $t0
move $t0, $zero

jal writeboolean
add	$sp, $sp, 0
lw	$v0, 0($sp)
lw	$v1, 4($sp)
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

la $a0, _string2
li $v0, 4
syscall
sub	$sp, $sp, 64
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
sub	$sp, $sp, 0

li $t0, 0
move $a0, $t0
move $t0, $zero

jal writeboolean
add	$sp, $sp, 0
lw	$v0, 0($sp)
lw	$v1, 4($sp)
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

li $v0, 10
syscall
main_end:
