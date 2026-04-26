# Módulo Domain 🧠

El módulo `domain` es el corazón de la aplicación. Contiene la lógica de negocio pura y es totalmente independiente de cualquier framework o librería externa de Android o infraestructura de datos.

## 📦 Contenido

### [model](model/)
Definición de las entidades de dominio (POJOs). Son clases de datos limpias que representan los conceptos principales de EnerGym: `Usuario`, `Rutina`, `Post`, `Receta`, etc.

### [repository](repository/)
Contiene las interfaces de los repositorios. Estas interfaces definen el contrato de qué datos necesita el negocio, sin preocuparse de cómo se obtienen.

### [usecase](usecase/)
Contiene las unidades de lógica de negocio específicas (Casos de Uso). Cada clase representa una única acción que el usuario puede realizar (ej. `LoginUserUseCase`, `GetPostsUseCase`).

## 📌 Responsabilidades
- Definir el lenguaje común del negocio.
- Contener las reglas de negocio y validaciones.
- Actuar como puente entre la capa de Datos y la capa de UI.
