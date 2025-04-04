### How to run it locally ?

1. Start kafka and create the topic with three partitions 
```bash
docker compose -f docker-compose-kafka.yml up 
```
Make sure that the topic has been created, a message like this should be printed in the logs:
```bash
Topic created successfully!
```

Alternatively, the application accept bootstrap server and topic as environment variables, so you can run the application with your own kafka instance.
See `docker-compose.yml` for more details.


2. Start application, it should be accessible on localhost:8080

Open a new terminal and run the following command

```bash
docker compose up --build --force-recreate



### Example usage

http://localhost:8080/topic/persons-topic/5?count=10
http://localhost:8080/topic/persons-topic/5
http://localhost:8080/topic/persons-topic
http://localhost:8080/topic/persons-topic?count=20

### What I would think of furhther improvements ?

I would have a cache in between to save application from making any unnecessary calls to kafka.

I don't like the package name 'people' probablly would have used 'persons' or 'challenge' instead :).
