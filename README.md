<h1 align="center"> External API </h1>
<p align="center">
   <img src="https://user-images.githubusercontent.com/82063170/213549339-79f1391c-e37f-4e2b-860b-578ed783ec8c.png">
</p>
<p>
  <img src=https://img.shields.io/badge/spring--boot-3.0.1-brightgreen>
  <img src=https://img.shields.io/badge/java-11-brightgreen>
  <img src=https://img.shields.io/badge/junit-5.8.1-blue>
  <img src=https://img.shields.io/badge/mockito-4.11.0-blue>
  <img src=https://img.shields.io/badge/springfox-3.0.0-orange>
  <img src=https://img.shields.io/badge/gradle-7.6-brightgreen>
</p>

<h2 align=left> Índice </h2>
<ul>
  <li><a href="#desc"> Descripcion del proyecto </a></li>
  <li><a href="#inst"> Instalación </a></li>
  <li><a href="#test"> Test </a></li>
</ul>

<h2 align=left id="desc"> Descripción del Proyecto </h2>
<p>
He desarrollado una API REST en Spring Boot que realiza operaciones CRUD en una base de datos MySQL, utilizando JPA y Hibernate para el manejo de la base de datos. Además, he implementado el concepto de optimistic locking para gestionar la concurrencia en la base de datos. En el proyecto, he aplicado conocimientos avanzados en el manejo de excepciones y he realizado pruebas unitarias exhaustivas utilizando JUnit y Mockito. También he utilizado Gradle para la gestión del proyecto y he implementado el concepto de scaffolding para acelerar el proceso de desarrollo. Por último, para documentar la API, he utilizado Swagger.
</p>

<h2 align=left id = "inst"> Instalación </h2>
<ul>
  <li><a href="https://www.oracle.com/java/technologies/downloads/#java11"> Java 11.0 </a></li>
  <li><a href="https://gradle.org/install/"> Gradle 7.6 </a></li>
  <li><a href="https://www.mysql.com/downloads/"> MySQL </a></li>
</ul>

```
$ git clone https://github.com/Gonzalo-Messina/externalapi.git
$ cd challenge
$ gradle clean
$ gradle bootRun
```
<p> Luego de ejecutar los comandos la aplicacion se encontrara levantada en la URL http://localhost:8090,
desde esa URL y agregando /televisions a la ruta general(http://localhost:8080/televisions) accederemos a los diferentes endpoints,
por ejemplo desde Postman
</p>

<h2 align=left id="test"> Tests </h2>
<p> Para correr los test, agregue la siguiente dependencia al build.gradle: apply plugin: 'jacoco'</p>
<p> Luego ejecutamos el siguiente comando</p>
```
$ gradle jacocoTestCoverageVerification
```

<p> Ademas, al correr dicho comando, se genera un reporte del coverage tanto de los tests unitarios como los de integración.
El mismo se aloja en el siguiente directorio: </p>
 ```
 build/reports/jacoco/test/html/index.html
```
<img src="src/main/resources/TestCoveragePackages.png">
