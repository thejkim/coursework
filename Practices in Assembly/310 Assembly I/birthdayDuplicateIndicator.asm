   TITLE   birthday duplicate      (joeunFinal6.asm)                     (special.asm)
;Special Problem Q6.
;a)Have the birthday problem print out duplicate in yellow and stop.
;Jo Eun Kim
INCLUDE Irvine32.inc
.data
	blank byte " ", 0
	count byte 365 dup(0)
	array dword ?

.code
main PROC
	call Clrscr
	call randomize
	mov edx, offset blank
	mov esi, 0
	mov edi, 0
	mov ecx,40
	printnum:
	   call random
	   push eax
	   movzx eax, count[esi]
	   .if(eax==2)
	   		jmp stop
	   .endif
	loop printnum
	stop:

	push eax
	mov eax, edi
	mov bl, 4
	div bl
	mov ecx, eax
	pop eax
	mov eax, 0
	pop ebx
	mov esi, 0

	copyprintWithColor:
		call printOut
	loop copyprintWithColor

 exit
	 
main ENDP
 

random proc
	   mov eax, 365
	   call randomrange
	   add eax, 1

	   mov array[edi],eax
	   add edi, 4

	   mov esi, eax
	   inc count[esi]
	stop:
	ret
random ENDP

printOut proc
mov eax, array[esi]
	.if(eax==ebx)
		push eax
		mov eax, yellow + 16*black
		call settextcolor
		pop eax
		add esi, 4
		jmp there
	.endif
	add esi, 4
	push eax
	mov eax, white + 16*black
	call settextcolor
	pop eax

	there:
	call writedec
	call writestring

	mov eax, white + 16*black
	call settextcolor
ret
printOut ENDP
end main



