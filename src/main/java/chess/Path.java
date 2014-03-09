package chess;

import java.util.LinkedList;

public class Path {
	
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
}
