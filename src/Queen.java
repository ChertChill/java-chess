// Класс фигуры Ферзь
public class Queen extends ChessPiece {

    // Реализация конструктора класса, принимающего цвет фигуры
    public Queen(String color) {
        super(color);
    }

    // Реализация метода getColor()
    @Override
    public String getColor() {
        return color;
    }

    // Реализация метода canMoveToPosition() для фигуры Ферзь
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

        // Проверка хода фигуры Ферзь - может двигаться и по прямой, и по диагонали
        // (разница между значениями строк и столбцов должна быть одинаковой)
        int lineDelta = Math.abs(line - toLine);
        int colDelta = Math.abs(column - toColumn);

        if (line == toLine || column == toColumn || lineDelta == colDelta) {
            int lineStep = Integer.compare(toLine - line, 0);      // Возращает 0 (ось закрепляется), 1 и -1 (выбор направления хода)
            int colStep = Integer.compare(toColumn - column, 0);

            // Проверка на отсутствие других фигур на пути
            int currentLine = line + lineStep;
            int currentColumn = column + colStep;

            while (currentLine != toLine || currentColumn != toColumn) {
                if (chessBoard.board[currentLine][currentColumn] != null) {
                    return false;
                }

                currentLine += lineStep;
                currentColumn += colStep;
            }

            // Проверка конечной позиции передвижения фигуры (либо пусто, либо фигура противника)
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
            return targetPiece == null || !targetPiece.getColor().equals(this.color);
        }

        return false;
    }

    // Реализация метода getSymbol() для фигуры Ферзь
    @Override
    public String getSymbol() {
        return "Q";
    }
}
