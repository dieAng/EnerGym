# Módulo DI (Dependency Injection) 💉

El módulo `di` contiene la configuración de **Hilt** para gestionar la inyección de dependencias en toda la aplicación.

## 📦 Contenido

### Módulos de Provisión
- **[NetworkModule](NetworkModule.kt)**: Provee instancias de Retrofit, OkHttp y las interfaces de las APIs.
- **[DatabaseModule](DataStoreModule.kt)** (incluye DataStore): Provee la base de datos Room, los DAOs y los gestores de preferencias.
- **[RepositoryModule](RepositoryModule.kt)**: Vincula las interfaces de los repositorios de Domain con sus implementaciones en Data.
- **[UseCaseModule](UseCaseModule.kt)**: Provee las instancias de los Casos de Uso.
- **[ViewModelModule](ViewModelModule.kt)**: Configuración para la inyección de ViewModels.

## 📌 Responsabilidades
- Desacoplar la creación de objetos de su uso.
- Facilitar el intercambio de implementaciones para pruebas unitarias.
- Centralizar la configuración de librerías de terceros.
