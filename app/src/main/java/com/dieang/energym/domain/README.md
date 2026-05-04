# Módulo Domain 🧠

El módulo `domain` es el núcleo de la aplicación, conteniendo la lógica de negocio pura de forma independiente a la tecnología Android o de datos.

## 📦 Contenido

### [model](model/)
Clases de datos que representan las entidades del negocio (`Usuario`, `Receta`, `Rutina`, etc.).

### [repository](repository/)
Interfaces que definen el contrato de acceso a datos que el dominio requiere.

### [usecase](usecase/)
Casos de Uso que encapsulan reglas de negocio atómicas (ej: `LoginUserUseCase`, `RegisterUserUseCase`, `GetRutinaEjerciciosUseCase`).

## 📌 Responsabilidades
- Definir el modelo mental y funcional de la aplicación.
- Validar las reglas del negocio.
- Exponer la funcionalidad de la app a la capa de UI sin exponer detalles de implementación de datos.
