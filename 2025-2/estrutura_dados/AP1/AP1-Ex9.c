#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct no {
    int numero;
    struct no *proximo;
}No;

typedef struct lse{
    No *primeiro;
    int n_elementos;
    char nome[50];
}LSE;

void inicializaLista(LSE *lista, char nome[]){
    lista->primeiro = NULL;
    lista->n_elementos = 0;
    strcpy(lista->nome,nome);
}

void insereNoInicio(LSE *lista, No *novo){
    novo->proximo = lista->primeiro;
    lista->primeiro = novo;
    lista->n_elementos++;
}

void insereNoFim(LSE *lista, No *novo){
    novo->proximo = NULL;
    if(lista->primeiro == NULL){
        lista->primeiro = novo;
    }else{
        No *aux = lista->primeiro;
        while(aux->proximo!=NULL){
            aux = aux->proximo;
        }
        aux->proximo = novo;
    }
    lista->n_elementos++;
}

No* localizaPosicao(LSE *lista, int posicao){
    //localiza um No na lista pela posicao e retorna o endereço
    if(posicao<0 || posicao>=lista->n_elementos){
        printf("\n\t\tPosicao inválida!\n");
        return NULL;
    }
    No *aux = lista->primeiro;
    for(int i=0; i<posicao; i++){
        aux = aux->proximo;
    }
    return aux;
}

void mostraElemento(No *no){
    printf("\t\tNúmero: %d\n",no->numero);
}

void mostraLista(LSE *lista){
    printf("\n\t%s\n", lista->nome);
    No *aux; 
    aux = lista->primeiro;
    while(aux!=NULL){
        mostraElemento(aux);
        aux = aux->proximo;
    }
    printf("\t\tTotal de elementos na %s = %d \n",lista->nome, lista->n_elementos);
    printf("\tFim da %s!\n", lista->nome);
}

int main() {
    LSE lista1, lista2;
    inicializaLista(&lista1, "Lista1");
    inicializaLista(&lista2, "Lista2");

    for (int i = 0; i<10; i++) {
        No *novo = (No*)(malloc(sizeof(No)));
        printf("Insira um número inteiro:");
        scanf("%d", &novo->numero);
        insereNoInicio(&lista1, novo);
    }

    mostraLista(&lista1);

    for (int i = 0; i<10; i++) {
        for (int j = i+1; j<10; j++) {
            No *aux1 = localizaPosicao(&lista1, i);
            No *aux2 = localizaPosicao(&lista1, j);
            if (aux1->numero <= aux2->numero) {
                insereNoFim(&lista2, aux1);
                break;
            }
        }
    }

    mostraLista(&lista2);

    exit(0);
}