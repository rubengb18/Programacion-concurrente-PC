package p5.parte2.cliente;

import java.util.concurrent.atomic.AtomicInteger;

public class AlgTicket {
    private final int M;
    private final numVolatil[] turn;
    private final AtomicInteger number;
	private final AtomicInteger next;

    public AlgTicket(int M) {
        next = new AtomicInteger();
        next.set(1);
        number = new AtomicInteger();
        number.set(1);
        this.M = M;

        turn = new numVolatil[2 * M - 1];
        for (int i = 0; i < 2 * M - 1; ++i) {
            turn[i] = new numVolatil();
        }
    }

    public void entry(int id) {
        turn[id].set(number.getAndIncrement());
        while (turn[id].get() != next.get()) ;
    }

    public void exit() {

        next.incrementAndGet();
    }

    public static class numVolatil {
        private volatile int n;

        public numVolatil() {
            this.n = 0;
        }

        public int get() {
            return n;
        }

        public void set(int n) {
            this.n = n;
        }
    }
}