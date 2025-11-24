#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_SIZE 15
#define CAMINHO_LIVRE ' '
#define PAREDE '#'
#define INICIO 'S'
#define SAIDA 'E'
#define VISITADO '.'
#define CAMINHO_SOLUCAO '*'
#define JOGADOR '@'

typedef enum
{
  UP = 0,
  DOWN = 1,
  LEFT = 2,
  RIGHT = 3,
  NENHUM = -1
} Direcao;

typedef struct passo
{
  int linha;
  int coluna;
  Direcao movimento;
  struct passo *proximo;
} Passo;

typedef struct pilha
{
  Passo *topo;
  int qtd;
} Pilha;

typedef struct labirinto
{
  char mapa[MAX_SIZE][MAX_SIZE];
  int tamanho;
  int inicio_linha;
  int inicio_coluna;
  int saida_linha;
  int saida_coluna;
  int nivel;
} Labirinto;

Pilha *criaPilha()
{
  Pilha *p = (Pilha *)malloc(sizeof(Pilha));
  p->topo = NULL;
  p->qtd = 0;
  return p;
}

Passo *criaElementoPasso(int linha, int coluna, Direcao movimento)
{
  Passo *p = (Passo *)malloc(sizeof(Passo));
  p->linha = linha;
  p->coluna = coluna;
  p->movimento = movimento;
  p->proximo = NULL;
  return p;
}

void push(Pilha *p, Passo *passo)
{
  if (passo != NULL)
  {
    passo->proximo = p->topo;
    p->topo = passo;
    p->qtd++;
  }
}

Passo *pop(Pilha *p)
{
  Passo *aux = p->topo;
  if (aux != NULL)
  {
    p->topo = p->topo->proximo;
    p->qtd--;
    aux->proximo = NULL;
  }
  return aux;
}

int pilhaVazia(Pilha *p)
{
  return (p->topo == NULL);
}

void apagaPilha(Pilha *p)
{
  while (!pilhaVazia(p))
  {
    Passo *aux = pop(p);
    free(aux);
  }
}

Pilha *invertePilha(Pilha *p)
{
  Pilha *ptemp1 = criaPilha();
  Pilha *ptemp2 = criaPilha();
  Pilha *pInvertida = criaPilha();

  Passo *aux = p->topo;
  while (aux != NULL)
  {
    push(ptemp1, criaElementoPasso(aux->linha, aux->coluna, aux->movimento));
    aux = aux->proximo;
  }

  Passo *temp = pop(ptemp1);
  while (temp != NULL)
  {
    push(ptemp2, temp);
    temp = pop(ptemp1);
  }

  temp = pop(ptemp2);
  while (temp != NULL)
  {
    push(pInvertida, temp);
    temp = pop(ptemp2);
  }

  free(ptemp1);
  free(ptemp2);

  return pInvertida;
}

void mostraPilha(Pilha *p)
{
  if (pilhaVazia(p))
  {
    printf("\n  -- Pilha Vazia (Nenhum movimento realizado)!!\n");
    return;
  }

  Passo *aux = p->topo;
  int contador = 1;
  char *nomes[] = {"UP", "DOWN", "LEFT", "RIGHT", "NENHUM"};

  printf("\n========== ESTADO DA PILHA ==========\n");
  printf("Total de passos: %d\n", p->qtd);

  while (aux != NULL)
  {
    printf("Passo %d: [%d,%d] - Movimento: %s\n",
           contador++, aux->linha, aux->coluna,
           aux->movimento >= 0 && aux->movimento <= 4 ? nomes[aux->movimento] : "INVALIDO");
    aux = aux->proximo;
  }
  printf("=====================================\n");
}

Labirinto *criaLabirinto()
{
  Labirinto *lab = (Labirinto *)malloc(sizeof(Labirinto));
  lab->tamanho = 0;
  lab->nivel = 1;
  return lab;
}

void limpaLabirinto(Labirinto *lab)
{
  for (int i = 0; i < lab->tamanho; i++)
    for (int j = 0; j < lab->tamanho; j++)
      lab->mapa[i][j] = CAMINHO_LIVRE;
}

