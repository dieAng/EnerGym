# Módulo UI (User Interface) 🎨

El módulo `ui` se encarga de la representación visual de la aplicación y de la interacción con el usuario. Utiliza las últimas tecnologías de Android para ofrecer una experiencia fluida y moderna.

## 📦 Contenido

### [feature](feature/)
Organización por funcionalidades (features). Cada carpeta contiene:
- **Screens**: Pantallas desarrolladas con Jetpack Compose.
- **ViewModels**: Mantienen el estado de la UI y se comunican con los Casos de Uso.
- **Components**: Componentes composables específicos de esa funcionalidad.
- **State/Events**: Clases que definen el estado de la pantalla y las acciones del usuario.

### [navigation](navigation/)
Define el flujo de navegación de la aplicación:
- **AppNavHost**: Grafo de navegación central.
- **BottomNavItem**: Definición de los destinos de la barra inferior (con soporte para notificaciones neón).

### [components](components/)
Componentes visuales genéricos y reutilizables en toda la aplicación (ej. botones neón, tarjetas personalizadas).

### [theme](theme/)
Definición del sistema de diseño de EnerGym:
- **Color.kt**: Paleta de colores de marca y gradientes.
- **Type.kt**: Tipografías modernas.
- **Theme.kt**: Configuración del tema Material 3 (por defecto Light Theme).

## 📌 Responsabilidades
- Renderizar los datos en la pantalla siguiendo el diseño de Figma.
- Gestionar el ciclo de vida de la UI y los estados de carga/error.
- Capturar los eventos del usuario y delegar la lógica al ViewModel.
