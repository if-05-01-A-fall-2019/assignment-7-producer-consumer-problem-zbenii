import java.util.LinkedList;

import java.util.Queue;
import java.util.concurrent.Semaphore;


public class semaphor{

    static Semaphore  semCon=new Semaphore(0);
    static Semaphore  semProd= new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {

        Buffer buffer = new Buffer(2);

        Thread producerThread = new Thread(new Runnable() {

            @Override

            public void run() {

                try {

                    buffer.produce();

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }

        });

        Thread consumerThread = new Thread(new Runnable() {

            @Override

            public void run() {

                try {


                    buffer.consume();

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }

        });

        producerThread.start();

        consumerThread.start();

        producerThread.join();

        consumerThread.join();

    }

    static class Buffer {

        private Queue<Integer> list;

        private int size;

        public Buffer(int size) {

            this.list = new LinkedList<>();

            this.size = size;

        }

        public void produce() throws InterruptedException {

            int value = 0;

            while (true) {

                semProd.acquire();

                list.add(value);

                System.out.println("Produced " + value);

                value++;

                semCon.release();

                Thread.sleep(1000);
            }

        }

        public void consume() throws InterruptedException {

            while (true) {

                semCon.acquire();

                int value = list.poll();

                System.out.println("Consume " + value);

                semProd.release();

                Thread.sleep(1000);

            }

        }

    }

}