void geraLabirintoNivel1(Labirinto *lab)
{
  lab->tamanho = 5;
  lab->nivel = 1;

  char mapa_fixo[5][5] = {
      {' ', ' ', ' ', '#', ' '},
      {'#', '#', ' ', '#', ' '},
      {' ', ' ', ' ', ' ', '#'},
      {' ', '#', '#', '#', ' '},
      {' ', ' ', ' ', ' ', ' '}};

  for (int i = 0; i < 5; i++)
    for (int j = 0; j < 5; j++)
      lab->mapa[i][j] = mapa_fixo[i][j];

  lab->inicio_linha = 0;
  lab->inicio_coluna = 0;
  lab->saida_linha = 4;
  lab->saida_coluna = 4;

  lab->mapa[lab->inicio_linha][lab->inicio_coluna] = INICIO;
  lab->mapa[lab->saida_linha][lab->saida_coluna] = SAIDA;
}

void geraLabirintoNivel2(Labirinto *lab)
{
  lab->tamanho = 10;
  lab->nivel = 2;
  limpaLabirinto(lab);

  for (int i = 0; i < lab->tamanho; i++)
    for (int j = 0; j < lab->tamanho; j++)
      if (rand() % 100 < 30)
        lab->mapa[i][j] = PAREDE;

  do
  {
    lab->inicio_linha = rand() % lab->tamanho;
    lab->inicio_coluna = rand() % lab->tamanho;
  } while (lab->mapa[lab->inicio_linha][lab->inicio_coluna] == PAREDE);

  do
  {
    lab->saida_linha = rand() % lab->tamanho;
    lab->saida_coluna = rand() % lab->tamanho;
  } while ((lab->saida_linha == lab->inicio_linha && lab->saida_coluna == lab->inicio_coluna) ||
           lab->mapa[lab->saida_linha][lab->saida_coluna] == PAREDE);

  lab->mapa[lab->inicio_linha][lab->inicio_coluna] = INICIO;
  lab->mapa[lab->saida_linha][lab->saida_coluna] = SAIDA;
}

void geraLabirintoNivel3(Labirinto *lab)
{
  lab->tamanho = 15;
  lab->nivel = 3;
  limpaLabirinto(lab);

  for (int i = 0; i < lab->tamanho; i++)
    for (int j = 0; j < lab->tamanho; j++)
      if (rand() % 100 < 40)
        lab->mapa[i][j] = PAREDE;

  do
  {
    lab->inicio_linha = rand() % lab->tamanho;
    lab->inicio_coluna = rand() % lab->tamanho;
  } while (lab->mapa[lab->inicio_linha][lab->inicio_coluna] == PAREDE);

  do
  {
    lab->saida_linha = rand() % lab->tamanho;
    lab->saida_coluna = rand() % lab->tamanho;
  } while ((lab->saida_linha == lab->inicio_linha && lab->saida_coluna == lab->inicio_coluna) ||
           lab->mapa[lab->saida_linha][lab->saida_coluna] == PAREDE);

  lab->mapa[lab->inicio_linha][lab->inicio_coluna] = INICIO;
  lab->mapa[lab->saida_linha][lab->saida_coluna] = SAIDA;
}

void mostraLabirinto(Labirinto *lab)
{
  printf("\n========== LABIRINTO (Nível %d - %dx%d) ==========\n",
         lab->nivel, lab->tamanho, lab->tamanho);
  printf("Legenda: S=Início | E=Saída | @=Jogador | (espaço)=Caminho | #=Parede | .=Visitado | *=Solução\n\n");

  printf("   ");
  for (int j = 0; j < lab->tamanho; j++)
    printf(" %2d", j);

  printf("\n");

  for (int i = 0; i < lab->tamanho; i++)
  {
    printf("%2d ", i);
    for (int j = 0; j < lab->tamanho; j++)
      printf("  %c", lab->mapa[i][j]);

    printf("\n");
  }
  printf("=================================================\n");
}

int posicaoValida(Labirinto *lab, int linha, int coluna)
{
  if (linha < 0 || linha >= lab->tamanho || coluna < 0 || coluna >= lab->tamanho)
    return 0;

  char celula = lab->mapa[linha][coluna];
  return (celula == CAMINHO_LIVRE || celula == SAIDA);
}

void obtemNovaPosicao(int *linha, int *coluna, Direcao dir)
{
  switch (dir)
  {
  case UP:
    (*linha)--;
    break;
  case DOWN:
    (*linha)++;
    break;
  case LEFT:
    (*coluna)--;
    break;
  case RIGHT:
    (*coluna)++;
    break;
  default:
    break;
  }
}

