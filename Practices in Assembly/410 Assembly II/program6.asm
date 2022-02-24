TITLE program6	(program6.asm)
;Date: May 1, 2018
;Name: Jo Eun Kim

INCLUDE Irvine32.inc

.data
	prompt byte "Enter any string: ", 0
	inputBuffer byte 25 dup(0)
	message1 byte " is reversed to ", 0

.code

; SWAP edx + ebx, edx + ecx
SWAP MACRO c1, c2
	push eax

	mov ah, [c1]
	mov al, [c2]

	xor ah, al
	xor al, ah
	xor ah, al  

	mov [c1], ah
	mov [c2], al

	pop eax

	ENDM

; reverse the string / no return
strrev PROC
	push ebp
	mov ebp, esp
	push eax
	push ecx
	push edx

	mov edx, [ebp + 8] ; input string address

	push edx
	call strlen ; return in EAX

	; ecx = str length
	mov ecx, eax
	dec ecx
	mov ebx, 0

	beginLoop:
		cmp ebx, ecx
		jge endLoop
		SWAP edx + ebx, edx + ecx
		inc ebx
		dec ecx
		jmp beginLoop
	endLoop:

	pop edx
	pop ecx
	pop eax
	pop ebp

	ret 4
strrev ENDP

; ask user to enter a string.
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
	push edx
	call strqry

	mov edx, offset inputBuffer
	call writeString
	push edx

	mov edx, offset message1
	call writeString

	pop edx

	push EDX
	call strrev ; reverse the string / no return
	; print string in EDX
	call writeString

 	exit 
main ENDP

	end main