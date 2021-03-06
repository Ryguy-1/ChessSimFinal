import java.util.ArrayList;
import java.util.Random;

public class ComputerTurn {

	private Piece[] copyBlackPieces;
	private Piece[] copyWhitePieces;

	public void run() {
if(GamePanel.computerSaveCheckMovePiece==null && GamePanel.computerSaveCheckMoveToSquare==null) {
		copyBlackPieces = new Piece[GamePanel.blackPieces.length];
		copyWhitePieces = new Piece[GamePanel.whitePieces.length];

		for (int i = 0; i < copyBlackPieces.length; i++) {
			copyBlackPieces[i] = new Piece(GamePanel.blackPieces[i].getX(), GamePanel.blackPieces[i].getY(),
					GamePanel.blackPieces[i].getWidth(), GamePanel.blackPieces[i].getHeight(),
					GamePanel.blackPieces[i].getSource(), GamePanel.blackPieces[i].getColor());
			copyBlackPieces[i].setBufferedImage(GamePanel.blackPieces[i].getImage());
			copyBlackPieces[i].setKingFirst(GamePanel.blackPieces[i].kingFirst());
			copyBlackPieces[i].setPawnFirst(GamePanel.blackPieces[i].pawnFirst());
			copyBlackPieces[i].setRookFirst(GamePanel.blackPieces[i].rookFirst());
			copyBlackPieces[i].setValue(GamePanel.blackPieces[i].getValue());
		}

		for (int i = 0; i < copyWhitePieces.length; i++) {
			copyWhitePieces[i] = new Piece(GamePanel.whitePieces[i].getX(), GamePanel.whitePieces[i].getY(),
					GamePanel.whitePieces[i].getWidth(), GamePanel.whitePieces[i].getHeight(),
					GamePanel.whitePieces[i].getSource(), GamePanel.whitePieces[i].getColor());
			copyWhitePieces[i].setBufferedImage(GamePanel.whitePieces[i].getImage());
			copyWhitePieces[i].setKingFirst(GamePanel.whitePieces[i].kingFirst());
			copyWhitePieces[i].setPawnFirst(GamePanel.whitePieces[i].pawnFirst());
			copyWhitePieces[i].setRookFirst(GamePanel.whitePieces[i].rookFirst());
			copyWhitePieces[i].setValue(GamePanel.whitePieces[i].getValue());
		}

		System.out.println("Computer Ran");

		ArrayList<RuleCheckAll> rules = new ArrayList<RuleCheckAll>();
		// ArrayList<Boolean> numbers = new ArrayList<Boolean>();
		// rules and numbers indices line up.
		int counter = 0;
		if (!GamePanel.userColor.equalsIgnoreCase("White")) {
			Piece rightRook = GamePanel.computerRightRook;
			Piece leftRook = GamePanel.computerLeftRook;
			for (int i = 0; i < copyWhitePieces.length; i++) {
				for (int j = 0; j < 8; j++) {
					for (int j2 = 0; j2 < 8; j2++) {
						rules.add(new RuleCheckAll());
						if (rules.get(counter).checkPlayerPossibilities(copyWhitePieces[i], GamePanel.squares[j][j2],
								rightRook, leftRook, "Computer")) {
							counter++;
						} else {
							rules.remove(rules.size() - 1);

						}
					}
				}
			}

		} else if (!GamePanel.userColor.equalsIgnoreCase("Black")) {
			Piece rightRook = GamePanel.computerRightRook;
			Piece leftRook = GamePanel.computerLeftRook;
			for (int i = 0; i < copyBlackPieces.length; i++) {
				for (int j = 0; j < 8; j++) {
					for (int j2 = 0; j2 < 8; j2++) {
						rules.add(new RuleCheckAll());
						if (rules.get(counter).checkPlayerPossibilities(copyBlackPieces[i], GamePanel.squares[j][j2],
								rightRook, leftRook, "Computer")) {
							System.out.println("true counter " + counter);
							System.out.println(rules.get(counter).getPieceMoved());
							counter++;
						} else {
							// numbers.add(false);
							rules.remove(rules.size() - 1);
						}
					}
				}
			}
		}

		// finds which of the rules objects in rules has the greatestValue. If there are
		// multiple, take a random one.
		int valueIdx = 0;
		ArrayList<Integer> valueIdxs = new ArrayList<Integer>();

		for (int i = 0; i < rules.size(); i++) {
			System.out.println("value added for index " + i + " is " + rules.get(i).getValueAdded() + ". Moving Piece "
					+ rules.get(i).getPieceMoved().getSource() + " to [" + rules.get(i).getSquareMovedTo().getIIndex()
					+ "][" + rules.get(i).getSquareMovedTo().getJIndex() + "]");
			if (rules.get(i).getValueAdded() >= rules.get(valueIdx).getValueAdded()) {
				valueIdx = i;
				valueIdxs.add(valueIdx);
			}
		}
		int tempLargest = valueIdx;
		System.out.println("value added = " + rules.get(valueIdx).getValueAdded());
		System.out.println("valueIdxs size = " + valueIdxs.size());
		for (int i = 0; i < valueIdxs.size(); i++) {
			System.out.println("Index " + i + " of valueIdxs before has the value of "
					+ rules.get(valueIdxs.get(i)).getValueAdded());
		}
		ArrayList<Integer> trueValues = new ArrayList<Integer>();
		for (int i = 0; i < valueIdxs.size(); i++) {
			if (rules.get(tempLargest).getValueAdded() == rules.get(valueIdxs.get(i)).getValueAdded()) {
				trueValues.add(valueIdxs.get(i));
			} else {

			}
		}

		System.out.println("trueValues size = " + trueValues.size());

		valueIdx = trueValues.get(new Random().nextInt(trueValues.size()));

		System.out.println("rules size is " + rules.size());

		System.out.println("The max value is with piece " + rules.get(valueIdx).getPieceMoved().getSource()
				+ " moving with a value capture of " + rules.get(valueIdx).getValueAdded());

		System.out.println(rules.size() + "rules size");
		// System.out.println(numbers.get(0));
		System.out.println("valueIdx = " + valueIdx);
		System.out.println(rules.get(valueIdx).getSquareMovedTo().getIIndex() + "<- i          -> j "
				+ rules.get(valueIdx).getSquareMovedTo().getJIndex());
		// rules.get(valueIdx).getPieceMoved();
		// rules.get(valueIdx).getPieceRemoved();
		// rules.get(valueIdx).getSquareMovedTo();

		for (int i = 0; i < copyWhitePieces.length; i++) {
			if (rules.get(valueIdx).getPieceMoved() == copyWhitePieces[i]) {
				if (null != rules.get(valueIdx).getPieceRemoved()) {
					removePlayerPiece(rules.get(valueIdx).getPieceRemoved());
				}
				copyWhitePieces[i].moveTo(copyWhitePieces[i].getSquare(), rules.get(valueIdx).getSquareMovedTo());
				GamePanel.whitePieces = copyWhitePieces;
			}
		}
		for (int i = 0; i < copyBlackPieces.length; i++) {
			if (rules.get(valueIdx).getPieceMoved() == copyBlackPieces[i]) {
				if (null != rules.get(valueIdx).getPieceRemoved()) {
					removePlayerPiece(rules.get(valueIdx).getPieceRemoved());
				}
				copyBlackPieces[i].moveTo(copyBlackPieces[i].getSquare(), rules.get(valueIdx).getSquareMovedTo());
				GamePanel.blackPieces = copyBlackPieces;
			}
		}

		// GamePanel.blackPieces[8].moveTo(GamePanel.squares[1][0],
		// GamePanel.squares[5][0]);
}else {
	if(GamePanel.computerSaveCheckMoveToSquare.hasPlayer()==true) {
		removePlayerPiece(GamePanel.computerSaveCheckMoveToSquare.getPiece());
	}
	GamePanel.computerSaveCheckMovePiece.moveTo(GamePanel.computerSaveCheckMovePiece.getSquare(), GamePanel.computerSaveCheckMoveToSquare);
	
	GamePanel.computerSaveCheckMovePiece = null;
	GamePanel.computerSaveCheckMoveToSquare = null;
	
}





		GamePanel.playerTurn = true;


	}

