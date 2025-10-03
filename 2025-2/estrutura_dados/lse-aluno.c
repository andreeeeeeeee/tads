#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct aluno{
    char nome[100];
    int idade;
    int matricula;
    struct aluno *proximo;
}Aluno;

typedef struct lse{
    Aluno *primeiro;
    int n_elementos;
    char nome[50];
}LSE;

void inicializaLista(LSE *lista, char nome[]){
    //recebe a estrutura de lista e preenche os parâmetros
    lista->primeiro = NULL;
    lista->n_elementos = 0;
    strcpy(lista->nome,nome);
}

void insereInicio(LSE *lista, Aluno *novo){
    //insere um elemento no início da lista (primeiro) 
    if(lista->primeiro == NULL){
        //Lista Vazia
        novo->proximo = NULL;
    }else{
        //Lista com elementos
        novo->proximo = lista->primeiro;
    }
    lista->primeiro = novo;
    lista->n_elementos++;
}

void insereInicioReduzida(LSE *lista, Aluno *novo){
    //insere um elemento no início da lista forma reduzida
    novo->proximo = lista->primeiro;
    lista->primeiro = novo;
    lista->n_elementos++;
}

void insereFim(LSE *lista, Aluno *novo){
    //insere um novo elemento no fim da lista (último)
    novo->proximo = NULL;
    if(lista->primeiro == NULL){
        //Lista Vazia
        lista->primeiro = novo;
    }else{
        //Lista com elementos
        Aluno *aux = lista->primeiro;
        while(aux->proximo!=NULL){
            aux = aux->proximo;
        }
        aux->proximo = novo;
    }
    lista->n_elementos++;
}

int inserePosicao(LSE *lista, Aluno *novo, int posicao){
    //inserir um elemento em qualquer posição da lista (posicao)
    if(posicao<0 || posicao>lista->n_elementos){
        printf("\n\t\tPosicao inválida!\n");
        return 0;
    }
    if(posicao == 0){
        insereInicio(lista,novo);
        return 1;
    }
    if(posicao == lista->n_elementos){
        insereFim(lista,novo);
        return 1;
    }
    
    Aluno *aux = lista->primeiro;
    for(int i=0; i<posicao-1; i++){
        aux = aux->proximo;
    }
    novo->proximo = aux->proximo;
    aux->proximo = novo;
    lista->n_elementos++;
    return 1;
}

Aluno* removeInicio(LSE *lista){
    //remove o primeiro elemento da lista
    Aluno *aux = lista->primeiro;
    if(aux != NULL){
        lista->primeiro = lista->primeiro->proximo;
        lista->n_elementos--;
        aux->proximo = NULL;
    }else{
        printf("\n\t\tLista Vazia!\n");
    }
    return aux;
}


Aluno* removeFim(LSE *lista) {
    //remove o último elemento da lista
    Aluno *aux = lista->primeiro;
    if (aux != NULL) {
        // lista com um ou mais elementos
        if (aux->proximo == NULL) {
            // lista com apenas um elemento
            lista->primeiro = NULL;
        } else {
            // lista com dois ou mais elementos
            Aluno *prev = NULL;
            while (aux->proximo != NULL) {
                prev = aux;
                aux = aux->proximo;
            }
            prev->proximo = NULL;
        }
        lista->n_elementos--;
        aux->proximo = NULL;
    } else {
        printf("\n\t\t  **** Lista Vazia! ****\n");
    }
}

Aluno* removePosicao(LSE *lista, int posicao){
    //remove o elemento da lista na posicao definida (posicao)
    if(posicao<0 || posicao>=lista->n_elementos){
        printf("\n\t\tPosicao inválida!\n");
        return NULL;
    }
    if(posicao == 0){
        return removeInicio(lista);
    }
    if(posicao == lista->n_elementos-1){
        return removeFim(lista);
    }
    Aluno *aux = lista->primeiro;
    for(int i=0; i<posicao-1; i++){
        aux = aux->proximo;
    }
    Aluno *removido = aux->proximo;
    aux->proximo = removido->proximo;
    lista->n_elementos--;
    removido->proximo = NULL;
    return removido;
}

