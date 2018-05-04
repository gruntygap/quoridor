# Quoridor
A version of Quoridor built with Java.
An eclipse project.
UI-Base: JavaFX

## Classes
### Model
##### Methods
* getFeedBack()
  * Displays who won the game.
* setSize(int size)
  * Sets the size of the board.
* getSize()
  * gets the size of the board (if needed).
* clear() or reset()
  * clears/resets the game board to original board.
* isGameWon()
  * Determines if a player has won the game
* setChanged()
  * Given method that says that something has changed
* notifyObservers(...)
  * Given method that notifies subscribers to update.
* changeTurn(...)
  * Switches the current player's turn.
* placeWall(...)
    * TBD
* makeMove(...)
    * TBD

##### Variables/Components
* DataType board
  * Stores game data.
  * Constructor:
  ```java
  ArrayList<ArrayList<Space>>() board;
  public Board(int size){
    board = new ArrayList<ArrayList<Space>();
    for // initiate the 2d ArrayList
    // fill values of arraylist
    // exclude() means that the space is placed but not valid
    for x..
      for y..
      if(x == 0) {
        space.top.exclude();
      }else if (x == size - 1) {
        space.bottom.exclude();
      }
      if(y == 0) {
        space.left.exclude();
      }else if(y == size - 1) {
        space.right.exclude();
      }
    }
  ```
* DataType space
  * Stores space data.
  * Constructor:
  ```java
  private Fence top;
  private Fence bottom;
  private Fence left;
  private Fence right;
  private DataType player;
  public Space(){
    // Instantiate Variables
    }
  ```
* DataType fence
  * Stores fence data.
* int size
  * Stores the size of the board.
* DataType player
  * Stores whose move it currently is.
* boolean gameOver
  * keeps track if the game has won. True for game over, false otherwise.

### View
##### Methods

* update(...)
  * Method used to update the view that is called by the model


##### Variables/Components
* Button reset/clear
   * Resets or clears the board by calling model.clear() (or model.reset()).
* ComboBox<Integer> size or sizeSelect.
    * Gives user a ComboBox to select the size of the board.
* Label playerMove
    * Displays whose turn it currently is.
* Label sizeLabel
    * says "select a size"


### Controller
##### Methods

##### Variables/Components
