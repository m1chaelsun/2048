/**
 * Execution: java Board
 *
 * Program Description: Creates a representation of the 2048 Board as a 2D array.
 * Handles all board operations, checks for wins and losses, and contains methods
 * that draw the win and loss messages.
 */

public class Board {
    private Tile[][] boardArray;        // 2D array
                                        // representation of board
    private int numMoves;               // Number of moves made
    private int numTiles;               // Number of tiles on board

    // Fixed X and Y-coordinates of possible tile positions on board
    private static double[] tileX = {0.2, 0.4, 0.6, 0.8};
    private static double[] tileY = {0.2, 0.4, 0.6, 0.8};

    /**
     * Input: none
     * Output: none
     * Description: Constructor that creates a 2D array representation of the 2028
     * board (4 by 4)
     */
    public Board() {
        this.boardArray = new Tile[4][4];
        this.numMoves = 0;

        for (int i = 0; i < 2; i++)
        {
            addTile();
        }
    }

    /**
     * Input: none
     * Output: none
     * Description: Creates a new tile object with value 2 or 4 and adds it to a
     * random, unoccupied space on the board.
     */
    public void addTile() {
        // Determine coordinates for new tile
        int i = (int) (Math.random() * 4);
        int j = (int) (Math.random() * 4);

        if (boardArray[i][j] == null) {
            boardArray[i][j] = new Tile(tileX[j], tileY[i]);
            numTiles++;
        }
        else {
            // If random space chosen is not null, rerun method until tile can be
            // successfully added
            addTile();
        }
    }

    /**
     * Input: none
     * Output: none
     * Description: Draws all tiles on Board.
     */
    public void draw()
    {
        PennDraw.setFontSize(30);

        // Iterates through Board
        for (Tile[] tiles : boardArray) {
            for (Tile tile : tiles) {
                if (tile != null) {
                    // Draws each tile
                    tile.drawTile();
                }
            }
        }
    }

    /**
     * Input: int i, int j, char dir
     * Output: Tile
     * Description: Returns the tile that is adjacent to the input tile in the
     * direction (char dir) inputted.
     */
    public Tile nextTile(int i, int j, char dir)
    {
        // W = above
        if (dir == 'w') {
            // Tile within bounds of 4x4 array
            if (i < 3) {
                // Column above, same row
                return boardArray[i + 1][j];
            }
        }

        // A = left
        else if (dir == 'a') {
            // Tile within bounds of 4x4 array
            if (j >= 1) {
                // Same column, row left
                return boardArray[i][j - 1];
            }
        }

        // S = down
        else if (dir == 's') {
            // Tile within bounds of 4x4 array
            if (i >= 1) {
                // Column below, same row
                return boardArray[i - 1][j];
            }
        }

        // D = right
        else if (dir == 'd') {
            // Tile within bounds of 4x4 array
            if (j < 3) {
                // Same column, row right
                return boardArray[i][j + 1];
            }
        }

        return null;
    }

    /**
     * Input: int i, int j, char dir
     * Output: boolean
     * Description: Determines if a move can be made to be used in the validKey
     * function. This function is called for each tile on the Board. Returns true
     * if the specified tile and direction is not blocked by another tile or the
     * edge of the Board. If either condition is not met, returns false.
     */
    public boolean canMove(int i, int j, char dir) {
        // If specified tile is null, a move cannot occur.
        if (boardArray[i][j] == null) {
            return false;
        }

        // If specified tile is at the edge of the board, a move cannot occur.
        else if (reachesEdge(i, j, dir)) {
            return false;
        }

        // If adjacent tile with respect to dir is null, a move is possible.
        else if (nextTile(i, j, dir) == null)
        {
            return true;
        }

        // Checks if adjacent tile with respect to dir has the same value as input
        // Tile. If so, tiles can be merged.
        else
        {
            return nextTile(i, j, dir).mergeAllowed(boardArray[i][j]);
        }
    }

    /**
     * Input: char dir
     * Output: boolean
     * Description: Returns true if the key pressed (char dir) can result in a move
     * on the current Board. False if no move can be processed from the key input.
     */
    public boolean validKey(char dir)
    {
        // Allowed move keys: WASD
        if (dir == 'w' || dir == 'a' || dir == 's' || dir == 'd') {

            // Iterates through entire Board to see if a tile can move. Returns
            // true when reaches a tile that can move.
            for (int i = 0; i < boardArray.length; i++)
            {
                for (int j = 0; j < boardArray[i].length; j++)
                {
                    if (canMove(i, j, dir)) {
                        return true;
                    }
                }
            }
        }

        // No moves possible
        return false;
    }

