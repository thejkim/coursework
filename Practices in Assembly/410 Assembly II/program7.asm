TITLE program7	(program7.asm)
;Date: May 15, 2018
;Name: Jo Eun Kim

INCLUDE Irvine32.inc

.data
HANDLE TEXTEQU <DWORD>
HEAP_ZERO_MEMORY EQU 8
GetProcessHeap PROTO
HeapAlloc PROTO, hHeap: HANDLE, dwFlags: HANDLE, dwBytes: HANDLE
HeapFree PROTO, hHeap: HANDLE, dwFlags: HANDLE, lpMem: HANDLE

.code

; Allocates memory space of the given bytes.
; Param: (Stack) Allocation size in bytes
; Return: (EAX) The address of the allocated memory space
alloc PROC
	LOCAL HEAP: HANDLE

	push ebp
	mov ebp, esp
	push ebx
	mov ebx, [ebp + 8] ; memory allocation size in bytes

	mov eax, 1
	invoke GetProcessHeap
	mov HEAP, eax

	;why does edx change?
	push edx
	invoke HeapAlloc, HEAP, HEAP_ZERO_MEMORY, ebx
	pop edx

	pop ebx
	pop ebp

	ret 4
alloc ENDP

malloc MACRO size
	mov eax, size
	push eax
	call alloc

ENDM

; Allocates memory space for an array of characters of the given length
;    Array of Chars = C style String
; Param: The length of the char array to be allocated
; Return: (EAX) The address of the allocated memory space
calloc MACRO length
	mov edx, length
	push edx
	call alloc

	push ebx
	mov ebx, 0
	mov ecx, 0
	toploop:
		mov [eax + ebx], ecx
		inc ebx
		cmp ebx, length
		jl toploop
	endLoop:
	pop ebx

ENDM

;int: 16 bits = 2 bytes
ialloc MACRO
	mov eax, 2
	push eax
	call alloc

	push bx
	mov bx, 0
	mov [eax], bx
	pop bx

ENDM

;long: 32 bits = 4 bytes
lalloc MACRO
	mov eax, 4
	push eax
	call alloc

	push ebx
	mov ebx, 0
	mov [eax], ebx
	pop ebx

ENDM

free PROC
	push ebp
	mov ebp, esp
	mov eax, [ebp + 8]
	push ebx
	mov ebx, eax

	invoke GetProcessHeap
	invoke HeapFree, eax, 0, ebx

	pop ebx
	pop ebp
	ret 4
free ENDP

delete MACRO addr
	mov eax, addr
	push eax
	call free

ENDM

main PROC
	LOCAL VarPtr: HANDLE

	;;; Alloc Usage
	mov eax, 4
	push eax
	call alloc
	mov VarPtr, eax

	mov eax, 0FFFFFFFFh
	call writedec
	call crlf

	;;; Free Usage
	push VarPtr
	call free
	;call dumpregs

	;;; Malloc Usage
	malloc 4
	mov VarPtr, eax

	mov eax, 0FFFFFFFEh
	call writedec
	call crlf

	;;; Delete Usage
	delete VarPtr
	;call dumpregs

	;;; Calloc Usage
	calloc 4
	mov VarPtr, eax

	mov dl, 75 ; K
	mov [eax], dl
	mov dl, 73 ; I
	mov [eax+1], dl
	mov dl, 77 ; M
	mov [eax+2], dl
	mov dl, 0 ; NULL
	mov [eax+3], dl
	mov edx, eax
	call writestring
	call crlf

	delete VarPtr

	;;; Ialloc Usage
	; int* varPtr;
	ialloc
	mov VarPtr, eax

	mov bx, 0FFFFFFFDh
	mov [eax], bx
	mov eax, [eax]
	call writedec
	call crlf

	delete VarPtr

	;;; Lalloc Usage
	lalloc
	mov VarPtr, eax

	mov ebx, 0FFFFFFFCh
	mov [eax], ebx
	mov eax, [eax]
	call writedec
	call crlf

	;;; Reassign Value
	mov ebx, 0FFFFFFFBh
	mov eax, VarPtr
	mov [eax], ebx
	mov eax, [eax]
	call writedec
	call crlf

	delete VarPtr

	; Verifying if the memory block is freed
	; Should hang
	; But I would command this part out for the convenience to run  
	;mov ebx, 0FFFFFFFAh
	;mov eax, VarPtr
	;mov [eax], ebx
	;mov ebx, [eax]
	;call writedec
	;call crlf

 	exit 
main ENDP

	end main