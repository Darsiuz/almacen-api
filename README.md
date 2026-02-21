# Sistema de Control de Almacen – Backend API

## Descripcion

API REST desarrollada con Spring Boot para la gestion integral de inventario en un almacen.  
Permite administrar productos, movimientos de entrada y salida, incidencias y configuracion del sistema, aplicando autenticacion JWT y control de acceso basado en roles (RBAC).

Este proyecto fue desarrollado como parte del curso de Diseño de Patrones de Software.

---

## Arquitectura

El sistema implementa una arquitectura en capas:

- Controller (Capa de presentacion REST)
- Service (Logica de negocio)
- Repository (Acceso a datos)
- DTO (Transferencia de datos)

### Patrones aplicados

- MVC (Model–View–Controller)
- Repository Pattern
- DTO Pattern
- Dependency Injection (IoC – Spring)
- RBAC (Role-Based Access Control)
- Stateless Authentication con JWT

---

## Roles del Sistema

- ADMIN
- MANAGER
- OPERATOR
- AUDITOR

Cada rol tiene permisos especificos definidos en `SecurityConfig`.

---

## Seguridad

- Autenticacion mediante JWT
- Filtro personalizado `JwtAuthenticationFilter`
- Autorizacion basada en roles
- Manejo personalizado de errores 401 y 403
- Arquitectura stateless (sin sesiones)

---

## Endpoints Principales

### Autenticacion

- POST `/auth/login`

### Productos

- GET `/product`
- POST `/product`
- PUT `/product/{id}`
- DELETE `/product/{id}`

### Movimientos

- GET `/movement`
- POST `/movement`
- PUT `/movement/{id}/approve`
- PUT `/movement/{id}/reject`

### Incidencias

- GET `/incident`
- POST `/incident`
- PUT `/incident/{id}/resolve`
- PUT `/incident/{id}/reject`

### Configuracion del Sistema

- GET `/admin/system`
- PUT `/admin/system`

---

## Tecnologias Utilizadas

- Java 21+
- Spring Boot
- Spring Security
- JWT
- JPA / Hibernate
- MySQL
- Maven
- lombok

---
