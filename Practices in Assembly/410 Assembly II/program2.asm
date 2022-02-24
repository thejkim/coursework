TITLE program2	(program2.asm)
;Date: Feb 26, 2018
;Name: Jo Eun Kim

INCLUDE Irvine32.inc

.data
	prompt byte "What is your name? ", 0
	inputBuffer byte 30 dup(0)
	message byte "Hello, ", 0

.code

strqry PROC
	push ebp
	mov ebp, esp
	push ecx
	push edx

	mov edx, [ebp + 8] ;point the address of pormpt
	call writeString

	mov edx, offset inputBuffer
	mov ecx, sizeOf inputBuffer
	call readString

	mov eax, offset inputBuffer

	pop edx
	pop ecx
	pop ebp

	ret	4
strqry ENDP

main PROC
	mov edx, offset prompt
	push edx
	call strqry

	mov edx, eax ;save the address of user input into edx
	push edx

	mov edx, offset message
	call writeString
	pop edx ;bring out the saved address of the user input.
	call writeString

 	exit 
main ENDP

	end main