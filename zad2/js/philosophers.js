// Teoria Współbieżnośi, implementacja problemu 5 filozofów w node.js
// Opis problemu: http://en.wikipedia.org/wiki/Dining_philosophers_problem
//   https://pl.wikipedia.org/wiki/Problem_ucztuj%C4%85cych_filozof%C3%B3w
// 1. Dokończ implementację funkcji podnoszenia widelca (Fork.acquire).
// 2. Zaimplementuj "naiwny" algorytm (każdy filozof podnosi najpierw lewy, potem
//    prawy widelec, itd.).
// 3. Zaimplementuj rozwiązanie asymetryczne: filozofowie z nieparzystym numerem
//    najpierw podnoszą widelec lewy, z parzystym -- prawy. 
// 4. Zaimplementuj rozwiązanie z kelnerem (według polskiej wersji strony)
// 5. Zaimplementuj rozwiążanie z jednoczesnym podnoszeniem widelców:
//    filozof albo podnosi jednocześnie oba widelce, albo żadnego.
// 6. Uruchom eksperymenty dla różnej liczby filozofów i dla każdego wariantu
//    implementacji zmierz średni czas oczekiwania każdego filozofa na dostęp 
//    do widelców. Wyniki przedstaw na wykresach.

var Fork = function () {
    this.state = 0;
    return this;
}

Fork.prototype.acquire = function (cb) {
    // zaimplementuj funkcję acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwszą próbą podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy próba jest nieudana, zwiększa czas oczekiwania dwukrotnie
    //    i ponawia próbę, itd.

    var fork = this;
    var acquire = function (waitTime) {
        setTimeout(function () {
            if (fork.state == 0) {
                fork.state = 1;
                cb();
            }
            else
                acquire(waitTime * 2);
        }, waitTime);
    };

    acquire(1);
}

Fork.prototype.release = function () {
    this.state = 0;
}

var Philosopher = function (id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    this.totalWaitTime = 0;
    this.startWaitTime = 0;

    return this;
}

Philosopher.prototype.startNaive = function (count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id,
        philosopher = this;

    // zaimplementuj rozwiązanie naiwne
    // każdy filozof powinien 'count' razy wykonywać cykl
    // podnoszenia widelców -- jedzenia -- zwalniania widelców

    var naive = function (count) {

        if (count == 0) {
            FINISHED_PHILOSOPHERS++;
            console.log("Philosopher " + id + " finished processing.");
            return;
        }

        philosopher.startWaitTime = new Date().getTime();
        setTimeout(function () {
            forks[f1].acquire(function () {
                console.log("Philosopher " + id + " took the left fork.");
                forks[f2].acquire(function () {
                    philosopher.totalWaitTime += new Date().getTime() - philosopher.startWaitTime;
                    console.log("Philosopher " + id + " took the right fork.");
                    console.log("Philosopher " + id + " begins to eat.");
                    setTimeout(function () {
                        forks[f1].release();
                        forks[f2].release();
                        console.log("Philosopher " + id + " finished eating.");
                        console.log("Philosopher " + id + " begins to think.");
                        naive(count - 1);
                    }, EATING_TIME)
                })
            })
        }, THINKING_TIME)

    };

    //losowe opóźnienie przy starcie, żeby uniknąć zakleczenia na samym początku 
    setTimeout(function () { naive(count) }, Math.floor(Math.random() * 100));
}

Philosopher.prototype.startAsym = function (count) {
    var forks = this.forks,
        f1 = this.id % 2 == 0 ? this.f1 : this.f2,
        f2 = this.id % 2 == 0 ? this.f2 : this.f1,
        id = this.id,
        philosopher = this;


    // zaimplementuj rozwiązanie asymetryczne
    // każdy filozof powinien 'count' razy wykonywać cykl
    // podnoszenia widelców -- jedzenia -- zwalniania widelców

    var asym = function (count) {

        if (count == 0) {
            FINISHED_PHILOSOPHERS++;
            console.log("Philosopher " + id + " finished processing.");
            return;
        }

        philosopher.startWaitTime = new Date().getTime();
        setTimeout(function () {
            forks[f1].acquire(function () {
                console.log("Philosopher " + id + " took the left fork.");
                forks[f2].acquire(function () {
                    philosopher.totalWaitTime += new Date().getTime() - philosopher.startWaitTime;
                    console.log("Philosopher " + id + " took the right fork.");
                    console.log("Philosopher " + id + " begins to eat.");
                    setTimeout(function () {
                        forks[f1].release();
                        forks[f2].release();
                        console.log("Philosopher " + id + " finished eating.");
                        console.log("Philosopher " + id + " begins to think.");
                        asym(count - 1);
                    }, EATING_TIME)
                })
            })
        }, THINKING_TIME)
    };

    asym(count);
}

//semafor ilosciowy dla kelnera 
var Conductor = function (permits) {
    this.state = permits;

    return this;
}

