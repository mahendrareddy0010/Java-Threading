`Problem solved here:`
    1. `Producer Consumer`
    2. `Rate Limiting with token bucket`
    3. `Deffered Callback`
    4. `Cunting Semaphore implementation`
    5. `n-Read n-Write Lock with starvation / partial starvation / without starvation`
    6. `Cyclic barrier implementation`
    7. `Uber Rider Problem`
    8. `Barber Shop Pproblem`
    9. `Lock Free Stack / Non blocking Stack implementation`
    10. `Data structures and Algorithms :`
            a. `Merge Sort`
    11. `Data Race demo and use of volatile keyword`
    12. `Unisex Bathroom problem without starvation`

`Note: `One of the reason why starvation leads to bad performance is that you need to hold on some valid & which are optimal to execute.
`For example`, Consider Read-Write locks, while first thread takes Read lock and wait lot of time, in mean time you can kept on executing read threads as long as it does not cross MAX reads at time and first thread releases lock. To avoid starvation, we have to hold back read threads.
`Crux of the problem` here, is that we don't know how any read thread holds the read lock.

`Reference : ` Java Multithreading, Concurrency & Performance Optimization course on udemy by Michael Pogrebinsky
            https://www.udemy.com/course/java-multithreading-concurrency-performance-optimization/?couponCode=ST13MT40224

`Reference : ` Educative.io Java Multi Threading for Senior Software Engineering Interviews