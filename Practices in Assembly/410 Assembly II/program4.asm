TITLE program4	(program4.asm)
;Date: March 10, 2018
;Name: Jo Eun Kim

INCLUDE Irvine32.inc

.data
	prompt byte "What is your name? ", 0
	inputBuffer byte 25 dup(0)
	message1 byte "The string ", 0
	message2 byte " has ", 0
	message3 byte " characters.",0

.code

; ask the user to enter string
; return in EAX
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

; determine string length
; long strlen(char* inputBuffer)
; return in EAX
strlen PROC
	; create own stack
	push ebp
	mov ebp, esp

	; save values in the registers that this procedure uses
	push ecx
	push ebx

	; initialize registers with data
	mov ecx, [ebp + 8]
	mov eax, 0

	toploop:
		mov bh, [ecx + eax]
		cmp bh, 0
		je endloop
		inc eax
		jmp toploop
	endloop:

	; restore saved values of the registers that this procedure used
	pop ebx
	pop ecx
	pop ebp

	ret 4 
strlen ENDP

main PROC
	mov edx, offset prompt
	push edx ; procedure argument
	call strqry ; retun in EAX

	mov edx, offset message1
	call writeString

	mov edx, eax
	call writeString

	mov edx, offset message2
	call writeString

	mov edx, eax ;save the address of user input into edx
	push edx ; procedure argument
	call strlen ; return in EAX
	
	call writedec

	mov edx, offset message3
	call writeString

 	exit 
main ENDP

	end main