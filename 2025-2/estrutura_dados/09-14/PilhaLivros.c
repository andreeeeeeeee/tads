#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct livro
{ // dados do elemento livro
    char autor[20];
    char titulo[20];
    int ano;
    int id;
    struct livro *proximo;
} Livro;

typedef struct pilha
{ // atributos de pilha
    Livro *topo;
    int qtd;
} Pilha;

Pilha *criaPilha()
{
    // cria estrutura para pilha
    Pilha *p = (Pilha *)malloc(sizeof(Pilha));
    p->topo = NULL;
    p->qtd = 0;
    return p;
}

Livro *criaElementoPilhaLivro(int id, int ano, char at[], char tit[])
{
    // cria um elemento de pilha
    Livro *lv = (Livro *)malloc(sizeof(Livro));
    lv->proximo = NULL;
    lv->ano = ano;
    strcpy(lv->autor, at);
    strcpy(lv->titulo, tit);
    lv->id = id;
    return lv;
}

void push(Pilha *p, Livro *lv)
{
    // empura um elemento na pilha
    if (lv != NULL)
    {
        lv->proximo = p->topo;
        p->topo = lv;
        p->qtd++;
    }
    else
    {
        printf("\n --- POP não Realizado - Elemento Inválido!!");
    }
}

Livro *pop(Pilha *p)
{
    // salta um elemento da pilha
    Livro *aux = p->topo;
    if (aux == NULL)
    {
        printf("\n -- Nao pode realizar POP - Pilha Vazia\n");
    }
    else
    {
        aux = p->topo;
        p->topo = p->topo->proximo;
        p->qtd--;
        aux->proximo = NULL;
    }
    return aux;
}

void mostraLivro(Livro lv)
{
    printf("\n\t %d - Titulo: %s \n\t Autor: %s \n\t Ano: %d \n", lv.id, lv.titulo, lv.autor, lv.ano);
}

void mostraTopo(Pilha *p)
{
    if (p->topo != NULL)
    {
        printf("\n Mostra TOPO - Pilha");
        mostraLivro(*p->topo);
    }
    else
    {
        printf("\n TOPO NULO - Pilha Vazia \n");
    }
}

void apagaPilha(Pilha *p)
{
    // apaga os elementos da pilha
    while (p->topo != NULL)
    {
        free(pop(p));
    }
}

Pilha *novaPilhaInvertida(Pilha *p)
{
    Pilha *pInvertida = criaPilha();
    Pilha *ptemp = criaPilha();
    Livro *aux = pop(p);
    while (aux != NULL)
    {
        Livro *l = criaElementoPilhaLivro(aux->id, aux->ano, aux->autor, aux->titulo);
        push(pInvertida, l);
        push(ptemp, aux);
        aux = pop(p);
    }

    aux = pop(ptemp);
    while (aux != NULL)
    {
        push(p, aux);
        aux = pop(ptemp);
    }
    return pInvertida;
}

void invertePilha(Pilha *p)
{
    Pilha *pInvertida = criaPilha();
    Livro *aux = pop(p);
    while (aux != NULL)
    {
        Livro *l = criaElementoPilhaLivro(aux->id, aux->ano, aux->autor, aux->titulo);
        push(pInvertida, l);
        aux = pop(p);
    }

    Pilha *p2 = criaPilha();
    Livro *aux2 = pop(pInvertida);
    while (aux2 != NULL)
    {
        Livro *l = criaElementoPilhaLivro(aux2->id, aux2->ano, aux2->autor, aux2->titulo);
        push(p2, l);
        aux2 = pop(pInvertida);
    }

    Livro *aux3 = pop(p2);
    while (aux3 != NULL)
    {
        Livro *l = criaElementoPilhaLivro(aux3->id, aux3->ano, aux3->autor, aux3->titulo);
        push(p, l);
        aux3 = pop(p2);
    }
    return;
}

void inverteExtremos(Pilha *p)
{
    Livro *primeiro = pop(p);

    Pilha *pInvertida = criaPilha();
    Pilha *ptemp = criaPilha();
    Livro *aux = pop(p);
    while (aux != NULL)
    {
        Livro *l = criaElementoPilhaLivro(aux->id, aux->ano, aux->autor, aux->titulo);
        push(pInvertida, l);
        push(ptemp, aux);
        aux = pop(p);
    }

    Livro *ultimo = pop(ptemp);
    push(p, primeiro);

    aux = pop(ptemp);
    while (aux != NULL)
    {
        push(p, aux);
        aux = pop(ptemp);
    }

    push(p, criaElementoPilhaLivro(ultimo->id, ultimo->ano, ultimo->autor, ultimo->titulo));
}

void mostraPilha(Pilha *p)
{
    Livro *aux = p->topo;
    if (aux == NULL)
        printf("\n  -- Pilha Vazia!!\n");
    else
        while (aux != NULL)
        {
            mostraLivro(*aux);
            aux = aux->proximo;
        }
}

void menu(Pilha *p1, int ct)
{
    int op;
    Livro *lv;
    char ch;
    int ano;
    char titulo[20], autor[20];
    do
    {
        printf("\n\nInforme uma Opção:");
        printf("\n -- 1 - Inserir novo Livro -  PUSH:");
        printf("\n -- 2 - Remover um Livro - POP:");
        printf("\n -- 3 - Mostra TOPO:");
        printf("\n -- 4 - Apagar Pilha:");
        printf("\n -- 5 - Mostrar Pilha:");
        printf("\n -- 6 - Copia pilha invertida:");
        printf("\n -- 7 - Inverte pilha:");
        printf("\n -- 8 - Troca extremos:");
        printf("\n -- 0 - para Sair do Programa:\n");
        printf("\nInforme sua Opçao:");
        scanf("%d", &op);
        fflush(stdin);

        switch (op)
        {
        case 1:
            printf("\n Função PUSH. \n");
            printf("Informe o Titulo do Livro:");
            scanf("%s", titulo);
            printf("Informe o Autor:");
            scanf("%s", autor);
            printf("Informe o Ano:");
            scanf("%d", &ano);
            push(p1, criaElementoPilhaLivro(ct++, ano, autor, titulo));
            printf("\n Inserção Realizada com Sucesso");
            break;
        case 2:
            printf("\n Função POP. \n");
            lv = pop(p1);
            if (lv != NULL)
            {
                mostraLivro(*lv);
                printf("\n --- Remoção Realizada com Sucesso!!");
            }
            else
            {
                printf("\n --- ERRO - Remoção inválida!!");
            }
            break;
        case 3:
            printf("Mostra TOPO Pilha P1:");
            mostraTopo(p1);
            break;
        case 4:
            printf("\n Apagar a Pilha !! \n");
            apagaPilha(p1);
            break;
        case 5:
            printf("\nMostra Pilha P1:");
            mostraPilha(p1);
            break;
        case 6:
            Pilha *p2 = novaPilhaInvertida(p1);
            printf("original");
            mostraPilha(p1);
            if (p2->qtd > 0 || p2->topo != NULL)
            {
                printf("cópia");
                mostraPilha(p2);
            };
            break;
        case 7:
            printf("original");
            mostraPilha(p1);
            invertePilha(p1);
            printf("invertida");
            mostraPilha(p1);
            break;
        case 8:
            printf("original");
            mostraPilha(p1);
            inverteExtremos(p1);
            printf("trocados");
            mostraPilha(p1);
            break;
        default:
            printf("\nFim do programa!!\n");
        }

    } while (op > 0);
}

int main()
{

    Pilha *p1;
    Livro *l1, *l2, *l3, *l4;

    p1 = criaPilha(); // pilha end X

    int ct = 1;

    l1 = criaElementoPilhaLivro(ct++, 2016, "Paul Deitel and  Harvey Deitel", "Java: Como Programar");
    l2 = criaElementoPilhaLivro(ct++, 2006, "H. M. Deitel", "C++: Como Programar 5°Ed.");
    l3 = criaElementoPilhaLivro(ct++, 2016, "Allen B. Downey", "Pense em Python:");
    l4 = criaElementoPilhaLivro(ct++, 1980, "CSLEWIS", "Nárnia");

    push(p1, l1);
    push(p1, l2);
    push(p1, l3);
    push(p1, l4);

    menu(p1, ct);

    exit(0);
}