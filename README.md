# todo-list
할일 목록(todo list)을 관리하는 웹 어플리케이션입니다.
임베디드 톰캣, 인 메모리 데이터베이스(h2)를 사용해서 자바와 메이븐만 설치되어있으면 실행할 수 있습니다.

## Requirements
- [JDK 1.8+](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [maven](https://maven.apache.org)

## 빌드
```
mvn clean package -Dmaven.test.skip=true
```

## 실행
```
mvn spring-boot:run
```

## 실행 화면
http://localhost:8080/todos
![image](https://user-images.githubusercontent.com/12438898/52912007-c5951d80-32ee-11e9-916c-39c83d406096.png)

## REST API
이 프로젝트는 REST API를 지향합니다. 어플리케이션을 실행한 후 [스웨거 링크](http://localhost:8080/swagger-ui.html#/to-do-list-controller)로 접속하면 REST API를 확인할 수 있습니다.

## 데이터베이스
이 프로젝트는 프로덕션 코드가 아니므로, 설치없이 바로 사용할 수 있는 [h2 database](http://www.h2database.com/html/main.html)를 사용합니다. 어플리케이션을 재실행하면 저장된 데이터는 모두 초기화됩니다. 실행 시 [data.sql](https://github.com/godekdls/todo-list/blob/master/src/main/resources/data.sql)에 저장된 쿼리를 실행하여 더미 데이터를 삽입합니다.
![image](https://user-images.githubusercontent.com/12438898/52914598-b8d3f200-330d-11e9-803f-077837b0ac45.png)
