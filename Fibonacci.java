import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/* 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711 ...*/

public class Fibonacci extends RecursiveTask<BigInteger> {
    private final int N;

    public Fibonacci(int n){ // конструктор
        N = n;
    }

    @Override
    protected BigInteger compute() {
        switch (N){
            case 0: return BigInteger.ZERO; // элемент последовательност 0 = 0
            case 1: return BigInteger.ONE; // элемент последовательности 1 = 1
            default:
                Fibonacci fib_1 = new Fibonacci(N-1);
                Fibonacci fib_2 = new Fibonacci(N-2);
                fib_1.fork();
                fib_2.fork();
                return(fib_1.join().add(fib_2.join()));
        }
    }
}

class Test{
    public static void main(String args[]){
        int n = 35;
        ForkJoinPool pool = new ForkJoinPool();
        Fibonacci f = new Fibonacci(n);
        pool.invoke(f);
        try {
                System.out.println("Fibonacci [" + n + "]: " + f.get());
        }catch (Exception e){}
    }
}
