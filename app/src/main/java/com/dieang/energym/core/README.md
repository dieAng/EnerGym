# Módulo Core 🛠

El módulo `core` contiene utilidades transversales, extensiones y configuraciones base que son utilizadas por el resto de las capas de la aplicación.

## 📦 Contenido

### [util](util/)
Contiene clases de utilidad generales y funciones de extensión para tipos básicos de Kotlin/Android que no pertenecen a una lógica de negocio específica.

### [network](network/)
Infraestructura de red simplificada:
- **Configuración**: Definición de la URL base de Azure (`BASE_URL`) y tiempos de espera.
- **Proveedores**: Configuración centralizada de `Retrofit` y `OkHttpClient`.

## 📌 Responsabilidades
- Proporcionar herramientas comunes para reducir la duplicación de código.
- Definir la infraestructura base sobre la que se construye la comunicación de datos.
- Mantener lógica agnóstica a las reglas de negocio de EnerGym.
