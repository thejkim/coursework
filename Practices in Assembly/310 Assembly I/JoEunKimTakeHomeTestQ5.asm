   TITLE multiplyingNum      (JoEunKimTakeHomeTestQ5.asm)

INCLUDE Irvine32.inc

.data
	multiplySign byte "*",0
	equalSign byte "=",0
.code
main PROC

	mov eax, 3 ;number that will be multiplied.
	mov ebx, 6 ;times.
	; the multiplication result will be stored in EBX
	push ebx ; save for later use to print the original value (6 for this case)
	
	call writedec
	mov edx, offset multiplySign
	call writestring

	call multiply ; multiply EAX and EBX, and store the result in EBX

	pop eax

	call writedec
	mov edx, offset equalSign
	call writestring

	mov eax, ebx ;eax becomes the accumulated value in ebx.
	call writedec

exit
main ENDP

;; Method (procedure) to multiply two numbers
multiply PROC
	mov ecx, ebx ;to make a loop ebx times
	mov ebx, 0 ;make ebx empty to store added eax value.
	myloop:
		add ebx, eax ;accumulate added eax into ebx. 
	loop myloop
	ret
multiply ENDP

END main