void cadastraAluno(Aluno *aluno, char nome[], int idade, int matricula){
    //recebe um ponteiro de aluno e atribui os valores aos membros
    strcpy(aluno->nome,nome);
    aluno->idade = idade;
    aluno->matricula = matricula;
    aluno->proximo = NULL;
}

Aluno* informaNovoAluno(){
    //aloca dinamicamente um novo aluno, preenche os dados e retorna o endereço
    Aluno *novo = (Aluno*)(malloc(sizeof(Aluno)));
    printf("\nInforme o nome do Aluno:");
    scanf("%s",novo->nome);
    printf("Informe a idade do Aluno:");
    scanf("%d",&novo->idade);
    printf("Informe a matricula do Aluno:");
    scanf("%d",&novo->matricula);
    return novo;
}

void mostraAluno(Aluno novo){
    //mostra os dados de um elemento Aluno
    printf("\t\tAluno Nome: %s Idade %d Matricula %d\n",novo.nome,novo.idade,novo.matricula);
}

void mostraLista(LSE *lista){
    //mostra todos elementos da lista 
    printf("\n\tMostra Lista Simplesmente Encadeada; \n");
    Aluno *aux; //ponteiro auxiliar
    aux = lista->primeiro;
    while(aux!=NULL){
        mostraAluno(*aux);
        aux = aux->proximo;
    }
    printf("\t\tTotal de alunos na Lista = %d \n",lista->n_elementos);
    printf("\tFim da Lista!\n");
}

void mostraLista2(Aluno *aluno){
    //mostra todos elementos da lista com o uso de recursão na função
    if(aluno!=NULL){
        mostraAluno(*aluno);
        mostraLista2(aluno->proximo);
    }
}

Aluno* localizaAluno(LSE *lista, int posicao){
    //localiza um aluno na lista pela posicao e retorna o endereço
    if(posicao<0 || posicao>=lista->n_elementos){
        printf("\n\t\tPosicao inválida!\n");
        return NULL;
    }
    Aluno *aux = lista->primeiro;
    for(int i=0; i<posicao; i++){
        aux = aux->proximo;
    }
    return aux;
}

void apagaLista(LSE *lista){
    //apaga todos os elementos da lista
    Aluno *aux = lista->primeiro;
    Aluno *temp;
    while(aux!=NULL){
        temp = aux;
        aux = aux->proximo;
        free(temp);
    }
    lista->primeiro = NULL;
    lista->n_elementos = 0;
    printf("\n\t\tLista Apagada!\n");
}

Aluno* localizaAlunoNome(LSE *lista, char nome[]){
    //localiza um aluno na lista pelo nome e retorna o endereço
    Aluno *aux = lista->primeiro;
    while(aux!=NULL){
        if(strcmp(aux->nome,nome)==0){
            return aux;
        }
        aux = aux->proximo;
    }
    printf("\n\t\tAluno não encontrado!\n");
    return NULL;
}

int quantidadeElementos(LSE *lista){
    //retorna a quantidade de elementos na lista
    return lista->n_elementos;
}

void apagaAluno(Aluno *aluno){
    //apaga um elemento aluno
    free(aluno);
    printf("\n\t\tAluno Apagado!\n");
}

