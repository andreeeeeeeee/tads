#include <stdio.h>
#include <stdlib.h>

int * cria_vetor(int n) {
    int *v;
    v = (int *)(malloc(n * sizeof(int)));
    for (int i = 0; i < n; i++) {
        printf("Digite o %d° elemento: ", i+1);
        scanf("%d", &v[i]);
    }

    return v;
}
int main() {
    int n;
    printf("Digite o tamanho do vetor: ");
    scanf("%d", &n);
    
    int *v = cria_vetor(n);

    for (int i = 0; i<n; i++) {
        printf("%d", v[i]);
        if (i < n-1) printf(", ");
    }
    printf("\n");
    free(v);
    exit(0);
}