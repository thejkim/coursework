   TITLE   100 random numbers using array and print         (JoEunKimTakeHomeQ3.asm)

INCLUDE Irvine32.inc
.data
blank byte " ", 0
array dword 21 dup(0)
target dword lengthof array dup(0)
.code
main PROC
	call randomize
	mov edx, offset blank
	
	mov ecx,100 ; for creating 100 random numbers.
	mov esi, 0 ; esi: 0~9. the counter starts from the first element of array, [0].
	printnum:
		mov eax, 21 ;-10~10 : 21 numbers
		call randomrange
		sub eax, 10 ;to include negative int.

		mov array[esi], eax ;set each random number into array
		mov eax, array[esi] ;prepare to print out
		neg eax ;to allow negative.
		call writeInt 
		call writestring
		inc esi
	loop printnum
	call crlf

	;;print the array backward.
	mov ecx, 100
	mov esi, ecx ;esi starts counting backward
	sub esi, 1 ; esi: 0~99, not 100
	printBack:
		mov eax, array[esi] ;store each value from the last in array into eax.
		neg eax ;to allow negative
		call writeInt
		call writestring
		dec esi ;array[last] --> array[first]
	loop printBack
	call crlf 

	;;copy the array to an array called target
	mov ecx, 100
	mov esi, 0
	copyArray:
		mov eax, array[esi]
		neg eax
		mov target[esi], eax
		inc esi
	loop copyArray
	
	;;print the target
	mov ecx, 100
	mov esi, 0
	print:
		mov eax, target[esi]
		call writeInt
		call writestring
		inc esi
	loop print

 exit
	 
main ENDP
 
end main