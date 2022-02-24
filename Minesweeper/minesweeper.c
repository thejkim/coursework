/*
ICSI 333
jkim_ICSI333_p3.c
Project 3 - Minesweeper3
Due on April 12 by 11:59 PM
Jo Eun Kim
*/
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <ctype.h>
int width, height = 0;
char **calculatedBoard;
struct inputset {
    char command;
    int xc; // x coordinate
    int yc; // y coordinate
};
/* for each square, have following datas
  1. presence of a bomb
  2. has this square been revealed?
  3. is a flag placed here?
  4. how many squares around this square hold bombs?
*/
struct squareInfo {
    int isBombPlaced;
    int isRevealed;
    int isFlagPlaced;
    int numOfBombsAround;
};

// function prototypes
void initialization(char** mineBoard, char** userBoard, struct squareInfo** inputSqr);
int acceptInput(struct inputset *input);
int updateState(struct inputset *input, struct squareInfo** inputSqr, char** mineBoard, char** userBoard);
void makeMines(char** mineBoard);
int countMinesAround(int row, int column, char** mineBoard);
int isValid(int row, int col);
int isMine(int row, int col, char** mineBoard);
int win(struct squareInfo** inputSqr, char** mineBoard); // determine two winning conditions
void openSquaresUtil(char** userBoard, char** calculatedBoard, struct squareInfo** inputSqr, int row, int col); // determine to open each adjacent square or not
void openSquares(char** userBoard, char** calculatedBoard, struct squareInfo** inputSqr, int row, int col); // helper function of openSquaresUtil
void displayState(char** board); // print board.
void teardown(char** mineBoard, char** userBoard);

int main(int argc, char *argv[]) {
    if(argc < 3 || argc > 4) { // if number of arguments with programFileName != 2
        printf("Enter as the following form: \"programFileName widthOfYourBoard heightOfYourBoard\"");
        return 0;
    } else {
        width = atoi(argv[1]);
        height = atoi(argv[2]);
        if(width <= 0 || height <= 0) { // if negative or 0
            printf("Error: Enter positive values for arguments.\n");
            return 1;
        }
    }
    //for generating random numbers
    srand(time(0));

    // mineBoard will hold randomly generated mines
    char** mineBoard;
    mineBoard = (char **)malloc(width * sizeof(char *));
    for (int i=0; i<width; i++)
         mineBoard[i] = (char *)malloc(height * sizeof(char));
    // userBoard will hold squareInfo corresponding player's moves
    char** userBoard;
    userBoard = (char **)malloc(width * sizeof(char *));
    for (int i=0; i<width; i++)
         userBoard[i] = (char *)malloc(height * sizeof(char));

    // squares will hold information about each square
    struct squareInfo** squares;
    squares = (struct squareInfo **)malloc(width * sizeof(struct squareInfo *));
    for (int i=0; i<width; i++)
         squares[i] = (struct squareInfo *)malloc(height * sizeof(struct squareInfo));

    initialization(mineBoard, userBoard, squares);
    int isSet = 0;
    struct inputset input;

    while (isSet == 0) {
      printf("(F) flag a spot as a mine (R) remove a flag (A) assert that a spot is mine free (Q) quit\n");
      isSet = acceptInput(&input);
      isSet = updateState(&input, squares, mineBoard, userBoard);
      if(input.command != 'Q') // display the updated board unless the game is quit.
        displayState(userBoard);
    }
    teardown(mineBoard, userBoard);
    return 0;
}
void initialization(char** mineBoard, char** userBoard, struct squareInfo** inputSqr) {
    printf("Setting up the game\n");
    makeMines(mineBoard);
    for (int i = 0; i <  width; i++)
      for (int j = 0; j < height; j++) {
        userBoard[i][j] = '-';
        inputSqr[i][j].isBombPlaced = 0;
        inputSqr[i][j].isRevealed = 0;
        inputSqr[i][j].isFlagPlaced = 0;
        inputSqr[i][j].numOfBombsAround = 0;
      }
}

