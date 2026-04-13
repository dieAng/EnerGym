# 📘 Módulo `domain`

## 🧩 Descripción general

El módulo domain es el **corazón de la arquitectura Clean.**
Define **la lógica de negocio pura**, completamente independiente de:

- Android
- Retrofit
- Room
- DataStore
- Hilt
- UI
- Cualquier framework

El dominio es **estable, testable, independiente y sin dependencias externas.**

---

## 🏛️ Responsabilidades del módulo `core`

### Definir las interfaces de repositorios

Son contratos que la capa **``data``** debe implementar.

### Definir los casos de uso (UseCases)

Cada caso de uso representa una acción del negocio:

- Login
- Obtener recetas
- Crear rutina
- Enviar mensaje
- Registrar sesión
- etc

### Definir los modelos del dominio

Modelos puros, sin anotaciones de frameworks:

- ``Usuario``

- ``Receta``

- ``Ingrediente``

- ``Rutina``

- ``Ejercicio``

- ``SesionEntrenamiento``

- ``SerieRealizada``

- ``Post``

- ``Comentario``

- ``Mensaje``

### Contener la lógica de negocio

Validaciones, reglas, cálculos,

---

## 📁 Estructura

```text
domain/
   ├── model/
   │     ├── Usuario.kt
   │     ├── Receta.kt
   │     ├── Ingrediente.kt
   │     ├── Rutina.kt
   │     ├── Ejercicio.kt
   │     ├── SesionEntrenamiento.kt
   │     ├── SerieRealizada.kt
   │     ├── Post.kt
   │     ├── Comentario.kt
   │     └── Mensaje.kt
   │
   ├── repository/
   │     ├── AuthRepository.kt
   │     ├── UsuarioRepository.kt
   │     ├── RecetaRepository.kt
   │     ├── RutinaRepository.kt
   │     ├── EjercicioRepository.kt
   │     ├── SesionRepository.kt
   │     ├── PostRepository.kt
   │     ├── ComentarioRepository.kt
   │     └── MensajeRepository.kt
   │
   └── usecase/
         ├── auth/
         ├── usuario/
         ├── recetas/
         ├── rutinas/
         ├── ejercicios/
         ├── sesiones/
         ├── posts/
         ├── comentarios/
         └── mensajes/

```

---

## 🧠 model/

Modelos puros, sin dependencias externas.

Ejemplo:

````kotlin
data class Usuario(
    val id: UUID,
    val nombre: String,
    val email: String
)
````

---

## 🔗 repository/

Interfaces que definen cómo se accede a los datos.

Ejemplo:

````kotlin
interface RecetaRepository {
    suspend fun getRecetas(): List<Receta>
    suspend fun getReceta(id: UUID): Receta
    suspend fun createReceta(receta: Receta)
    suspend fun updateReceta(receta: Receta)
    suspend fun deleteReceta(id: UUID)
}
````

La capa **``data``** implementa estas interfaces.

---

## ⚡ usecase/

Cada caso de uso representa una acción del negocio.

Ejemplo:

````kotlin
class GetRecetasUseCase(
    private val repo: RecetaRepository
) {
    suspend operator fun invoke(): List<Receta> =
        repo.getRecetas()
}
````

Características:

- Son clases pequeñas
- Tienen **una sola responsabilidad**
- Son fáciles de testear
- No conocen Retrofit, Room ni Android

---

## 🔗 Integración con DI

El módulo **``domain``** **no usa Hilt**, pero se integra mediante:

````text
/di/UseCaseModule.kt
````

Ejemplo:

````kotlin
@Provides
fun provideGetRecetasUseCase(repo: RecetaRepository) =
    GetRecetasUseCase(repo)
````

## 🧠 Cómo interactúa core con el resto de capas

```text
presentation → ViewModels
ViewModels → UseCases (domain)
UseCases → Repositorios (domain)
Repositorios → Implementación en data
data → API / Room / DataStore

```

El dominio **no depende de ninguna capa**, pero todas dependen de él.

---

## 🎯 Ventajas de este diseño

**Independencia total**
El dominio no depende de Android ni de frameworks.

**Testabilidad**
Puedes testear UseCases sin mocks complejos.

**Escalabilidad**
Agregar una nueva feature implica:

- Nuevos modelos
- Nuevos repositorios
- Nuevos UseCases

**Profesional**
Arquitectura Clean real, sin atajos.