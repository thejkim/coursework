TITLE move around the box     (joeunFinal2.asm)
;Special Problem Q2: Move around the box
;Jo Eun Kim
INCLUDE Irvine32.inc
.data
  startx byte 5
  starty byte 5
  boxSize byte 11
  symbol byte ?

  countA byte 12 dup(0) ; count to move besides line
  countB byte 4 dup(0) ; count to shift direction

  counterMsg byte "Counter: ",0

  space byte " ",0

.code
main proc
  call Clrscr

  ;draw stable box
  mov dh, starty
  mov al, "*"
  mov SYMBOL, al
  movzx ecx, boxSize
  myloop:
    mov dl, startx
    push ecx
    call drawline1
    inc dh
    pop ecx
  loop myloop

  mov esi, 0 ; for countA
  mov edi, 0 ; for countB
  mov dl, 16 ; (x)start at the right down of the box.
  mov dh, 16 ; (y)

here:
mov ecx, 12
    mov eax, yellow + 16*black
    call settextcolor
  draw:
    call gotoxy
    mov al, symbol
    call writechar
    mov eax, white + 16*black
    call settextcolor
    mov eax, 500
    call delay
    call gotoxy
    mov al, " "
    call writechar

    .if(edi==0)
    dec dh
    .endif
    .if(edi==1)
    dec dl
    .endif
    .if(edi==2)
    inc dh
    .endif
    .if(edi==3)
    inc dl
    .endif
    .if(edi==4)
    jmp stop
    .endif

    inc esi
    inc bl
    call count

    .if (esi==12)
      inc edi
      mov esi, 0
    .endif
    jmp here
    
  loop draw

  stop:

  call crlf
  call crlf
  call crlf


  exit
main ENDP

count proc
  push eax
  push edx
  mov dl, 1
  mov dh, 1
  movzx eax, bl
  call gotoxy
  push edx
  mov edx, offset counterMsg
  call writestring
  pop edx
  call writedec
  mov edx, offset space
  call writestring
  pop edx
  pop eax
ret
count endp

drawline1 proc
  movzx ecx, boxSize
  line:
    mov al, symbol
    call gotoxy
    call writechar
    inc dl
  loop line
  ret
drawline1 endp

moveUp proc
  dec dh
ret
moveUp endp

moveLeft proc
  dec dl
ret
moveLeft endp

moveDown proc
  inc dh
ret
moveDown endp

moveRight proc
  inc dl
ret
moveRight endp

star proc
  mov al, symbol
  call writechar
star endp
 
END main