// Класс фигуры Пешка
public class Pawn extends ChessPiece {

    // Реализация конструктора класса, принимающего цвет фигуры
    public Pawn(String color) {
        super(color);
    }

    // Реализация метода getColor()
    @Override
    public String getColor() {
        return color;
    }

    // Реализация метода canMoveToPosition() для фигуры Пешка
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

        // Проверка направления хода для фигуры Пешка разных цветов
        int direction = color.equals("White") ? 1 : -1;

        // Проверка хода фигуры Пешка - может сдвинуться на 1 клетку вперед
        if (column == toColumn && toLine == line + direction && chessBoard.board[toLine][toColumn] == null) {
            check = false;
            return true;
        }

        // Проверка на первый ход - фигура может сдвинуться на 2 клетки вперед
        if (check && column == toColumn && toLine == line + 2 * direction &&
            chessBoard.board[toLine][toColumn] == null && chessBoard.board[line + direction][column] == null) {
            check = false;
            return true;
        }

        // Проверка на атаку - фигура может атаковать по диагонали на одну клетку, но не сдвигаться туда
        if (Math.abs(column - toColumn) == 1 && toLine == line + direction) {
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
            return targetPiece != null && !targetPiece.getColor().equals(this.color);
        }

        return false;
    }

    // Реализация метода getSymbol() для фигуры Пешка
    @Override
    public String getSymbol() {
        return "P";
    }
}