int acceptInput(struct inputset *input) {
  char c = 'S';
  int x, y, valid = 0;
  //if input is acceptable or in range, valid = 1 otherwise = 0.
  while(valid == 0) {
    scanf("%c", &c);
    if(c == 'F' || c == 'R' || c == 'A' || c == 'Q') {
      valid = 1;
    } else if (c == '\n') { // previous input was x,y coordinates -> next prompt.
    }
    else {
      printf("Invalid Command.\n");
      printf("(F) flag a spot as a mine (R) remove a flag (A) assert that a spot is mine free (Q) quit\n");
    }
  }
  if(c != 'Q') {
    valid = 0;
    while(valid == 0) {
      //accept coordinate inputs
      printf("Enter your horizontal coordinate (0-%d) and press enter\n", width-1);
      scanf("%d", &x);
      printf("Enter your vertical coordinate (0-%d) and press enter\n", height-1);
      scanf("%d", &y);

      if(isValid(x,y) == 1) { // within range
        //store input data into struct inputset
        input->command = c;
        input->xc = x;
        input->yc = y;
        valid = 1;
        return 0;
      } else // out of range. back to ask to enter again.
        printf("Out of range. ");
    } // end of while
    return 0;
  } else { // c = 'Q'
    input->command = c; // update to use it in updateState(), which will return 1 to quit 
    return 1; 
  }
}

int updateState(struct inputset *input, struct squareInfo** inputSqr, char** mineBoard, char** userBoard) {
  if(input->command == 'A') {
    if(isMine(input->xc, input->yc, mineBoard)) {
      printf("BOOM! You lose.\n");
      for(int i=0; i<width; i++) {
        for(int j=0; j<height; j++) {
          if(isMine(i, j, mineBoard) == 1)
            userBoard[i][j] = mineBoard[i][j];
        }
      }
      return 1;
    } else {
      inputSqr[input->xc][input->yc].isRevealed = 1; // true. it is revealed.
      openSquaresUtil(userBoard, calculatedBoard, inputSqr, input->xc, input->yc); // determine if adjacent squares also need to be open
      if(win(inputSqr, mineBoard) == 1) {
        printf("\nYou win!!\n"); 
        return 1;
      }
    }
  } else if (input->command == 'F') {
    inputSqr[input->xc][input->yc].isFlagPlaced = 1; // It is flaged.
    userBoard[input->xc][input->yc] = '/';
    if(win(inputSqr, mineBoard) == 1) {
      printf("\nYou win!!\n"); 
      return 1;
    } 
  } else if (input->command == 'R') {
    inputSqr[input->xc][input->yc].isFlagPlaced = 0;
    if(inputSqr[input->xc][input->yc].isRevealed == 1)
      userBoard[input->xc][input->yc] = calculatedBoard[input->xc][input->yc];
    else {
      userBoard[input->xc][input->yc] = '-';
      if(win(inputSqr, mineBoard) == 1) {
        printf("\nYou win!!\n"); 
        return 1;
      } 
    }
  } else { // c = 'Q'
    return 1;
  }
  return 0;
}

/*
will place randomly generated mines,
and build calculatedBoard, which count of mines added, corresponding the mines.
*/
void makeMines(char** mineBoard) {
  int numOfSqrs = width * height;
  int maxRange = numOfSqrs-1;
  int minRange = 0;
  int numOfBombs = (numOfSqrs * 0.25); // 1:4 ratio
  int randNums[numOfBombs];

  // random numbers for the position of bombs.
  for(int i=0; i<numOfBombs; i++) {
    int num = (rand()%(maxRange - minRange + 1)) + minRange;

    if(i>0){
        int count;
        for(count=0; count<i; count++)
            if(randNums[count] == num)
                num = (rand()%(maxRange - minRange + 1)) + minRange;
    } //end of outer if
    randNums[i] = num;
  } //end of outer loop

  /* place mines according to the random number generated above.
    rowIndex = randomNumber / width
    columnIndex = randomNumber % width
  */
  int rowIndex, columnIndex = 0;
  for(int i=0; i< width; i++)
      for(int j=0; j<height; j++) {
          mineBoard[i][j] = '-';
          for(int c=0; c<numOfBombs; c++) {
              rowIndex = (randNums[c])/(width);
              columnIndex = randNums[c]%width;
              mineBoard[rowIndex][columnIndex] = '*';
          }
      }

  calculatedBoard = (char **)malloc(width * sizeof(char *));
  for (int i=0; i<width; i++)
       calculatedBoard[i] = (char *)malloc(height * sizeof(char));

  // place mines and countOfMines together in calculatedBoard
  for(int i=0; i<width; i++)
    for(int j=0; j<height; j++) {
      calculatedBoard[i][j] = '0';
      if(mineBoard[i][j] != '*')
        calculatedBoard[i][j] = countMinesAround(i, j, mineBoard)+48;
      else
        calculatedBoard[i][j] = '*';
    }
}

