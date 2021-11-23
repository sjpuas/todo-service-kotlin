ToDo Service
============

Apply spotless
```shell
./gradlew :spotlessApply
```

How to run
```shell
./gradlew bootRun 
```

Build 
```shell
./gradlew build
```

##### Endpoints 

Create a new ToDo Item
```shell
http POST :8080/todos "text=todo task"
```

Get all ToDo Items
```shell
http :8080/todos 
```

Get a ToDo Items by Id
```shell
http :8080/todos/$id
```

Delete a ToDo Items by Id
```shell
http DELETE :8080/todos/$id
```
