// Класс фигуры Слон
public class Bishop extends ChessPiece {

    // Реализация конструктора класса, принимающего цвет фигуры
    public Bishop(String color) {
        super(color);
    }

    // Реализация метода getColor()
    @Override
    public String getColor() {
        return color;
    }

    // Реализация метода canMoveToPosition() для фигуры Слон
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

        // Проверка хода фигуры Слон - может двигаться только по диагонали
        // (разница между значениями строк и столбцов должна быть одинаковой)
        if (Math.abs(line - toLine) == Math.abs(column - toColumn)) {
            int lineDirection = (toLine - line) > 0 ? 1 : -1;
            int colDirection = (toColumn - column) > 0 ? 1 : -1;

            // Проверка на отсутствие других фигур на пути
            int currentLine = line + lineDirection;
            int currentColumn = column + colDirection;

            while (currentLine != toLine && currentColumn != toColumn) {
                if (chessBoard.board[currentLine][currentColumn] != null) {
                    return false;
                }

                currentLine += lineDirection;
                currentColumn += colDirection;
            }

            // Проверка конечной позиции передвижения фигуры (либо пусто, либо фигура противника)
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
            return targetPiece == null || !targetPiece.getColor().equals(this.color);
        }

        return false;
    }

    // Реализация метода getSymbol() для фигуры Слон
    @Override
    public String getSymbol() {
        return "B";
    }
}