// check if passing row and col are valid/in range
int isValid(int row, int col) {
    // if condition = true then return 1 otherwise 0
    return ((row>=0) && (row < height) && (col >=0) && (col < width)) ?  1 : 0;
}

// chech if passig point has a mine.
int isMine(int row, int col, char** mineBoard) {
  return (mineBoard[row][col] == '*') ? 1 : 0;
}

// count the number of mines around each square
int countMinesAround(int row, int column, char** mineBoard) {
  /*  With arr[r][c] as a pivot point, 8 points around it are
          topLeft = arr[r-1][c-1]
          top = arr[r-1][c]
          topRight = arr[r-1][c+1]
          left = arr[r][c-1]
          right = arr[r][c+1]
          bottomLeft = arr[r+1][c-1]
          bottom = arr[r+1][c]
          bottomRight = arr[r+1][c+1]
  */
  int count = 0;
  if(isValid(row-1, column-1) && isMine(row-1, column-1, mineBoard) )
      count++;
  if(isValid(row-1, column) && isMine(row-1, column, mineBoard) )
      count++;
  if(isValid(row-1, column+1) && isMine(row-1, column+1, mineBoard) )
      count++;
  if(isValid(row, column-1) && isMine(row, column-1, mineBoard) )
      count++;
  if(isValid(row, column+1) && isMine(row, column+1, mineBoard) )
      count++;
  if(isValid(row+1, column-1) && isMine(row+1, column-1, mineBoard) )
      count++;
  if(isValid(row+1, column) && isMine(row+1, column, mineBoard) )
      count++;
  if(isValid(row+1, column+1) && isMine(row+1, column+1, mineBoard) )
      count++;
  return count;
}

/* Algorithm for revealing based on the square chosen-
Function "openSquaresUtil"
  open a chosen square and set the square as revealed.
  while the chosen square contains 0
    open the chosen square and set the square as revealed.
    (*) recursively call the function itself for 8 adjacent squares 
        in order to move to each of its adjacent position and open it 
        until it is the first non-0 square.

note: for the repeatation for each 8 case, 
separate the above part (*) to another fuction "openSquares" as a helper function.
*/

/*
1.  open chosen square and set as revealed
2.  while chosen square contains 0
3.    open it and set as revealed
4.    call helper function called "openSquares" for topLeft adjecnt square
5.    call helper function called "openSquares" for top adjecnt square
6.    call helper function called "openSquares" for topRight adjecnt square
7.    call helper function called "openSquares" for left adjecnt square
8.    call helper function called "openSquares" for right adjecnt square
9.    call helper function called "openSquares" for bottomLeft adjecnt square
10.   call helper function called "openSquares" for bottom adjecnt square
11.   call helper function called "openSquares" for bottomRight adjecnt square
12.   return 0
*/
void openSquaresUtil(char** userBoard, char** calculatedBoard, struct squareInfo** inputSqr, int row, int col) {
  userBoard[row][col] = calculatedBoard[row][col];
  inputSqr[row][col].isRevealed = 1;
  while(calculatedBoard[row][col] == '0') {
    userBoard[row][col] = calculatedBoard[row][col];
    inputSqr[row][col].isRevealed = 1;
    openSquares(userBoard, calculatedBoard, inputSqr, row-1, col-1); //topLeft
    openSquares(userBoard, calculatedBoard, inputSqr, row-1, col); //top
    openSquares(userBoard, calculatedBoard, inputSqr, row-1, col+1); //topRight
    openSquares(userBoard, calculatedBoard, inputSqr, row, col-1); //left
    openSquares(userBoard, calculatedBoard, inputSqr, row, col+1); //right
    openSquares(userBoard, calculatedBoard, inputSqr, row+1, col-1); //bottomLeft
    openSquares(userBoard, calculatedBoard, inputSqr, row+1, col); //bottom
    openSquares(userBoard, calculatedBoard, inputSqr, row+1, col+1); //bottomRight
    
  }
}

