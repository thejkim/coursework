   TITLE   print random numbers, sum, count       (joeun3.asm)
;Practice Test(Lady Gaga) Q3.
;create 120 random numbers from 1 to 10 and print out
;put them in an array called 'source' and print it out
;add the numbers in source and print out the sum
;using push and pop to put source into an array called 'backsource' and print out
;count how many of each number occurred and put the result in an array called 'count'
INCLUDE Irvine32.inc
.data
blank byte " ", 0
sum byte "The sum of the source array is ",0
line byte "*****************************************",0
source dword 120 dup(0)
backsource dword lengthof source dup(0)
count byte 10 dup(0)

.code
main PROC
	call randomize
	mov edx, offset blank
	mov ebx, 0 ; for later use to accumulate each number.
	mov ecx,120 ; create 120 random numbers.
	mov esi, 0 ; esi: 0~9. the counter starts from the first element of array, [0].
	mov edi, 0 ; for counting
	printnum:
		mov eax, 10 ;1~10 : 10 numbers
		call randomrange
		add eax, 1 ;1~10, not 0~10.
		mov source[esi], eax ;set each random number into array called 'source'
		call writedec ;print out the source
		
		mov edi, eax
		inc count[edi]
		
		add ebx, eax ;accumulate each added eax value and store in ebx
		call writestring
		add esi, 4 ;because of type dword
	loop printnum
	call crlf

	;print a line to show clear.
	call lines

	;;print the sum of numbers in the array 'source'
	push edx
	mov edx, offset sum
	call writestring
	mov eax, ebx
	call writedec
	pop edx

	call crlf
	call lines

	;;use pish and pop to put source into an array called 'backsource'
	;;push each eax in array 'source'
	mov ecx, 120
	mov esi, 0
	pushNum:
		mov eax, source[esi]
		push eax
		add esi, 4
	loop pushNum

	;;pop each eax so that I can store it backward in array 'backsource'
	mov ecx, 120
	mov esi, 0
	popBackward:
		pop eax
		mov backsource[esi], eax
		add esi, 4
	loop popBackward

	;;print the result, the array 'backsource'
	mov ecx, 120
	mov esi, 0
	printBacksource:
		mov eax, backsource[esi]
		call writedec
		call writestring
		add esi, 4
	loop printBacksource
	call crlf

	;;count hou many of each number occurred 
	;;and put the result in array called 'count'
	mov ebx, 10 ;check those 10 different numbers in 120 random numbers. 
	mov ecx, ebx ;do the loop ebx times
	mov edi, 1
	countLoop:
		mov eax, ebx ;eax is now to show which number is counted.
		sub eax, ecx ;eax-ecx, which is decreasing.
		add eax, 1 ;1~10, not 0~9.
		call writedec
		call writestring
		call writestring
		call writestring
		movzx eax, count[edi] ;eax is now to print counting number.
		call writedec
		call crlf
		inc edi
	loop countLoop

 exit
	 
main ENDP

lines PROC
	push edx
	mov edx, offset line
	call writestring
	pop edx
	call crlf
	ret
lines ENDP

end main