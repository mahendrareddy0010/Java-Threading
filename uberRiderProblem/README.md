`Idea :`
    I solved it without using any in built Barriers or semaphores

`Cyclic Barrier vs Uber Rider Problem :`
    1. Whenever we need to process in batches, we have to use boolean for stopping next batch threads untill we are finished with current batch.
    2. Once we reach the end of batch, we have to use release count for other waiting members of the batch to process, and to identify the last memeber of the batch.