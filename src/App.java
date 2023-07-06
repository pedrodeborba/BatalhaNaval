import java.util.Scanner;
import java.util.Random;

public class App {
    private static final int TAMANHO_TABULEIRO = 10;
    private static final int QUANTIDADE_BARCOS = 10;
    private static int[] tamanhosBarcos = { 4, 3, 3, 2, 2, 2, 1, 1, 1, 1 };
    private static char[] caracteresBarcos = { 'N', 'I', 'I', 'L', 'L', 'L', 'C', 'C', 'C', 'C' };
    private static char[][] tabJogador1;
    private static char[][] tabJogador2;
    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        boolean start = true;
        while (start) {
            System.out.println("BATALHA NAVAL");
            System.out.println("[1] Jogar contra o computador");
            System.out.println("[2] Jogar multiplayer");
            System.out.println("[0] Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = ler.nextInt();
            tabJogador1 = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
            tabJogador2 = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
            for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
                for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                    tabJogador1[i][j] = ' ';
                    tabJogador2[i][j] = ' ';
                }
            }
            switch (opcao) {
                case 1:
                    System.out.println("\nComo deseja alocar seus barcos?");
                    System.out.println("[A] Automaticamente");
                    System.out.println("[B] Manualmente");
                    System.out.print("Escolha uma opção: ");
                    char forma = ler.next().toUpperCase().charAt(0);
                    switch (forma) {
                        case 'A':
                            // Aloca automaticamente os barcos dos jogadores
                            automatic(tabJogador1);
                            automatic(tabJogador2);
                            // Exibe tabuleiro do jogador 1
                            System.out.println("\nPronto, seus barcos já foram alocados!\n");
                            System.out.println("Seu tabuleiro:\n");
                            imprimirTabuleiro(tabJogador1);
                            // Inicia o jogo
                            SinglePlayer(tabJogador1, tabJogador2, ler);
                        break;

                        case 'B':
                            manual(tabJogador1, ler);
                            automatic(tabJogador2);
                            System.out.printf("Todos seus barcos foram alocados.\n");
                            SinglePlayer(tabJogador1, tabJogador2, ler);
                        break;

                        default:
                            System.out.println("Opção inválida.\n");
                        break;
                    }
                break;

                case 2:
                    System.out.println("\nJogador (1), como deseja alocar seus barcos?");
                    System.out.println("[A] Automaticamente");
                    System.out.println("[B] Manualmente");
                    System.out.print("Escolha uma opção: ");
                    char alocaP1 = ler.next().toUpperCase().charAt(0);
                    System.out.println("\nJogador (2), como deseja alocar seus barcos?");
                    System.out.println("[A] Automaticamente");
                    System.out.println("[B] Manualmente");
                    System.out.print("Escolha uma opção: ");
                    char alocaP2 = ler.next().toUpperCase().charAt(0);
                    // alocar barcos do player-1
                    switch (alocaP1) {
                        case 'A':
                            System.out.println("Jogador (1), seus barcos foram alocados automaticamente!\n");
                            automatic(tabJogador1);
                        break;

                        case 'B':
                            System.out.println("Jogador (1), aloque seus barcos. \n");
                            manual(tabJogador1, ler);
                        break;

                        default:
                            System.out.println("Opção invalida!!!\n");
                        break;
                    }
                    // alocar barcos do player-2
                    switch (alocaP2) {
                        case 'A':
                            System.out.println("Jogador (2), seus barcos foram alocados automaticamente!\n");
                            automatic(tabJogador2);
                            MultiPlayer(tabJogador1, tabJogador2, ler);
                        break;

                        case 'B':
                            System.out.println("Jogador (2), aloque seus barcos. \n");
                            manual(tabJogador2, ler);
                            MultiPlayer(tabJogador1, tabJogador2, ler);
                        break;

                        default:
                            System.out.println("Opção invalida!!!\n");
                        break;
                    }
                    // matriz dos jogadores
                    System.out.println("Tabuleiro Jogador (1):\n");
                    imprimirTabuleiro(tabJogador1);
                    System.out.println("Tabuleiro Jogador (2):\n");
                    imprimirTabuleiro(tabJogador2);
                    System.out.println("Fim de jogo!");
                break;
                case 0:
                    // Encerra o programa
                    start = false;
                break;
                default:
                    System.out.println("Opção inválida.");
                break;
            }
            // testa se o jogo pode ser finalizado
            if (!start) {
                break;
            }
            // Jogar novamente?
            System.out.println("\nDeseja jogar novamente?");
            System.out.println("[1] Sim");
            System.out.println("[0] Não");
            System.out.print("Escolha uma opção: ");
            int jogarNovamente = ler.nextInt();
            if (jogarNovamente == 0) {
                start = false;
            }
            if (jogarNovamente != 1 && jogarNovamente != 0) {
                System.out.println("Opção inválida! Optamos por sair do jogo para você.");
                start = false;
            }
        }
        System.out.println("Obrigado por jogar! Até mais!");
        ler.close();
    }

    public static int letraParaNumero() {
        Scanner ler = new Scanner(System.in);
        String letra = ler.next().toUpperCase();
        String alfabeto = "ABCDEFGHIJ";
        int y = alfabeto.indexOf(letra);
        return y;
    }

    // Aloca automaticamente os barcos no tabuleiro
    private static void automatic(char[][] tabuleiro) {
        Random rand = new Random();
        for (int i = 0; i < QUANTIDADE_BARCOS; i++) {
            int tamanho = tamanhosBarcos[i];
            char caracter = caracteresBarcos[i];
            boolean alocado = false;
            while (!alocado) {
                int x = rand.nextInt(TAMANHO_TABULEIRO);
                int y = rand.nextInt(TAMANHO_TABULEIRO);
                int direcao = rand.nextInt(2); // 0 - horizontal, 1 - vertical
                if (direcao == 0) {
                    if (x + tamanho <= TAMANHO_TABULEIRO) {
                        boolean colisao = false;
                        for (int j = x; j < x + tamanho; j++) {
                            if (tabuleiro[j][y] != ' ') {
                                colisao = true;
                                break;
                            }
                        }
                        if (!colisao) {
                            for (int j = x; j < x + tamanho; j++) {
                                tabuleiro[j][y] = caracter;
                            }
                            alocado = true;
                        }
                    }
                } else {
                    if (y + tamanho <= TAMANHO_TABULEIRO) {
                        boolean colisao = false;
                        for (int j = y; j < y + tamanho; j++) {
                            if (tabuleiro[x][j] != ' ') {
                                colisao = true;
                                break;
                            }
                        }
                        if (!colisao) {
                            for (int j = y; j < y + tamanho; j++) {
                                tabuleiro[x][j] = caracter;
                            }
                            alocado = true;
                        }
                    }
                }
            }
        }
    }

    private static void manual(char[][] tabuleiro, Scanner ler) {
        for (int i = 0; i < QUANTIDADE_BARCOS; i++) {
            int tamanho = tamanhosBarcos[i];
            char caracter = caracteresBarcos[i];
            int x;
            char direcao;
            boolean error = true;
            while (error) {
                System.out.println("\nPosicione o barco " + caracter + " (tamanho: " + tamanho + ")");
                System.out.print("Digite a coordenada x (0 a 9): ");
                if (ler.hasNextInt()) {
                    x = ler.nextInt();
                } else {
                    System.out.println("\nCoordenada 'X' inválida. Informe os campos novamente!");
                    error = true;
                    ler.nextLine();
                    continue;
                }
                System.out.print("Digite a coordenada y (A a J): ");
                int y = letraParaNumero();
                if (y != -1) {
                    System.out.print("[V] vertical, [H] horizontal : ");
                    direcao = ler.next().toUpperCase().charAt(0);
                    ler.nextLine();
                    while (direcao != 'V' && direcao != 'H') {
                        System.out.println("Direção inválida, deve ser H o V");
                        System.out.print("[V] vertical, [H] horizontal : ");
                        direcao = ler.next().toUpperCase().charAt(0);
                    }
                    if (direcao == 'V') {
                        if (x + tamanho <= TAMANHO_TABULEIRO) {
                            boolean colisao = false;
                            for (int j = x; j < x + tamanho; j++) {
                                if (tabuleiro[j][y] != ' ') {
                                    colisao = true;
                                    break;
                                }
                            }
                            if (!colisao) {
                                for (int j = x; j < x + tamanho; j++) {
                                    tabuleiro[j][y] = caracter;
                                }
                                System.out.println("Barco alocado com sucesso!\n");
                                error = false;
                                imprimirTabuleiro(tabuleiro);

                            } else {
                                System.out.println("Posição inválida! Barcos colidem.\n");
                                error = true;
                            }
                        } else {
                            System.out.println("Posição inválida! O barco não cabe no tabuleiro.\n");
                            error = true;
                        }
                    }
                    if (direcao == 'H') {
                        if (y + tamanho <= TAMANHO_TABULEIRO) {
                            boolean colisao = false;
                            for (int j = y; j < y + tamanho; j++) {
                                if (tabuleiro[x][j] != ' ') {
                                    colisao = true;
                                    break;
                                }
                            }
                            if (!colisao) {
                                for (int j = y; j < y + tamanho; j++) {
                                    tabuleiro[x][j] = caracter;
                                }
                                System.out.println("Barco alocado com sucesso!\n");
                                error = false;
                                imprimirTabuleiro(tabuleiro);
                            } else {
                                System.out.println("Posição inválida! Barcos colidem.\n");
                                error = true;
                            }
                        } else {
                            System.out.println("Posição inválida! O barco não cabe no tabuleiro.\n");
                            error = true;
                        }
                    }
                } else {
                    error = true;
                    System.out.println("Coordenada inválida!");
                }
            }
        }
    }

    // Executa o jogo contra a máquina
    private static void SinglePlayer(char[][] tabuleiro1, char[][] tabuleiro2, Scanner ler) {
        boolean fimJogo = false;
        int x;
        // Contadores para verificar vitória
        int contShot1 = 0;
        int contShot2 = 0;

        // Matriz para registrar os tiros realizados
        boolean[][] tirosRealizados = new boolean[10][10];

        while (!fimJogo) {
            // Jogador 1 atira
            System.out.println("\nSua vez de jogar!");
            System.out.print("Digite a coordenada x (0 a 9): ");
            if (ler.hasNextInt()) {
                x = ler.nextInt();
                if (x < 0 || x > 9) {
                    System.out.println("\nCoordenada 'X' inválida. Informe os campos novamente!");
                    fimJogo = false;
                    ler.nextLine();
                    continue;
                }
            } else {
                System.out.println("\nCoordenada 'X' inválida. Informe os campos novamente!");
                fimJogo = false;
                ler.nextLine();
                continue;
            }

            System.out.print("Digite a coordenada y (A a J): ");
            int y = letraParaNumero();

            if (y != -1) {
                if (!tirosRealizados[x][y]) {
                    tirosRealizados[x][y] = true; // Marca a coordenada como verdadeira, assim não atirando no mesmo lugar.
                    if (tabuleiro2[x][y] == ' ') {
                        System.out.println("Você errou o tiro!");
                        tabuleiro2[x][y] = '*';
                    } else if (tabuleiro2[x][y] != 'X') {
                        char caracter = tabuleiro2[x][y];
                        System.out.println("Você acertou um barco " + caracter + "!");
                        tabuleiro2[x][y] = 'X';
                        contShot1++;
                        if (contShot1 == 20) {
                            System.out.println("O jogador 1 venceu!");
                            fimJogo = true;
                        }
                    } else {
                        System.out.println("Você já atirou nestas coordenadas!");
                    }
                } else {
                    System.out.println("Você já atirou nestas coordenadas. Perdeu a vez!");
                }
                if (!fimJogo) {
                    // Jogador 2 atira aleatoriamente
                    Random rand = new Random();
                    int x2 = rand.nextInt(TAMANHO_TABULEIRO);
                    int y2 = rand.nextInt(TAMANHO_TABULEIRO);
                    if (tabuleiro1[x2][y2] == ' ') {
                        System.out.println("Jogador 2 errou o tiro!");
                        tabuleiro1[x2][y2] = '*';
                    } else if (tabuleiro1[x2][y2] != 'X') {
                        char caracter = tabuleiro1[x2][y2];
                        System.out.println("Jogador 2 acertou um barco " + caracter + "!");
                        tabuleiro1[x2][y2] = 'X';
                        contShot2++;
                        if (contShot2 == 20) {
                            System.out.println("O jogador 2 venceu!");
                            fimJogo = true;
                        }
                    }
                    // Imprime os tabuleiros
                    System.out.println("\nSeu tabuleiro:");
                    imprimirTabuleiro(tabuleiro1);
                    System.out.println("\nTabuleiro do Adversário:");
                    imprimirTabuleiro(tabuleiro2);
                }
            } else {
                System.out.println("Coordenada Y inválida (A a J), preencha os campos novamente!");
            }
        }
    }

    // Modo Multiplayer
    private static void MultiPlayer(char[][] tabuleiro1, char[][] tabuleiro2, Scanner ler) {
        boolean fimJogo = false;
        int x = 0;
        int x2 =0;
        // Contadores para verificar vitória
        int contShot1 = 0;
        int contShot2 = 0;
        // Matriz para registrar os tiros realizados
        boolean[][] tirosRealizados = new boolean[10][10];
        boolean[][] tirosRealizados2 = new boolean[10][10];
        while (!fimJogo) {
            // Jogador 1 atira
            System.out.println("\nSua vez de jogar! Player (1):");
            System.out.print("Digite a coordenada x (0 a 9): ");
            if (ler.hasNextInt()) {
                x = ler.nextInt();
                if (x < 0 || x > 9) {
                    System.out.println("\nCoordenada 'X' inválida. Informe os campos novamente!");
                    fimJogo = false;
                    ler.nextLine();
                    continue;
                }
            } else {
                System.out.println("\nCoordenada 'X' inválida. Informe os campos novamente!");
                fimJogo = false;
                ler.nextLine();
                continue;
            }
            System.out.print("Digite a coordenada y (A a J): ");
            int y = letraParaNumero();
            if (y != -1) {
                if (!tirosRealizados[x][y]) {
                    tirosRealizados[x][y] = true; // Marca a coordenada como verdadeira, assim não atirando no mesmo lugar.
                    if (tabuleiro2[x][y] == ' ') {
                        System.out.println("Jogador(1) errou o tiro!");
                        tabuleiro2[x][y] = '*';
                    } else if (tabuleiro2[x][y] != 'X') {
                        char caracter = tabuleiro2[x][y];
                        System.out.println("Jogador(1) acertou um barco " + caracter + "!");
                        tabuleiro2[x][y] = 'X';
                        contShot1++;
                        if (contShot1 == 20) {
                            System.out.println("O jogador (1) venceu!");
                            fimJogo = true;
                        }
                    } else {
                        System.out.println("Você já atirou nestas coordenadas!");
                    }
                } else {
                    System.out.println("Você já atirou nestas coordenadas. Perdeu a vez!");
                }
            
                if (!fimJogo) {
                    // Jogador 2 atira
                    System.out.println("\nSua vez de jogar! Player (2):");
                    System.out.print("Digite a coordenada x (0 a 9): ");
                    if (ler.hasNextInt()) {
                        x2 = ler.nextInt();
                        if (x2 < 0 || x2 > 9) {
                            System.out.println("\nCoordenada 'X' inválida. Informe os campos novamente!");
                            fimJogo = false;
                            ler.nextLine();
                            continue;
                        }
                    } else {
                        System.out.println("\nCoordenada 'X' inválida. Informe os campos novamente!");
                        fimJogo = false;
                        ler.nextLine();
                        continue;
                    }
                    System.out.print("Digite a coordenada y (A a J): ");
                    int y2 = letraParaNumero();
                    if (y2 != -1) {
                        if (!tirosRealizados2[x2][y2]) {
                            tirosRealizados2[x2][y2] = true; // Marca a coordenada como verdadeira, assim não atirando no mesmo lugar.
                            if (tabuleiro1[x2][y2] == ' ') {
                                System.out.println("Jogador(2) errou o tiro!");
                                tabuleiro1[x2][y2] = '*';
                            } else if (tabuleiro1[x2][y2] != 'X') {
                                char caracter = tabuleiro1[x][y];
                                System.out.println("Jogador(2) acertou um barco " + caracter + "!");
                                tabuleiro1[x2][y2] = 'X';
                                contShot2++;
                                if (contShot2 == 20) {
                                    System.out.println("O jogador 2 venceu!");
                                    fimJogo = true;
                                }
                            } else {
                                System.out.println("Você já atirou nestas coordenadas!");
                            }
                        } else {
                            System.out.println("Você já atirou nestas coordenadas. Perdeu a vez!");
                        }
                        // Imprime os tabuleiros
                        System.out.println("\nTabuleiro player (1):");
                        imprimirTabuleiro(tabuleiro1);
                        System.out.println("\nTabuleiro player (2):");
                        imprimirTabuleiro(tabuleiro2);
                    }
                } else {
                    System.out.println("Coordenada Y inválida (A a J), preencha os campos novamente!");
                }
            }
        }
    }

    // Imprime o tabuleiro
    private static void imprimirTabuleiro(char[][] tabuleiro) {
        System.out.println("       A    B    C    D    E    F    G    H    I    J  \n");
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            System.out.printf("  %d  ", i);
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                // Exibe o conteúdo da célula do tabuleiro
                System.out.printf("[ %c ]", tabuleiro[i][j]);
            }
            System.out.println("\n");
        }
    }
}