int validaSolucaoExiste(Labirinto *lab)
{
  char mapa_temp[MAX_SIZE][MAX_SIZE];

  for (int i = 0; i < lab->tamanho; i++)
    for (int j = 0; j < lab->tamanho; j++)
      mapa_temp[i][j] = lab->mapa[i][j];

  Pilha *p = criaPilha();
  push(p, criaElementoPasso(lab->inicio_linha, lab->inicio_coluna, NENHUM));

  while (!pilhaVazia(p))
  {
    Passo *atual = pop(p);
    int l = atual->linha;
    int c = atual->coluna;

    if (l == lab->saida_linha && c == lab->saida_coluna)
    {
      free(atual);
      apagaPilha(p);
      free(p);
      return 1;
    }

    free(atual);

    if (mapa_temp[l][c] != INICIO)
      mapa_temp[l][c] = VISITADO;

    for (int dir = 0; dir < 4; dir++)
    {
      int nl = l, nc = c;
      obtemNovaPosicao(&nl, &nc, (Direcao)dir);

      if (nl >= 0 && nl < lab->tamanho && nc >= 0 && nc < lab->tamanho)
      {
        char celula = mapa_temp[nl][nc];
        if (celula == CAMINHO_LIVRE || celula == SAIDA)
          push(p, criaElementoPasso(nl, nc, (Direcao)dir));
      }
    }
  }

  apagaPilha(p);
  free(p);
  return 0;
}

int resolveLabirintoBacktracking(Labirinto *lab, Pilha *pilha, int visualizar)
{
  clock_t inicio_cpu = clock();
  time_t inicio_real = time(NULL);

  int linha_atual = lab->inicio_linha;
  int coluna_atual = lab->inicio_coluna;

  push(pilha, criaElementoPasso(linha_atual, coluna_atual, NENHUM));

  int passos = 0;
  int max_iteracoes = lab->tamanho * lab->tamanho * 10;

  while (!pilhaVazia(pilha) && passos < max_iteracoes)
  {
    passos++;

    if (linha_atual == lab->saida_linha && coluna_atual == lab->saida_coluna)
    {
      clock_t fim_cpu = clock();
      time_t fim_real = time(NULL);

      double tempo_cpu = ((double)(fim_cpu - inicio_cpu)) / CLOCKS_PER_SEC;
      double tempo_real = difftime(fim_real, inicio_real);

      printf("\n✓ LABIRINTO RESOLVIDO COM SUCESSO!\n");
      printf("Tempo de CPU (processamento): %6.4f s\n", tempo_cpu);
      printf("Tempo Real (total):           %6.1f s\n", tempo_real);
      printf("Total de passos na solução:  %6d", pilha->qtd);

      return 1;
    }

    if (lab->mapa[linha_atual][coluna_atual] != INICIO &&
        lab->mapa[linha_atual][coluna_atual] != SAIDA)
      lab->mapa[linha_atual][coluna_atual] = VISITADO;

    if (visualizar /* && passos % 5 == 0 */)
    {
      // system("cls || clear");
      mostraLabirinto(lab);
      printf("Processando... Passo %d\n", passos);
    }

    int movimento_encontrado = 0;

    for (int dir = 0; dir < 4; dir++)
    {
      int nova_linha = linha_atual;
      int nova_coluna = coluna_atual;
      obtemNovaPosicao(&nova_linha, &nova_coluna, (Direcao)dir);

      if (posicaoValida(lab, nova_linha, nova_coluna))
      {
        push(pilha, criaElementoPasso(nova_linha, nova_coluna, (Direcao)dir));
        linha_atual = nova_linha;
        coluna_atual = nova_coluna;
        movimento_encontrado = 1;
        break;
      }
    }

    if (!movimento_encontrado)
    {
      Passo *volta = pop(pilha);
      if (volta != NULL)
        free(volta);

      if (!pilhaVazia(pilha))
      {
        linha_atual = pilha->topo->linha;
        coluna_atual = pilha->topo->coluna;
      }
    }
  }

  clock_t fim_cpu = clock();
  time_t fim_real = time(NULL);

  double tempo_cpu = ((double)(fim_cpu - inicio_cpu)) / CLOCKS_PER_SEC;
  double tempo_real = difftime(fim_real, inicio_real);

  printf("\n✗ Não foi possível resolver o labirinto.\n");
  printf("Tempo de CPU (processamento): %6.4f s\n", tempo_cpu);
  printf("Tempo Real (total):           %6.1f s\n", tempo_real);
  return 0;
}

