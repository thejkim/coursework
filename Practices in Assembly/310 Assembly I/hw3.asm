   TITLE hw3 values      (hw3.asm)                     (special.asm)
 
;Last updated Oct 6, 2017 Written by Jo Eun Kim, Muhammad H. Kardar, Fatematuz zohora
INCLUDE Irvine32.inc
.data
    array byte 1,2,3,4,5,6,7,29
    target byte lengthof array dup(0)
    btarget byte lengthof array dup(0)
	addition byte " + ",0    
	equal byte " = ", 0

dash byte "=====================================",0
.code
main PROC
     mov ecx,8
     mov esi, 0
     mov eax, 0
     myloop:
        mov al, array[esi]
        mov target[esi], al

        call writedec
	

        call crlf
        inc esi

     loop myloop 
mov ecx, lengthof target
mov esi,0
mov eax, 0
Printd:
movzx eax, target[esi]
call writedec
call crlf
inc esi
loop Printd
    

mov ecx, 8
mov esi,0
mov edi, lengthof array
dec edi
mov eax, 0
bloop:
mov al, array[esi]
        mov btarget[edi], al
inc esi

dec edi
loop bloop              

mov ecx, lengthof target ; end
mov esi,0 ; 
mov eax, 0 ; where we start
Print:
movzx eax, btarget[esi]
call writedec
call crlf
inc esi
loop Print


mov ecx,lengthof array - 1
mov esi,0
mov eax,0
mov edx, offset addition
yourlooop:
mov al,array[esi]
call writedec
call writestring
add bl,al
inc esi
loop yourlooop

mov al, array[esi]
call writedec
add al,bl
mov edx,offset equal
call writestring
call writedec

        exit
	 
main ENDP
 
end main

