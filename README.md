# Net_Java_AQA_9_2

###1. Подготовительная часть. 
- создаем проект с Gradle в Idea и добавляем файл docker-compose.yml (  [отсюда](https://github.com/reportportal/reportportal/blob/master/docker-compose.yml))
- т.к. ReportPortal разворачивается под Win то вносим коррективы в docker-compose.yml:
 ```
  volumes:
  # For windows host
    - postgres:/var/lib/postgresql/data
  # For unix host
  # - ./data/postgres:/var/lib/postgresql/data >
````
```
# Docker volume for Windows host
volumes:
  postgres:
  minio:
```
- через VPN запускаем docker-compose.yml и разворачиваем ReportPortal

###3. Интеграция Junit5+Gradle используя [это](https://github.com/reportportal/agent-java-junit5)
- добавляем в build.gradle
```
repositories {
    mavenLocal()
    mavenCentral()
}
dependencies {
    ....
    testImplementation 'com.epam.reportportal:agent-java-junit5:5.1.2'
    testImplementation 'com.epam.reportportal:logger-java-log4j:5.1.4'
    testImplementation 'org.apache.logging.log4j:log4j-core-its:2.8.2'
    testImplementation 'org.apache.logging.log4j:log4j-api-scala_2.10:2.8.2'
    ....
}
```
- объявляем логер и добавлем в тест 
```
public class CallBackTests {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(CallBackTests.class);
    ...
    @Test
    public void shouldWorkHappyPath() {
    ...
         LOGGER.info("Logger test message");
    }
}
```
- добавляем log4j2.xml в папку src/test/resources 

###3. Проверка портала
- проверяем доступность http://localhost:8080/
- Суперпользователем создаем новый проект и добавляем в него обычного тестового пользователя
- в src/test/resources создаем файл reportportal.properties , куда копируем информацию из профиля пользователя на портале 
- для обозначения тестов которые будет ловить ReportPortal используем аннотацию **@ExtendWith(ReportPortalExtension.class)**
- настраиваем Junit для использования ReportPortal.В build.gradle добавим
```
...
  test {
    ...
    testLogging.showStandardStreams = true
    useJUnitPlatform()
    systemProperty 'junit.jupiter.extensions.autodetection.enabled', true
    ...
}
  ```

Если все настроено верно, то при любое прохождение тестов отражается на портале