TITLE printing initials and move it FINISH        (JoEunKimTakeHomeQ1F.asm)
  
INCLUDE Irvine32.inc
;print picture no procedure
.data  
 array1 byte 1,' ',1,'*',1,' ',2,'*',3,' ',3,'*',1,' '
 array2 byte 1,' ',2,'*',2,' ',1,'*',1,' ',1,'*',3,' ',1,'*',1,' '
 array3 byte 1,' ',1,'*',5,' ',1,'*',3,' ',1,'*',1,' '
 array4 byte 1,' ',1,'*',5,' ',1,'*',3,' ',1,'*',1,' '
 array5 byte 1,' ',1,'*',6,' ',3,'*',1,' '

 ;------------------------------
 ; we wish to output
 ;    * **   ***
 ;    **  * *   *
 ;    *     *   *
 ;    *     *   *
 ;    *      ***
 ;------------------------------
 delta dword ?
 l dword ?
 lenarray1 = lengthof array1
 lenarray2 = lengthof array2
 lenarray3 = lengthof array3
 lenarray4 = lengthof array4
 lenarray5 = lengthof array5
 grandarray dword 10  dup(?) 

 movementDistance byte 15

 .code
main proc
 call clrscr
  
;---------------------------
; fill grandarray using the procedure fill
  call fill

  ;move it from (5,10) to (20,10)
  ; Initial Movement Setup
  mov ecx, 15 ; distance to move : 15 bcz 5 --> 20
  mov bh, 10 ; (y) starting row (for this project, should stay the same because the initial moves horizontally)
  mov bl, 5 ; (x) starting column (for this project, should increase to move to the right as iterating the printing loops)

  ; Moving to the right: RED
  mov eax, lightred+16*black
  call setTextColor

movement:

  mov dl, bl ; set starting column (for each frame)
  mov dh, bh ; set starting row (for each frame)
  call gotoxy ; move the cursor to the x,y position, specified by the EDX register (x: dl, y : dh)
  push ecx

  ;---------------------------
   mov esi,offset grandarray
   sub esi,4   ; we start a dword back
   mov ecx,5

   outer:
     push ecx
     ; get each array from grandarry data
     add esi,4
     mov edi, [esi]
     add esi, 4
     mov ecx, [esi]
     mov l,ecx
     mov ecx, l
     
     inner:
       push ecx     ;use it later from each line to set up  
       mov ecx,0
       mov   cl, [edi]
        
       inc edi
       mov al,[edi]
       call drawline
       inc edi
        
       pop ecx
     loop inner
     inc dh
  
     mov dl, bl
     pop ecx
     call gotoxy
    loop outer

    inc bl ; increase the position of the starting colum (moving to the right)
    pop ecx
  loop movement

; Moving to the left: BLUE
mov eax, lightblue+16*black
call setTextColor

mov ecx, 15
movementL:
   
  mov dl, bl ; set starting column (for each frame)
  mov dh, bh ; set starting row (for each frame)
  call gotoxy ; move the cursor to the x,y position, specified by the EDX register (x: dl, y : dh)
  push ecx

  ;---------------------------
   mov esi,offset grandarray
   sub esi,4   ; we start a dword back
   mov ecx,5

   outerL:
     push ecx
     ; get each array from grandarry data
     add esi,4
     mov edi, [esi]
     add esi, 4
     mov ecx, [esi]
     mov l,ecx
     mov ecx, l
     
     innerL:
       push ecx     ;use data from each line to set up  
       mov ecx,0
       mov   cl, [edi]
        
       inc edi
       mov al,[edi]
       call drawline
       inc edi
        
       pop ecx
     loop innerL
     inc dh
      
     mov dl, bl
     pop ecx
     call gotoxy
    loop outerL

    dec bl ; increase the position of the starting colum (moving to the right)
    pop ecx
  loop movementL

  ;change color to normal setting after all. 
  mov eax, white+16*black
  call setTextColor

  exit  
     
main ENDP
drawline proc
     
    lineloop:
       call writechar
       inc dl
    loop lineloop
      mov eax,1
      call delay
      ret

      ;mov eax, 50
      ;call delay
      ;ret

 drawline endp

fill proc
  mov esi,offset grandarray
  mov [esi],offset array1
  add esi,4
  mov ebx, lenarray1/2
  mov [esi],ebx
;======================================
  add esi,4
  mov [esi],  offset array2
  add esi,4
  mov ebx, lenarray2/2
  mov [esi],ebx
;====================================
  add esi,4
  mov [esi],   offset array3
  add esi,4
  mov ebx, lenarray3/2
  mov [esi],ebx
;====================================
  add esi,4
  mov [esi],offset array4
  add esi,4
  mov ebx, lenarray4/2
  mov [esi],ebx
;====================================
  add esi,4
  mov [esi],   offset array5
  add esi,4
  mov ebx, lenarray5/2
  mov [esi],ebx
;====================================
  ret

fill endp
 END main