.text
writeint:
li $v0, 1
syscall
jr $ra
.globl main
main:
.data
_string0:.asciiz "Hello world!"
.text
la $a0, _string0
li $v0, 4
syscall
li $v0, 10
syscall
main_end:
