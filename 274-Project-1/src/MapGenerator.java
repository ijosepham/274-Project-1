/**
 * @author: Joey Pham
 * @date: 24 September 2018
 * @description: program generates a 9x9 map full of ~'s. After that, will insert 5 X's at unique locations in the map.
 *               writes the array to a text file.
 */
 
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.Math;

class MapGenerator { // program 1
    public static void main(String[] args) {
        generateMap();
    }

    /**
     *generate a 9x9 map of ~'s, then insert 5 X's at unique locations.
     *next, will write the 2d array to the file
     */
    public static void generateMap() {
        String [][] map = createBlankMap();
        generateXs(map, 0, false);
        writeFile(map);
    }

    /**
     *creates a 9x9 2d array full of ~'s
     *@return map 9x9 2d array full of ~'s
     */
    public static String [][] createBlankMap() {
        String [][] map = new String[9][9]; // [row][column] [y][x]
        for (int y = 0; y < map.length; y++) { // map.length == height
            for(int x = 0; x < map[y].length ; x++) { // map[y].length == width`
                map[y][x] = "~"; // assign
            }
        }
        return map;
    }

    /**
     *will generate 5 X's and assign it at random locations of the map
     *@param map passes in map to assign random X's to
     *       xCount number of X's already in the map, so doesn't go over
     *       repeated so won't loop twice as many times if failed
     */
    public static void generateXs(String [][] map, int xCount, boolean repeated) { 
        while (xCount < 5) { // unitl all 5 xCount have been uniquely placed
        // if i didn't have repeated boolean, it would run too many times after a repeat
            boolean isUnique = false;
            while (!isUnique) { // dont repeat spots  
                int randY = (int) (Math.random() * 9); // rand num 0-8
                int randX = (int) (Math.random() * 9); // rand num 0-8
                if (map[randY][randX] == "X") { // if that place already has an X
                    // generateXs(map, xCount, true); // repeat itself until finds valid answer
                } else { // if wasn't a dupe
                    map[randY][randX] = "X"; // assign 
                    isUnique = true; // go on to the next X now
                    xCount = xCount + 1; // xCount counter
                }                
            }
        }
    }

    /**
     *writes the 2d array to the txt file in 2d array format
     *@param map pass in map so it can loop through the values of the map
     */
    public static void writeFile(String [][] map) {
        try {
            PrintWriter writer = new PrintWriter("treasures.txt");
            for (int y = 0; y < map.length; y++) { // map.length == height
                for(int x = 0; x < map[y].length ; x++) { // map[y].length == width
                    if (x == 0 && y != 0) { // every 9th ~ start a new line
                            writer.print("\n" + map[y][x]);
                    } else if (x == 0) { // if not, then just print normally
                            writer.print(map[y][x]);
                    } else {
                        writer.print(" " + map[y][x]);
                    }
                }
            }
            writer.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("File was not found.");
        }
    }
}