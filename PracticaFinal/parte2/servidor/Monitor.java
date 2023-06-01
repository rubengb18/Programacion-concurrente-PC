package p5.parte2.servidor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private final Lock lock;        //Lock para el monitor
    private final Condition oktoread, oktowrite;    //Colas de condicion para poder leer y escribir
    private int n_lectores, n_escritores;    //Numero de escritores y lectores

    public Monitor() {
        this.n_lectores = 0;
        this.n_escritores = 0;
        this.lock = new ReentrantLock();
        this.oktoread = lock.newCondition();
        this.oktowrite = lock.newCondition();
    }

    void solicita_lectura() throws InterruptedException {
        lock.lock();

        while (n_escritores > 0) oktoread.await();
        ++n_lectores;

        lock.unlock();
    }

    void libera_lectura() {
        lock.lock();

        --n_lectores;
        if (n_lectores == 0) oktowrite.signal();

        lock.unlock();
    }

    void solicita_escritura() throws InterruptedException {
        lock.lock();

        while (n_escritores > 0 || n_lectores > 0) oktowrite.await();
        ++n_escritores;

        lock.unlock();
    }

    void libera_escritura() {
        lock.lock();

        --n_escritores;
        oktowrite.signal();
        oktoread.signal();

        lock.unlock();

    }
}