void replaySolucao(Labirinto *lab, Pilha *pilha)
{
  printf("\n========== INICIANDO REPLAY DA SOLUÇÃO ==========\n");
  printf("Invertendo a pilha...\n");

  Pilha *pilhaInvertida = invertePilha(pilha);

  for (int i = 0; i < lab->tamanho; i++)
    for (int j = 0; j < lab->tamanho; j++)
      if (lab->mapa[i][j] == VISITADO || lab->mapa[i][j] == CAMINHO_SOLUCAO)
        lab->mapa[i][j] = CAMINHO_LIVRE;

  lab->mapa[lab->inicio_linha][lab->inicio_coluna] = INICIO;
  lab->mapa[lab->saida_linha][lab->saida_coluna] = SAIDA;

  printf("Reproduzindo o caminho do INÍCIO até a SAÍDA...\n\n");

  Passo *passo = pop(pilhaInvertida);
  int contador = 0;

  while (passo != NULL)
  {
    contador++;

    if (lab->mapa[passo->linha][passo->coluna] != INICIO &&
        lab->mapa[passo->linha][passo->coluna] != SAIDA)
      lab->mapa[passo->linha][passo->coluna] = CAMINHO_SOLUCAO;

    // system("cls || clear");
    mostraLabirinto(lab);
    printf("Replay: Passo %d - Posição [%d,%d]\n", contador, passo->linha, passo->coluna);

    free(passo);
    passo = pop(pilhaInvertida);
  }

  printf("\n✓ Replay concluído! Total de passos: %d\n", contador);
  apagaPilha(pilhaInvertida);
  free(pilhaInvertida);
}

void movimentoManual(Labirinto *lab, Pilha *pilha, int *linha_atual, int *coluna_atual)
{
  char comando[10];

  char celula_original = lab->mapa[*linha_atual][*coluna_atual];
  if (celula_original != INICIO && celula_original != SAIDA)
    lab->mapa[*linha_atual][*coluna_atual] = JOGADOR;

  mostraLabirinto(lab);

  if (celula_original != INICIO && celula_original != SAIDA)
    lab->mapa[*linha_atual][*coluna_atual] = celula_original;

  printf("\nPosição atual: [%d, %d]\n", *linha_atual, *coluna_atual);
  printf("Digite o movimento (W=Cima | A=Esquerda | S=Baixo | D=Direita): ");
  scanf("%s", comando);

  Direcao dir = NENHUM;

  if (strcmp(comando, "W") == 0 || strcmp(comando, "w") == 0)
    dir = UP;
  else if (strcmp(comando, "S") == 0 || strcmp(comando, "s") == 0)
    dir = DOWN;
  else if (strcmp(comando, "A") == 0 || strcmp(comando, "a") == 0)
    dir = LEFT;
  else if (strcmp(comando, "D") == 0 || strcmp(comando, "d") == 0)
    dir = RIGHT;
  else
  {
    printf("Comando inválido!\n");
    return;
  }

  int nova_linha = *linha_atual;
  int nova_coluna = *coluna_atual;
  obtemNovaPosicao(&nova_linha, &nova_coluna, dir);

  if (posicaoValida(lab, nova_linha, nova_coluna))
  {
    if (lab->mapa[*linha_atual][*coluna_atual] != INICIO)
      lab->mapa[*linha_atual][*coluna_atual] = VISITADO;

    *linha_atual = nova_linha;
    *coluna_atual = nova_coluna;

    push(pilha, criaElementoPasso(nova_linha, nova_coluna, dir));

    if (nova_linha == lab->saida_linha && nova_coluna == lab->saida_coluna)
    {
      mostraLabirinto(lab);
      printf("\n✓✓✓ PARABÉNS! VOCÊ CHEGOU NA SAÍDA! ✓✓✓\n");
    }
    else
      printf("Movimento realizado com sucesso!\n");
  }
  else
    printf("✗ Movimento inválido! Posição bloqueada ou fora dos limites.\n");
}

