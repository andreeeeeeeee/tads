#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define NUM_REGISTROS 10000
#define TAMANHO_HASH 101
#define TAM_CODIGO 12

char categorias[] = "ABCDEFGHLPT";

typedef struct No
{
  char codigo[TAM_CODIGO];
  struct No *proximo;
} No;

No *tabela_hash[TAMANHO_HASH];

char lista_linear[NUM_REGISTROS][TAM_CODIGO];

int funcao_hash(char *codigo)
{
  int soma = 0;
  for (int i = 0; codigo[i] != '\0'; i++)
    soma += codigo[i];

  return soma % TAMANHO_HASH;
}

void gerar_codigo(char *codigo)
{
  int idx_cat = rand() % strlen(categorias);
  char cat = categorias[idx_cat];
  int ano = 2025;
  int serial = rand() % 100000;
  sprintf(codigo, "%c%d-%05d", cat, ano, serial);
}

void inserir_hash(char *codigo)
{
  int indice = funcao_hash(codigo);
  No *novo_no = (No *)malloc(sizeof(No));
  strcpy(novo_no->codigo, codigo);
  novo_no->proximo = tabela_hash[indice];
  tabela_hash[indice] = novo_no;
}

int buscar_hash(char *codigo)
{
  int indice = funcao_hash(codigo);
  No *atual = tabela_hash[indice];
  while (atual)
  {
    if (strcmp(atual->codigo, codigo) == 0)
      return 1;
    atual = atual->proximo;
  }
  return 0;
}

int busca_linear(char *alvo)
{
  for (int i = 0; i < NUM_REGISTROS; i++)
    if (strcmp(lista_linear[i], alvo) == 0)
      return 1;

  return 0;
}

int main()
{
  srand(time(NULL));

  for (int i = 0; i < TAMANHO_HASH; i++)
    tabela_hash[i] = NULL;

  for (int i = 0; i < NUM_REGISTROS; i++)
  {
    gerar_codigo(lista_linear[i]);
    inserir_hash(lista_linear[i]);
  }

  int escolha;
  do
  {
    printf("\nMenu:\n");
    printf("1. Busca Individual\n");
    printf("2. Busca Média/Aleatória\n");
    printf("3. Sair\n");
    printf("Escolha uma opção: ");
    scanf("%d", &escolha);

    if (escolha == 1)
    {
      printf("\nExemplos de códigos disponíveis:\n");
      for (int i = 0; i < 5; i++)
        printf(" - %s\n", lista_linear[rand() % NUM_REGISTROS]);

      char alvo[TAM_CODIGO];
      printf("Digite o código de pedido: ");
      scanf("%s", alvo);

      clock_t inicio = clock();
      int encontrado_linear = busca_linear(alvo);
      clock_t fim = clock();
      double tempo_linear = (double)(fim - inicio) / CLOCKS_PER_SEC;

      inicio = clock();
      int encontrado_hash = buscar_hash(alvo);
      fim = clock();
      double tempo_hash = (double)(fim - inicio) / CLOCKS_PER_SEC;

      printf("Lista Linear: %s (Tempo: %.6f segundos)\n", encontrado_linear ? "Encontrado" : "Não encontrado", tempo_linear);
      printf("Tabela Hash: %s (Tempo: %.6f segundos)\n", encontrado_hash ? "Encontrado" : "Não encontrado", tempo_hash);
    }
    else if (escolha == 2)
    {
      int indices[20];
      for (int i = 0; i < 20; i++)
        indices[i] = rand() % NUM_REGISTROS;

      clock_t inicio = clock();
      int contador_encontrados_linear = 0;
      for (int i = 0; i < 20; i++)
        if (busca_linear(lista_linear[indices[i]]))
          contador_encontrados_linear++;

      clock_t fim = clock();
      double tempo_total_linear = (double)(fim - inicio) / CLOCKS_PER_SEC;
      double tempo_medio_linear = tempo_total_linear / 20;

      inicio = clock();
      int contador_encontrados_hash = 0;
      for (int i = 0; i < 20; i++)
        if (buscar_hash(lista_linear[indices[i]]))
          contador_encontrados_hash++;

      fim = clock();
      double tempo_total_hash = (double)(fim - inicio) / CLOCKS_PER_SEC;
      double tempo_medio_hash = tempo_total_hash / 20;

      printf("Lista Linear: Tempo total %.6f s, Tempo médio %.6f s\n", tempo_total_linear, tempo_medio_linear);
      printf("Tabela Hash: Tempo total %.6f s, Tempo médio %.6f s\n", tempo_total_hash, tempo_medio_hash);
    }
    else if (escolha == 3)
      printf("Saindo...\n");
    else
      printf("Opção inválida.\n");

  } while (escolha != 3);

  for (int i = 0; i < TAMANHO_HASH; i++)
  {
    No *atual = tabela_hash[i];
    while (atual)
    {
      No *temp = atual;
      atual = atual->proximo;
      free(temp);
    }
  }

  return 0;
}