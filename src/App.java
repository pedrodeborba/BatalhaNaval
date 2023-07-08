import java.util.Scanner; //Importando Scanner
import java.util.Random; //Importando Random

public class App {
    private static final int TAMANHO_TABULEIRO = 10; // Tamanho do tabuleiro 10x10
    private static final int QUANTIDADE_BARCOS = 10; // Quantidade de dez barcos (4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
    private static int[] tamanhosBarcos = { 4, 3, 3, 2, 2, 2, 1, 1, 1, 1 }; // Vetor com os dez barcos
    private static char[] caracteresBarcos = { 'N', 'I', 'I', 'L', 'L', 'L', 'C', 'C', 'C', 'C' }; // Transformar os dez barcos em caracteres
    private static char[][] tabJogador1; // Tabuleiro do primeiro jogador
    private static char[][] tabJogador2; // Tabuleiro do segundo jogador

    public static void main(String[] args) { // Método principal
        Scanner ler = new Scanner(System.in); // Chamando Scanner
        boolean start = true; // Variável para finalizar o game

        while (start) {
            tabJogador1 = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO]; // Atribuindo o valor [10][10]
            tabJogador2 = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO]; // Atribuindo o valor [10][10]
            limparTabuleiro();// Deixa todos os espaços do tabuleiro vazios

            System.out.println("\nBATALHA NAVAL");
            System.out.println("[1] Jogar contra o computador");
            System.out.println("[2] Jogar multiplayer");
            System.out.println("[0] Sair");
            System.out.print("Escolha uma opção: ");
            while (!ler.hasNextInt()) { // Testa se o usuário realemnte informou um número
                System.out.println("Opção inválida. Por favor, digite um número válido.");
                ler.next(); // Descarta o valor inválido digitado pelo usuário
            }
            int opcao = ler.nextInt();

