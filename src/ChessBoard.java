public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8]; // Поле для игры 8 на 8
    String nowPlayer;   // Показывает, чей сейчас ход

    // Конструктор с указанием игрока
    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    // Метод для отображения чей сейчас ход
    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    // Метод для передвижения фигур
    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (checkPos(startLine) && checkPos(startColumn)) {
            ChessPiece movingPiece = board[startLine][startColumn];

            // Проверка, что фигура принадлежит текущему игроку
            if (!nowPlayer.equals(movingPiece.getColor())) return false;

            // Проверка на возможность передвижения фигуры на указанную позицию
            if (movingPiece.canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
                // Если передвигаемая фигура - Король или Ладья, устанавливается флаг первого хода
                if (movingPiece instanceof King || movingPiece instanceof Rook) {
                    movingPiece.check = false;  // Снятие флага первого хода
                }

                // Перемещение фигуры
                board[endLine][endColumn] = movingPiece;
                board[startLine][startColumn] = null;   // Установка null на предыдущую позицию

                // Проверка на проведение фигуры Пешка в Ферзя
                if (movingPiece instanceof Pawn) {
                    if ((movingPiece.getColor().equals("White") && endLine == 7) ||
                        (movingPiece.getColor().equals("Black") && endLine == 0)) {
                        board[endLine][endColumn] = new Queen(movingPiece.getColor());
                        System.out.println("Пешка проведена в Ферзи.");
                    }
                }

                // Переключение хода
                this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";
                return true;
            } else return false;
        } else return false;
    }

    // Метод для рокировки по 0 столбцу
    public boolean castling0() {
        int row = nowPlayer.equals("White") ? 0 : 7;
        King king = (King) board[row][4];
        Rook rook = (Rook) board[row][0];

        // Проверка фигур Король и Ладья - они находятся на начальных позициях, принадлежат текущему игроку, не двигались и между ними есть свободные клетки
        if (king != null && rook != null && king.getColor().equals(rook.getColor()) &&
            king.check && rook.check &&
            board[row][1] == null && board[row][2] == null && board[row][3] == null &&
            !king.isUnderAttack(this, row, 2)) {

            // Передвижение фигуры Король
            board[row][2] = king;
            board[row][4] = null;
            king.check = false;

            // Передвижение фигуры Ладья
            board[row][3] = rook;
            board[row][0] = null;
            rook.check = false;

            // Переключение хода
            nowPlayer = nowPlayer.equals("White") ? "Black" : "White";
            return true;
        }

        return false;
    }

    // Метод для рокировки по 7 столбцу
    public boolean castling7() {
        int row = nowPlayer.equals("White") ? 0 : 7;
        King king = (King) board[row][4];
        Rook rook = (Rook) board[row][0];

        // Проверяем, что король и ладья находятся на начальных позициях, принадлежат текущему игроку, не двигались и что между ними свободные клетки
        if (king != null && rook != null && king.getColor().equals(rook.getColor()) &&
            king.check && rook.check &&
            board[row][5] == null && board[row][6] == null &&
            !king.isUnderAttack(this, row, 6)) {

            // Передвижение фигуры Король
            board[row][6] = king;
            board[row][4] = null;
            king.check = false;

            // Передвижение фигуры Ладья
            board[row][5] = rook;
            board[row][7] = null;
            rook.check = false;

            // Переключение хода
            nowPlayer = nowPlayer.equals("White") ? "Black" : "White";
            return true;
        }

        return false;
    }

    // Метод для печати шахматного поля в консоль
    public void printBoard() {
        System.out.println("Ход " + nowPlayer + ".");
        System.out.println();
        System.out.println("Player 2(Black)");
        System.out.println();
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");

        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Player 1(White)");
        System.out.println();
    }

    // Метод для проверки положения фигуры на доске (фигура должна находиться внутри поля)
    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    // Метод для проверки на шах
    public boolean isCheck(String color) {
        int[] kingPosition = findKingPosition(color);
        if (kingPosition == null) return false;

        King king = (King) board[kingPosition[0]][kingPosition[1]];
        return king.isUnderAttack(this, kingPosition[0], kingPosition[1]);
    }

    // Метод для проверки на мат
    public boolean isCheckmate(String color) {
        int[] kingPosition = findKingPosition(color);
        if (kingPosition == null || !isCheck(color)) return false;

        King king = (King) board[kingPosition[0]][kingPosition[1]];

        // Проверка всех возможных ходов фигуры Король
        for (int row = kingPosition[0] - 1; row <= kingPosition[0] + 1; row++) {
            for (int col = kingPosition[1] - 1; col <= kingPosition[1] + 1; col++) {
                // Проверка на возможность хода на клетку (row, col) для фигуры Король
                if (!king.canMoveToPosition(this, kingPosition[0], kingPosition[1], row, col)) {
                    continue;
                }

                // Перемещение фигуры Король на свободную клетку
                ChessPiece tempPiece = board[row][col];
                board[row][col] = king;
                board[kingPosition[0]][kingPosition[1]] = null;

                // Проверка безопасности фигуры Король на этой клетке
                boolean isSafe = !king.isUnderAttack(this, row, col);

                // Возвращение фигуры Король на изначальную клетку
                board[kingPosition[0]][kingPosition[1]] = king;
                board[row][col] = tempPiece;

                if (isSafe) return false;
            }
        }

        return true;
    }

    // Метод для поиска фигуры Король на шахматной доске
    private int[] findKingPosition(String color) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                ChessPiece piece = board[i][j];

                if (piece instanceof King && piece.getColor().equals(color)) {
                    return new int[]{i, j}; // Позиция фигуры Король
                }
            }
        }

        return null; // Позиция фигуры Король не найдена
    }

}