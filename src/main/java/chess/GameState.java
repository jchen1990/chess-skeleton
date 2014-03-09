package chess;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

/**
 * Class that represents the current state of the game.  Basically, what pieces are in which positions on the
 * board.
 */
public class GameState {

    /**
     * The current player
     */
    private Player currentPlayer;

    /**
     * A map of board positions to pieces at that position
     */
    private Map<Position, Piece> positionToPieceMap;
    
    private LinkedHashSet<Position> whitePieces;
    private LinkedHashSet<Position> blackPieces;
    private Position whiteKing;
    private Position blackKing;
    private int turns;
    

    /**
     * Create the game state.
     */
    public GameState() {
    	positionToPieceMap = new HashMap<Position, Piece>();
    	whitePieces = new LinkedHashSet<Position>();
        blackPieces = new LinkedHashSet<Position>();
        currentPlayer = Player.White;
        whiteKing = null;
        blackKing = null;
        turns = 0;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Call to initialize the game state into the starting positions
     */
    public void reset() {
        // White Pieces
        placePiece(new Rook(Player.White), new Position("a1"));
        placePiece(new Knight(Player.White), new Position("b1"));
        placePiece(new Bishop(Player.White), new Position("c1"));
        placePiece(new Queen(Player.White), new Position("d1"));
        whiteKing = new Position("e1");
        placePiece(new King(Player.White), whiteKing);
        placePiece(new Bishop(Player.White), new Position("f1"));
        placePiece(new Knight(Player.White), new Position("g1"));
        placePiece(new Rook(Player.White), new Position("h1"));
        placePiece(new Pawn(Player.White), new Position("a2"));
        placePiece(new Pawn(Player.White), new Position("b2"));
        placePiece(new Pawn(Player.White), new Position("c2"));
        placePiece(new Pawn(Player.White), new Position("d2"));
        placePiece(new Pawn(Player.White), new Position("e2"));
        placePiece(new Pawn(Player.White), new Position("f2"));
        placePiece(new Pawn(Player.White), new Position("g2"));
        placePiece(new Pawn(Player.White), new Position("h2"));

        // Black Pieces
        placePiece(new Rook(Player.Black), new Position("a8"));
        placePiece(new Knight(Player.Black), new Position("b8"));
        placePiece(new Bishop(Player.Black), new Position("c8"));
        placePiece(new Queen(Player.Black), new Position("d8"));
        blackKing = new Position("e8");
        placePiece(new King(Player.Black), blackKing);
        placePiece(new Bishop(Player.Black), new Position("f8"));
        placePiece(new Knight(Player.Black), new Position("g8"));
        placePiece(new Rook(Player.Black), new Position("h8"));
        placePiece(new Pawn(Player.Black), new Position("a7"));
        placePiece(new Pawn(Player.Black), new Position("b7"));
        placePiece(new Pawn(Player.Black), new Position("c7"));
        placePiece(new Pawn(Player.Black), new Position("d7"));
        placePiece(new Pawn(Player.Black), new Position("e7"));
        placePiece(new Pawn(Player.Black), new Position("f7"));
        placePiece(new Pawn(Player.Black), new Position("g7"));
        placePiece(new Pawn(Player.Black), new Position("h7"));
    }

    /**
     * Get the piece at the position specified by the String
     * @param colrow The string indication of position; i.e. "d5"
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(String colrow) {
        Position position = new Position(colrow);
        return getPieceAt(position);
    }

    /**
     * Get the piece at a given position on the board
     * @param position The position to inquire about.
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(Position position) {
        return positionToPieceMap.get(position);
    }
    
    public int getTurnCount(){
    	return turns;
    }
    
    public LinkedHashMap<Position, LinkedList<Position>> getValidMovesFor(Player player){
    	LinkedHashMap<Position, LinkedList<Position>> validMoves = new LinkedHashMap<Position, LinkedList<Position>>();
    	
    	LinkedHashSet<Position> pieces = player.equals(Player.White) ? whitePieces : blackPieces;
    	for(Position piece : pieces){
    		LinkedList<Position> pieceMoves = getValidMovesForPieceAt(piece);
    		if(!pieceMoves.isEmpty()) validMoves.put(piece, pieceMoves);
    	}
    	return validMoves;
    }
    
    public boolean movePiece(Position from, Position to){
    	Piece piece = getPieceAt(from);
    	if(piece == null || !piece.getOwner().equals(currentPlayer) || !getValidMovesForPieceAt(from).contains(to))
    		return false;
    	else{
    		Player pieceOwner = piece.getOwner();
    		if(pieceOwner.equals(Player.White)){
    			whitePieces.remove(from);
    			if(piece.getIdentifier() == 'k') whiteKing = to;
    		}else{
    			blackPieces.remove(from);
    			if(piece.getIdentifier() == 'K') blackKing = to;
    		}
    		positionToPieceMap.remove(from);
    		placePiece(piece, to);
    		switchPlayer();
    		turns++;
    		return true;
    	}
    }
    
    public GameStatus checkEndGame(){
    	GameStatus gameStatus = GameStatus.Continue;
    	
    	Player opposingPlayer = currentPlayer.equals(Player.White) ? Player.Black : Player.White;
    	Position king = currentPlayer.equals(Player.White) ? whiteKing : blackKing;
    	
    	LinkedList<Position> opposingMoves = new LinkedList<Position>();
    	for(LinkedList<Position> moves : getValidMovesFor(opposingPlayer).values())
    		opposingMoves.addAll(moves);
    	
    	if(opposingMoves.contains(king)){
    		gameStatus = GameStatus.Check;
    		if(opposingMoves.containsAll(getValidMovesForPieceAt(king)))
    			gameStatus = GameStatus.Checkmate;
    	}else{
    		LinkedList<Position> kingMoves = getValidMovesForPieceAt(king);
    		if(!kingMoves.isEmpty() && opposingMoves.containsAll(kingMoves))
    			gameStatus = GameStatus.Draw;
    	}
    	
    	return gameStatus;
    }
    
    private LinkedList<Position> getValidMovesForPieceAt(Position piecePosition){
		LinkedList<Position> pieceMoves = new LinkedList<Position>();
		Piece piece = getPieceAt(piecePosition);
		Piece obstacle;
		if(Character.toLowerCase(piece.getIdentifier()) == 'p'){
			int direction;
			int startRow;
			if(piece.getOwner().equals(Player.White)){
				direction = 1;
				startRow = 2;
			}else{
				direction = -1;
				startRow = 7;
			}
			
			Position position = new Position(piecePosition.getColumn(), piecePosition.getRow() + direction);
    		obstacle = getPieceAt(position);
    		if(obstacle == null){
    			pieceMoves.add(position);
    		}
    		if(piecePosition.getRow() == startRow){
    			position = new Position(piecePosition.getColumn(), piecePosition.getRow() + 2 * direction);
        		obstacle = getPieceAt(position);
        		if(obstacle == null){
        			pieceMoves.add(position);
        		}
    		}
    		position = new Position((char) (piecePosition.getColumn() + 1), piecePosition.getRow() + direction);
    		obstacle = getPieceAt(position);
    		if(obstacle != null){
    			if(!obstacle.getOwner().equals(piece.getOwner()))
    				pieceMoves.add(position);
    		}
    		position = new Position((char) (piecePosition.getColumn() - 1), piecePosition.getRow() + direction);
    		obstacle = getPieceAt(position);
    		if(obstacle != null){
    			if(!obstacle.getOwner().equals(piece.getOwner()))
    				pieceMoves.add(position);
    		}
		}else{
			for(Path path : piece.getMovePaths(piecePosition)){
				for(Position position : path){
					obstacle = getPieceAt(position);
					if(obstacle == null){
						pieceMoves.add(position);
					}else{
						if(!obstacle.getOwner().equals(piece.getOwner()))
							pieceMoves.add(position);
						break;
					}
				}
			}
		}
		return pieceMoves;
    }

    /**
     * Method to place a piece at a given position
     * @param piece The piece to place
     * @param position The position
     */
    private void placePiece(Piece piece, Position position) {
        positionToPieceMap.put(position, piece);
        if(piece.getOwner().equals(Player.White))
        	whitePieces.add(position);
        else
        	blackPieces.add(position);
    }
    
    private void switchPlayer(){
    	currentPlayer = currentPlayer.equals(Player.White) ? Player.Black : Player.White;
    }
    
}
