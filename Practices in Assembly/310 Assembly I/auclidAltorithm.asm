   TITLE   euclid's algorithm using divBySub 5 WORKS FINAL      (euclidAlgorithmVer2.asm)
;Special Problems Q4.
;Create a division prodecure using subtraction,
;Apply it to Euclid's algorithm. So gcf = ?
;Jo Eun Kim
INCLUDE Irvine32.inc
.data
	dividend dword 231
	divisor dword 141
	quotient byte "GCF = ",0 ;Greatest common factor
.code
main proc
	call Clrscr
	mov eax, dividend
	mov ebx, divisor
	
	come1:
	call divBySub
	.if(eax!=0) ;keep division until remainder(eax)=0.
		mov esi, 0 ;reset the previous count of subtraction.
		;;set the new dividend and divisor to prepare the next division proc.
		mov edi, ebx ;edi stores ebx which is the previous divisor to use ebx register newly
		mov ebx, eax ;set the next divisor 'ebx' as the previous remainder(which is in eax)
		mov eax, edi ;set the next dividend as the previous divisor(which was in ebx but now edi stores its value)
		jmp come1 ;do the next subtraction until the remainder(eax) = 0. 
	.endif ;if eax=0
	call printOut ;print the result out.

  exit
main ENDP

divBySub proc
come2:
	inc esi ;count how many subtractions are done => quotient.
	sub eax, ebx ;subtract divisor(ebx) from dividend(eax) 
	.if (eax<ebx) ;if eax < ebx, stop the subtraction.
		jmp end2 ;here eax becomes remainder
	.endif ;if eax >= ebx
		jmp come2 ;jump to do more subtraction.
	end2:
	ret
divBySub ENDP

printOut proc
	mov edx, offset quotient
	call writestring
	;According to Euclid's algorithm, 
	;the latest divisor that remains 0 is the gcf.
	mov eax, ebx ;set eax as the latest divisor(ebx)
	call writedec ;print out the divisor
	ret
printOut ENDP

END main
