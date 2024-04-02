`1 Token / second`
1. We `don't need producer thread` for token generation, because we can calculate based on time
2. We can have `Producer Thread` for token generation, then it simply looks like `producer consumer problem` with `1 producer`, `n comsumers`
    `Note:` Used Factory method just to start background thread generating tokens, 
            Otherwise, we have to use do it in constructor itself which is bad, it could use `this` object without completing the object creation process. In our case, we start thread in the end of construction. it would work because it has created all the variables by the time we start the thread. But this is not good design.