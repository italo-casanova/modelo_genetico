package src.enviroment;
import java.util.*;
public class Maze {

    private MapType mapMatrix[][]; //encapsula este porfa un getter
    // genero getters a setters? solo getMapMatrix
    private Random ran = new Random();
    private final boolean[][] map;
    private final int lenght;
	private final int width;
    // private Vector<Point> cells;
    
    //try to emulate the behaviour of a set with a LinkedList
    private  LinkedList<Point> cells = new LinkedList<>();
    List<Integer> found = new ArrayList<Integer>();
    // una celula sobrevive si tiene entre 1-5 vecinos
    // una célula aparece en una celda si esta celda tiene exactamente 3 vecinos 

    public Maze(int len, int wid) {
        lenght = len;
        width = wid;
        map = new boolean[len][wid];
        found.removeif()
        this.mapMatrix = MapType.newMapLW(len, wid);
        generateMaze();
    
    }

    public MapType[][] getMapMatrix() {
        return this.mapMatrix;
        // por que es void?
        // pq el autocompletado es mas inutil que acosta en algoritimia
        //JA
        //voy a probarlo, con mucha fe
    }
    

    class Point {
        public int i, j;
        public boolean nexState;
        Point(int i, int j){
            this.i = i;
            this.j = j;
        }
    }

    private void generateMaze(){
        startRandom();
        for(int i = 0; i < 100; i++){
            tick();
        }
    }



    private void  startRandom(){

        //count will be 50 just temporarily
        int count = 50;

        while (count > 0) {
            int istart = (this.width - 10) / 2, jstart = (this.lenght - 10) / 2;
            loop:
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++) {
                    // generate random obstacles in the map
                    if(ran.nextInt(2) == 1 && !map[i + istart][j + jstart]) {
                        mapMatrix[i + istart][j + jstart] = MapType.OBSTACLE;
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

        //tas eliminando mientras iteras
        // me bota error
        
        Set<Point> conjuntoAuxiliar = new HashSet(); //que

        for (Point cell: cells) {
            if (this.mapMatrix[cell.i][cell.j] == MapType.EMPTY) {
                conjuntoAuxiliar.add(cell);
                // y at a ya
            }
        }
        cells.addAll(conjuntoAuxiliar);
    }

    private void addNeighbours(){
        // 
        // ya sé que cosa está mal. Ahorita lo arreglo
        // que era?
        //estoy añadiendo elementos mientras itero
        // ah xd, eso iba a hacer en remove xd 

        
        Set<Point> conjuntoAuxiliar = new HashSet(); //que
        for(Point cell : cells){
            for (Point otherCell: getNeighbours(cell)){
                conjuntoAuxiliar.add(otherCell);
            }
        }
        cells.addAll(conjuntoAuxiliar);
    }

    private void tick(){
        //run rules
        addNeighbours();
        for(Point cell : cells){
            if(pointBool(cell)){
                // check if point survives
                int count = 0;
                for(Point neighbour : getNeighbours(cell)) if(pointBool(neighbour)) count++;
                if( 1<= count && count <= 5){
                    cell.nexState = true;
                } else cell.nexState = false;
                
                
            } else {
                //check if cell must exist
                int count = 0;
                for(Point neighbour : getNeighbours(cell)) if(pointBool(neighbour)) count++;
                if(count == 3){
                    cell.nexState = true;
                } else cell.nexState = false;

                
            }
            
        }

        //update state
        for(Point cell : cells) mapMatrix[cell.i][cell.j] = cell.nexState == true ? MapType.OBSTACLE : MapType.EMPTY;
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


