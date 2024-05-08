`Date race vs Race condition :`
    `Data Race` is execution of out of order instruction respecting the semantics of the program.
    `Race condition` is interleaving of access (reads and atleast one write) to shared variable.

`Volatile keyword:`
    `(all instructions executed before below instruction)`
    `access (read/write) to volatile variable`
    `(all instructions executed after above instruction )`

`It's a memory barrier.`

`Note:`
    In our example, if we use if (x < y) in checkDatarace() method, we may/may not see the datarace.
    `Reason:` consider the scenario, `x = y = 0`.
        `y++` 
                `x < y this is not atomic opertion`
        `x++`
                `read x`
                `read y`
                `check x < y , here now x=1, y=1, so it fails`
        `y++` 
