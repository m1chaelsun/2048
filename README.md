**GAME SPECIFICATIONS:**

- 4x4 grid
- User presses one of four keys - W, S, A, D which represent the directions up, down, left, right. All the current tiles on the board shift to the direction specified by the user’s key press. If a number tile “crashes” into another tile with the same number, they merge to form a single tile with twice the value.
- During every turn, a new number tile consisting of 2 or 4 enters the board. The tile enters at a random, unoccupied position on the board.
- If the grid is full of number tiles and there is no move possible for the user then the game is over.
- If the user has managed to bring a 2048 tile onto the board (by merging 2 1048 tiles) then the user wins.
- Outputs a victory/defeat message with the number of moves made by the user.

**HOW TO RUN:**

Run Game2048.java

**DESCRIPTION OF CLASSES:**

Board.java: Creates a representation of the 2048 Board as a 2D array. Handles all board
operations, checks for wins and losses, and contains methods that draw the win and
loss messages.

Tile.java: Creates Tile objects that represent the up to 16 individual tiles that populate the
Board.

Game2048.java: Runs Board and Tile to execute a 2048 game. This file is where the program is run and
the simulation is activated.

For instructions on how to play 2048, visit
https://en.wikipedia.org/wiki/2048_(video_game)
