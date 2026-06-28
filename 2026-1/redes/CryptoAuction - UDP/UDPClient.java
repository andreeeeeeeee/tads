import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {

    private static final String HOST = "localhost";
    private static final int PORTA_SERVIDOR = 10000;
    private static boolean registrado = false;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress servidor = InetAddress.getByName(HOST);

            System.out.println("=================================");
            System.out.println("      CRYPTO AUCTION CLIENT      ");
            System.out.println("=================================");
            System.out.println("Servidor: " + HOST + ":" + PORTA_SERVIDOR);
            System.out.println("Porta local: " + socket.getLocalPort());
            System.out.println();

            Thread receptor = new Thread(() -> {
                try {
                    while (true) {

                        byte[] buffer = new byte[1024];
                        DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);

                        socket.receive(pacote);

                        String resposta = new String(
                            pacote.getData(),
                            0,
                            pacote.getLength()
                        );

                        if (resposta.contains("Cliente aceito para o jogo")) {
                            registrado = true;
                        }

                        System.out.println();
                        System.out.println("---------------------------------");
                        System.out.println("[SERVIDOR]");
                        System.out.println(resposta);
                        System.out.println("---------------------------------");
                    }
                } catch (Exception e) {
                    if (!socket.isClosed()) {
                        System.out.println("Erro ao receber mensagens.");
                        e.printStackTrace();
                    }
                }
            });

            receptor.setDaemon(true);
            receptor.start();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                exibirMenu();
                System.out.print("Escolha uma opção: ");
                String opcao = scanner.nextLine();
                String mensagem;

                switch (opcao) {
                    case "1":
                        if (registrado) {
                            System.out.println("Você já está registrado.");
                            break;
                        }

                        System.out.print("Digite seu nickname: ");
                        String nickname = scanner.nextLine();

                        mensagem = "/registrar/" + nickname;
                        enviarMensagem(socket, servidor, mensagem);
                        Thread.sleep(300);

                        break;
                    case "2":
                        System.out.print("Digite o valor do lance: ");
                        String valor = scanner.nextLine();

                        mensagem = "/lance/" + valor;
                        enviarMensagem(socket, servidor, mensagem);
                        Thread.sleep(300);

                        break;
                    case "3":
                        mensagem = "/nova-partida";
                        enviarMensagem(socket, servidor, mensagem);
                        Thread.sleep(300);

                        break;
                    case "4":
                        System.out.println("Encerrando cliente...");

                        socket.close();
                        scanner.close();

                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro no cliente:");
            e.printStackTrace();
        }
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("============== MENU =============");
        if (!registrado) {
            System.out.println("1 - Registrar Jogador");
        }
        System.out.println("2 - Enviar Lance");
        System.out.println("3 - Nova Partida");
        System.out.println("4 - Sair");
        System.out.println("=================================");
    }

    private static void enviarMensagem(DatagramSocket socket, InetAddress servidor, String mensagem) {
        try {
            byte[] dados = mensagem.getBytes();

            DatagramPacket pacote = new DatagramPacket(
                dados,
                dados.length,
                servidor,
                PORTA_SERVIDOR
            );

            socket.send(pacote);
        } catch (Exception e) {
            System.out.println("Erro ao enviar mensagem.");
            e.printStackTrace();
        }
    }
}