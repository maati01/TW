package agh.tw;

public class PhilosopherWithWaiter extends PhilosopherBase{
    private final Waiter waiter;

    PhilosopherWithWaiter(int index, Table table, int iterations, Waiter waiter) {
        super(index, table, iterations);
        this.waiter = waiter;
    }

    @Override
    public void run(){
        for (int i = 0; i < iterations; i++) {
            timer.startTime();

            while (!waiter.tryAcquire()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                table.getSticks().get(leftIndex).acquire();
                table.getSticks().get(rightIndex).acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer.stopTime();
            eat();
            table.getSticks().get(leftIndex).release();
            table.getSticks().get(rightIndex).release();
            waiter.release();
            think();
        }
    }
}
