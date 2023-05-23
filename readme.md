## Backend para el portfolio

Backend desarrollado con Java Springboot.

Este proyecto es una <b>API REST</b>.

## Documentación <b>API-REST</b>

* <a href="https://portfolio-backend-ss.onrender.com/swagger-ui" target="_blank">Link a la documentación Generada Por Swagger UI y Open API.</a>

## Diagrama DER

* En la carpeta "sql" están el diseño de la base de datos en formatos DER y el script para crear la base de datos

![ScreenShot](sql/DER_portfolio_DB.png)

## Tecnologías usadas:

* SpringBoot 3.0.5
* Java 17
* Swagger UI

## Plataformas y servicios usados:

* <a href="https://portfolio-backend-ss.onrender.com" target="_blank">Render (Heakth check page)</a> para alojar la aplicación de backend.
* <a href="https://stats.uptimerobot.com/XBxDGu4LpO" target="_blank">Uptime Robot (Status page)</a> para mantener el servicio activo  e impedir que render apague la aplicación.


## Estado Actual:

* Implementada funcionalidades de CRUD y login básico
* Relaciones many-to-one para correcta representación del DER y borrado en cascada
* Funcionalidad completa y optima en entidades Persona y Proyectos
* Se permite crear nuevos usuarios y sus propias entidades con sus relaciones y borrado en cascada o sea crear un usuario con sus propios proyectos, experiencias, etc, que son solo editables por el y se pueden visualizar en el sitio al logearse y deslogearse.

## Actualmente implementando:
* Login avanzado con JWT
* Implementación y refactorización del resto de enpoints para las entidades.

## Deciciones de diseño

* Deshabilitando Spring open session in view (OSIV) y @Transactional para lograr mejor performance relacionada con la base de datos (https://www.baeldung.com/spring-open-session-in-view)
* Usando relaciones many-to-one por ser mas versátiles que one-to-many en relación con paginación y filtrado y ordenamiento ()

## Desarrollador
* Sebastián Sala - *Diseño e implementación del Sistema*

## Contacto 
* sebastiansala.dev@gmail.com
* https://sebastiansala.web.app