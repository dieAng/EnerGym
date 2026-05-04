# Módulo UI (User Interface) 🎨

El módulo `ui` se encarga de la representación visual de la aplicación y de la interacción con el usuario, organizada por funcionalidades.

## 📦 Contenido

### [feature](feature/)
Organización por funcionalidades (features). Cada carpeta contiene:
- **Screens**: Pantallas desarrolladas con Jetpack Compose que incluyen sus propias `@Preview` con datos realistas.
- **ViewModels**: Gestión del estado de la UI (MVVM).
- **Components**: Componentes composables específicos de cada feature.
- **State/Events**: Definición del estado y acciones de la pantalla.

### [navigation](navigation/)
Sistema de navegación centralizado y modular:
- **AppNavHost**: Orquestador principal de navegación.
- **Nested Graphs**: Cada feature tiene su propio grafo de navegación anidado (`postsNavGraph`, `usuarioNavGraph`, etc.).
- **AuthGuard**: Protección de rutas para asegurar que solo usuarios autenticados accedan a ciertas pantallas.

### [components](components/)
Componentes visuales genéricos y reutilizables en toda la aplicación.

### [theme](theme/)
Sistema de diseño de EnerGym:
- **Color.kt**: Paleta de colores de marca, acentos neón y gradientes.
- **Theme.kt**: Configuración del tema Material 3.

## 📌 Responsabilidades
- Renderizar la interfaz de usuario con soporte completo para Previews.
- Gestionar la navegación fluida y segura entre pantallas.
- Delegar la lógica de negocio a los ViewModels y reaccionar a los cambios de estado.
