   TITLE   Horse race test13         (horseRaceTest13.asm)

INCLUDE Irvine32.inc
.data
  message1 byte "Today's contenders are: POTATO, TURNIP, OATKID, MUFASA.",0
  message2 byte "Type name <in caps> of the horse you want to bet on.",0
  beginMsg byte "The race begins!",0

  potato byte "POTATO",0
  turnip byte "TURNIP",0
  oatkid byte "OATKID",0
  mufasa byte "MUFASA",0
  space byte " ",0
  
  star byte '*',0

  win byte "Win!",0
  lose byte "Lost.",0

  randomSpeed dword 4 dup(0)

.code
main proc
call clrscr
mov edx, offset message1
call writestring
call crlf
mov edx, offset message2
call writestring
call crlf
mov edx, offset beginMsg
call crlf
call writestring
call crlf
call crlf

  mov edx, offset potato
  call writestring
  call crlf
  mov edx, offset turnip
  call writestring
  call crlf
  mov edx, offset oatkid
  call writestring
  call crlf
  mov edx, offset mufasa
  call writestring

  mov bl,8

  call randomize
  mov esi, 0
  mov ecx, 20
  printNum:
    mov eax, 4
    call randomrange
    .if(eax==0)
      call potatoRun
      ;inc bl
                ;.if (bl==20)
                ;  jmp stop
                ;.endif
    .endif
    .if(eax==1)
      call turnipRun
      ;inc bl
                ;.if (bl==20)
                ;  jmp stop
                ;.endif
    .endif
    .if(eax==2)
      call oatkidRun
      ;inc bl
                ;.if (bl==20)
                ;  jmp stop
                ;.endif
    .endif
    .if(eax==3)
      call mufasaRun
      ;inc bl
                ;.if (bl==20)
                ;  jmp stop
                ;.endif
    .endif
  loop printNum

  stop:


  exit
main ENDP

drawStar proc
drawingStar:

    mov dh, bh
    mov dl, bl
    call gotoxy
    push ecx
    mov ecx, 1
    outerR:
      push ecx
      innerR:
        push ecx
        mov ecx, 1
            push eax
        mov al, star
         lineloop:
          call writechar
          ;inc dl
         loop lineloop
            pop eax
        pop ecx
      loop innerR
          ;inc dh
      ;mov dl, bl
      pop ecx
      call gotoxy
    loop outerR
    ;inc bl
    pop ecx
    push eax
    mov eax, 300
    call delay
    pop eax
  loop drawingStar
ret
drawStar endp

potatoRun proc
  mov ecx, 1
  mov bh, 5
  mov bl, 8
  call drawStar
ret
potatoRun endp

turnipRun proc
  mov ecx, 1
  mov bh, 6
  mov bl, 8
  call drawStar
ret
turnipRun endp

oatkidRun proc
  mov ecx, 1
  mov bh, 7
  mov bl, 8
  call drawStar
ret
oatkidRun endp

mufasaRun proc
  mov ecx, 1
  mov bh, 8
  mov bl, 8
  call drawStar
ret
mufasaRun endp


END main
