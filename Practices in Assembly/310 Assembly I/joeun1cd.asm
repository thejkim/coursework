   TITLE   Q1-c & d FINISH       (joeun1cd.asm)
;c. Draw 10 lines like part b, one under another.
;Use random colors for the forward line.
;wind up with a blank screen.
INCLUDE Irvine32.inc
.data
	A dword 2 ;starting col point
	B dword 9 ;ending point
	Y dword 10 ;height of the line
	startRow byte 0 ;starting row point
	char byte '*'
.code
main PROC
	call Clrscr

	call randomize

	;get random color digit
		mov eax, 15
		call randomrange
		add eax, 1 ;(1~15, not 0~14)
		mov esi, eax ;store the random digit into esi for later use


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
 	call setTextColor ; reset color change
 	call Clrscr ; to wind up with a blank screen
 exit
	 
main ENDP

drawline PROC
	mov ecx, B
	sub ecx, A
	add ecx, 1
	colLoop:
			;
			push ecx
			cmp ecx, 8 ;first '*' in the loop should be colored.
			jne whiteColor

			push eax
			mov eax, esi ; eax <= esi(random color digit)
			call setTextColor
			pop eax
			call writechar
			jmp nextChar

			whiteColor:
			push eax
			mov eax, white+16*black
			call setTextColor
			pop eax
			call writechar
			;
		;call writechar
		push eax
		mov eax, 50
		call delay
		pop eax

			nextChar:
			pop ecx
	loop colLoop
	
	call crlf
	ret
drawline ENDP

drawback PROC
	mov edx, B
	sub edx, 1
	inc ebx ; row + 1
	mov dh, bl ; next (increased) row
	call gotoxy

	mov ecx, B
	sub ecx, A
	colLoop:
			;
			push ecx
			cmp ecx, 1 ;last '*' in the loop should be colored.
			jne whiteColor

			push eax
			mov eax, esi
			call setTextColor
			pop eax
			call writechar
			jmp nextCharB

			whiteColor:
			push eax
			mov eax, white+16*black
			call setTextColor
			pop eax
			call writechar
			;

		call writechar
		push eax
		mov eax, 50
		call delay
		pop eax
		dec dl
		call gotoxy

			nextCharB:
			pop ecx
	loop colLoop
	ret
drawback ENDP

end main