/*
1.  if given(passed) adjacent square contains 0
2.    if given adjacent square has not revealed with value 0
3.      open it and set as revealed
4.      call function "openSquareUtil"
5.  else (if given adjacent square dose not contain 0)
6.    if given adjacent square has not reavealed with value 0
7.      open it and set as revealed
*/
void openSquares(char** userBoard, char** calculatedBoard, struct squareInfo** inputSqr, int row, int col) {
  // if given adjacent square contains 0, call openSquaresUtil until new adjacent square contains non 0
  if (isValid(row,col) == 1 && calculatedBoard[row][col] == '0') {
      // make sure it calls openSquaresUtil to decide to move to the next or not for all 8 cases 
      if(userBoard[row][col] != '0') { 
        userBoard[row][col] = calculatedBoard[row][col];
        inputSqr[row][col].isRevealed = 1;
        displayState(userBoard);
        openSquaresUtil(userBoard, calculatedBoard, inputSqr, row, col);
      }
  } else { // if given adjacent square contains non 0, just open then done
    if(isValid(row, col) == 1 && userBoard[row][col] != '0') {
      userBoard[row][col] = calculatedBoard[row][col];
      inputSqr[row][col].isRevealed = 1;
      displayState(userBoard);
    } 
  }
}

/* win if either the following condition is true.
1. only and all bomb squares marked with flag
2. all non-bomb squares are revealed. 
*/
int win(struct squareInfo** inputSqr, char** mineBoard) {
  int cntFlagOnMine = 0; // count number of flags which are placed in the correct position(where bombs are placed).
  int cntAllFlags = 0; // count number of all flages. 
                      // It is needed to compare with cntFlagOnMine to check if only bomb squares are marked with flags
  int cntRevealedSqr = 0; // count number of revealed squares.
  int numOfBombs = width*height*0.25; // to compare with cntFlagOnMine. 
  for (int i = 0; i < width; i++) 
    for(int j = 0; j < height; j++) {
      if(inputSqr[i][j].isFlagPlaced == 1)
        cntAllFlags++;
      if(isMine(i, j, mineBoard) == 1 && inputSqr[i][j].isFlagPlaced == isMine(i, j, mineBoard))
        cntFlagOnMine++;
      if(inputSqr[i][j].isRevealed == 1)
        cntRevealedSqr++;
    }
  // win if cntFlagOnMine = cntAllFlags = numOfBombs OR cntRevealedSqr = number of all non-bomb squares
  return ((cntFlagOnMine == numOfBombs && cntAllFlags == numOfBombs) || cntRevealedSqr == ((width*height)-numOfBombs)) ? 1 : 0;
}

void displayState(char** board) {
  for (int i = 0; i <  width; i++) {
    for (int j = 0; j < height; j++)
       printf("%c ", board[i][j]);
    printf("\n");
  }
}

void teardown(char** mineBoard, char** userBoard) {
    printf("Destroying the game\n");
    for(int i=0; i<width; i++) {
      free(calculatedBoard[i]);
      free(mineBoard[i]);
      free(userBoard[i]);
    }
    free(calculatedBoard);
    free(mineBoard);
    free(userBoard);
}
