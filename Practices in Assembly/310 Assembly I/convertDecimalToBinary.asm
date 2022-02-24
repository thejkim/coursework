   TITLE   convert decimal to binary        (joeunFinal3.asm)
;Change decimal(any typed in dword) to binary using shifts.
;Jo Eun Kim
INCLUDE Irvine32.inc
.data
	number dword ?
	input byte "Type a decimal: ",0
	space byte " ",0
.code
main proc
	call Clrscr
	mov edx, offset input
	call writestring
	call readInt
	mov number, eax
	mov eax, number
	mov esi, 32
	mov ecx, 32
	mov ebx, eax
	start:
		xor eax, eax
		shl ebx, 1
		adc eax, 0
		call writedec
		
		push eax
		push ecx
		push ebx
		sub ecx, 1
		mov eax, ecx
		mov bl, 4
		div bl
		.if (ah==0)
			mov edx, offset space
			call writestring
		.endif
		pop ebx
		pop ecx
		pop eax
	loop start
	call crlf

  exit
main ENDP
END main
