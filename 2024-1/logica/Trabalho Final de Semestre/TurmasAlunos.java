import java.util.Scanner;

public class TurmasAlunos {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    Boolean rodar = true;
    String[] turmas = new String[0];
    String[] professores = new String[0];
    String[][] alunos = new String[0][0];

    // String[] turmas = { "A", "B", "C" };
    // String[] professores = { "prof a", "prof b", "prof c" };
    // String[][] alunos = {
    //    { "abc", "acb" },
    //    { "bac", "bca" },
    //    { "cab", "cba" }
    // };

    System.out.println("\nOlá, seja bem-vindo(a) ao Sistema de Gerenciamento de Turmas e Alunos!");
    while (rodar) {
      System.out.println("\nEscolha uma das opções abaixo:");
      System.out.println(" 1: Adicionar nova turma");
      System.out.println(" 2: Adicionar aluno a uma turma");
      System.out.println(" 3: Buscar turmas por nome do professor");
      System.out.println(" 4: Listar alunos de uma turma");
      System.out.println(" 5: Transferir aluno entre turmas");
      System.out.println(" 6: Listar todas as turmas e alunos");
      System.out.println(" Outro: Sair");

      System.out.print("\n ");
      int escolha = in.nextInt();
      in.nextLine();

      switch (escolha) {
        case 1: {
          Boolean existe = false;

          String[] turmasAux = new String[turmas.length + 1];
          String[] professoresAux = new String[professores.length + 1];
          String[][] alunosAux = new String[alunos.length + 1][0];

          System.out.println("\n1 - Adicionar nova turma:");
          System.out.println("Digite o nome da nova turma:");
          String novaTurma = in.nextLine();
          System.out.println("Digite o nome do professor responsável:");
          String novoProf = in.nextLine();

          for (int i = 0; i < turmas.length && i < professores.length; i++) {
            if (turmas[i].equals(novaTurma) || professores[i].equals(novoProf)) {
              existe = true;
              break;
            }
          }

          if (!existe) {
            for (int i = 0; i <= turmas.length && i <= professores.length; i++) {
              if (i == turmas.length && i == professores.length) {
                turmasAux[i] = novaTurma;
                professoresAux[i] = novoProf;
              } else {
                turmasAux[i] = turmas[i];
                professoresAux[i] = professores[i];
                alunosAux[i] = alunos[i];
              }
            }
            turmas = turmasAux;
            professores = professoresAux;
            alunos = alunosAux;
          } else {
            System.out.println("\nTurma já existe e/ou professor já é responsável por outra turma.");
          }
        }
          break;
        case 2: {
          int i = 0;
          Boolean turmaExiste = false;

          System.out.println("\n2 - Adicionar aluno a uma turma:");
          System.out.println("Digite o nome da turma a qual o aluno será adicionado:");
          String turma = in.nextLine();

          for (; i < turmas.length; i++) {
            if (turmas[i].equals(turma)) {
              turmaExiste = true;
              break;
            }
          }

          if (turmaExiste) {
            System.out.println("Digite o nome do novo aluno:");
            String novoAluno = in.nextLine();
            String[] alunosTurma = alunos[i];
            String[] alunosAux = new String[alunosTurma.length + 1];

            for (int j = 0; j <= alunosTurma.length; j++) {
              if (j == alunosTurma.length) {
                alunosAux[j] = novoAluno;
              } else {
                alunosAux[j] = alunosTurma[j];
              }
            }

            alunos[i] = alunosAux;
          } else {
            System.out.println("Turma não encontrada.\n");
          }
        }
          break;
        case 3: {
          int i = 0;
          Boolean professorExiste = false;

          System.out.println("\n3 - Buscar turma por nome do professor:");
          System.out.println("Digite o nome do professor:");
          String professor = in.nextLine();

          for (; i < professores.length; i++) {
            if (professores[i].equals(professor)) {
              professorExiste = true;
              break;
            }
          }

          if (professorExiste) {
            System.out.printf("\nTurma: %s - Professor: %s\nAlunos: ", turmas[i], professores[i]);
            for (int j = 0; j < alunos[i].length; j++) {
              System.out.print(alunos[i][j] + " ");
            }
            System.out.println();
          } else {
            System.out.println("Professor não encontrado.\n");
          }
        }
          break;
        case 4: {
          int i = 0;
          Boolean encontrado = false;
          System.out.println("\n4 - Listar alunos de uma turma:");
          System.out.println("Digite o nome da turma ou do professor responsável:");
          String pesquisa = in.nextLine();

          for (; i < turmas.length && i < professores.length; i++) {
            if (turmas[i].equals(pesquisa) || professores[i].equals(pesquisa)) {
              encontrado = true;
              break;
            }
          }

          if (encontrado) {
            System.out.printf("\nTurma: %s - Professor: %s\nAlunos: ", turmas[i], professores[i]);
            for (int j = 0; j < alunos[i].length; j++) {
              System.out.print(alunos[i][j] + " ");
            }
            System.out.println();
          } else {
            System.out.println("Turma não encontrada.\n");
          }
        }
          break;
        case 5: {
          int i = 0;
          Boolean turmaExiste = false;

          System.out.println("\n5 - Transferir aluno entre turmas:");
          System.out.println("Digite o aluno que será transferido:");
          String aluno = in.nextLine();
          System.out.println("Digite a turma para a qual será transferido:");
          String turma = in.nextLine();

          for (; i < turmas.length; i++) {
            if (turmas[i].equals(turma)) {
              turmaExiste = true;
              break;
            }
          }

          if (turmaExiste) {
            int j = 0;
            Boolean alunoExiste = false;

            for (; j < alunos.length; j++) {
              for (int k = 0; k < alunos[j].length; k++) {
                if (alunos[j][k].equals(aluno)) {
                  alunoExiste = true;
                  break;
                }
              }
              if (alunoExiste) {
                break;
              }
            }

            if (alunoExiste) {
              String[] alunosTurmaAntiga = alunos[j];
              String[] alunosTurmaNova = alunos[i];
              String[] turmaAntiga = new String[alunosTurmaAntiga.length - 1];
              String[] turmaNova = new String[alunosTurmaNova.length + 1];

              for (int k = 0; k < turmaAntiga.length; k++) {
                turmaAntiga[k] = alunosTurmaAntiga[k];
              }
              for (int k = 0; k < turmaNova.length; k++) {
                if (k == turmaNova.length - 1) {
                  turmaNova[k] = aluno;
                } else {
                  turmaNova[k] = alunosTurmaNova[k];
                }
              }

              alunos[j] = turmaAntiga;
              alunos[i] = turmaNova;
            } else {
              System.out.println("\nAluno não encontrado.");
            }
          } else {
            System.out.println("\nTurma não encontrada.");
          }
        }
          break;
        case 6: {
          System.out.println("\n6 - Listar todas as turmas e alunos");
          for (int i = 0; i < turmas.length && i < professores.length; i++) {
            System.out.printf("\nTurma: %s - Professor: %s\nAlunos: ", turmas[i], professores[i]);
            for (int j = 0; j < alunos[i].length; j++) {
              if (j == alunos[i].length - 1) {
                System.out.print(alunos[i][j]);
              } else {
                System.out.print(alunos[i][j] + ", ");
              }
            }
            System.out.println();
          }
        }
          break;

        default:
          rodar = false;
          break;
      }
    }
    for (int i = 0; i < turmas.length && i < professores.length; i++) {
      System.out.printf("\nTurma: %s - Professor: %s\nAlunos: ", turmas[i], professores[i]);
      for (int j = 0; j < alunos[i].length; j++) {
        if (j == alunos[i].length - 1) {
          System.out.print(alunos[i][j]);
        } else {
          System.out.print(alunos[i][j] + ", ");
        }
      }
      System.out.println();
    }

    System.out.println();
    in.close();
  }
}
