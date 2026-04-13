# 📘 Módulo `di` — Dependency Injection

## 🧩 Descripción general

El módulo **`di`** contiene todos los Hilt Modules responsables de **ensamblar las dependencias** de
la aplicación.  
Su función es conectar los componentes definidos en:

- **`core`** → infraestructura (Retrofit, OkHttp, Interceptor)
- **`data`** → repositorios, APIs, DAOs, DataStore
- **`domain`** → casos de uso (UseCases)
- **`ui`** → ViewModels

No implementa lógica de negocio ni lógica de red: solo **provee** instancias.

---

## 🏛️ Responsabilidades del módulo ``di``

### Proveer instancias de Retrofit, OkHttp y APIs

Usando ``RetrofitProvider`` del módulo **``core``**.

### Proveer repositorios

Cada repositorio se inyecta como su interfaz del dominio.

### Proveer casos de uso (UseCases)

Cada UseCase recibe su repositorio correspondiente.

### Proveer ViewModels

Hilt crea los ViewModels con sus UseCases ya inyectados.

### Gestionar el ciclo de vida de dependencias

- ``SingletonComponent`` → instancias globales
- ``ViewModelComponent`` → instancias por ViewModel

---

## 📁 Estructura

```text
di/
   ├── NetworkModule.kt
   ├── DataStoreModule.kt
   ├── RepositoryModule.kt
   ├── UseCaseModule.kt
   └── ViewModelModule.kt

```

---

## 🌐 NetworkModule.kt

- Provee `AuthInterceptor`
- Provee `OkHttpClient`
- Provee `Retrofit`
- Provee todas las APIs

Usa `RetrofitProvider` del módulo **`core`**

---

## 🗂️ DataStoreModule.kt

- Provee DataStore
- Provee `TokenProvider`
- Provee `UserStore`

---

## 🧠 RepositoryModule.kt

- Inyecta cada repositorio como su interfaz del dominio

Ejemplo:

```kotlin
@Provides
fun provideUsuarioRepository(api: UsuarioApi, dao: UsuarioDao): UsuarioRepository =
    UsuarioRepositoryImpl(api, dao)
```

---

## ⚡ UseCaseModule.kt

- Provee todos los casos de uso
- Cada UseCase recibe su repositorio correspondiente

Ejemplo:

````kotlin
@Provides
fun provideGetRecetasUseCase(repo: RecetaRepository) =
    GetRecetasUseCase(repo)
````

---

## 🧩 ViewModelModule.kt

- Inyectar todos los ViewModels
- Cada ViewModel recibe sus UseCases

Ejemplo:

````kotlin
@Provides
fun provideRecetaViewModel(
    getRecetas: GetRecetasUseCase,
    getReceta: GetRecetaUseCase,
    createReceta: CreateRecetaUseCase,
    updateReceta: UpdateRecetaUseCase,
    deleteReceta: DeleteRecetaUseCase
): RecetaViewModel =
    RecetaViewModel(getRecetas, getReceta, createReceta, updateReceta, deleteReceta)
````

---

## 🔗 Cómo se integra di con el resto de la arquitectura

````text
core → provee infraestructura
di → ensambla dependencias
data → implementa repositorios y APIs
domain → define interfaces y casos de uso
presentation → consume ViewModels inyectados
````

El módulo **``di``** es el **pegamento** que une todas las capas.

---

## 🎯 Ventajas de este diseño

**Arquitectura limpia y escalable**

Cada módulo tiene una responsabilidad clara.

**Inyección automática y segura**

Hilt gestiona el ciclo de vida.

**Fácil de testear**

Puedes mockear repositorios o UseCases sin tocar la UI.

**Fácil de extender**

Agregar una nueva feature implica:

- Nuevo repositorio
- Nuevos UseCases
- Nuevo ViewModel
- Añadirlos al módulo di