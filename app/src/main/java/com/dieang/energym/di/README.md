# Módulo DI (Dependency Injection) 💉

El módulo `di` gestiona la inyección de dependencias mediante **Hilt**, centralizando la creación y provisión de componentes en toda la app.

## 📦 Contenido

### Módulos Hilt
- **[NetworkModule](NetworkModule.kt)**: Provee OkHttpClient, Retrofit y todas las interfaces de las APIs.
- **[DatabaseModule](DatabaseModule.kt)**: Provee la base de datos Room y sus DAOs.
- **[DataStoreModule](DataStoreModule.kt)**: Provee la instancia de DataStore y el gestor de perfil `UserStore`.
- **[RepositoryModule](RepositoryModule.kt)**: Vincula interfaces de repositorio con sus implementaciones.
- **[UseCaseModule](UseCaseModule.kt)**: Provee todos los casos de uso del dominio (Auth, Rutinas, Sesiones, etc.).

## 📌 Responsabilidades
- Desacoplar la instanciación de clases de su uso.
- Proveer dependencias de forma eficiente respetando los alcances (`Singleton`, `ViewModelScoped`, etc.).
- Centralizar la configuración de librerías externas.
