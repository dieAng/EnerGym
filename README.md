# EnerGym — Proyecto Android

## Descripción

EnerGym es una aplicación móvil modular basada en Clean Architecture.  
Incluye autenticación, recetas, rutinas, ejercicios, sesiones, posts, comentarios y mensajes.

EnerGym es una aplicación móvil diseñada para gestionar entrenamiento, nutrición y comunidad fitness.
Incluye:

- Autenticación con tokens y refresh automático

- Gestión de recetas, rutinas, ejercicios y sesiones

- Registro de series realizadas

- Sistema de posts, comentarios y mensajes

- Arquitectura Clean Architecture modular

- UI moderna con Jetpack Compose

- Navegación desacoplada y escalable

- Inyección de dependencias con Hilt

- Persistencia con Room + DataStore

- Networking con Retrofit + OkHttp + Interceptor JWT

El objetivo es ofrecer una base sólida, escalable y profesional para una app fitness completa.

---

## Arquitectura

- **presentation**: UI con Jetpack Compose, ViewModels, navegación
- **domain**: modelos puros, repositorios (interfaces), casos de uso
- **data**: Retrofit, Room, DataStore, RepositoryImpl
- **core**: infraestructura transversal (network, utils)
- **di**: módulos Hilt

---

## Estructura del proyecto

---

## Autenticación

- JWT Access Token
- Refresh Token automático
- Interceptor que reintenta peticiones
- DataStore para persistencia
- AuthGuard para rutas protegidas

---

## Navegación

- NavHost
- NavGraph por feature
- Rutas centralizadas en `Routes.kt`
- `AuthNavGraph` y `MainNavGraph`

---

## UI

- Jetpack Compose
- StateFlow + ViewModel
- Pantallas por feature
- Componentes reutilizables

---

## Testabilidad

- UseCases testables sin Android
- Repositorios mockeables
- ViewModels testables con DispatchersProvider

---

## Roadmap

- Notificaciones push
- Estadísticas de entrenamiento
- Modo offline
- Sincronización incremental
- Perfil avanzado