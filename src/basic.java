import java.util.LinkedList;

import java.util.Queue;

public class basic{

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

                    while (list.size() >= size) {
                        wait();
                    }

                    list.add(value);

                    System.out.println("Produced " + value);

                    value++;

                    notify();

                    Thread.sleep(1000);
            }

        }

        public void consume() throws InterruptedException {

            while (true) {

                    while (list.size() == 0) {
                        wait();
                    }

                    int value = list.poll();

                    System.out.println("Consume " + value);

                    notify();

                    Thread.sleep(1000);

                }

            }

        }

    }


