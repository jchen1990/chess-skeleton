package chess.pieces;

import java.util.LinkedList;

import chess.Path;
import chess.Player;
import chess.Position;

/**
 * The King class
 */
public class King extends Piece {
    public King(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'k';
    }

	@Override
	public LinkedList<Path> getMovePaths(Position currentPosition) {

		LinkedList<Path> paths = new LinkedList<Path>();
		Path path = new Path();
		
		Position pos = new Position((char)(currentPosition.getColumn() + 1), currentPosition.getRow());
		if(pos.getColumn() != 0)
			path.addPosition(pos);
		paths.add(path);
		path = new Path();
		
		pos = new Position((char)(currentPosition.getColumn() - 1), currentPosition.getRow());
		if(pos.getColumn() != 0)
			path.addPosition(pos);
		paths.add(path);
		path = new Path();
		
		pos = new Position((char)(currentPosition.getColumn()), currentPosition.getRow() + 1);
		if(pos.getRow() != 0)
			path.addPosition(pos);
		paths.add(path);
		path = new Path();
		
		pos = new Position((char)(currentPosition.getColumn()), currentPosition.getRow() - 1);
		if(pos.getRow() != 0)
			path.addPosition(pos);
		paths.add(path);
		path = new Path();
		
		pos = new Position((char)(currentPosition.getColumn() + 1), currentPosition.getRow() + 1);
		if(pos.getColumn() != 0 && pos.getRow() != 0)
			path.addPosition(pos);
		paths.add(path);
		path = new Path();
		
		pos = new Position((char)(currentPosition.getColumn() + 1), currentPosition.getRow() - 1);
		if(pos.getColumn() != 0 && pos.getRow() != 0)
			path.addPosition(pos);
		paths.add(path);
		path = new Path();
		
		pos = new Position((char)(currentPosition.getColumn() - 1), currentPosition.getRow() + 1);
		if(pos.getColumn() != 0 && pos.getRow() != 0)
			path.addPosition(pos);
		paths.add(path);
		path = new Path();
		
		pos = new Position((char)(currentPosition.getColumn() - 1), currentPosition.getRow() - 1);
		if(pos.getColumn() != 0 && pos.getRow() != 0)
			path.addPosition(pos);
		paths.add(path);
		
		return paths;
	}
}
