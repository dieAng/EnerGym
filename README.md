# EnerGym ⚡️

EnerGym es una aplicación Android de vanguardia diseñada para el seguimiento integral del fitness, la gestión de la nutrición y el fomento de la comunidad entre atletas. La aplicación permite a los usuarios planificar rutinas de entrenamiento, registrar sus progresos, gestionar su alimentación y compartir sus logros con otros usuarios.

## 🚀 Funcionalidades Principales

- **Entrenamiento Inteligente**: Gestión de rutinas, ejercicios y sesiones de entrenamiento con seguimiento detallado de series y volumen. Soporte para sesiones activas y visualización de historial.
- **Nutrición**: Catálogo de recetas saludables, gestión de ingredientes y resumen nutricional diario.
- **Comunidad y Social**: Muro de publicaciones, sistema de "likes", comentarios e historias para compartir el progreso.
- **Gamificación**: Sistema de logros desbloqueables y seguimiento de rachas para mantener la motivación.
- **Perfil del Atleta**: Estadísticas detalladas, historial de peso y seguimiento de récords personales (PRs).
- **Autenticación Simplificada**: Sistema de inicio de sesión y registro rápido sin complejidad de tokens.
- **Sincronización Offline**: Capacidad de trabajar sin conexión y sincronizar datos automáticamente mediante WorkManager.

## 🛠 Arquitectura y Tecnologías

La aplicación sigue los principios de **Clean Architecture** y el patrón **MVVM (Model-View-ViewModel)**, garantizando un código escalable, testeable y mantenible.

- **UI**: Jetpack Compose con Material 3 y Previews en todas las pantallas.
- **Navegación**: Jetpack Navigation Compose con grafos anidados y `AuthGuard`.
- **Inyección de Dependencias**: Hilt.
- **Persistencia**: Room Database para datos locales y DataStore para el perfil de usuario.
- **Red**: Retrofit con OkHttp y GSON apuntando a Azure.
- **Multihilo**: Corrutinas de Kotlin y Flow.
- **Trabajo en Segundo Plano**: WorkManager.

## 📁 Estructura del Proyecto

El código fuente está organizado en los siguientes módulos principales dentro de `com.dieang.energym`:

1. **[Core](app/src/main/java/com/dieang/energym/core/README.md)**: Utilidades generales y configuración base de red simplificada.
2. **[Data](app/src/main/java/com/dieang/energym/data/README.md)**: Implementaciones de repositorios, base de datos local y APIs remotas.
3. **[Domain](app/src/main/java/com/dieang/energym/domain/README.md)**: Modelos de negocio, interfaces de repositorios y casos de uso.
4. **[DI](app/src/main/java/com/dieang/energym/di/README.md)**: Configuración centralizada de inyección de dependencias con Hilt.
5. **[UI](app/src/main/java/com/dieang/energym/ui/README.md)**: Pantallas (Screens) con Previews, ViewModels, componentes reutilizables y navegación modular.

---
© 2026 EnerGym. Todos los derechos reservados.
