package src.enviroment;
import java.util.*;
public class Maze {

    public MapType mapMatrix[][];

    private Random ran = new Random();
    private final boolean[][] map;
    private final int lenght;
	private final int width;
    // private Vector<Point> cells;
    private  Set<Point> cells = new HashSet<>();
    // una celula sobrevive si tiene entre 1-5 vecinos
    // una célula aparece en una celda si esta celda tiene exactamente 3 vecinos 

    public Maze(int len, int wid) {
        LinkedList<Boolean> lines = new LinkedList<Boolean>();
        lenght = len;
        width = wid;
        map = new boolean[len][wid];
    }

    class Point {
        private int i, j;
        public boolean nexState;
        Point(int i, int j){
            this.i = i;
            this.j = j;
        }
    }

    private void  startRandom(){

        int count = 50;

        while (count > 0) {
            int istart = (this.width - 10) / 2, jstart = (this.lenght - 10) / 2;
            loop:
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++) {
                    // generate random obstacles in the map
                    if(ran.nextInt(2) == 1 && !map[i + istart][j + jstart]) {
                        map[i + istart][j + jstart] = true;
                        cells.add( new Point(i+istart, j+jstart));
                        count--;
                    }
                    if (count == 0) {
                        break loop;
                    }

                }

            }
        }
        
    }

    private void removeEmpties(){

    }

    private void addNeighbours(){
        // 
        for(Point cell : cells){
            for (Point otherCell: getNeighbours(cell)){
                cells.add(otherCell);
            }
        }
    }

    private void tick(){
        //run rules
        addNeighbours();
        for(Point cell : cells){
            if(pointBool(cell)){
                // check if point survives
                int count = 0;
                for(var neighbour : getNeighbours(cell)) if(pointBool(neighbour)) count++;
                if( 1<= count && count <= 5){
                    cell.nexState = true;
                } else cell.nexState = false;
                
                
            } else {
                //check if cell must exist
                int count = 0;
                for(var neighbour : getNeighbours(cell)) if(pointBool(neighbour)) count++;
                if(count == 3){
                    cell.nexState = true;
                } else cell.nexState = false;
                
            }
            
        }

        //update state
        for(Point cell : cells){
            //TODO
        }
        removeEmpties();

    }

    private boolean pointBool(Point point){
        return map[point.i][point.j];
    }
    private boolean offLimits(Point point){
        return offLimits(point.i, point.j);
    }
    private boolean offLimits(int i, int j) {
        if (i < 0 || i >= this.lenght || j < 0 || j >= this.width) {
            return true;
        }
        return false;
    }

    private Set<Point> getNeighbours(Point point){
        Set<Point> neighbours;
        neighbours = new HashSet<Point>();
        for (int index = -1; index < 2; index ++) {
            for (int j = -1; j < 2; j++) {
                if (!offLimits(point.i + index, point.j + j) && (index != 0 && j != 0)) {

                    neighbours.add(new Point(point.i + index, point.j + j));
                }
            }
        }
        return neighbours;
    }
}


