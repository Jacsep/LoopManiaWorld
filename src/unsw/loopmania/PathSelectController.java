package unsw.loopmania;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class PathSelectController {
    private MenuSwitcher gameSwitcher;
    private Rectangle[][] rectArray;
    private int arrayWidth;
    private int arrayLength;
    private int startingCol;
    private int startingRow;
    
    @FXML
    private GridPane createCustomPath;

    @FXML
    private Button startCustomGame;

    public void setGameSwitcher(MenuSwitcher gameSwitcher) {
        this.gameSwitcher = gameSwitcher;
    }

    @FXML
    private void switchToCustomGame() throws IOException {
        gameSwitcher.switchMenu();
    }

    @FXML
    public void initialize(int width, int length, int startGold, String goalType, int goalNum) {
        arrayWidth = width;
        arrayLength = length;
        this.createCustomPath.setAlignment(Pos.CENTER);
        this.createCustomPath.getChildren().clear();
        this.startCustomGame.setDisable(true);

        rectArray = new Rectangle[width][length];

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < length; row++) {
                Rectangle square = new Rectangle(col * 32, row * 32, 32, 32);
                square.setStyle("-fx-fill: green; -fx-stroke: black; -fx-stroke-width: 2;");
                createCustomPath.add(square, col, row);
                square.setOnMouseClicked(this::squareClicked);
                rectArray[col][row] = square;
            }
        }
    }

    private void squareClicked(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        Integer colIndex = GridPane.getColumnIndex(clickedNode);
        Integer rowIndex = GridPane.getRowIndex(clickedNode);
        String returnedColor = rectArray[colIndex][rowIndex].getFill().toString();
        if (returnedColor.equals("0x008000ff")) {
            rectArray[colIndex][rowIndex].setFill(Paint.valueOf("grey"));
        } else if (returnedColor.equals("0x808080ff")) {
            rectArray[colIndex][rowIndex].setFill(Paint.valueOf("aqua"));
        } else if (returnedColor.equals("0x00ffffff")) {
            rectArray[colIndex][rowIndex].setFill(Paint.valueOf("green"));
        }
        checkValid();
    }

    private void checkValid() {
        int numOfPath = 0;
        int numOfStart = 0;
        for (int col = 0; col < arrayWidth; col++) {
            for (int row = 0; row < arrayLength; row++) {
                if (rectArray[col][row].getFill().toString().equals("0x808080ff")) {
                    numOfPath++;
                }
                if (rectArray[col][row].getFill().toString().equals("0x00ffffff")) {
                    numOfStart++;
                }
            }
        }
        if (numOfPath > 0 && numOfStart == 1 && validPath()) {
            this.startCustomGame.setDisable(false);
        } else {
            this.startCustomGame.setDisable(true);
        }
    }

    private boolean validPath() {
        int[][] pathCheck = new int[arrayWidth][arrayLength];
        
        for (int col = 0; col < arrayWidth; col++) {
            for (int row = 0; row < arrayLength; row++) {
                if (!rectArray[col][row].getFill().toString().equals("0x008000ff")) {
                    if (rectArray[col][row].getFill().toString().equals("0x00ffffff")) {
                        startingCol = col;
                        startingRow = row;
                    }
                    pathCheck[col][row] = 1;
                }
            }
        }
        int traversingCol = startingCol;
        int traversingRow = startingRow;
        pathCheck[traversingCol][traversingRow] = 0;
        if ((traversingRow + 1) < arrayLength && pathCheck[traversingCol][traversingRow + 1] == 1) {
            traversingRow = traversingRow + 1;
        } else if ((traversingCol + 1) < arrayWidth && pathCheck[traversingCol + 1][traversingRow] == 1) {
            traversingCol = traversingCol + 1;
        } else if ((traversingCol - 1) >= 0 && pathCheck[traversingCol - 1][traversingRow] == 1) {
            traversingCol = traversingCol - 1;
        } else if ((traversingRow - 1) >= 0 && pathCheck[traversingCol][traversingRow - 1] == 1) {
            traversingRow = traversingRow - 1;
        }

        while (true) {
            pathCheck[traversingCol][traversingRow] = 0;
            if (numOfAdjacents(pathCheck, traversingCol, traversingRow) > 1) {
                return false;
            }
            if (numOfAdjacents(pathCheck, traversingCol, traversingRow) == 0) {
                if (numOfOnes(pathCheck) != 0) {
                    return false;
                }
                if (Math.abs(traversingCol - startingCol) ==  1 && traversingRow == startingRow) {
                    return true;
                }
                if (Math.abs(traversingRow - startingRow) ==  1 && traversingCol == startingCol) {
                    return true;
                }
                return false;
            }
            if ((traversingRow + 1) < arrayLength && pathCheck[traversingCol][traversingRow + 1] == 1) {
                traversingRow = traversingRow + 1;
            } else if ((traversingCol + 1) < arrayWidth && pathCheck[traversingCol + 1][traversingRow] == 1) {
                traversingCol = traversingCol + 1;
            } else if ((traversingCol - 1) >= 0 && pathCheck[traversingCol - 1][traversingRow] == 1) {
                traversingCol = traversingCol - 1;
            } else if ((traversingRow - 1) >= 0 && pathCheck[traversingCol][traversingRow - 1] == 1) {
                traversingRow = traversingRow - 1;
            }
        }
    }

    private int numOfOnes(int [][] pathCheck) {
        int number = 0;
        for (int col = 0; col < arrayWidth; col++) {
            for (int row = 0; row < arrayLength; row++) {
                if (pathCheck[col][row] == 1) {
                    number++;
                }  
            }
        }
        return number;
    }

    private int numOfAdjacents(int[][] path, int x, int y) {
        int num = 0;
        if ((y + 1) < arrayLength && path[x][y + 1] == 1) {
            num++;
        } 
        if ((x + 1) < arrayWidth && path[x + 1][y] == 1) {
            num++;
        } 
        if ((x - 1) >= 0 && path[x - 1][y] == 1) {
            num++;
        } 
        if ((y - 1) >= 0 && path[x][y - 1] == 1) {
            num++;
        }
        return num;
    }

    public void writeInitialToJSON(int width, int length, int startGold, String goalType, int goalNum) {
        FileWriter file = null;
        JSONObject newObj = new JSONObject();
        newObj.put("width", width);
        newObj.put("height", length);
        newObj.put("startingGold", startGold);
        JSONObject goalObject = new JSONObject();
        goalObject.put("goal", goalType);
        goalObject.put("quantity", goalNum);
        newObj.put("goal-condition", goalObject);

        newObj.put("rare_items", new JSONArray());

        JSONArray entityArray = new JSONArray();
        JSONObject entityObject = new JSONObject();
        entityObject.put("x", startingCol);
        entityObject.put("y", startingRow);
        entityObject.put("type", "hero_castle");
        entityArray.put(entityObject);
        newObj.put("entities", entityArray);

        JSONObject pathObj = new JSONObject();
        pathObj.put("type", "path_tile");
        pathObj.put("x", startingCol);
        pathObj.put("y", startingRow);

        JSONArray pathDirections = new JSONArray();
        

        int traversingCol = startingCol;
        int traversingRow = startingRow;

        while (true) {
            if (numOfNonGreens(rectArray) == 0) {
                break;
            }
            if ((traversingRow + 1) < arrayLength && (traversingCol != startingCol || traversingRow + 1 != startingRow) && !rectArray[traversingCol][traversingRow + 1].getFill().toString().equals("0x008000ff")) {
                traversingRow = traversingRow + 1;
                pathDirections.put("DOWN");
            } else if ((traversingCol + 1) < arrayWidth && (traversingCol + 1 != startingCol || traversingRow != startingRow) && !rectArray[traversingCol + 1][traversingRow].getFill().toString().equals("0x008000ff")) {
                traversingCol = traversingCol + 1;
                pathDirections.put("RIGHT");
            } else if ((traversingCol - 1) >= 0 && (traversingCol - 1 != startingCol || traversingRow != startingRow) && !rectArray[traversingCol - 1][traversingRow].getFill().toString().equals("0x008000ff")) {
                traversingCol = traversingCol - 1;
                pathDirections.put("LEFT");
            } else if ((traversingRow - 1) >= 0 && (traversingCol != startingCol || traversingRow - 1 != startingRow) && !rectArray[traversingCol][traversingRow - 1].getFill().toString().equals("0x008000ff")) {
                traversingRow = traversingRow - 1;
                pathDirections.put("UP");
            } else {
                if (numOfNonGreens(rectArray) == 1) {
                    if (traversingRow < startingRow) {
                        pathDirections.put("DOWN");
                    } else if (traversingRow > startingRow) {
                        pathDirections.put("LEFT");
                    } else if (traversingCol < startingCol) {
                        pathDirections.put("RIGHT");
                    } else {
                        pathDirections.put("UP");
                    }
                    break;
                }
            }
            rectArray[traversingCol][traversingRow].setFill(Paint.valueOf("green"));
        }

        pathObj.put("path", pathDirections);
        newObj.put("path", pathObj);

        try {
            file = new FileWriter("worlds/loadFile.json");
            file.write(newObj.toString());
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int numOfNonGreens(Rectangle [][] rectArray) {
        int number = 0;
        for (int col = 0; col < arrayWidth; col++) {
            for (int row = 0; row < arrayLength; row++) {
                if (!rectArray[col][row].getFill().toString().equals("0x008000ff")) {
                    number++;
                }  
            }
        }
        return number;
    }
}
