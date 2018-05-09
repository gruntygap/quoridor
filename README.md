# Quoridor
A version of Quoridor built with Java.
An eclipse project.
UI-Base: JavaFX

## Model
### Main Objects of the Model
* <b>Game (Model)</b>
  * The encapsulating object that contains all algorithms and constructs of Quoridor
* <b>Board</b>
  * The board that holds n x n spaces. (n must be odd and n >= 5)
* <b>Space</b>
  * The tile that holds the player, and is surrounded by fences
  * Has top, left, right, bottom Fence attributes.
  * Contains a player
  * <b>Player (1/2)</b>
    * A pawn controlled by a player.
    * A player can move from space to space
  * <b>Fence</b>
    * A barrier that the player cannot hop over
    * Can be <i>valid</i> (usable by the player) (inner Fences) or <i>invalid</i> (Not used by the player; default state of border).
    * Can be <i>placed</i> (Automatically the outside ring of fences) or <i>not placed</i> (the inner fences until changed by a Player).

### Classes:
### Model
##### Variables
* ArrayList<ArrayList< Space > board;
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

##### Methods
* getFeedBack()
  * Displays who won the game.
* setSize(int size)
  * Sets the size of the board.
* getSize()
  * gets the size of the board (if needed).
* reset()
  * clears/resets the game board to original board.
* isGameWon()
  * Determines if a player has won the game
* setChanged()
  * Given method that says that something has changed (needed method by observable)
* notifyObservers(...)
  * Given method that notifies subscribers to update. (needed method by observable)
  * When a player Moves.
  * When a fence is placed.
  * setting the board Size.
* changeTurn(...)
  * Switches the current player's turn.
* placeWall(...)
    * TBD
* makeMove(...)
    * TBD
  * reset()
    * Resets the game board

##### Methods
* Constructor();
* getSpace(int x, int y);

### Space
##### Variables
* Fence top;
* Fence left;
* Fence bottom;
* Fence right;
* Player playerSpace;

##### Methods
* Constructor();
* setSpace();

### Player
##### Variables
* String identifier
  * Gives the "name" of the player

##### Methods

### Fence
##### Variables
* boolean placed;
* boolean valid;
##### Methods
* exclude(...)
* isValid(...)

# <<<<< in progress
##### Variables/Components
* DataType board

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

# >>>>>>>>>

## View
##### Methods

* update(...)
  * Method used to update the view that is called by the model (needed method by observable)


##### Variables/Components
* Model object to communicate with model
* Button reset
   * Resets the board by calling model.reset.
* ComboBox<Integer> sizeSelect.
    * Gives user a ComboBox to select the size of the board.
* Label feedback
    * Displays whose turn it currently is/who won the game.
* Label sizeLabel
    * says "select a size"
* int boardSize
  * takes in size user inputed from sizeSelect


## Controller
##### Methods
* Handle method
    * for ActionEvents

##### Variables/Components
* Model object
    * for communicating with Model
* View object (unless controller is inside of view)
    * for communicating with view
