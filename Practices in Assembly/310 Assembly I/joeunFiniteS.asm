   TITLE finite state program gaga       (joeunFiniteS.asm) 
;Last updated 12/10/2017 written by Jo Eun Kim
;Take home protion of the Final(GAGA)
;01011 => s0 s1 s3 s2 s1 s3 => abbcbb
INCLUDE Irvine32.inc

.data
  digit DWord ?
  promptDigit  byte "what is your digit? ",0
  messageS0 byte "you are in s0 with output ",0
  messageS1 byte "you are in s1 with output ",0
  messageS2 byte "you are in s2 with output ",0
  messageS3 byte "you are in s3 with output ",0
	invalidInput byte "Invalid input. Please type 0, 1 or 2 to stop.",0
    
.code

main PROC
  call clrscr
  call s0 ; start at s0.
  exit
main ENDP

readdigits proc
  mov edx, offset promptDigit
  call writestring
  call readInt
  mov digit,eax
  mov eax, digit
  .if(eax==2) 
    exit ;stop the program.
  .endif
  ret 
readdigits endp


s0 proc
  mov edx,offset messageS0
  call writestring
  mov eax, 'a'
  call writechar
  call crlf
    
  call readdigits
  .if(digit==0)
    call s1
  .endif
  .if(digit==1)
    call s2
  .endif
  call invalid 
  call s0
  ret
s0 endp

s1 proc
	mov edx, offset messageS1
	call writestring
	mov eax, 'b'
	call writechar
	call crlf

	call readdigits
  .if(digit==0)
    call s2
  .endif
  .if(digit==1)
    call s3
  .endif
  call invalid 
  call s1
  ret
s1 endp

s2 proc
	mov edx, offset messageS2
	call writestring
	mov eax, 'c'
	call writechar
	call crlf

	call readdigits
  .if(digit==0)
    call s2
  .endif
  .if(digit==1)
    call s1
  .endif
  call invalid 
  call s2
  ret
s2 endp

s3 proc
	mov edx, offset messageS3
	call writestring
	mov eax, 'b'
	call writechar
	call crlf

	call readdigits
  .if(digit==0)
    call s2
  .endif
  .if(digit==1)
    call s3
  .endif
  call invalid 
  call s3
  ret
s3 endp

;;ask to type again if there is non-binary formed number in input.
invalid proc
	mov edx, offset invalidInput
	call writestring
	call crlf
	ret
invalid endp

end main