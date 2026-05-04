# Módulo Data 💾

El módulo `data` es responsable de la persistencia de datos y de la comunicación con fuentes externas (APIs). Implementa las interfaces de repositorio definidas en la capa de Domain.

## 📦 Contenido

### [local](local/)
Gestiona la persistencia local de la aplicación:
- **Room**: Base de datos SQLite (`EnerGymDatabase`).
- **DAOs**: Objetos de acceso a datos para cada entidad.
- **DataStore**: Almacenamiento de preferencias del usuario y perfil de sesión simplificado.

### [remote](remote/)
Encargado de la comunicación con el servidor de Azure:
- **API Interfaces**: Definiciones de Retrofit para los servicios web (Auth, Usuario, Rutinas, etc.).
- **DTOs**: Objetos de transferencia de datos para la comunicación con el backend.

### [repository](repository/)
Contiene las implementaciones de los repositorios. Orquesta la lógica de acceso a datos siguiendo un enfoque Offline-first.

### [mappers](mappers/)
Funciones de conversión para transformar objetos entre capas (`DTO` ↔ `Entity` ↔ `Domain Model`).

### [worker](worker/)
Implementación de `SyncWorker` mediante **WorkManager** para la sincronización automática de datos.

## 📌 Responsabilidades
- Implementar la lógica de acceso a datos y caché.
- Gestionar la sesión del usuario de forma simplificada en `UserStore`.
- Realizar el mapeo de datos a modelos de dominio.
