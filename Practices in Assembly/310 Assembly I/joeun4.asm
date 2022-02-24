   TITLE   count Letter FINISH       (joeun4.asm)
;Quenstion #4. written by Jo Eun Kim
;create 4 strings and print each one out using a procedure.
;count a-z and print out the result.
;count words.
INCLUDE Irvine32.inc
.data
	;********************************
	; ASCII CODE
	;   space: 32 (0x20)
	;   ,: 44 (0x2C)
	;   .: 46 (0x2E)
	;   A-Z: 65-90 (0x41-0x5A)
	;   a-z: 97-122 (0x61-0x7A)
	;********************************
	string1 byte 'Cotton on the roadside, cotton in the ditch.'
	string2 byte 'We all picked the cotton but we never got rich.'
	string3 byte 'Daddy was a veteran, a southern democrat.'
	string4 byte 'They oughta get a rich man to vote like that.'
	grandarray dword 8 dup(?)
	count byte 27 dup(0) ; [0-25]Alphabet / [26] ' '
	wordCountLabel byte "words:",0
.code
main PROC
	call fill
	mov edx, offset wordCountLabel

	;outer loop to read all lines (ecx=4 bcz 4 strings)
	mov eax, lengthof grandarray
	mov bl, 2
	div bl
	mov ecx, eax
	mov esi, 0

	readInStringArrays:
		push ecx
		; sting array pointer
		mov ebx, grandarray[esi]
		add esi, 4
		; string array length
		mov ecx, grandarray[esi]
		add esi, 4

		printLineAndCount:
			; the array EBX is pointing is a byte array
			; copy the byte value into a byte register (al)
			call printStrings
			;**************************************************************
			; This logic works ONLY under the assumption that
			; the input text contains ONLY [a-z / A-Z / ' ' / ',' / '.'].
			; but ',' / '.' would not be counted.
			;**************************************************************
			; in case of a-z (97-122)
			cmp eax, 97
			jae countLowerAtoZ ; if above or equal

			; in case of A-Z (65-90)
			cmp eax, 65
			jae countUpperAtoZ ; if above or equal
			
			; in case of space (32)
			cmp eax, 32
			je countSpace ; if equal
			
			; in case of , (44)
			cmp eax, 44
			je countComma ; if equal
			
			; in case of . (46)
			cmp eax, 46
			je countPeriod ; if equal

			;lower and upper case of a-z are counted as the same.
			countLowerAtoZ: ; in case of a-z
			sub eax, 97
			mov edi, eax ; make edi range between 0 and 25
			inc count[edi]
			jmp nextChar

			countUpperAtoZ: ; in case of A-Z
			sub eax, 65
			mov edi, eax ; make edi range between 0 and 25
			inc count[edi]
			jmp nextChar
			
			countSpace: ; in case of space
			inc count[26]
			jmp nextChar

			;, and . does not have to be counted
			countComma: ; in case of ,
			jmp nextChar

			countPeriod: ; in case of .
			jmp nextChar

			nextChar: ; read next char
			inc ebx
		loop printLineAndCount

		call crlf
		pop ecx
	loop readInStringArrays
	call crlf
	;*************************************
	; Printing character count result
	;*************************************
	;; NEED CLEANING UP!!!
	mov eax, 0
	mov ebx, 97
	mov ecx, lengthof count-1 ;count a-z
	mov edi, 0

	printCount:
		cmp ebx, 123
		jne printAtoZ

		mov eax, 32
		jmp printEax

		printAtoZ:
		mov eax, ebx

		printEax:
		call writechar
		mov eax, ':'
		call writechar
		movzx eax, count[edi]
		call writedec
		mov eax, ' '
		call writechar
		inc ebx
		inc edi
	loop printCount
		call crlf
		;********************
		; Word counting
		;********************	
		call writestring ; "words:"
		movzx eax, count[edi] ;count space
		;number of words
		;	= number of space + number of lines
		mov ebx, lengthof grandarray/2 ;number of lines
		add eax, ebx ; # space + # lines
		call writedec

	exit
main ENDP

printStrings PROC
	mov al, [ebx]
	call writechar
	ret
printStrings ENDP

fill proc
; dword array will be of the form 
  mov esi, offset grandarray
  mov [esi], offset string1
  add esi, 4
  mov ebx, lengthof string1
  mov [esi], ebx
;======================================
  add esi, 4
  mov [esi], offset string2
  add esi, 4
  mov ebx, lengthof string2
  mov [esi], ebx
;====================================
  add esi, 4
  mov [esi], offset string3
  add esi, 4
  mov ebx, lengthof string3
  mov [esi], ebx
;====================================
  add esi, 4
  mov [esi], offset string4
  add esi, 4
  mov ebx, lengthof string4
  mov [esi], ebx
;====================================
  ret
fill endp

end main