            switch (opcao) {
                case 1: // Jogar contra a máquina
                    formaAlocar(); // Como deseja alocar seus barcos?
                    char forma = ler.next().toUpperCase().charAt(0);
                    switch (forma) {
                        case 'A': // Aloca automaticamente os barcos dos jogadores
                            gameAuto(ler); // Chama o método 'gameAuto'
                            SinglePlayer(tabJogador1, tabJogador2, ler); // Inicia o jogo no modo Single Player
                        break;
                        case 'B': // Aloca manualmente os barcos dos jogadores
                            manual(tabJogador1, ler); // Chama o método 'manual' para o tabuleiro do jogador 1
                            automatic(tabJogador2); // Chama o método 'automatic' para o tabuleiro do jogador 2
                            SinglePlayer(tabJogador1, tabJogador2, ler); // Inicia o jogo no modo Single Player
                        break;
                        default:
                            System.out.println("Optamos por alocar seus barcos automaticamente!\n");
                            gameAuto(ler);
                            SinglePlayer(tabJogador1, tabJogador2, ler);
                        break;
                    }
                break;
                case 2: // Jogar Multiplayer
                    System.out.println("\nPlayer (1):");
                    formaAlocar(); // Como deseja alocar seus barcos?
                    char alocaP1 = ler.next().toUpperCase().charAt(0); // Pega informações da forma de alocar (player 1)
                    System.out.println("\nPlayer (2):");
                    formaAlocar(); // Como deseja alocar seus barcos?
                    char alocaP2 = ler.next().toUpperCase().charAt(0); // Pega informações da forma de alocar (player 2)
                    switch (alocaP1) { // alocar barcos do jogador 1
                        case 'A': // Forma automatica
                            automatic(tabJogador1); // Chama o método automatico para o jogador 1
                        break;
                        case 'B': // Forma manual
                            manual(tabJogador1, ler); // Chama o método manual para o jogador 1
                        break;
                        default: // Aloca automaticamente caso digitar uma opção inválida
                            System.out.println("Opção invalida!!!\nOptamos por alocar seus barcos automaticamente.\n");
                            automatic(tabJogador1); // Chama o método automatico para o jogador 1
                        break;
                    }
                    switch (alocaP2) { // alocar barcos do jogador 2
                        case 'A': // Forma automatica
                            automatic(tabJogador2); // Chama o método automatico para o jogador 2
                            MultiPlayer(tabJogador1, tabJogador2, ler); // Inicia o jogo Multiplayer
                        break;
                        case 'B':
                            manual(tabJogador2, ler); // Chama o método manual para o jogador 2
                            MultiPlayer(tabJogador1, tabJogador2, ler); // Inicia o jogo Multiplayer
                        break;
                        default:
                            System.out.println("Opção invalida!!!\nOptamos por alocar seus barcos automaticamente.\n");
                            automatic(tabJogador2); // Chama o método automatico para o jogador 2
                            MultiPlayer(tabJogador1, tabJogador2, ler); // Inicia o jogo Multiplayer
                        break;
                    }
                    System.out.println("Tabuleiro Jogador (1):\n");
                    imprimirTabuleiro(tabJogador1); // Exibe tabuleiro
                    System.out.println("Tabuleiro Jogador (2):\n");
                    imprimirTabuleiro(tabJogador2); // Exibe tabuleiro
                    System.out.println("Fim de jogo!"); // Finaliza o jogo
                break;
                case 0:
                    start = false; // Encerra o programa
                break;
                default:
                    System.out.println("Opção inválida!\nDigite (1) para jogar novamente e informe números válidos.\nCaso queira sair, informe (0)");
                break;
            }
            if (!start) { // Testa se o jogo pode ser finalizado
                break;
            }
            jogarNovamente(ler, start); // Chama o método (Deseja jogar novamente?)
        }
        System.out.println("Obrigado por jogar! Até mais!");
        ler.close(); // Limpa os dados do Scanner
    }

    private static void automatic(char[][] tabuleiro) { // Aloca automaticamente os barcos no tabuleiro
        Random rand = new Random(); // Gera valores aleatórios
        for (int i = 0; i < QUANTIDADE_BARCOS; i++) { // Laço de repetição para alocar os barcos
            int tamanho = tamanhosBarcos[i]; // Determina o tamanho do barco. Padrao: { 4, 3, 3, 2, 2, 2, 1, 1, 1, 1 }
            char caracter = caracteresBarcos[i]; // Transforma o barco em um caractere único. Padrão: { 'N', 'I', 'I', 'L', 'L', 'L', 'C', 'C', 'C', 'C' }
            boolean alocado = false; // Verifica se o barco foi alocado
            while (!alocado) { // Executa enquanto o barco não estiver alocado
                int x = rand.nextInt(TAMANHO_TABULEIRO); // Gera uma posição aleatória no eixo x
                int y = rand.nextInt(TAMANHO_TABULEIRO); // Gera uma posição aleatória no eixo y
                int direcao = rand.nextInt(2); // Gera um valor aleatório (0 ou 1) para determinar a direção (horizontal=0 ou vertical=1)
                if (direcao == 0) { // Se a direção for horizontal
                    if (x + tamanho <= TAMANHO_TABULEIRO) { // Verifica se é possível alocar o barco horizontalmente sem ultrapassar o tamanho do tabuleiro
                        boolean colisao = false; // Criado para verificar se há colisão com outros barcos
                        for (int j = x; j < x + tamanho; j++) { // Laço de repetição para verificar se há colisão com outros barcos na posição horizontal
                            if (tabuleiro[j][y] != ' ') { // Se encontrar um espaço ocupado, há colisão
                                colisao = true;
                                break;
                            }
                        }
                        if (!colisao) { // Se não houver colisão, alocar o barco horizontalmente
                            for (int j = x; j < x + tamanho; j++) { // Laço de repetição para alocar o barco no tabuleiro horizontalmente
                                tabuleiro[j][y] = caracter;
                            }
                            alocado = true; // O barco foi alocado com sucesso, encerra o laço while
                        }
                    }
                } else { // Se a direção for vertical
                    if (y + tamanho <= TAMANHO_TABULEIRO) { // Verifica se é possível alocar o barco verticalmente sem ultrapassar o tamanho do tabuleiro
                        boolean colisao = false; // Variável de controle para verificar se há colisão com outros barcos
                        for (int j = y; j < y + tamanho; j++) { // Laço de repetição para verificar se há colisão com outros barcos na posição vertical
                            if (tabuleiro[x][j] != ' ') { // Se encontrar um espaço ocupado, há colisão
                                colisao = true;
                                break;
                            }
                        }
                        if (!colisao) { // Se não houver colisão, alocar o barco verticalmente
                            for (int j = y; j < y + tamanho; j++) { // Laço de repetição para alocar o barco no tabuleiro verticalmente
                                tabuleiro[x][j] = caracter;
                            }
                            alocado = true; // O barco foi alocado com sucesso, encerra o laço while
                        }
                    }
                }
            }
        }
    }

    private static void manual(char[][] tabuleiro, Scanner ler) { // alocar barcos manualmente
        System.out.println("Aloque seus barcos. \n");
        for (int i = 0; i < QUANTIDADE_BARCOS; i++) { // Laço de repetição padrão
            int tamanho = tamanhosBarcos[i]; // Determina o tamanho do barco. Padrao: { 4, 3, 3, 2, 2, 2, 1, 1, 1, 1 }
            char caracter = caracteresBarcos[i]; // Transforma o barco em um caractere único. Padrão: { 'N', 'I', 'I', 'L', 'L', 'L', 'C', 'C', 'C', 'C' }
            int x; // Linha
            char direcao; // Variável para armazenar a direção (vertical ou horizontal) informada pelo jogador
            boolean error = true; // Variável de controle para verificar se houve algum erro na alocação do barco
            while (error) { // Executa enquanto houver um erro na alocação do barco
                System.out.println("\nPosicione o barco " + caracter + " (tamanho: " + tamanho + ")");
                System.out.print("Digite a coordenada x (0 a 9): ");
                if (ler.hasNextInt()) { // Verifica se foi digitado um número inteiro
                    x = ler.nextInt(); // Lê o número inteiro digitado
                } else {
                    System.out.println("\nCoordenada 'X' inválida. Informe os campos novamente!");
                    error = true; // Repete o laço
                    ler.nextLine(); // Limpa os dados do Scanner
                    continue;
                }
                System.out.print("Digite a coordenada y (A a J): ");
                int y = letraParaNumero(ler); // Chama o método letraParaNumero para converter a letra informada pelo jogador em um número
                if (y != -1) { // Verifica se a letra foi convertida corretamente. Se for igual a -1 é inválida
                    System.out.print("[V] vertical, [H] horizontal : ");
                    direcao = ler.next().toUpperCase().charAt(0); // Lê a direção informada pelo jogador e converte para maiúscula
                    ler.nextLine();
                    while (direcao != 'V' && direcao != 'H') { // Valida se a direção informada é 'V' ou 'H'
                        System.out.println("Direção inválida, deve ser H ou V");
                        System.out.print("[V] vertical, [H] horizontal : ");
                        direcao = ler.next().toUpperCase().charAt(0);
                    }
                    if (direcao == 'V') { // Se a direção for vertical
                        if (x + tamanho <= TAMANHO_TABULEIRO) { // Verifica se é possível alocar o barco verticalmente sem ultrapassar o tamanho do tabuleiro
                            boolean colisao = false; // Variável de controle para verificar se há colisão com outros barcos
                            for (int j = x; j < x + tamanho; j++) { // Laço de repetição para verificar se há colisão com outros barcos na posição vertical
                                if (tabuleiro[j][y] != ' ') { // Se encontrar um espaço ocupado, há colisão
                                    colisao = true;
                                    break;
                                }
                            }
                            if (!colisao) { // Se não houver colisão, alocar o barco verticalmente
                                for (int j = x; j < x + tamanho; j++) { // Laço de repetição para alocar o barco no tabuleiro verticalmente
                                    tabuleiro[j][y] = caracter;
                                }
                                System.out.println("Barco alocado com sucesso!\n");
                                error = false; // Não houve erro na alocação, encerra o laço while
                                imprimirTabuleiro(tabuleiro);
                            } else {
                                System.out.println("Posição inválida! Barcos colidem.\n");
                                error = true; // Houve colisão, continua no laço while para que o jogador informe uma nova posição
                            }
                        } else {
                            System.out.println("Posição inválida! O barco não cabe no tabuleiro.\n");
                            error = true; // O barco não cabe no tabuleiro, continua no laço while para que o jogador informe uma nova posição
                        }
                    }
                    if (direcao == 'H') { // Se a direção for horizontal
                        if (y + tamanho <= TAMANHO_TABULEIRO) { // Verifica se é possível alocar o barco horizontalmente sem ultrapassar o tamanho do tabuleiro
                            boolean colisao = false; // Variável de controle para verificar se há colisão com outros barcos
                            for (int j = y; j < y + tamanho; j++) { // Laço de repetição para verificar se há colisão com outros barcos na posição horizontal
                                if (tabuleiro[x][j] != ' ') { // Se encontrar um espaço ocupado, há colisão
                                    colisao = true;
                                    break;
                                }
                            }
                            if (!colisao) { // Se não houver colisão, alocar o barco horizontalmente
                                for (int j = y; j < y + tamanho; j++) { // Laço de repetição para alocar o barco no tabuleiro horizontalmente
                                    tabuleiro[x][j] = caracter;
                                }
                                System.out.println("Barco alocado com sucesso!\n");
                                error = false; // Não houve erro na alocação, encerra o laço while
                                imprimirTabuleiro(tabuleiro);
                            } else {
                                System.out.println("Posição inválida! Barcos colidem.\n");
                                error = true; // Houve colisão, continua no laço while para que o jogador informe uma nova posição
                            }
                        } else {
                            System.out.println("Posição inválida! O barco não cabe no tabuleiro.\n");
                            error = true; // O barco não cabe no tabuleiro, continua no laço while para que o jogador informe uma nova posição
                        }
                    }
                } else {
                    error = true; // A letra não foi convertida corretamente, continua no laço while para que o jogador informe uma nova posição
                    System.out.println("Coordenada inválida!");
                }
            }
        }
    }

    private static void SinglePlayer(char[][] tabuleiro1, char[][] tabuleiro2, Scanner ler) { // Executa o jogo contra a
                                                                                              // máquina
        int x; // Linha
        int contShot1 = 0; // Contador de tiros acertados do jogador 1
        int contShot2 = 0; // Contador de tiros acertados do jogador 2
        boolean[][] tirosRealizados = new boolean[10][10]; // Matriz para registrar os tiros realizados
        boolean fimJogo = false; // Variável de controle para verificar se o jogo chegou ao fim

        while (!fimJogo) {
            System.out.println("\nSua vez de jogar!"); // Jogador 1 atira
            System.out.print("Digite a coordenada x (0 a 9): ");
            if (ler.hasNextInt()) { // Verifica se foi digitado um número inteiro
                x = ler.nextInt(); // Lê o número inteiro digitado
                if (x < 0 || x > 9) { // Verifica se o X é válido (0 - 9)
                    System.out.println("\nCoordenada 'X' inválida. Informe os campos novamente!");
                    fimJogo = false; // Repete o laço
                    ler.nextLine();
                    continue;
                }
            } else { // Caso não for um número digitado, coordenada inválida
                System.out.println("\nCoordenada 'X' inválida. Informe os campos novamente!");
                fimJogo = false;
                ler.nextLine();
                continue;
            }
            System.out.print("Digite a coordenada y (A a J): ");
            int y = letraParaNumero(ler); // Converte a letra informada pelo jogador em um número
            if (y != -1) { // Verifica se a letra foi convertida corretamente
                if (!tirosRealizados[x][y]) { // Verifica se a coordenada já foi atirada antes
                    tirosRealizados[x][y] = true; // Marca a coordenada como verdadeira para indicar que já foi atirada
                    if (tabuleiro2[x][y] == ' ') { // Verifica se a coordenada do tabuleiro do jogador 2 é vazia
                        System.out.println("Você errou o tiro!");
                        tabuleiro2[x][y] = '*'; // Marca a coordenada como tiro errado ('*')
                    } else if (tabuleiro2[x][y] != 'X') { // Verifica se a coordenada do tabuleiro do jogador 2 não foi atingida anteriormente
                        char caracter = tabuleiro2[x][y];
                        System.out.println("Você acertou um barco " + caracter + "!");
                        tabuleiro2[x][y] = 'X'; // Marca a coordenada como tiro acertado ('X')
                        contShot1++; // Incrementa o contador de tiros acertados do jogador 1
                        if (contShot1 == 20) { // Verifica se o jogador 1 acertou todos os tiros nos barcos do jogador 2
                            System.out.println("O jogador 1 venceu!");
                            fimJogo = true; // Indica que o jogo chegou ao fim
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
                    int x2 = rand.nextInt(TAMANHO_TABULEIRO); // Gera uma coordenada x aleatória para o jogador 2
                    int y2 = rand.nextInt(TAMANHO_TABULEIRO); // Gera uma coordenada y aleatória para o jogador 2
                    if (tabuleiro1[x2][y2] == ' ') { // Verifica se a coordenada é vazia
                        System.out.println("Jogador 2 errou o tiro!");
                        tabuleiro1[x2][y2] = '*'; // Marca a coordenada como tiro errado ('*')
                    } else if (tabuleiro1[x2][y2] != 'X') { // Verifica se a coordenada do tabuleiro do jogador 1 não foi atingida anteriormente
                        char caracter = tabuleiro1[x2][y2];
                        System.out.println("Jogador 2 acertou um barco " + caracter + "!");
                        tabuleiro1[x2][y2] = 'X'; // Marca a coordenada como tiro acertado ('X')
                        contShot2++; // Incrementa o contador de tiros acertados do jogador 2
                        if (contShot2 == 20) { // Verifica se o jogador 2 acertou todos os tiros nos barcos do jogador 1
                            System.out.println("O jogador 2 venceu!");
                            fimJogo = true; // Indica que o jogo chegou ao fim
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

    private static void MultiPlayer(char[][] tabuleiro1, char[][] tabuleiro2, Scanner ler) {
        boolean fimJogo = false; // Variável de controle para verificar se o jogo chegou ao fim
        int x = 0; // Variável para armazenar a coordenada x informada pelo jogador 1
        int x2 = 0; // Variável para armazenar a coordenada x informada pelo jogador 2
        int contShot1 = 0; // Contador de tiros acertados do jogador 1
        int contShot2 = 0; // Contador de tiros acertados do jogador 2
        boolean[][] tirosRealizados = new boolean[10][10]; // Matriz para registrar os tiros realizados pelo jogador 1
        boolean[][] tirosRealizados2 = new boolean[10][10]; // Matriz para registrar os tiros realizados pelo jogador 2

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
            int y = letraParaNumero(ler);
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
                    int y2 = letraParaNumero(ler);
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

    private static void imprimirTabuleiro(char[][] tabuleiro) { // Imprime o tabuleiro
        System.out.println("       A    B    C    D    E    F    G    H    I    J  \n");
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            System.out.printf("  %d  ", i);
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                System.out.printf("[ %c ]", tabuleiro[i][j]);
            }
            System.out.println("\n");
        }
    }

    public static int letraParaNumero(Scanner ler) { // Método para transformar letras em números. EX: A = 0, B = 1...
        String letra = ler.next().toUpperCase(); // Transforma a letra em minúscula
        String alfabeto = "ABCDEFGHIJ"; // Armazena letras (A - J)
        int y = alfabeto.indexOf(letra); // Método para transformar uma letra em seu equivalente numérico
        return y;
    }

    private static void formaAlocar() {
        System.out.println("\nComo deseja alocar seus barcos?");
        System.out.println("[A] Automaticamente");
        System.out.println("[B] Manualmente");
        System.out.print("Escolha uma opção: ");
    }

    private static void gameAuto(Scanner ler) {
        System.out.println("Barcos alocados automaticamente (sucesso)!\n");
        automatic(tabJogador1);
        automatic(tabJogador2);
        System.out.println("\nPronto, seus barcos já foram alocados!\n");
        System.out.println("Seu tabuleiro:\n");
        imprimirTabuleiro(tabJogador1);
    }

    private static void limparTabuleiro() {
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) { // Laço de repetição para deixar os tabuleiros vazios
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                tabJogador1[i][j] = ' ';
                tabJogador2[i][j] = ' ';
            }
        }
    }

    private static void jogarNovamente(Scanner ler, boolean start) {
        System.out.println("\nDeseja jogar novamente?");
        System.out.println("[1] Sim");
        System.out.println("[0] Não");
        System.out.print("Escolha uma opção: ");
        int jogarNovamente = ler.nextInt();
        if (jogarNovamente == 0) { // Finaliza o programa caso a opção = 0
            start = false;
        }
        if (jogarNovamente != 1 && jogarNovamente != 0) { // Nenhuma das opções, o programa fecha automaticamente.
            System.out.println("Opção inválida! Optamos por sair do jogo para você.");
        }
    }

}