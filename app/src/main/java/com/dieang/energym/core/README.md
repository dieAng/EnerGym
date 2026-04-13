# 📘 Módulo `core`

## 🧩 Descripción general

El módulo **`core`** contiene la infraestructura transversal y reutilizable de la app.  
No pertenece a ninguna feature y no debe conocer detalles de UI ni de negocio.

Su objetivo es:

- Centralizar la **configuración de red**
- Proveer utilidades **compartidas** (corrutinas, Result wrapper)
- Mantenerse **independiente** de **`data`**, **`domain`** y **`ui`**

---

## 🏛️ Responsabilidades del módulo `core`

El módulo **`core`** se encarga de:

### Configuración de red (Retrofit + OkHttp)

- Creación del cliente HTTP
- Timeouts
- Base URL
- Interceptor de autenticación
- Proveedor de Retrofit

### Interceptor de autenticación:

- Inserta el token en cada request
- Detecta errores 401
- Ejecuta el flujo de refresh token
- Reintenta la petición original
- Limpia tokens si el refresh falla

### Utilidades globales:

- `DispatchersProvider` para corrutinas
- `Result<T>` como wrapper estándar de resultados

---

## 📁 Estructura

```text
core/
   ├── network/
   │     ├── AuthInterceptor.kt
   │     ├── NetworkConfig.kt
   │     └── RetrofitProvider.kt
   │
   └── util/
         ├── DispatchersProvider.kt
         └── Result.kt
```

---

## 🌐 network/

### 🔐 AuthInterceptor.kt

Responsable de:

- Añadir el header `Authorization: Bearer <token>`
- Interceptar respuestas 401
- Solicitar un nuevo access token usando el refresh token
- Guardar los nuevos tokens en DataStore
- Reintentar la petición original con el token renovado

Este interceptor garantiza que **toda la app maneje tokens automáticamente.**

### ⚙️ NetworkConfig.kt

Contiene la configuración global de red:

- ``BASE_URL``
- ``TIMEOUT_SECONDS``

Permite centralizar valores críticos sin duplicación.

### 🔧 RetrofitProvider.kt

Crea:

- ``OkHttpClient`` con el interceptor
- ``Retrofit`` con Gson y la base URL

Este provider permite que el módulo **``di``** solo tenga que **inyectar**, no construir.

---

## 🧰 util/

### 🚦 DispatchersProvider.kt

Abstracción de corrutinas:

- ``io``
- ``main``
- ``default``

Permite testear ViewModels y UseCases sin depender de ``Dispatchers``.

### 📦 Result.kt

Wrapper estándar para representar estados:

- ``Success<T>``
- ``Error``
- ``Loading``

Ideal para flujos UI → ViewModel → UseCases.

---

## 🔗 Integración con DI (Hilt)

El módulo **`core`** no depende de **Hilt**, pero se integra con él mediante el módulo:

```text
/di/NetworkModule.kt
```

Ejemplo:

```kotlin
@Provides
@Singleton
fun provideOkHttpClient(
    interceptor: AuthInterceptor
): OkHttpClient =
    RetrofitProvider.createOkHttpClient(interceptor)
```

Esto permite:

- Mantener core limpio y desacoplado
- Dejar la inyección en el módulo di
- Reutilizar RetrofitProvider en cualquier entorno (tests, previews, etc.)

---

## 🧠 Cómo interactúa core con el resto de capas

```text
presentation → usa → ViewModels
ViewModels → usan → UseCases
UseCases → usan → Repositorios (domain)
Repositorios → usan → APIs (data)
APIs → usan → Retrofit (core)
Retrofit → usa → OkHttp + Interceptor (core)
```

El módulo **`core`** es la **base del stack de red**, pero no conoce ninguna feature.

---

## 🎯 Ventajas de este diseño

**Desacoplamiento total**

**``core``** no depende de **``data``**, **``domain``** ni **``ui``**.

**Reutilizable**

Puedes usarlo en tests, previews, o incluso en otro proyecto.

**Escalable**

Agregar nuevas APIs no requiere modificar **``core``**.

**Profesional**

Este patrón es estándar en arquitecturas limpias reales.