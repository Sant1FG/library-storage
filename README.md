# 📚 Library Storage API

REST API para la gestión de una biblioteca local. 

## 📖 Descripción
REST API que permite administrar el catálogo de libros, los usuarios con carnet bibliotecario y los préstamos activos. Desarrollado con Java 17 y el framework Spring Boot, almacenamiento de datos usando MySQL y despliegue mediante Docker.

## 🛠️ Tecnologías

- Java 17
- Spring Boot 3
- Spring Data JPA / Hibernate
- MySQL
- Docker & Docker Compose
- Maven

## 📌 Manual de uso

### Requisitos
- Docker y Docker Compose instalados

### Pasos

```bash
# Clonar el repositorio
git clone https://github.com/Sant1FG/library-storage.git
cd library-storage

# Levantar la aplicación
docker compose up --build
```

La API estará disponible en `http://localhost:8080`. Se recomienda utilizar herramientas de testeo de APIs como Postman para probarla.

> La base de datos se inicializa automáticamente al arrancar el contenedor.

---

## 📖 Endpoints

### Books

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/books` | Devuelve todos los libros |
| `GET` | `/books/{ISBN}` | Devuelve un libro por su ISBN |
| `POST` | `/books` | Crea un nuevo libro |
| `PUT` | `/books/{ISBN}` | Modifica un libro existente |
| `DELETE` | `/books/{ISBN}` | Elimina un libro por su ISBN |

**Atributos de Book**

| Campo | Tipo | Requerido | Restricciones |
|-------|------|-----------|---------------|
| `ISBN` | String | ✅ | Clave primaria |
| `title` | String | ✅ | No puede estar vacío |
| `author` | String | ✅ | No puede estar vacío |
| `publisher` | String | ❌ | — |
| `copies` | Integer | ✅ | Debe ser positivo |

---

### Users

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/users` | Devuelve todos los usuarios |
| `GET` | `/users/{userID}` | Devuelve un usuario por su ID |
| `POST` | `/users` | Crea un nuevo usuario |
| `PUT` | `/users/{userID}` | Modifica un usuario existente |
| `DELETE` | `/users/{userID}` | Elimina un usuario por su ID |

**Atributos de User**

| Campo | Tipo | Requerido | Restricciones |
|-------|------|-----------|---------------|
| `dni` | String | ✅ | No puede estar vacío, debe ser único |
| `name` | String | ✅ | No puede estar vacío |
| `surname` | String | ❌ | — |
| `address` | String | ❌ | — |
| `phoneNumber` | String | ❌ | — |

---

### Loans

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/loans` | Devuelve todos los préstamos |
| `GET` | `/loans/{loanID}` | Devuelve un préstamo por su ID |
| `POST` | `/loans` | Crea un nuevo préstamo |
| `PUT` | `/loans/{loanID}` | Modifica la fecha de devolución de un préstamo |
| `PATCH` | `/loans/{loanID}/return` | Marca un préstamo como devuelto |
| `DELETE` | `/loans/{loanID}` | Elimina un préstamo por su ID |

**Atributos de LoanRequest (POST)**

| Campo | Tipo | Requerido | Formato |
|-------|------|-----------|---------|
| `ISBN` | String | ✅ | — |
| `dni` | String | ✅ | — |
| `endDate` | Date | ✅ | `AAAA-MM-DD` |


## 📋 Lógica de negocio destacada

- Un usuario no puede tener dos préstamos activos del mismo libro simultáneamente.
- No se puede crear un préstamo si no quedan ejemplares disponibles.
