`General Pattern: Starvation`
    To avoid starvation, Other threads should know about waiting threads of other type.
    1. waitingThreads like `waitingMen`, `waitingWomen`
    2. insideThreadCount like `menInside`, `womenInside`    
    3. how many threads served in this round like `menServed`, `womenServed`

`Note:` This is different from printing infinite series of numbers in Even/Odd. If you follow order printing solution, then we get deadlock in `Unisex Bathroom` problem