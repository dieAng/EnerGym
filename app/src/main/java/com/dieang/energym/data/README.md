# 📘 Módulo `data`

## 🧩 Descripción general

El módulo **`data`** implementa la **capa de datos** de la arquitectura Clean.  
Su responsabilidad es **obtener, almacenar y transformar** datos desde:

- API REST (Retrofit)
- Base de datos local (Room)
- DataStore
- Cachés locales
- Fuentes mixtas (remote + local)

No contiene lógica de negocio: solo **implementaciones de repositorios y fuentes de datos.**

---

## 🏛️ Responsabilidades del módulo `data`

El módulo **`core`** se encarga de:

### Implementar los repositorios definidos en **``domain``**

Cada repositorio del dominio tiene su implementación en **``data``**.

### Gestionar las fuentes de datos

- Remote → Retrofit
- Local → Room
- Preferences → DataStore

### Mapear DTOs ↔ Domain Models

El módulo **``data``** transforma datos externos en modelos del dominio.

### Manejar errores de red y almacenamiento

Incluye try/catch, validaciones y manejo de excepciones.

### Proveer datos consistentes a los UseCases

Los UseCases nunca deben saber si los datos vienen de red o local.

---

## 📁 Estructura

```text
data/
   ├── remote/
   │     ├── api/
   │     ├── dto/
   │     │     ├── request/
   │     │     └── response/
   │     └── mapper/
   │
   ├── local/
   │     ├── dao/
   │     ├── entity/
   │     └── mapper/
   │
   ├── repository/
   │     └── impl/
   │
   └── datastore/
         ├── TokenDataStore.kt
         └── UserDataStore.kt

```

---

## remote/

- APIs Retrofit
- DTOs de request/response
- Mappers DTO → Domain

---

## local/

- Entities Room
- DAOs
- Mappers Entity ↔ Domain

---

## datastore/

- TokenDataStore
    - Guarda access token
    - Guarda refresh token
    - Limpia tokens
- UserDataStore
    - Guarda usuario logueado
    - Devuelve usuario actual
    - Limpia datos

---

## repository/impl/

Implementaciones de los repositorios del dominio.  
Cada repositorio combina:

- API
- Room
- DataStore

---

## Integración con DI

El módulo `data` se integra mediante:

- `NetworkModule`
- `DataStoreModule`
- `RepositoryModule`

---

## Relación con la arquitectura

````text
presentation → ViewModels
ViewModels → UseCases (domain)
UseCases → Repositorios (domain)
Repositorios → data
data → API / Room / DataStore
````

---

## 🎯 Ventajas de este diseño

**Dominio limpio**

No depende de Retrofit, Room ni DataStore.

**Fácil de testear**

Puedes mockear repositorios o APIs.

**Escalable**

Agregar una nueva feature implica:

- API
- DTOs
- Entity
- DAO
- RepositoryImpl

**Profesional**

Arquitectura Clean real, sin atajos.