//acquire dla semafora ilosciowego
Conductor.prototype.acquire = function (cb) {
    var conductor = this;

    var acquire = function (waitTime) {
        setTimeout(function () {
            if (conductor.state > 0) {
                conductor.state--;
                cb();
            } else {
                if (waitTime < 4096)
                    acquire(waitTime * 2);
                else
                    acquire(waitTime)
            }
        }, waitTime)
    };

    acquire(1);
}

Conductor.prototype.release = function () {
    this.state++;
}

Philosopher.prototype.startConductor = function (count, conductor) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id,
        philosopher = this;

    // zaimplementuj rozwiązanie z kelnerem
    // każdy filozof powinien 'count' razy wykonywać cykl
    // podnoszenia widelców -- jedzenia -- zwalniania widelców

    var conductorFunc = function (count) {

        if (count == 0) {
            FINISHED_PHILOSOPHERS++;
            console.log("Philosopher " + id + " finished processing.");
            return;
        }

        philosopher.startWaitTime = new Date().getTime();
        setTimeout(function () {
            conductor.acquire(function () {
                forks[f1].acquire(function () {
                    console.log("Philosopher " + id + " took the left fork.");
                    forks[f2].acquire(function () {
                        philosopher.totalWaitTime += new Date().getTime() - philosopher.startWaitTime;
                        console.log("Philosopher " + id + " took the right fork.");
                        console.log("Philosopher " + id + " begins to eat.");
                        setTimeout(function () {
                            forks[f1].release();
                            forks[f2].release();
                            conductor.release();
                            console.log("Philosopher " + id + " finished eating.");
                            console.log("Philosopher " + id + " begins to think.");
                            conductorFunc(count - 1);
                        }, Math.ceil(Math.random() * 5))
                    })
                })

            }
            )
        }, Math.ceil(Math.random() * 75))

    };

    conductorFunc(count);
}


// TODO: wersja z jednoczesnym podnoszeniem widelców
// Algorytm BEB powinien obejmować podnoszenie obu widelców, 
// a nie każdego z osobna

Philosopher.prototype.acquireBothForks = function (cb) {
    const fork1 = this.forks[this.f1], fork2 = this.forks[this.f2];

    var acquire = function (waitTime) {
        setTimeout(function () {
            if (fork1.state == 0 && fork2.state == 0) {
                fork1.state = fork2.state = 1;
                cb();
            } else {
                acquire(waitTime);
            }
        }, waitTime)
    };

    acquire(1);
}

Philosopher.prototype.startBothForks = function (count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id,
        philosopher = this;

    var bothForks = function (count) {

        if (count == 0) {
            FINISHED_PHILOSOPHERS++;
            console.log("Philosopher " + id + " finished processing.");
            return;
        }

        philosopher.startWaitTime = new Date().getTime();
        setTimeout(function () {
            philosopher.acquireBothForks(function () {
                console.log("Philosopher " + id + " took the left fork.");
                philosopher.totalWaitTime += new Date().getTime() - philosopher.startWaitTime;
                console.log("Philosopher " + id + " took the right fork.");
                console.log("Philosopher " + id + " begins to eat.");
                setTimeout(function () {
                    forks[f1].release();
                    forks[f2].release();
                    console.log("Philosopher " + id + " finished eating.");
                    console.log("Philosopher " + id + " begins to think.");
                    bothForks(count - 1);
                }, EATING_TIME)
            })
        }, THINKING_TIME)
    };

    bothForks(count);
}

function saveResults(philosophers, N, count, version) {
    var fs = require('fs')
    var logger = fs.createWriteStream(`results/${version}_${N}_${count}.txt`, {
        flags: 'a'
    })

    for (var i = 0; i < N; i++) {
        logger.write(`${(philosophers[i].totalWaitTime / count)}\n`)
    }
    console.log("Results have been saved!")

}

function waitFor(conditionFunction) {

    const poll = resolve => {
        if (conditionFunction()) resolve();
        else setTimeout(_ => poll(resolve), 400);
    }

    return new Promise(poll);
}

const THINKING_TIME = 10;
const EATING_TIME = 5;

const args = process.argv.slice(2);
var N = parseInt(args[0]);
var count = parseInt(args[1]);
var version = args[2];

var forks = [];
var philosophers = []
var conductor = new Conductor(N - 1);
var FINISHED_PHILOSOPHERS = 0;

for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

for (var i = 0; i < N; i++) {
    if (version === "naive")
        philosophers[i].startNaive(count);
    else if (version === 'asym')
        philosophers[i].startAsym(count);
    else if (version === 'conductor')
        philosophers[i].startConductor(count, conductor);
    else if (version === 'both_forks')
        philosophers[i].startBothForks(count);
}

waitFor(_ => N === FINISHED_PHILOSOPHERS)
    .then(_ => saveResults(philosophers, N, count, version));
