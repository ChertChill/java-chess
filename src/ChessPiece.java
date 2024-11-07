// Абстрактный класс шахматной фигуры
public abstract class ChessPiece {
    protected String color;   // Цвет фигуры
    protected boolean check = true;   // Проверка на движение фигуры

    // Конструктор класса, принимающий цвет фигуры
    public ChessPiece (String color) {
        this.color = color;
    }

    // Метод для получения цвета фигуры
    public String getColor() {
        return color;
    }

    // Абстрактный метод для проверки возможности перемещения фигуры
    public abstract boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);

    // Абстрактный класс для получения типа фигуры
    public abstract String getSymbol();
}



