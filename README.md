# todo-list
할일 목록(todo list)을 관리하는 웹 어플리케이션입니다.
임베디드 톰캣, 인 메모리 데이터베이스(h2)를 사용해서 자바와 메이븐만 설치되어있으면 실행할 수 있습니다.

## Requirements
- [JDK 1.8+](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [maven](https://maven.apache.org)

## 빌드 방법
```
mvn clean package -Dmaven.test.skip=true
```

## 실행 방법
```
mvn spring-boot:run
```

## 실행 화면
http://localhost:8080/todos
![image](https://user-images.githubusercontent.com/12438898/52912007-c5951d80-32ee-11e9-916c-39c83d406096.png)