    /**
     * Input: int i, int j, char dir
     * Output: boolean
     * Description: Returns true if the key pressed (char dir) results in the tile
     * hitting the edge of the Board.
     */
    public boolean reachesEdge(int i, int j, char dir) {
        // W = up
        if (dir == 'w') {
            return i == 3;
        }

        // A = left
        else if (dir == 'a') {
            return j == 0;
        }

        // S = down
        else if (dir == 's') {
            return i == 0;
        }

        // D = right
        else if (dir == 'd') {
            return j == 3;
        }

        return false;
    }

    /**
     * Input: char dir
     * Output: none
     * Description: Moves all blocks on Board by manipulating the boardArray as
     * determined by the key pressed (char dir).
     */
    public void moveAllTiles(char dir) {
        // W = up
        if (dir == 'w') {
            // Iterates through all 4 rows
            for (int i = 0; i < 4; i++) {
                // Iterates through all 4 columns
                for (int j = 0; j < 4; j++) {
                    int k = i;
                    // Ensures current tile encounters another tile or an edge
                    while ((!reachesEdge(k, j, dir)) &&
                            (nextTile(k, j, dir) == null))
                    {
                        // Shifts tile up 1 row
                        boardArray[k + 1][j] = boardArray[k][j];

                        // Overwrites previous position
                        boardArray[k][j] = null;

                        k++;
                    }
                }
            }
        }

        // A = left
        else if (dir == 'a') {
            // Iterates through all 4 columns
            for (int j = 0; j < 4; j++) {
                // Iterates through all 4 rows
                for (int i = 0; i < 4; i++) {
                    int k = j;
                    // Ensures current tile encounters another tile or an edge
                    while ((!reachesEdge(i, k, dir)) &&
                            (nextTile(i, k, dir) == null))
                    {
                        // Shifts tile left 1 column
                        boardArray[i][k - 1] = boardArray[i][k];

                        // Overwrites previous position
                        boardArray[i][k] = null;

                        k--;
                    }
                }
            }
        }

        // S = down
        else if (dir == 's') {
            // Iterates through all 4 rows
            for (int i = 0; i < 4; i++) {
                // Iterates through all 4 columns
                for (int j = 0; j < 4; j++) {
                    int k = i;
                    // Ensures current tile encounters another tile or an edge
                    while ((!reachesEdge(k, j, dir)) &&
                            (nextTile(k, j, dir) == null))
                    {
                        // Shifts tile down 1 row
                        boardArray[k - 1][j] = boardArray[k][j];

                        // Overwrites previous position
                        boardArray[k][j] = null;

                        k--;
                    }
                }
            }
        }

        // D = right
        else if (dir == 'd') {
            // Iterates through all 4 columns
            for (int j = 0; j < 4; j++) {
                // Iterates through all 4 rows
                for (int i = 0; i < 4; i++) {
                    int k = j;
                    // Ensures current tile encounters another tile or an edge
                    while ((!reachesEdge(i, k, dir)) &&
                            (nextTile(i, k, dir) == null))
                    {
                        // Shifts tile right 1 column
                        boardArray[i][k + 1] = boardArray[i][k];

                        // Overwrites previous position
                        boardArray[i][k] = null;

                        k++;
                    }
                }
            }
        }
    }

    /**
     * Input: int i, int j, char dir
     * Output: boolean
     * Description: Returns true if adjacent tile has same value as input tile.
     * Returns false otherwise, or if current and/or adjacent tiles are null.
     * Adjacent tile defined using key pressed (char dir).
     */
    public boolean canMerge(int i, int j, char dir) {
        // If tile is null, cannot merge.
        if (boardArray[i][j] == null) {
            return false;
        }

        // If adjacent tile is null, cannot merge.
        else if (nextTile(i, j, dir) == null) {
            return false;
        }

        // If adjacent tile has same value as input tile, can merge.
        else if (nextTile(i, j, dir).mergeAllowed(boardArray[i][j]))
        {
            return true;
        }

        return false;
    }

