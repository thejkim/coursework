   TITLE   Horse race        (joeunFinal1.asm)
;Special Problem Q2: horse race
;Jo Eun Kim
INCLUDE Irvine32.inc
.data
  message1 byte "Today's contenders are: POTATO, TURNIP, OATKID, MUFASA.",0
  message2 byte "Type name <in caps> of the horse you want to bet on.",0
  beginMsg byte "The race begins!",0

  potato byte "POTATO",0
  turnip byte "TURNIP",0
  oatkid byte "OATKID",0
  mufasa byte "MUFASA",0

  input dword ?

  star byte '*',0
  win byte "Win!",0
  lose byte "Lose.",0

  randomSpeed dword 4 dup(0)
  colArray byte 4 dup(8)

.code
main proc
  call clrscr
  mov edx, offset message1
  call writestring
  call crlf
  mov edx, offset message2
  call writestring
  call crlf

  call readInput

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
  mov ecx, -1
  printNum:
    mov eax, 4
    call randomrange
    .if(eax==0)
      call case0
    .endif
    .if(eax==1)
      call case1
    .endif
    .if(eax==2)
      call case2
    .endif
    .if(eax==3)
      call case3
    .endif
  loop printNum
  exit
main ENDP

case0 proc
    call potatoRun
      inc colArray[0]
      mov dh, 11
      mov dl, 0
      call gotoxy
      .if(colArray[0]==29)
        mov edx, offset potato
        .if(edx==input)
          mov edx, offset win
          call writestring
          exit
        .endif
          mov edx, offset lose
          call writestring
          exit
      .endif
    ret
case0 endp

case1 proc
call turnipRun
      inc colArray[1]
      mov dh, 11
      mov dl, 0
      call gotoxy
      .if(colArray[1]==29)
        mov edx, offset turnip
        .if(edx==input)
          mov edx, offset win
          call writestring
          exit
        .endif
          mov edx, offset lose
          call writestring
          exit
      .endif
    ret
case1 endp

case2 proc
call oatkidRun
      inc colArray[2]
      mov dh, 11
      mov dl, 0
      call gotoxy
      .if(colArray[2]==29)
        mov edx, offset oatkid
        .if(edx==input)
          mov edx, offset win
          call writestring
          exit
        .endif
          mov edx, offset lose
          call writestring
          exit
      .endif
    ret
case2 endp

case3 proc
call mufasaRun
      inc colArray[3]
      mov dh, 11
      mov dl, 0
      call gotoxy
      .if(colArray[3]==29)
        mov edx, offset mufasa
        .if(edx==input)
          mov edx, offset win
          call crlf
          call writestring
          exit
        .endif
          mov edx, offset lose
          call crlf
          call writestring
          exit
      .endif
    ret
case3 endp

readInput proc
  mov edx, offset message2
  mov ecx, sizeof input
  call readString
  mov edx, offset input
  mov input, eax
  ret
readInput endp

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
        push eax
        mov al, star
        call writechar
        pop eax
      loop innerR
      pop ecx
      call gotoxy
    loop outerR
    pop ecx
    push eax
    mov eax, 50
    call delay
    pop eax
  loop drawingStar
ret
drawStar endp

potatoRun proc
  mov ecx, 1
  mov bh, 6
  mov bl, colArray[0]
  call drawStar
ret
potatoRun endp

turnipRun proc
  mov ecx, 1
  mov bh, 7
  mov bl, colArray[1]
  call drawStar
ret
turnipRun endp

oatkidRun proc
  mov ecx, 1
  mov bh, 8
  mov bl, colArray[2]
  call drawStar
ret
oatkidRun endp

mufasaRun proc
  mov ecx, 1
  mov bh, 9
  mov bl, colArray[3]
  call drawStar
ret
mufasaRun endp

END main