void menuTesteLista(LSE *lista) {
    int op = 0, posicao = 0;
    Aluno *aux = NULL;
    printf("\nMenu de operacoes sobre um LSE:\n");
    printf("\n\t1 - Insere no Inicio:");
    printf("\n\t2 - Insere no Fim:");
    printf("\n\t3 - Insere na Posicao:");
    printf("\n\t4 - Remove no Inicio:");
    printf("\n\t5 - Remove no Fim:");
    printf("\n\t6 - Remove na Posicao:");
    printf("\n\t7 - Mostra Lista:");
    printf("\n\t8 - Mostra Aluno na Posicao:");
    printf("\n\t9 - Apaga Lista:");
    printf("\n\t10 - Localiza um Aluno pelo Nome");
    printf("\n\t11 - Informa quantidade de elementos");
    printf("\n\t12 - Apaga elemento");
    printf("\n");
    printf("\n\t0 - Para Sair da Funcao Menu:");
    printf("\n\tInforme a opcao:");
    scanf("%d", &op);
    switch (op) {
        case 1:
            printf("\n\tFuncao Insere no Inicio!!");
            insereInicio(lista, informaNovoAluno());
            break;
        case 2:
            printf("\n\tFuncao Insere no Fim!!");
            insereFim(lista, informaNovoAluno());
            break;
        case 3:
            printf("\n\tFuncao Insere na Posicao!!");
            printf("\n\t\tInforme a posicao:");
            scanf("%d", &posicao);
            inserePosicao(lista, informaNovoAluno(), posicao);
            break;
        case 4:
            printf("\n\tFuncao Remove no Inicio:");
            aux = removeInicio(lista);
            if (aux != NULL) {
                mostraAluno(*aux);
                free(aux);
            }
            break;
        case 5:
            printf("\n\tFuncao Remove no Fim:");
            aux = removeFim(lista);
            if (aux != NULL) {
                mostraAluno(*aux);
                free(aux);
            }
            break;
        case 6:
            printf("\n\tFuncao Remove na Posicao!!");
            printf("\n\t\tInforme a posicao:");
            scanf("%d", &posicao);
            aux = removePosicao(lista, posicao);
            if (aux != NULL) {
                mostraAluno(*aux);
                free(aux);
            }
            break;
        case 7:
            printf("\n\nMostra Lista de %s!!!", lista->nome);
            mostraLista(lista);
            break;
        case 8:
            printf("\n\tFuncao Mostra um Aluno na Posicao - Pos!!");
            printf("\n\t\tInforme a posicao:");
            scanf("%d", &posicao);
            aux = localizaAluno(lista, posicao);
            if (aux != NULL) {
                mostraAluno(*aux);
            }
            break;
        case 9:
            printf("\n\tFuncao Apaga toda Lista!");
            apagaLista(lista);
            break;
        case 10:
            printf("\n\n Localiza aluno pelo nome");
            char nome[100];
            printf("\n\t\tInforme o nome do Aluno:");
            scanf("%s", nome);
            aux = localizaAlunoNome(lista, nome);
            if (aux != NULL) {
                mostraAluno(*aux);
            }
            break;
        case 11:
            printf("\n\n Quantidade de elementos na lista: %d", quantidadeElementos(lista));
            break;
        case 12:
            printf("\n\n Apaga um elemento da lista");
            printf("\n\t\tInforme a posicao:");
            scanf("%d", &posicao);
            aux = localizaAluno(lista, posicao);
            if (aux != NULL) {
                mostraAluno(*aux);
                apagaAluno(aux);
                removePosicao(lista, posicao);
            }
            break;
        case 0:
            printf("\n\n*** Fim do Programa!!! ***\n");
            break;
        default:
            printf("\n\n*** Opcao Invalida!!! ***\n");
    }
    if (op > 0 && op <= 10) {
        menuTesteLista(lista);
    }
}


//programa principal para teste 

int main(){

    printf("Exemplo - Lista Simplesmente Encadeada - Lista de Alunos\n");

    //declaração da Lista
    LSE matematica;
    //inicialização da Lista
    inicializaLista(&matematica,"Matematica");

    //declara e inicializa um novo elemento da lista de alunos
    Aluno joao = {"Joao Pedro",23,12345};
    joao.proximo = NULL;
    insereInicio(&matematica,&joao);
    mostraLista(&matematica);


    // //novo aluno declarado estaticamente
    Aluno paulo;
    cadastraAluno(&paulo,"Paulo Roberto",34,232323);
    insereInicio(&matematica,&paulo);
    mostraLista(&matematica);


    Aluno maria;
    cadastraAluno(&maria,"Maria",21,334343);
    insereInicio(&matematica,&maria);
    mostraLista(&matematica);

    //mostraLista2(matematica.primeiro);

    // //Aloca dinamicamente o espaço para um novo Aluno e atribui o endereço a um ponteiro
    menuTesteLista(&matematica);
    
    exit(0);
}
