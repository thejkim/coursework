TITLE program5	(program5.asm)
;Date: April 29, 2018
;Name: Jo Eun Kim

INCLUDE Irvine32.inc

.data
	string byte "JoEun", 0
	stringCopy byte 30 dup(0)

.code

strcpy PROC
	push ebp
	mov ebp, esp

	push eax
	push ebx
	push ecx
	push edx

	mov eax, [ebp + 12] ; original string pointer
	mov ebx, [ebp + 8] ; copied string pointer

	topLoop:
		mov cl, [eax]
		mov [ebx], cl
		inc eax
		inc ebx
		cmp cl, 0
		jne topLoop

	pop edx
	pop ecx
	pop ebx
	pop eax
	pop ebp

	ret 8
strcpy ENDP


main PROC
	mov edx, offset string
	call writeString
	mov ecx, offset stringCopy

	push edx
	push ecx
	call strcpy

	mov edx, offset stringCopy
	; print string in EDX
	call crlf
	call writeString

 	exit 
main ENDP

	end main