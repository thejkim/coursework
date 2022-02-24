   TITLE   birthdayhw       (birthdayhw.asm)                     (special.asm)
 
;This  
;Last updated 10.17.2017 Written by Jo Eun Kim
INCLUDE Irvine32.inc

.data
	blank byte "   ", 0
	count byte 365 dup(0)

.code
main PROC
	call randomize
	mov edx, offset blank
	
	mov ecx,40
	printnum:
	   mov eax, 365
	   call randomrange
	   add eax, 1
	   call writedec
	   mov esi, eax
	   inc count[esi]
	   call writestring
	loop printnum
	call crlf

	mov ebx, 365
	mov ecx, ebx
	mov esi, 1
	myloop:
	   mov eax, ebx
	   sub eax, ecx
	   add eax, 1
	   call writedec
	   call writestring
	   movzx EAX, count[esi]
	   call writedec
	   inc esi
	   call crlf
	loop myloop
	

	
    
 exit
	 
main ENDP
 
end main



