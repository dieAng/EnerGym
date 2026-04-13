# 📘 Módulo `ui`

## 🧩 Descripción general

El módulo **``ui``** contiene toda la capa de **interfaz de usuario (UI)** de la aplicación.
Su responsabilidad es **mostrar datos, recibir acciones del usuario y comunicarse con los
ViewModels.**

Esta capa:

- No contiene lógica de negocio
- No conoce Retrofit, Room ni DataStore
- Solo interactúa con **ViewModels**
- Es completamente **reactiva** gracias a StateFlow
- Está construida con **Jetpack Compose + Navigation + Hilt**

---

## 🏛️ Responsabilidades del módulo `ui`

### Mostrar datos provenientes del ViewModel

La UI observa estados (``StateFlow``) y se actualiza automáticamente.

### Recibir acciones del usuario

Clicks, inputs, navegación, etc.

### Navegación entre pantallas

Usa ``NavHost``, ``NavController`` y rutas centralizadas.

### Gestionar estados de UI

Cada feature tiene su propio ``State.kt``.

### Inyectar ViewModels con Hilt

``hiltViewModel()`` se usa en cada pantalla.

---

## 📁 Estructura

```text
presentation/
   ├── auth/
   │     ├── AuthViewModel.kt
   │     ├── AuthState.kt
   │     └── LoginScreen.kt
   │
   ├── recetas/
   │     ├── RecetaViewModel.kt
   │     ├── RecetaState.kt
   │     └── RecetasScreen.kt
   │
   ├── rutinas/
   │     ├── RutinaViewModel.kt
   │     ├── RutinaState.kt
   │     └── RutinasScreen.kt
   │
   ├── ejercicios/
   │     ├── EjercicioViewModel.kt
   │     ├── EjercicioState.kt
   │     └── EjerciciosScreen.kt
   │
   ├── sesiones/
   │     ├── SesionViewModel.kt
   │     ├── SesionState.kt
   │     └── SesionesScreen.kt
   │
   ├── posts/
   │     ├── PostViewModel.kt
   │     ├── PostState.kt
   │     └── PostsScreen.kt
   │
   ├── mensajes/
   │     ├── MensajeViewModel.kt
   │     ├── MensajeState.kt
   │     └── MensajesScreen.kt
   │
   ├── components/
   │     ├── CustomButton.kt
   │     ├── CustomTextField.kt
   │     └── LoadingIndicator.kt
   │
   └── navigation/
         ├── Routes.kt
         ├── AppNavHost.kt
         ├── AuthNavGraph.kt
         ├── MainNavGraph.kt
         └── AuthGuard.kt
```

---

## 🧠 ViewModels

Cada ViewModel:

- Recibe UseCases desde Hilt
- Expone un ``StateFlow<State>``
- Contiene lógica de UI (no de negocio)
- Llama a los casos de uso
- Maneja loading, error y datos

Ejemplo:

````kotlin
class RecetaViewModel(
    private val getRecetas: GetRecetasUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RecetaState())
    val state = _state.asStateFlow()

    fun loadRecetas() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        try {
            val recetas = getRecetas()
            _state.update { it.copy(isLoading = false, recetas = recetas) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
````

---

## 🎨 States

Cada feature tiene su propio ``State.kt``:

- ``isLoading``
- ``error``
- Datos principales
- Elemento seleccionado (si aplica)

Ejemplo:

````kotlin
data class RecetaState(
    val isLoading: Boolean = false,
    val recetas: List<Receta> = emptyList(),
    val recetaSeleccionada: Receta? = null,
    val error: String? = null
)
````

---

## 🖼️ Pantallas Compose

Cada pantalla:

- Observa el ``state`` del ViewModel
- Llama a funciones del ViewModel
- Renderiza UI reactiva
- No contiene lógica de negocio

Ejemplo:

````kotlin
@Composable
fun RecetasScreen(
    state: RecetaState,
    onRefresh: () -> Unit,
    onSelect: (UUID) -> Unit
) {
    LaunchedEffect(Unit) { onRefresh() }

    LazyColumn {
        items(state.recetas) { receta ->
            Text(receta.nombre)
        }
    }
}

````

---

## 🧭 4. Navegación

La navegación está centralizada en:

````text
presentation/navigation/
````

Incluye:

- ``Routes.kt`` → rutas únicas
- ``AppNavHost.kt`` → NavHost raíz
- ``AuthNavGraph.kt`` → flujo de login
- ``MainNavGraph.kt`` → flujo principal
- ``AuthGuard.kt`` → protección de rutas

Ejemplo:

`````kotlin
composable(Routes.RECETAS) {
    val vm: RecetaViewModel = hiltViewModel()
    RecetasScreen(
        state = vm.state.collectAsState().value,
        onRefresh = { vm.loadRecetas() },
        onSelect = { id -> navController.navigate("receta/$id") }
    )
}
`````

---

## 🔗 Integración con DI (Hilt)

El módulo ``ui`` se integra mediante:

```text
/di/ViewModelModule.kt
```

Ejemplo:

```kotlin
@Provides
fun provideRecetaViewModel(
    getRecetas: GetRecetasUseCase
): RecetaViewModel =
    RecetaViewModel(getRecetas)
```

---

## 🧠 Cómo interactúa core con el resto de capas

```text
presentation → ViewModels
ViewModels → UseCases (domain)
UseCases → Repositorios (domain)
Repositorios → data
data → API / Room / DataStore
```

La UI solo conoce ViewModels.

---

## 🎯 Ventajas del diseño

**UI desacoplada**

No conoce repositorios ni APIs.

**Reactiva**

StateFlow + Compose = UI automática.

**Escalable**

Cada feature tiene su propio módulo UI.

**Testable**

ViewModels se testean sin UI.

**Profesional**

Arquitectura limpia y mantenible.