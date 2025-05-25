### How to run it locally ?

#### Start kafka and create the topic with three partitions 
```bash
docker compose -f docker-compose-kafka.yml up 
```
Make sure that the topic has been created, a message like this should be printed in the logs:
```bash
Topic created successfully!
```

Alternatively, the application accept bootstrap server and topic as environment variables, so you can run the application with your own kafka instance.
See `docker-compose.yml` for more details.


#### Start application, it should be accessible on localhost:8080

Open a new terminal and run the following command

```bash
docker compose up --build --force-recreate
```


#### Example usage
```bash
http://localhost:8080/topic/persons-topic/5?count=10
http://localhost:8080/topic/persons-topic/5
http://localhost:8080/topic/persons-topic
http://localhost:8080/topic/persons-topic?count=20
```
