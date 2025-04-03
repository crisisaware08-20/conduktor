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
```bash
docker compose up --build --force-recreate
