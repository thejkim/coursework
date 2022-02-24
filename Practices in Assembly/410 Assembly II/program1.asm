TITLE program1	(program1.asm)
;Date: Feb 14, 2018
;Name: Jo Eun Kim

INCLUDE Irvine32.inc

.data

prompt byte	"What is your name? ", 0
MAX_INPUT_LEN = 30
inputBuffer	byte MAX_INPUT_LEN+1 dup(0)
message	byte "Hello, ", 0

.code
	
Main PROC

	mov	edx, offset prompt
	call writeString

	mov	edx, offset inputBuffer
	mov ecx, MAX_INPUT_LEN
	call readString

	mov edx, offset message
	call writeString

	mov edx, offset inputBuffer
	call writeString

 	exit
 
main ENDP

	end	main
