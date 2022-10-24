package com.jetbrains;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        IfSemaphore ifSemaphore = new IfSemaphore();
        WhileSemaphore whileSemaphore = new WhileSemaphore();
        CountingSemaphore countingSemaphore = new CountingSemaphore(1);

	    Race raceWithIf = new Race(ifSemaphore);
        Race raceWithWhile = new Race(whileSemaphore);
        Race raceWithCountingSemaphore = new Race(countingSemaphore);

        raceWithIf.run(2,50000);
        raceWithWhile.run(2,50000);
        raceWithCountingSemaphore.run(2, 50000);
    }
}
