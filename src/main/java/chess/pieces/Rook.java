package chess.pieces;

import java.util.LinkedList;

import chess.Path;
import chess.Player;
import chess.Position;

/**
 * The 'Rook' class
 */
public class Rook extends Piece {

    public Rook(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'r';
    }

	@Override
	public LinkedList<Path> getMovePaths(Position currentPosition) {
		LinkedList<Path> paths = new LinkedList<Path>();
		Path path = new Path();
		
		Position pos = new Position((char)(currentPosition.getColumn() + 1), currentPosition.getRow());
		while(pos.getColumn() != 0){
			path.addPosition(pos);
			pos = new Position((char)(pos.getColumn() + 1), pos.getRow());
		}
		paths.add(path);
		path = new Path();
		
		pos = new Position((char)(currentPosition.getColumn() - 1), currentPosition.getRow());
		while(pos.getColumn() != 0){
			path.addPosition(pos);
			pos = new Position((char)(pos.getColumn() - 1), pos.getRow());
		}
		paths.add(path);
		path = new Path();
		
		pos = new Position((char)(currentPosition.getColumn()), currentPosition.getRow() + 1);
		while(pos.getRow() != 0){
			path.addPosition(pos);
			pos = new Position((char)(pos.getColumn()), pos.getRow() + 1);
		}
		paths.add(path);
		path = new Path();
		
		pos = new Position((char)(currentPosition.getColumn()), currentPosition.getRow() - 1);
		while(pos.getRow() != 0){
			path.addPosition(pos);
			pos = new Position((char)(pos.getColumn()), pos.getRow() - 1);
		}
		paths.add(path);
		
		return paths;
	}
}
