import functions.*;
import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Log;
import functions.basic.Sin;
import functions.meta.Composition;
import threads.*;

import java.io.*;
import java.util.Random;


public class Main {
    public static void main(String[] args){
        //Exp exp = new Exp();
        //System.out.println(Functions.integrate(exp, 0 , 1, 0.001));  // в 7 знаке после запятой отличие
        //Main.nonThread();
        //Main.simpleThreads();
        Main.complicatedThreads();
    }
    public static void nonThread(){
        Task task = new Task();
        task.setCountRunningTasks(100);
        for (int i = 0; i < task.getCountRunningTasks(); i++) {
            task.setFunction(new Log(Math.random() * 9 + 1));
            task.setLeft(Math.random() * 100);
            task.setRight(Math.random() * 100 + 100);
            task.setStep(Math.random());
            System.out.println("Source " + task.getLeft() + " " + task.getRight() + " " + task.getStep());
            double result = Functions.integrate(task.getFunction(), task.getLeft(), task.getRight(), task.getStep());
            System.out.println("Result " + task.getLeft() + " " + task.getRight() + " " + task.getStep() + " " +result);
        }
    }

    public static void simpleThreads(){
        Task task = new Task();
        task.setCountRunningTasks(100);

        Thread thread1 = new Thread(new SimpleGenerator(task));
        Thread thread2 = new Thread(new SimpleIntegrator(task));

        /*
        thread1.setPriority(9);
        thread2.setPriority(2);
        */
        thread1.start();
        thread2.start();

    }

    public static void complicatedThreads(){
        Task task = new Task();
        task.setCountRunningTasks(100);

        Semaphore semaphore = new Semaphore();
        Thread thread1 = new Thread(new Generator(task, semaphore));
        Thread thread2 = new Thread(new Integrator(task, semaphore));


        thread1.setPriority(2);
        thread2.setPriority(4);


        thread1.start();
        thread2.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.interrupt();
        thread2.interrupt();
    }
}