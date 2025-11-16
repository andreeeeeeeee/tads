/*
1. Crie três Listas Simplesmente Encadeada, uma para cada time de futebol de salão (times Inter, Grêmio e Juventude), atribua nome as listas;
2. Com base nas funções de listas simplesmente encadeada, implemente um sistema
para controlar a formação dos times.
1. Um aluno só pode fazer parte de um time, o mesmo aluno não pode ser cadastrado em duplicidade (Validar matrícula);
2. Todo aluno deve estar inscrito em um time. Após, definir as informações do aluno
deve solicitar o time que pretende ser inserido;
3. Os 5 primeiros elementos (Alunos) da lista formam o time titular. Os demais serão
reservas.
4. Os jogadores podem trocar de times, passar de A para B;
5. Os jogadores pode mudar de posição na lista e ingressar no time titular (5 primeiros). Escolher um jogador dos reservas para trocar por um jogador titular, os jogadores trocaram de posição.
6. O jogador pode deixar a lista de titular e ir para o final da lista.
Obs: Os itens 5 e 6 devem realizar a troca dos elementos, através da manipulação
dos ponteiros “*proximo”. (Não é permitido trocar o conteúdo de cada elemento).https://open.spotify.com/collection/tracks
7. O sistema deve mostrar a escalação de cada time (Os titulares);
8. O sistema deve mostrar os times completos (Todos alunos do time);
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct aluno
{
  char nome[100];
  int idade;
  int matricula;
  struct aluno *proximo;
} Aluno;

typedef struct lse
{
  Aluno *primeiro;
  int n_elementos;
  char nome[50];
} LSE;

LSE inter, gremio, juventude;

Aluno *localizaAlunoMatricula(LSE *lista, int matricula);

void inicializaLista(LSE *lista, char nome[])
{
  // recebe a estrutura de lista e preenche os parâmetros
  lista->primeiro = NULL;
  lista->n_elementos = 0;
  strcpy(lista->nome, nome);
}

void insereInicio(LSE *lista, Aluno *novo)
{
  // insere um elemento no início da lista (primeiro)
  if (lista->primeiro == NULL)
  {
    // Lista Vazia
    novo->proximo = NULL;
  }
  else
  {
    // Lista com elementos
    novo->proximo = lista->primeiro;
  }
  lista->primeiro = novo;
  lista->n_elementos++;
}

void insereInicioReduzida(LSE *lista, Aluno *novo)
{
  // insere um elemento no início da lista forma reduzida
  novo->proximo = lista->primeiro;
  lista->primeiro = novo;
  lista->n_elementos++;
}

void insereFim(LSE *lista, Aluno *novo)
{
  // insere um novo elemento no fim da lista (último)
  novo->proximo = NULL;
  if (lista->primeiro == NULL)
  {
    // Lista Vazia
    lista->primeiro = novo;
  }
  else
  {
    // Lista com elementos
    Aluno *aux = lista->primeiro;
    while (aux->proximo != NULL)
    {
      aux = aux->proximo;
    }
    aux->proximo = novo;
  }
  lista->n_elementos++;
}

int inserePosicao(LSE *lista, Aluno *novo, int posicao)
{
  // inserir um elemento em qualquer posição da lista (posicao)
  if (posicao < 0 || posicao > lista->n_elementos)
  {
    printf("\n\t\tPosicao inválida!\n");
    return 0;
  }
  if (posicao == 0)
  {
    insereInicio(lista, novo);
    return 1;
  }
  if (posicao == lista->n_elementos)
  {
    insereFim(lista, novo);
    return 1;
  }

  Aluno *aux = lista->primeiro;
  for (int i = 0; i < posicao - 1; i++)
  {
    aux = aux->proximo;
  }
  novo->proximo = aux->proximo;
  aux->proximo = novo;
  lista->n_elementos++;
  return 1;
}

Aluno *removeInicio(LSE *lista)
{
  // remove o primeiro elemento da lista
  Aluno *aux = lista->primeiro;
  if (aux != NULL)
  {
    lista->primeiro = lista->primeiro->proximo;
    lista->n_elementos--;
    aux->proximo = NULL;
  }
  else
  {
    printf("\n\t\tLista Vazia!\n");
  }
  return aux;
}

Aluno *removeFim(LSE *lista)
{
  // remove o último elemento da lista
  Aluno *aux = lista->primeiro;
  if (aux != NULL)
  {
    // lista com um ou mais elementos
    if (aux->proximo == NULL)
    {
      // lista com apenas um elemento
      lista->primeiro = NULL;
    }
    else
    {
      // lista com dois ou mais elementos
      Aluno *prev = NULL;
      while (aux->proximo != NULL)
      {
        prev = aux;
        aux = aux->proximo;
      }
      prev->proximo = NULL;
    }
    lista->n_elementos--;
    aux->proximo = NULL;
  }
  else
  {
    printf("\n\t\t  **** Lista Vazia! ****\n");
  }
  return aux;
}

Aluno *removePosicao(LSE *lista, int posicao)
{
  // remove o elemento da lista na posicao definida (posicao)
  if (posicao < 0 || posicao >= lista->n_elementos)
  {
    printf("\n\t\tPosicao inválida!\n");
    return NULL;
  }
  if (posicao == 0)
  {
    return removeInicio(lista);
  }
  if (posicao == lista->n_elementos - 1)
  {
    return removeFim(lista);
  }
  Aluno *aux = lista->primeiro;
  for (int i = 0; i < posicao - 1; i++)
  {
    aux = aux->proximo;
  }
  Aluno *removido = aux->proximo;
  aux->proximo = removido->proximo;
  lista->n_elementos--;
  removido->proximo = NULL;
  return removido;
}

int existeMatricula(LSE *lista, int matricula)
{
  // verifica se existe um aluno com a matrícula na lista
  Aluno *aux = lista->primeiro;
  while (aux != NULL)
  {
    if (aux->matricula == matricula)
    {
      return 1;
    }
    aux = aux->proximo;
  }
  return 0;
}

LSE *selecionaTime(int opcao)
{
  // retorna o time selecionado com base na opção
  switch (opcao)
  {
  case 1:
    return &inter;
  case 2:
    return &gremio;
  case 3:
    return &juventude;
  default:
    printf("\nOpção inválida! Retornando time Internacional por padrão.\n");
    return &inter;
  }
}

void cadastraAluno(Aluno *aluno, char nome[], int idade, int matricula, int timeEscolhido)
{
  if (existeMatricula(&inter, matricula) || existeMatricula(&gremio, matricula) || existeMatricula(&juventude, matricula))
  {
    printf("\n\t\tMatrícula já existe em um dos times! Cadastro não realizado.\n");
    return;
  }
  strcpy(aluno->nome, nome);
  aluno->idade = idade;
  aluno->matricula = matricula;
  aluno->proximo = NULL;

  if (timeEscolhido == 0)
  {
    printf("\nEscolha o time para inserir o aluno:\n1. Internacional\n2. Grêmio\n3. Juventude\n");
    scanf("%d", &timeEscolhido);
  }
  LSE *time = selecionaTime(timeEscolhido);
  insereFim(time, aluno);
  printf("\nAluno %s inserido no time %s com sucesso!\n", nome, time->nome);
}

void perdeTitularidade(LSE *lista, Aluno *aluno)
{
  // move o aluno para o final da lista
  if (lista->primeiro == NULL || lista->primeiro->proximo == NULL)
  {
    printf("\n\t\tLista com menos de dois elementos, não é possível mover.\n");
    return;
  }
  Aluno *aux = lista->primeiro;
  Aluno *prev = NULL;

  // Encontrar o aluno na lista
  while (aux != NULL && aux != aluno)
  {
    prev = aux;
    aux = aux->proximo;
  }

  if (aux == NULL)
  {
    printf("\n\t\tAluno não encontrado na lista.\n");
    return;
  }

  // Remover o aluno da posição atual
  if (prev == NULL)
  {
    // O aluno é o primeiro da lista
    lista->primeiro = aux->proximo;
  }
  else
  {
    prev->proximo = aux->proximo;
  }

  // Inserir o aluno no final da lista
  aux->proximo = NULL;
  if (lista->primeiro == NULL)
  {
    lista->primeiro = aux;
  }
  else
  {
    Aluno *temp = lista->primeiro;
    while (temp->proximo != NULL)
    {
      temp = temp->proximo;
    }
    temp->proximo = aux;
  }
}

void trocaPosicaoNaLista(LSE *lista, int posicao1, int posicao2)
{
  if (lista == NULL || posicao1 < 0 || posicao2 < 0 ||
      posicao1 >= lista->n_elementos || posicao2 >= lista->n_elementos ||
      posicao1 == posicao2)
  {
    printf("\n\t\tPosições inválidas!\n");
    return;
  }

  // Garantir que posicao1 < posicao2 para simplificar a lógica
  if (posicao1 > posicao2)
  {
    int temp = posicao1;
    posicao1 = posicao2;
    posicao2 = temp;
  }

  Aluno *node1 = NULL, *prev1 = NULL;
  Aluno *node2 = NULL, *prev2 = NULL;
  Aluno *aux = lista->primeiro;

  for (int i = 0; i < lista->n_elementos && (node1 == NULL || node2 == NULL); i++)
  {
    if (i == posicao1)
    {
      node1 = aux;
    }
    if (i == posicao2)
    {
      node2 = aux;
      if (i > 0)
      {
        prev2 = (i == posicao1 + 1) ? node1 : prev2;
      }
    }

    if (i == posicao1 - 1)
      prev1 = aux;
    if (i == posicao2 - 1 && posicao2 != posicao1 + 1)
      prev2 = aux;

    aux = aux->proximo;
  }

  if (posicao2 == posicao1 + 1)
  {
    if (prev1 != NULL)
    {
      prev1->proximo = node2;
    }
    else
    {
      lista->primeiro = node2;
    }
    node1->proximo = node2->proximo;
    node2->proximo = node1;
  }
  else
  {
    if (prev1 != NULL)
    {
      prev1->proximo = node2;
    }
    else
    {
      lista->primeiro = node2;
    }

    if (prev2 != NULL)
    {
      prev2->proximo = node1;
    }
    else
    {
      lista->primeiro = node1;
    }

    Aluno *temp = node1->proximo;
    node1->proximo = node2->proximo;
    node2->proximo = temp;
  }

  printf("\n\t\tPosições trocadas com sucesso!\n");
}

Aluno *informaNovoAluno()
{
  // aloca dinamicamente um novo aluno, preenche os dados e retorna o endereço
  Aluno *novo = (Aluno *)(malloc(sizeof(Aluno)));
  printf("\nInforme o nome do Aluno:");
  scanf("%s", novo->nome);
  printf("Informe a idade do Aluno:");
  scanf("%d", &novo->idade);
  printf("Informe a matricula do Aluno:");
  scanf("%d", &novo->matricula);
  return novo;
}

void mostraAluno(Aluno novo)
{
  // mostra os dados de um elemento Aluno
  printf("\t\tAluno Nome: %s Idade %d Matricula %d\n", novo.nome, novo.idade, novo.matricula);
}

void mostraLista(LSE *lista)
{
  // mostra todos elementos da lista
  printf("\n\t%s\n", lista->nome);
  Aluno *aux; // ponteiro auxiliar
  aux = lista->primeiro;
  int count = 0;
  printf("\t--- Time Titular ---\n");
  while (aux != NULL)
  {
    if (count == 5)
    {
      printf("\t--- Reservas ---\n");
    }
    mostraAluno(*aux);
    aux = aux->proximo;
    count++;
  }
  printf("\t\tTotal de alunos na Lista = %d \n", lista->n_elementos);
  printf("\tFim da Lista!\n");
}

void mostraTitulares(LSE *lista)
{
  // mostra os 5 primeiros elementos da lista (titulares)
  printf("\n\t%s - Time Titular\n", lista->nome);
  Aluno *aux = lista->primeiro;
  int count = 0;
  while (aux != NULL && count < 5)
  {
    mostraAluno(*aux);
    aux = aux->proximo;
    count++;
  }
  if (count == 0)
  {
    printf("\tNenhum titular cadastrado.\n");
  }
  printf("\tFim da Lista de Titulares!\n");
}

void mostraLista2(Aluno *aluno)
{
  // mostra todos elementos da lista com o uso de recursão na função
  if (aluno != NULL)
  {
    mostraAluno(*aluno);
    mostraLista2(aluno->proximo);
  }
}

Aluno *localizaAluno(LSE *lista, int posicao)
{
  // localiza um aluno na lista pela posicao e retorna o endereço
  if (posicao < 0 || posicao >= lista->n_elementos)
  {
    printf("\n\t\tPosicao inválida!\n");
    return NULL;
  }
  Aluno *aux = lista->primeiro;
  for (int i = 0; i < posicao; i++)
  {
    aux = aux->proximo;
  }
  return aux;
}

void apagaLista(LSE *lista)
{
  // apaga todos os elementos da lista
  Aluno *aux = lista->primeiro;
  Aluno *temp;
  while (aux != NULL)
  {
    temp = aux;
    aux = aux->proximo;
    free(temp);
  }
  lista->primeiro = NULL;
  lista->n_elementos = 0;
  printf("\n\t\tLista Apagada!\n");
}

Aluno *localizaAlunoMatricula(LSE *lista, int matricula)
{
  // localiza um aluno na lista pela matricula e retorna o endereço
  Aluno *aux = lista->primeiro;
  while (aux != NULL)
  {
    if (aux->matricula == matricula)
    {
      return aux;
    }
    aux = aux->proximo;
  }
  printf("\n\t\tAluno não encontrado!\n");
  return NULL;
}

Aluno *localizaAlunoNome(LSE *lista, char nome[])
{
  // localiza um aluno na lista pelo nome e retorna o endereço
  Aluno *aux = lista->primeiro;
  while (aux != NULL)
  {
    if (strcmp(aux->nome, nome) == 0)
    {
      return aux;
    }
    aux = aux->proximo;
  }
  printf("\n\t\tAluno não encontrado!\n");
  return NULL;
}

int quantidadeElementos(LSE *lista)
{
  // retorna a quantidade de elementos na lista
  return lista->n_elementos;
}

void apagaAluno(LSE *lista, Aluno *aluno)
{
  if (lista->primeiro == NULL || aluno == NULL)
  {
    printf("\n\t\t  **** Lista Vazia ou Aluno Inválido! ****\n");
    return;
  }

  Aluno *aux = lista->primeiro;
  Aluno *prev = NULL;

  // Procurar o aluno na lista
  while (aux != NULL && aux != aluno)
  {
    prev = aux;
    aux = aux->proximo;
  }

  if (aux == NULL)
  {
    printf("\n\t\tAluno não encontrado na lista!\n");
    return;
  }

  // Remover o aluno
  if (prev == NULL)
  {
    // Primeiro elemento
    lista->primeiro = aux->proximo;
  }
  else
  {
    prev->proximo = aux->proximo;
  }

  lista->n_elementos--;
  free(aluno);
  printf("\n\t\tAluno Apagado!\n");
}

void trocaTimes(int matricula, LSE *timeAtual, LSE *novoTime)
{
  Aluno *aluno = localizaAlunoMatricula(timeAtual, matricula);
  if (aluno != NULL)
  {
    apagaAluno(timeAtual, aluno);
    // Insere o aluno no novo time
    insereFim(novoTime, aluno);
    printf("\nAluno com matrícula %d trocado de time com sucesso.\n", matricula);
  }
  else
  {
    printf("\nAluno com matrícula %d não encontrado no time atual.\n", matricula);
  }
}

void menuInterativo()
{
  int op = 0, posicao = 0;
  Aluno *aux = NULL;
  printf("\n\nMenu de Opções - Time de futebol\n");
  printf("1 - Cadastrar aluno\n");
  printf("2 - Trocar jogador de time\n");
  printf("3 - Mudar posição na lista\n");
  printf("4 - Retirar titular\n");
  printf("5 - Mostrar titulares\n");
  printf("6 - Mostrar time completo\n");
  printf("\n");
  printf("0 - Sair do Programa\n");
  printf("\n");
  printf("\n\tInforme a opcao:");
  scanf("%d", &op);

  switch (op)
  {
  case 1:
    printf("\n\tFuncao Cadastrar Aluno!!");
    Aluno *a = (Aluno *)malloc(sizeof(Aluno));
    char nome[100];
    printf("Informe o nome do Aluno: ");
    scanf("%s", nome);
    int idade, matricula;
    printf("Informe a idade do Aluno:");
    scanf("%d", &idade);
    printf("Informe a matricula do Aluno:");
    scanf("%d", &matricula);
    cadastraAluno(a, nome, idade, matricula, 0);
    break;
  case 2:
    printf("\n\tFuncao Trocar Jogador de Time!!");
    int matriculaTroca, timeAtual, novoTime;
    printf("Informe a matrícula do jogador que deseja trocar de time: ");
    scanf("%d", &matriculaTroca);
    printf("Informe o time atual do jogador (1-Inter, 2-Grêmio, 3-Juventude): ");
    scanf("%d", &timeAtual);
    printf("Informe o novo time do jogador (1-Inter, 2-Grêmio, 3-Juventude): ");
    scanf("%d", &novoTime);
    trocaTimes(matriculaTroca, selecionaTime(timeAtual), selecionaTime(novoTime));
    break;
  case 3:
    printf("\n\tFuncao Mudar Posição na Lista!!");
    int pos1, pos2, timeEscolhido;
    printf("Escolha o time (1-Inter, 2-Grêmio, 3-Juventude): ");
    scanf("%d", &timeEscolhido);
    LSE *time = selecionaTime(timeEscolhido);
    if (time->n_elementos <= 5)
    {
      printf("\n\t\tO time deve ter mais de 5 jogadores para trocar posições.\n");
      break;
    }
    printf("Informe a primeira posição (0-4): ");
    scanf("%d", &pos1);
    printf("Informe a segunda posição (5-%d): ", time->n_elementos - 1);
    scanf("%d", &pos2);
    trocaPosicaoNaLista(time, pos1, pos2);
    break;
  case 4:
  {
    printf("\n\tFuncao Retirar Titular!!");
    int matriculaRetirada, timeEscolhido;
    printf("Escolha o time (1-Inter, 2-Grêmio, 3-Juventude): ");
    scanf("%d", &timeEscolhido);
    LSE *time = selecionaTime(timeEscolhido);
    printf("Informe a matrícula do jogador que deseja retirar de titular: ");
    scanf("%d", &matriculaRetirada);
    perdeTitularidade(time, localizaAlunoMatricula(time, matriculaRetirada));
  }
  break;
  case 5:
  {
    printf("\n\tFuncao Mostrar Titulares!!");
    int timeEscolhido;
    printf("Escolha o time (1-Inter, 2-Grêmio, 3-Juventude): ");
    scanf("%d", &timeEscolhido);
    mostraTitulares(selecionaTime(timeEscolhido));
  }
  break;
  case 6:
  {
    printf("\n\tFuncao Mostrar Time Completo!!");
    int timeEscolhido;
    printf("Escolha o time (1-Inter, 2-Grêmio, 3-Juventude): ");
    scanf("%d", &timeEscolhido);
    mostraLista(selecionaTime(timeEscolhido));
    break;
  }
  case 0:
    printf("\n\n*** Fim do Programa!!! ***\n");
    break;
  default:
    printf("\n\n*** Opcao Invalida!!! ***\n");
    break;
  }
  if (op > 0 && op <= 6)
  {
    menuInterativo();
  }
}

// programa principal para teste
int main()
{

  printf("Time de futebol\n");

  // inicialização da Lista
  inicializaLista(&inter, "Internacional");
  inicializaLista(&gremio, "Grêmio");
  inicializaLista(&juventude, "Juventude");

  for (int i = 0; i < 7; i++)
  {
    Aluno *a = (Aluno *)malloc(sizeof(Aluno));
    char nome[100];
    sprintf(nome, "Inter_Aluno_%d", i + 1);
    cadastraAluno(a, nome, 18 + i, 1000 + i, 1);
  }
  for (int i = 0; i < 7; i++)
  {
    Aluno *a = (Aluno *)malloc(sizeof(Aluno));
    char nome[100];
    sprintf(nome, "Gremio_Aluno_%d", i + 1);
    cadastraAluno(a, nome, 18 + i, 2000 + i, 2);
  }
  for (int i = 0; i < 7; i++)
  {
    Aluno *a = (Aluno *)malloc(sizeof(Aluno));
    char nome[100];
    sprintf(nome, "Juventude_Aluno_%d", i + 1);
    cadastraAluno(a, nome, 18 + i, 3000 + i, 3);
  }

  menuInterativo();
  exit(0);
}