	public void removePlayerPiece(Piece removedPiece) {

		GamePanel.piecesComputerTook.add(removedPiece);
		System.out.println("Computer has taken " + GamePanel.piecesComputerTook.size() + " pieces from the user.");
		if (removedPiece.getColor().equalsIgnoreCase("Black")) {
			ArrayList<Piece> temp = new ArrayList<Piece>();
			for (int i = 0; i < GamePanel.blackPieces.length; i++) {
				if (removedPiece.equals(GamePanel.blackPieces[i])) {

				} else {

					temp.add(GamePanel.blackPieces[i]);
				}
			}
			Piece[] temp2 = new Piece[temp.size()];
			// System.out.println(temp.size());
			for (int i = 0; i < temp2.length; i++) {
				temp2[i] = temp.get(i);
			}

			GamePanel.blackPieces = temp2;

		} else if (removedPiece.getColor().equalsIgnoreCase("White")) {
			ArrayList<Piece> temp = new ArrayList<Piece>();
			for (int i = 0; i < GamePanel.whitePieces.length; i++) {
				if (removedPiece.equals(GamePanel.whitePieces[i])) {

				} else {

					temp.add(GamePanel.whitePieces[i]);
				}
			}
			Piece[] temp2 = new Piece[temp.size()];

			for (int i = 0; i < temp2.length; i++) {
				temp2[i] = temp.get(i);
			}

			GamePanel.whitePieces = temp2;

		}

	}

}

//each piece calls the computer rule check to see if it is a valid move. Returns true if yes and false if no. For all of the true moves, it first calculates if this move would take the king.

//if does take king, go into checkmate checking. -> Checkmate checking checks all legal moves for every one of the opponent's piece to check if they can either 1) take the piece which puts it in check or 2) check if the next move can 

//if does not take king, check which one of the moves has the best move.
