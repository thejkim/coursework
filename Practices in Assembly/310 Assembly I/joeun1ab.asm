   TITLE   Q1-a & b FINISH        (joeun1ab.asm)
;Sub question a and b of question #1.
;Written by Jo Eun Kim

INCLUDE Irvine32.inc
.data
	A dword 2 ;starting col point
	B dword 9 ;ending point
	Y dword 3 ;height of the line
	startRow byte 0 ;starting row point
	char byte '*'
.code
main PROC
	call Clrscr
	mov eax, lightred+16*black
	call setTextColor

	mov edx, A ; set starting column (for each frame)
	movzx ebx, startRow ; set starting row
    mov dh, bl ; set starting row (for each frame)
    call gotoxy

	movzx eax, char

	mov ecx, Y
	rowLoop:
		push ecx
		cmp ecx, Y
		jne goDrawback
		
		call drawline
		jmp nextRow

		goDrawback:
			call drawback

		nextRow:

		pop ecx
	loop rowLoop

	mov eax, white+16*black
	call setTextColor
 
 exit
	 
main ENDP

drawline PROC
	mov ecx, B
	sub ecx, A
	colLoop:
		call writechar
		push eax
		mov eax, 50
		call delay
		pop eax
	loop colLoop
	
	call crlf
	;mov edx, A ; set starting column (for each frame)
	;inc ebx ; row + 1
	;mov dh, bl ; next (increased) row
	;call gotoxy
	ret
drawline ENDP

drawback PROC
	;mov ecx, B
	;sub ecx, A
	mov edx, B
	sub edx, 1
	inc ebx ; row + 1
	mov dh, bl ; next (increased) row
	call gotoxy

	mov ecx, B
	sub ecx, A
	colLoop:
		call writechar
		push eax
		mov eax, 50
		call delay
		pop eax
		dec dl
		call gotoxy
	loop colLoop
	ret
drawback ENDP

end main