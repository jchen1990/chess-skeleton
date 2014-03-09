package chess;

import java.util.Iterator;
import java.util.LinkedList;

public class Path implements Iterable<Position>{
	
	private LinkedList<Position> positions;
	
	public Path(){
		positions = new LinkedList<Position>();
	}
	
	public void addPosition(Position position){
		positions.add(position);
	}
	
	public LinkedList<Position> getPositions(){
		return positions;
	}
	
	public Iterator<Position> iterator(){
		return positions.iterator();
	}
}
