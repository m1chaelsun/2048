/**
 * Execution: java Tile
 *
 * Program Description: Creates Tile objects that represent the up to 16
 * individual tiles that populate the Board.
 */

public class Tile
{
    private double x;       // Tile x-coordinate
    private double y;       // Tile y-coordinate
    private int value;      // Tile value

    /**
     * Input: int x, int y
     * Output: none
     * Description: Constructor that creates a Tile object and sets its value to
     * 2 or 4
     */
    public Tile(double x, double y) {
        // Initializes x and y variables
        this.x = x;
        this.y = y;

        // Sets new tile value to 2 or 4
        if (Math.random() < 0.5) {
            this.value = 2;
        }
        else {
            this.value = 4;
        }
    }

    /**
     * Input: none
     * Output: none
     * Description: Draws a tile at the called location.
     */
    public void drawTile() {
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.filledSquare(this.x, this.y, 0.09);

        if (this.value == 2048) {
            PennDraw.setPenColor(PennDraw.RED);
        }
        else {
            PennDraw.setPenColor(PennDraw.BLUE);
        }

        PennDraw.square(this.x, this.y, 0.09);
        PennDraw.setFontBold();
        PennDraw.setFontSize(30);
        PennDraw.text(this.x, this.y - 0.008, String.valueOf(this.value));
    }

    /**
     * Input: none
     * Output: int this.value
     * Description: Getter function that returns the value of a specified Tile
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Input: none
     * Output: double this.x
     * Description: Getter function that returns the x-coordinate of a specified Tile
     */
    public double getX() {
        return this.x;
    }

    /**
     * Input: none
     * Output: double this.y
     * Description: Getter function that returns the y-coordinate of a specified Tile
     */
    public double getY() {
        return this.y;
    }

    /**
     * Input: double newY
     * Output: none
     * Description: Setter method that replaces the y-coordinate for a Tile with newY
     */
    public void setY(double newY) {
        this.y = newY;
    }

    /**
     * Input: double newX
     * Output: none
     * Description: Setter method that replaces the x-coordinate for a Tile with newX
     */
    public void setX(double newX) {
        this.x = newX;
    }

    /**
     * Input: Tile t
     * Output: none
     * Description: Computes the new value of the merged tiles (doubles the
     * original Tile value)
     */
    public void finalMergeValue() {
        this.value *= 2;
    }

    /**
     * Input: Tile t
     * Output: boolean
     * Description: Determines if a Tile t can merge with another tile. True if
     * the tiles have the same value. False otherwise.
     */
    public boolean mergeAllowed(Tile t) {
        if (this.value == t.value) {
            return true;
        }
        else {
            return false;
        }
    }
}
