// Класс фигуры Конь
public class Horse extends ChessPiece {

    // Реализация конструктора класса, принимающего цвет фигуры
    public Horse(String color) {
        super(color);
    }

    // Реализация метода getColor()
    @Override
    public String getColor() {
        return color;
    }

    // Реализация метода canMoveToPosition() для фигуры Конь
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

        // Проверка хода фигуры Конь - "буквой Г" (два шага по одной оси и один по другой,
        // а также можно перескакивать через другие фигуры, как своего цвета, так и чужого)
        int lineDelta = Math.abs(toLine - line);
        int colDelta = Math.abs(toColumn - column);

        if ((lineDelta == 2 && colDelta == 1) || (lineDelta == 1 && colDelta == 2)) {
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];

            // Проверка конечной позиции передвижения фигуры (либо пусто, либо фигура противника)
            return targetPiece == null || !targetPiece.getColor().equals(this.color);
        }

        return false;
    }

    // Реализация метода getSymbol() для фигуры Конь
    @Override
    public String getSymbol() {
        return "H";
    }
}
