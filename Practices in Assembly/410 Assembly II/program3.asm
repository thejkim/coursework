TITLE program3	(program3.asm)
;Date: May 10, 2018
;Name: Jo Eun Kim

INCLUDE Irvine32.inc

.data
	promptForMonth byte "Enter a month: ", 0
	promptForName byte "Enter your name: ", 0

	inputBuffer byte 25 dup(0)
	
	spaceForName byte 25 dup(0)
	spaceForMonth byte 25 dup(0)

	messageForName byte "Hello, ", 0
	messageForMomth byte ". The month you entered is ", 0

.code

; char* strqry(char* prompt)
; parameter(by stack)
;	1st: pointer to prompt string
; return(by register)
;	eax: pointer to string inputBuffer
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


; char* queryForMonth()
; parameter: none
; return(by register)
;	eax: pointer to string inputBuffer (any type of month format)
; 		ex) "01", "Jan", "January"
; This procedure does not validate user input format.
queryForMonth PROC
	push ebp
	mov ebp, esp
	push edx
	mov edx, offset promptForMonth

	push edx
	call strqry

	pop edx
	pop ebp

	ret
queryForMonth ENDP

; char* queryForName()
; parameter: none
; return(by register)
;	eax: pointer to string inputBuffer (user's name)
queryForName PROC
	push ebp
	mov ebp, esp
	push edx

	mov edx, offset promptForName

	push edx
	call strqry

	pop edx
	pop ebp

	ret
queryForName ENDP

main PROC
	call queryForName

	push eax
	mov eax, offset spaceForName
	push eax
	call strcpy

	call queryForMonth
	
	push eax
	mov eax, offset spaceForMonth
	push eax
	call strcpy
	
	mov edx, offset messageForName
	call writeString

	mov edx, offset spaceForName
	call writeString

	mov edx, offset messageForMomth
	call writeString
	
	mov edx, offset spaceForMonth
	call writeString

 	exit 
main ENDP

	end main