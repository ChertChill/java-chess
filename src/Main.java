import java.util.Scanner;

public class Main {

    public static ChessBoard buildBoard() {
        ChessBoard board = new ChessBoard("White");

        board.board[0][0] = new Rook("White");
        board.board[0][1] = new Horse("White");
        board.board[0][2] = new Bishop("White");
        board.board[0][3] = new Queen("White");
        board.board[0][4] = new King("White");
        board.board[0][5] = new Bishop("White");
        board.board[0][6] = new Horse("White");
        board.board[0][7] = new Rook("White");
        board.board[1][0] = new Pawn("White");
        board.board[1][1] = new Pawn("White");
        board.board[1][2] = new Pawn("White");
        board.board[1][3] = new Pawn("White");
        board.board[1][4] = new Pawn("White");
        board.board[1][5] = new Pawn("White");
        board.board[1][6] = new Pawn("White");
        board.board[1][7] = new Pawn("White");

        board.board[7][0] = new Rook("Black");
        board.board[7][1] = new Horse("Black");
        board.board[7][2] = new Bishop("Black");
        board.board[7][3] = new Queen("Black");
        board.board[7][4] = new King("Black");
        board.board[7][5] = new Bishop("Black");
        board.board[7][6] = new Horse("Black");
        board.board[7][7] = new Rook("Black");
        board.board[6][0] = new Pawn("Black");
        board.board[6][1] = new Pawn("Black");
        board.board[6][2] = new Pawn("Black");
        board.board[6][3] = new Pawn("Black");
        board.board[6][4] = new Pawn("Black");
        board.board[6][5] = new Pawn("Black");
        board.board[6][6] = new Pawn("Black");
        board.board[6][7] = new Pawn("Black");

        return board;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {

            ChessBoard board = buildBoard();
            System.out.println();
            System.out.println("""
               Чтобы проверить игру надо вводить команды:
               'exit' - для выхода
               'replay' - для перезапуска игры
               'castling0' или 'castling7' - для рокировки по соответствующей линии
               'move 1 1 2 3' - для передвижения фигуры с позиции 1 1 на 2 3 (поле это двумерный массив от 0 до 7)
               Проверьте могут ли фигуры ходить друг сквозь друга, корректно ли съедают друг друга, можно ли поставить шах и сделать рокировку?
            """);
            board.printBoard();

            boolean isPlaying = true;

            while (isPlaying) {
                String s = scanner.nextLine();

                if (s.equals("exit")) {
                    isRunning = false;
                    isPlaying = false;
                } else if (s.equals("replay")) {
                    System.out.println("Перезапуск игры.");
                    isPlaying = false;
                } else {
                    if (s.equals("castling0")) {
                        if (board.castling0()) {
                            System.out.println("Рокировка удалась.");
                            board.printBoard();
                        } else {
                            System.out.println("Рокировка не удалась.");
                        }
                    } else if (s.equals("castling7")) {
                        if (board.castling7()) {
                            System.out.println("Рокировка удалась.");
                            board.printBoard();
                        } else {
                            System.out.println("Рокировка не удалась.");
                        }
                    } else if (s.contains("move")) {
                        String[] a = s.split(" ");

                        try {
                            int line = Integer.parseInt(a[1]);
                            int column = Integer.parseInt(a[2]);
                            int toLine = Integer.parseInt(a[3]);
                            int toColumn = Integer.parseInt(a[4]);

                            if (board.moveToPosition(line, column, toLine, toColumn)) {
                                System.out.println("Фигура успешно передвинута.");
                                board.printBoard();

                                // Проверка на шах и мат после передвижения фигуры
                                if (board.isCheck("White")) {
                                    if (board.isCheckmate("White")) {
                                        System.out.println("Белый король находиться под шахом и матом! Чёрные выиграли.");
                                        isPlaying = false;
                                    } else {
                                        System.out.println("Белый король находится под шахом!");
                                    }
                                }

                                if (board.isCheck("Black")) {
                                    if (board.isCheckmate("Black")) {
                                        System.out.println("Чёрный король находиться под шахом и матом! Белые выиграли.");
                                        isPlaying = false;
                                    } else {
                                        System.out.println("Чёрный король находится под шахом!");
                                    }
                                }

                            } else System.out.println("Передвижение фигуры не удалось.");

                        } catch (Exception e) {
                            System.out.println("Вы ввели что-то не так, попробуйте ещё раз.");
                        }

                    }

                    // Проверка на перезапуск в конце игры
                    if (!isPlaying) {
                        System.out.println("Игра окончена. Хотите сыграть снова? (yes для перезапуска, no для выхода)");
                        String response;

                        while (true) {
                            response = scanner.nextLine();

                            if (response.equalsIgnoreCase("no")) {
                                System.out.println("Спасибо за игру!");
                                isRunning = false;
                                break;  // Завершение работы программы
                            } else if (response.equalsIgnoreCase("yes")) {
                                System.out.println("Перезапуск игры.");
                                System.out.println();
                                break;  // Перезапуск игры
                            } else {
                                System.out.println("Вы ввели что-то не так, попробуйте ещё раз.");
                            }
                        }
                    }
                }
            }
        }
    }
}