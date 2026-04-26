# Módulo Data 💾

El módulo `data` es responsable de la persistencia de datos y de la comunicación con fuentes externas (APIs). Implementa las interfaces de repositorio definidas en la capa de Domain.

## 📦 Contenido

### [local](local/)
Gestiona la persistencia local de la aplicación:
- **Room**: Base de datos SQLite (`EnergymDatabase`).
- **DAOs**: Objetos de acceso a datos para cada entidad.
- **DataStore**: Almacenamiento de preferencias del usuario y tokens de sesión.

### [remote](remote/)
Encargado de la comunicación con el servidor:
- **API Interfaces**: Definiciones de Retrofit para los servicios web.
- **DTOs**: Objetos de transferencia de datos optimizados para la red.

### [repository](repository/)
Contiene las implementaciones de los repositorios de Domain. Aquí se orquesta la lógica de cuándo obtener datos de la red o de la base de datos local (Offline-first approach).

### [mappers](mappers/)
Funciones de conversión para transformar objetos entre las diferentes capas:
- `DTO` ↔ `Entity`
- `Entity` ↔ `Domain Model`

### [worker](worker/)
Implementación de `SyncWorker` mediante **WorkManager** para la sincronización automática en segundo plano de datos pendientes.

## 📌 Responsabilidades
- Implementar la lógica de acceso a datos.
- Gestionar el almacenamiento en caché y la sincronización offline.
- Realizar el mapeo de datos crudos a modelos de dominio limpios.
