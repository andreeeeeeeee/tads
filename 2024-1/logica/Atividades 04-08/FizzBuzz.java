// mostrar os numeros de 1 a 100.
//quando o numero for multiplo de 3, mostrar a palavra fizz,
//quando for multiplo de 5, mostrar buzz

public class FizzBuzz {
    public static void main(String[] args) {
        
        int i = 0;

        while (i <=100) {
            if (i % 3 ==0 && i % 5 == 0) {
                System.out.println("FizzBuzz");
            } else if (i%3==0) {
                System.out.println("Fizz");
            } else if (i%5==0){
                System.out.println("Buzz");
            }
            i++;
        }
    }
}
