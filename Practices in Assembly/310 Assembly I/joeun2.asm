 TITLE move big alphabet t        (joeun2.asm)
 ;Question #2. written by Jo Eun Kim
 ;print out t starting at 10, 10
 ;move t acress 20 spaces and then 9 spaces up
 ;make t upside down and move to coma down to where it went up 
  
INCLUDE Irvine32.inc
.data  
 array1 byte 2,' ',2,'*',2,' '
 array2 byte 2,' ',2,'*',2,' '
 array3 byte 2,' ',2,'*',2,' '
 array4 byte 2,' ',2,'*',2,' '
 array5 byte 1,' ',5,'*',2,' '
 array6 byte 1,' ',5,'*',2,' '
 array7 byte 2,' ',2,'*',2,' '
 array8 byte 2,' ',2,'*',2,' '
 array9 byte 2,' ',2,'*',2,' '
 array10 byte 2,' ',2,'*',2,' '
 array11 byte 2,' ',4,'*',2,' '
 array12 byte 3,' ',3,'*',2,' '
 array13 byte 10,' '
 ;------------------------------
 ; we wish to output
 ;     **
 ;     **
 ;     **
 ;     **
 ;    *****
 ;    *****
 ;     **
 ;     **
 ;     **
 ;     **
 ;     ****
 ;      ***
 ;------------------------------
 lenarray1 = lengthof array1
 lenarray2 = lengthof array2
 lenarray3 = lengthof array3
 lenarray4 = lengthof array4
 lenarray5 = lengthof array5
 lenarray6 = lengthof array6
 lenarray7 = lengthof array7
 lenarray8 = lengthof array8
 lenarray9 = lengthof array9
 lenarray10 = lengthof array10
 lenarray11 = lengthof array11
 lenarray12 = lengthof array12
 lenarray13 = lengthof array13
 grandarray dword 26  dup(?) 
 ; grandarray contains the the 12 offsets and the 12 lengths of the arrays
.code
main proc
  call Clrscr
  call fill
  ;;;move t 20 spaces to the right
  mov ecx, 20 ; distance to move : 20 bcz 10 --> 30
  mov bl, 10 ; (x) starting position of column
  mov bh, 10 ; (y) starting position of row
  movementRight:
    mov dl, bl ; set starting column (for each frame)
    mov dh, bh ; set starting row (for each frame)
    call gotoxy ; move the cursor to the x(dl),y(dh) position, specified by the EDX register
    push ecx
    mov esi, offset grandarray
    sub esi, 4 ; initial index at the posotion 4 bytes (dword) before the index 0
    mov ecx, 13 ; print 12 lines 
    outerR:
      push ecx
      ; get each array from grandarry data
      add esi,4
      mov edi, [esi] ; character array address
      add esi, 4
      mov ecx, [esi] ; character array length / 2
      innerR:
        push ecx
        mov ecx,0
        mov   cl, [edi] ; number of loops to iterate for the character below
        inc edi
        mov al,[edi] ; char to print
        call drawline
        inc edi
        pop ecx
      loop innerR
      inc dh
      mov dl,bl
      pop ecx
      call gotoxy
    loop outerR
    inc bl ; increase the position of the starting column (moving to the right)
    pop ecx
  loop movementRight

;;;move t 9 spaces up
mov ecx, 9 ; distance to move : 9
movementUp:
  mov dl, bl ; set starting column (for each frame)
  mov dh, bh ; set starting row (for each frame)
  call gotoxy
  push ecx
  mov esi,offset grandarray
  sub esi,4
  mov ecx,13 ; print 12 lines
  outerU:
    push ecx
    ; get each array from grandarry data
    add esi,4
    mov edi, [esi]
    add esi, 4
    mov ecx, [esi]
    innerU:
      push ecx
      mov ecx,0
      mov cl, [edi]
      inc edi
      mov al,[edi]
      call drawline
      inc edi
      pop ecx
    loop innerU
    inc dh
    mov dl, bl
    pop ecx
    call gotoxy
  loop outerU
  dec bh ; decrease the position of the starting colum (moving up)
  pop ecx
loop movementUp

;;;move t coming down back to where it went up. BUT make t upside down.
mov ecx, 9 ; distance to move : 9
movementDown:
  mov dl, bl ; set starting column(for each frame)
  mov dh, bh ; set starting row (for each frame)
  call gotoxy ; move the cursor to the specified position
  push ecx
  mov esi, offset grandarray
  push ecx
  mov eax, 0
  mov eax, lengthof grandarray
  imul eax, 4 ; multiply eax times 4 bcz grandarray is dword while esi is byte
  add esi, eax ; start at the end of grandarray to flip t upside down
  mov ecx, 13 ; print 12 lines
  pop eax
    outerD:
      push ecx
      ;get each array from grandarray data
      sub esi, 4
      mov ecx, [esi] ; character array length / 2
      sub esi, 4
      mov edi, [esi] ; array address
      innerD:
        push ecx
        mov ecx, 0
        mov cl, [edi]
        inc edi
        mov al,[edi]
        call drawline
        inc edi
        pop ecx
      loop innerD
      inc dh
      mov dl, bl
      pop ecx
      call gotoxy
    loop outerD
    inc bh ; increase the position of the starting row (moving down)
    pop ecx
  loop movementDown
  exit

main ENDP
drawline proc
    lineloop:    
       call writechar ; print each character in al
       inc dl
    loop lineloop
    mov eax,1 
    call delay
    ret
 drawline endp
 
fill proc
; dword array will be of the form 
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
  add esi,4
  mov [esi],   offset array6
  add esi,4
  mov ebx, lenarray6/2
  mov [esi],ebx
;====================================
  add esi,4
  mov [esi],   offset array7
  add esi,4
  mov ebx, lenarray7/2
  mov [esi],ebx
;====================================
  add esi,4
  mov [esi],   offset array8
  add esi,4
  mov ebx, lenarray8/2
  mov [esi],ebx
;====================================
  add esi,4
  mov [esi],   offset array9
  add esi,4
  mov ebx, lenarray9/2
  mov [esi],ebx
;====================================
  add esi,4
  mov [esi],   offset array10
  add esi,4
  mov ebx, lenarray10/2
  mov [esi],ebx
;====================================
  add esi,4
  mov [esi],   offset array11
  add esi,4
  mov ebx, lenarray11/2
  mov [esi],ebx
;====================================
  add esi,4
  mov [esi],   offset array12
  add esi,4
  mov ebx, lenarray12/2
  mov [esi],ebx
;====================================
  add esi,4
  mov [esi],   offset array13
  add esi,4
  mov ebx, lenarray13/2
  mov [esi],ebx
;====================================
  ret
fill endp
 END main