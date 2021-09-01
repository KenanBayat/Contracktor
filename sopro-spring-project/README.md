Spring Boot + Thymeleaf project template by Alexander Krause (akr), Henning Schnoor (hs),
and Sandro Esquivel (sae) for CAU Kiel Softwareprojekt

# How to start in Eclipse
- Import to Eclipse with **File -> Import... -> Gradle -> Existing Gradle Project**
- (Optional) If Eclipse shows errors upon importing, try **Right click project -> Gradle -> Refresh Gradle Project**
- (Optional) If not already depicted, open `Gradle Tasks` window **Window -> Show View -> Gradle -> Gradle Tasks**
- Run with **Gradle Tasks -> sopro-spring-project -> application -> bootRun**
- Run unit tests with **Gradle Tasks -> sopro-spring-project -> verification -> test**
- Access locally running application via web browser at `localhost:8080`

## How to start via command line interface (CLI)
- Open the `sopro-spring-project` folder in your CLI
- Run the Gradle Tasks via the embedded Gradle Wrapper, e.g., `./gradlew bootRun`

# Employed technologies
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Thymeleaf](https://www.thymeleaf.org)
- [H2 Database Engine](http://www.h2database.com/html/main.html)
- [Bootstrap](https://getbootstrap.com)
- [Docker](https://www.docker.com/)

# Further information
- [Beginner's guide to JPA](https://vladmihalcea.com/a-beginners-guide-to-jpa-and-hibernate-cascade-types/)
- [One2Many vs. Many2Many simple explanation](https://stackoverflow.com/questions/21407402/one-to-many-vs-many-to-many-relationship)
- [Why you should not use CascadeType.REMOVE in Many2Many](https://thoughts-on-java.org/avoid-cascadetype-delete-many-assocations/)

# Guides at spring.io
The following guides provide detailed information how to use certain features:

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
- [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
- [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

# How to start a gitlab-runner for CI/CD
1. Start the Docker-based gitlab-runner on your machine
```
docker run -d \
--name gitlab-runner \
-v /srv/gitlab-runner/config:/etc/gitlab-runner \
-v /var/run/docker.sock:/var/run/docker.sock \
gitlab/gitlab-runner:latest
```

2. Connect the gitlab-runner with Gitlab
```
docker run --rm -ti \
-v /srv/gitlab-runner/config:/etc/gitlab-runner \
gitlab/gitlab-runner register \
--docker-volumes /var/run/docker.sock:/var/run/docker.sock \
--url https://git.informatik.uni-kiel.de/ \
--executor docker \
--description "sopro gitlab runner" \
--docker-image "docker:19.03.1"
```

Your CI/CD pipeline will now be executed by this gitlab-runner.