    /**
     * Input: char dir
     * Output: none
     * Description: Goes through entire board and merges all possible tiles in the
     * specified direction (char dir). If there are three tiles in a row, the two
     * tiles closest to the direction of movement can be merged. If there are four
     * tiles in a row, the pairs are merged.
     */
    public void mergeAll(char dir) {
        // W = up
        if (dir == 'w') {
            // Iterates through all 4 rows
            for (int i = 0; i < 4; i++) {
                // Iterates through all 4 columns
                for (int j = 0; j < 4; j++) {
                    // Ensures tile can merge with adjacent tile
                    if (canMerge(i, j, dir)) {
                        // Sets new tile value (double old tile value)
                        nextTile(i, j, dir).finalMergeValue();

                        // Overwrites previous position
                        boardArray[i][j] = null;

                        // Decreases number of tiles by 1
                        numTiles--;
                    }
                }
            }
        }

        // A = left
        else if (dir == 'a') {
            // Iterates through all 4 columns
            for (int j = 0; j < 4; j++) {
                // Iterates through all 4 rows
                for (int i = 0; i < 4; i++) {
                    // Ensures tile can merge with adjacent tile
                    if (canMerge(i, j, dir)) {
                        // Sets new tile value (double old tile value)
                        nextTile(i, j, dir).finalMergeValue();

                        // Overwrites previous position
                        boardArray[i][j] = null;

                        // Decreases number of tiles by 1
                        numTiles--;
                    }
                }
            }
        }

        // S = down
        else if (dir == 's') {
            // Iterates through all 4 rows
            for (int i = 0; i < 4; i++) {
                // Iterates through all 4 columns
                for (int j = 0; j < 4; j++) {
                    // Ensures tile can merge with adjacent tile
                    if (canMerge(i, j, dir)) {
                        // Sets new tile value (double old tile value)
                        nextTile(i, j, dir).finalMergeValue();

                        // Overwrites previous position
                        boardArray[i][j] = null;

                        // Decreases number of tiles by 1
                        numTiles--;
                    }
                }
            }
        }

        // D = right
        else if (dir == 'd') {
            // Iterates through all 4 columns
            for (int j = 0; j < 4; j++) {
                // Iterates through all 4 rows
                for (int i = 0; i < 4; i++) {
                    // Ensures tile can merge with adjacent tile
                    if (canMerge(i, j, dir)) {
                        // Sets new tile value (double old tile value)
                        nextTile(i, j, dir).finalMergeValue();

                        // Overwrites previous position
                        boardArray[i][j] = null;

                        // Decreases number of tiles by 1
                        numTiles--;
                    }
                }
            }
        }
    }

    /**
     * Input: char dir
     * Output: none
     * Description: Processes the move by executing moveAllTiles and mergeAll
     * functions in the input direction (char dir). Adds a new tile once move
     * completes and increments number of tiles. Also updates boardArray after the
     * move.
     */
    public void move(char dir) {
        if (validKey(dir)) {
            // Process move
            moveAllTiles(dir);
            mergeAll(dir);
            moveAllTiles(dir);

            // Update boardArray
            for (int i = 0; i < boardArray.length; i++)
            {
                for (int j = 0; j < boardArray[i].length; j++)
                {
                    if (boardArray[i][j] != null) {
                        boardArray[i][j].setX(tileX[j]);
                        boardArray[i][j].setY(tileY[i]);
                    }
                }
            }

            // Adds new tile
            addTile();

            // Increases number of moves made
            numMoves++;
        }
    }

    /**
     * Input: none
     * Output: boolean
     * Description: If the value of a Tile reaches 2048, return true. Otherwise,
     * return false.
     */
    public boolean winChecker() {
        // Iterates through all elements in boardArray
        for (Tile[] tiles : boardArray) {
            for (Tile tile : tiles) {
                // Checks if tile value equals 2048
                if (tile != null && tile.getValue() == 2048) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Input: none
     * Output: boolean
     * Description: If board is full and no tiles can merge, the game is lost and
     * returns true. Otherwise, can continue playing and returns false.
     */
    public boolean lossChecker() {
        // Board still has empty spaces, not lost yet
        if (numTiles < 16) {
            return false;
        }

        // Iterates through entire Board
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // Checks if adjacent tiles have the same score to merge
                if (i >= 3 ||
                        !boardArray[i][j].mergeAllowed(boardArray[i + 1][j])) {
                    if (j < 3 &&
                            boardArray[i][j].mergeAllowed(boardArray[i][j + 1])) {
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Input: none
     * Output: none
     * Description: Draws the number of moves made. To be used on the win and loss
     * screens.
     */
    public void printMovesMade() {
        PennDraw.setFontSize(25);
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.text(0.5, 0.35, "Moves Made: " + this.numMoves);
    }

    /**
     * Input: none
     * Output: none
     * Description: Draws the win screen
     */
    public void winDrawing() {
        // Green background
        PennDraw.setPenColor(43, 196, 46);
        PennDraw.filledSquare(0.5, 0.5, 0.5);

        // Win message and number of moves
        PennDraw.setFontSize(32);
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.text(0.5, 0.5, "2048 Achieved! You win!");
        printMovesMade();
    }

    /**
     * Input: none
     * Output: none
     * Description: Draws the lose screen
     */
    public void loseDrawing() {
        // Red background
        PennDraw.setPenColor(196, 53, 43);
        PennDraw.filledSquare(0.5, 0.5, 0.5);

        // Lose message and number of moves
        PennDraw.setFontSize(32);
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.text(0.5, 0.5, "Out of moves. You lose.");
        printMovesMade();
    }

    /**
     * Input: int i
     * Output: double
     * Description: Getter method that returns the input (int i) index for tileX
     */
    public double getTileX(int i)
    {
        return tileX[i];
    }

    /**
     * Input: int j
     * Output: double
     * Description: Getter method that returns the input (int j) index for tileY
     */
    public double getTileY(int j)
    {
        return tileY[j];
    }
}
