# Proyecto Monolítico con Spring Boot

## Descripción
Este es un sistema monolítico basado en arquitectura en capas, que contiene dos módulos funcionales:

### GiftCard
Un sistema para la gestión de operaciones CRUD de las tarjetas de regalo, además de la implementación de un sistema de notificaciones utilizando JavaMail para los endpoints de redimir y creación de tarjetas.

### User
Un sistema de autenticación que utiliza JSON Web Tokens (JWT) para gestionar la seguridad y la autenticación de usuarios y poder acceder a los endpoints según el rol (`ADMIN`, `USER`).

El código fuente se encuentra en este repositorio y el README contiene instrucciones para poder clonar el proyecto en local y realizar la compilación y ejecución.

## Tecnologías Utilizadas
- Java 21
- Spring Boot 3
- Lombok
- Spring Security
- JWT (JSON Web Token)
- Hibernate y JPA
- PostgreSQL
- JUnit y Mockito
- JavaMail
- Gradle
- Swagger UI

## Instrucciones de Compilación y Ejecución
A continuación se proporcionan instrucciones claras sobre cómo instalar, ejecutar y probar la aplicación. 🚀

### 1. Requisitos Previos
Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes programas:
- JDK 21
- Gradle 7+
- PostgreSQL

### 2. Clonar el repositorio
Abrir la terminal o línea de comandos y ejecutar:
```sh
git clone https://github.com/andres-colonia-al/proyecto-monolitico
cd proyecto-monolitico
```

### 3. Configurar la Base de Datos
Crear una base de datos en PostgreSQL y ejecutar:
```sql
CREATE DATABASE PRUEBA_TECNICA;
```

### 4. Configurar las variables en `application.properties`
El archivo `application.properties` dentro de `src/main/resources` debe configurarse con las credenciales adecuadas para conectar la base de datos PostgreSQL y el email al que se desean enviar las notificaciones.

Ejemplo de configuración:
```properties
# Configuración de la Base de Datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/PRUEBA_TECNICA
spring.datasource.username=postgres  # Reemplazar con el usuario de PostgreSQL
spring.datasource.password=1234      # Reemplazar con la contraseña de PostgreSQL
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate (para que cree las tablas automáticamente)
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=create-drop  # Crea las tablas y las elimina al cerrar la app
spring.jpa.show-sql=true  # Muestra las consultas SQL en la consola

# Configuración del Token JWT
jwt.secret=mi_secreto_seguro  # Se puede cambiar por otro valor seguro
jwt.expiration=86400000  # Duración del token en milisegundos (1 día)

# Configuración del email para notificaciones
email.sender=gloweelatam@gmail.com
email.password=foozgmzcrcceindr
email.notifications=andrescolonia7@gmail.com  # Modifica el correo
```
📌 **Importante:**
- Para ambiente local, el usuario y la contraseña deben coincidir con los de la instalación de PostgreSQL.
- Si `email.sender` genera conflictos al envío de los correos por temas de seguridad, puedes reemplazarlo por otra cuenta, **solo Gmail**.

### 5. Instalar dependencias
```sh
# En Linux/Mac
./gradlew build

# En Windows
gradlew build
```

### 6. Ejecutar la aplicación
```sh
# Linux/Mac
./gradlew bootRun

# Windows
gradlew bootRun
```

### 7. Acceder a la API y la documentación en Swagger
- **API REST:** [http://localhost:8080](http://localhost:8080)
- **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### 8. Pruebas del proyecto
```sh
# En Linux/Mac
./gradlew test

# En Windows
gradlew test
```

## Endpoints Principales

### 📌 Módulo de Gift Cards

- **Obtener Todas las Gift Cards**  
  `GET /giftcard`

- **Obtener GiftCard por ID**  
  `GET /giftcard/{id}`

- **Crear Gift Card**
  `POST /giftcard`
En este Endpoint no se le debe pasar un ID.  
  ```json
  {
      "code": "ABCD1234",
      "amount": 100.0,
      "expiresAt": "2025-02-24T00:17:38.013Z",
      "status": "ACTIVA"
  }
  ```

- **Actualizar Gift Card**  
  `PUT /giftcard/{id}`
  Solo los campos descritos son actualizables
  ```json
  {
      "code": "GC1004GHI", 
      "amount": 100.0,  (Actualizable)
      "expiresAt": "2025-02-24T00:17:38.013Z",  (Actualizable)
      "status": "ACTIVA"  (Actualizable)
  }
  ```

- **Redimir Gift Card**
  `POST /giftcard/redeem/{code}`
  Este Endpoint recibe el código único (code) de la gift card. 

### 🔐 Módulo de Autenticación

- **Autenticación (Login)**  
  `POST /login`
  ```json
  {
      "username": "pruebaadmin",
      "password": "1234"
  }
  ```
  **Respuesta:**
  ```json
  {
      "Message": "Autenticación Correcta",
      "token": "eyJhbGciOiJIUzI1NiIsInR5cC",
      "username": "pruebaadmin"
  }
  ```

### 👥 Módulo de Usuarios
Se requiere el token para acceder a los siguientes endpoints debido a que están protegidos, el token se pasa como un Bearer Token

- **Obtener Usuarios**  
  `GET /user` *(Requiere rol ADMIN o USER)*

- **Crear Usuario**  
  `POST /user` *(Requiere rol ADMIN)*
  En este Endpoint no se le debe pasar un ID.
  ```json
  {
      "username": "prueba3",
      "cc": 123456784,
      "name": "Prueba-User3",
      "lastName": "P3",
      "password": "1234",
      "dateOfBirth": "1996-05-15",
      "email": "prueba3@example.com",
      "role": "ADMIN"
  }
  ```

- **Actualizar Usuario**  
  `PUT /user/{id}` *(Requiere rol ADMIN)*
  Solo los campos descritos son actualizables
  ```json
  {
      "username": "prueba3",
      "cc": 123456784,
      "name": "Prueba-User3",  (Actualizable)
      "lastName": "P3",  (Actualizable)
      "password": "1234",  (Actualizable)
      "dateOfBirth": "1996-05-15",  (Actualizable)
      "email": "prueba3@example.com",  
      "role": "ADMIN"  (Actualizable)
  }
  ```

- **Eliminar Usuario**  
  `DELETE /user/{id}` *(Requiere rol ADMIN)*

## 📄 API Swagger
Documentación de la API en:  
<img width="1433" alt="Image" src="https://github.com/user-attachments/assets/9ac3efef-bdbd-4352-95af-3790a98e8c11" />

## 📌 Autor
**Andrés Felipe Colonia Aldana**
