package chess.pieces;

import java.util.LinkedList;

import chess.Path;
import chess.Player;
import chess.Position;

/**
 * The Pawn
 */
public class Pawn extends Piece {
    public Pawn(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'p';
    }

	@Override
	public LinkedList<Path> getMovePaths(Position currentPosition) {
		return null;
	}
}