void menu(Labirinto *lab)
{
  int op;
  Pilha *pilha = criaPilha();
  int linha_jogador = lab->inicio_linha;
  int coluna_jogador = lab->inicio_coluna;
  int jogo_resolvido = 0;

  do
  {
    printf("\n\n========== MENU DO JOGO DE LABIRINTO ==========");
    printf("\n1 - Mostrar labirinto atual");
    printf("\n2 - Mover manualmente (W/A/S/D)");
    printf("\n3 - Executar resolução automática (Backtracking)");
    printf("\n4 - Mostrar estado atual da pilha");
    printf("\n5 - Reiniciar labirinto (gerar novo)");
    printf("\n6 - Inverter Pilha e Replay da solução");
    printf("\n0 - Sair do programa");
    printf("\n===============================================");
    printf("\nInforme sua opção: ");
    scanf("%d", &op);
    fflush(stdin);

    switch (op)
    {
    case 1:
      mostraLabirinto(lab);
      break;

    case 2:
      movimentoManual(lab, pilha, &linha_jogador, &coluna_jogador);
      break;

    case 3:
      printf("\n========== RESOLUÇÃO AUTOMÁTICA ==========\n");
      printf("Visualizar passo a passo? (1=Sim, 0=Não): ");
      int visualizar;
      scanf("%d", &visualizar);

      apagaPilha(pilha);
      free(pilha);
      pilha = criaPilha();

      for (int i = 0; i < lab->tamanho; i++)
        for (int j = 0; j < lab->tamanho; j++)
          if (lab->mapa[i][j] == VISITADO || lab->mapa[i][j] == CAMINHO_SOLUCAO)
            lab->mapa[i][j] = CAMINHO_LIVRE;

      lab->mapa[lab->inicio_linha][lab->inicio_coluna] = INICIO;
      lab->mapa[lab->saida_linha][lab->saida_coluna] = SAIDA;

      jogo_resolvido = resolveLabirintoBacktracking(lab, pilha, visualizar);
      // mostraLabirinto(lab);
      break;

    case 4:
      mostraPilha(pilha);
      break;

    case 5:
      printf("\n========== REINICIAR LABIRINTO ==========\n");
      printf("Escolha o nível de dificuldade:\n");
      printf("1 - Fácil (5x5)\n");
      printf("2 - Médio (10x10)\n");
      printf("3 - Difícil (15x15)\n");
      printf("Opção: ");
      int nivel;
      scanf("%d", &nivel);

      switch (nivel)
      {
      case 1:
        geraLabirintoNivel1(lab);
        break;
      case 2:
        geraLabirintoNivel2(lab);
        break;
      case 3:
        geraLabirintoNivel3(lab);
        break;
      default:
        printf("Nível inválido! Gerando nível 1.\n");
        geraLabirintoNivel1(lab);
      }

      printf("\nValidando se existe solução...\n");
      if (validaSolucaoExiste(lab))
      {
        printf("✓ Labirinto válido! Existe pelo menos um caminho até a saída.\n");
        mostraLabirinto(lab);
      }
      else
      {
        printf("✗ ATENÇÃO: Este labirinto não possui solução!\n");
        printf("Gerando novo labirinto...\n");

        int tentativas = 0;
        while (!validaSolucaoExiste(lab) && tentativas < 10)
        {
          switch (nivel)
          {
          case 2:
            geraLabirintoNivel2(lab);
            break;
          case 3:
            geraLabirintoNivel3(lab);
            break;
          default:
            geraLabirintoNivel1(lab);
          }
          tentativas++;
        }

        if (validaSolucaoExiste(lab))
        {
          printf("✓ Labirinto válido gerado com sucesso!\n");
          mostraLabirinto(lab);
        }
        else
        {
          printf("Não foi possível gerar labirinto válido. Usando nível 1.\n");
          geraLabirintoNivel1(lab);
        }
      }

      apagaPilha(pilha);
      free(pilha);
      pilha = criaPilha();
      linha_jogador = lab->inicio_linha;
      coluna_jogador = lab->inicio_coluna;
      jogo_resolvido = 0;
      break;

    case 6:
      printf("\n========== REPLAY DA SOLUÇÃO ==========\n");
      if (!jogo_resolvido || pilhaVazia(pilha))
        printf("✗ Erro: Execute primeiro a resolução automática (Opção 3)!\n");
      else
        replaySolucao(lab, pilha);
      break;

    case 0:
      printf("\nEncerrando o programa...\n");
      break;

    default:
      printf("\nOpção inválida! Tente novamente.\n");
    }

  } while (op != 0);

  apagaPilha(pilha);
  free(pilha);
}

int main()
{
  srand(time(NULL));

  printf("========================================================\n");
  printf("   JOGO DE LABIRINTO - ESTRUTURA DE DADOS (PILHA)\n");
  printf("   Implementação de Backtracking com Pilha Dinâmica\n");
  printf("========================================================\n");

  Labirinto *lab = criaLabirinto();

  geraLabirintoNivel1(lab);

  printf("\nLabirinto inicial (Nível 1) carregado!\n");

  menu(lab);

  free(lab);

  printf("\nObrigado por jogar! Até a próxima.\n");

  return 0;
}