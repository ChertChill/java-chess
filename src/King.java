// Класс фигуры Король
public class King extends ChessPiece {

    // Реализация конструктора класса, принимающего цвет фигуры
    public King(String color) {
        super(color);
    }

    // Реализация метода getColor()
    @Override
    public String getColor() {
        return color;
    }

    // Реализация метода canMoveToPosition() для фигуры Король
    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Проверка выхода за пределы доски
        if (!chessBoard.checkPos(line) || !chessBoard.checkPos(column) ||
            !chessBoard.checkPos(toLine) || !chessBoard.checkPos(toColumn)) {
            return false;
        }

        // Проверка на несовпадение координат точек начала и конца движения фигуры
        if (line == toLine && column == toColumn) {
            return false;
        }

        // Проверка хода фигуры Король - может двигаться на одну клетку в любом направлении
        int lineDelta = Math.abs(line - toLine);
        int colDelta = Math.abs(column - toColumn);

        if (lineDelta <= 1 && colDelta <= 1) {
            // Проверка конечной позиции передвижения фигуры (либо пусто, либо фигура противника)
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
            return targetPiece == null || !targetPiece.getColor().equals(this.color);
        }

        return false;
    }

    // Метод для проверки того, находится ли фигура Король под атакой (по полю, на котором она находится или куда собирается пойти)
    public boolean isUnderAttack(ChessBoard chessBoard, int line, int column) {
        // Проверка для каждой клетки доски, есть ли угроза от фигуры противника
        for (int i = 0; i < chessBoard.board.length; i++) {
            for (int j = 0; j < chessBoard.board[i].length; j++) {
                ChessPiece piece = chessBoard.board[i][j];

                if (piece != null && !piece.getColor().equals(this.color)) {
                    if (piece.canMoveToPosition(chessBoard, i, j, line, column)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // Реализация метода getSymbol() для фигуры Король
    @Override
    public String getSymbol() {
        return "K";
    }
}
