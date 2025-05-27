# Gestión y Administración de Clientes - Microservicio

## Descripción
 Este microservicio está desarrollado con Spring Boot y Maven para gestionar clientes, sus direcciones, y el historial de compras asociado. Las operaciones incluyen la creación, actualización, eliminación y consulta de clientes y sus datos relacionados. El diseño sigue una arquitectura en capas: Model, Repository, Service y Controller, y expone funcionalidades mediante APIs REST.

## Tecnologías usadas

- Java 17  
- Spring Boot  
- Oracle Database  
- Maven  
- Lombok  
- Postman (para pruebas de endpoints)
- Swagger (SpringDoc OpenAPI) 

## Dependencias incluidas (`pom.xml`)

- `spring-boot-starter-web`  
- `spring-boot-starter-data-jpa`  
- `lombok`  
- `ojdbc8` (Oracle JDBC Driver)  
- `oraclepki`, `osdt_core`, `osdt_cert` (Oracle Wallet Security)  
- `springdoc-openapi-starter-webmvc-ui` (Swagger UI / documentación de API)

## Estructura del proyecto
# Modelos:
Definen las entidades principales: Cliente, Direccion, e HistorialDeCompra. Estas clases incluyen atributos específicos y relaciones como @OneToOne y @ManyToOne.

**Repository:**  
Extienden JpaRepository para permitir operaciones CRUD sobre cada entidad y consultas personalizadas (por ejemplo, buscar clientes por ID o por rut).

**Service:**  
Contienen la lógica de negocio para cada entidad: guardar, actualizar, listar y eliminar clientes.

**Controller:**  
Define los endpoints REST para exponer las operaciones CRUD. Se implementan validaciones en las solicitudes y se manejan respuestas con códigos HTTP adecuados (ejemplo: 200, 201, 400, 404).

## Relación entre entidades
`Cliente` incluye información personal y está relacionado directamente con una Direccion mediante una relación @OneToOne.

`Historial de compra` permite rastrear múltiples compras por cliente a través de una relación @ManyToOne.

## Funcionalidades principales

- Crear, actualizar, eliminar y listar clientes.
- Búsquedas por atributos relevantes como ejemplo por rut o ID.
- Validaciones básicas en las solicitudes para  evitar duplicados.

## Configuración importante

- Configuración en `application.properties` para conexión a base de datos Oracle con parámetros de Wallet.
- Hibernate configurado para actualizar el esquema automáticamente (`spring.jpa.hibernate.ddl-auto=update`).
