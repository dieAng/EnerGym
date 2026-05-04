package com.dieang.energym.di

import com.dieang.energym.data.local.dao.*
import com.dieang.energym.data.local.datastore.UserStore
import com.dieang.energym.data.remote.api.*
import com.dieang.energym.data.repository.*
import com.dieang.energym.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        usuarioApi: UsuarioApi,
        userStore: UserStore
    ): AuthRepository = AuthRepositoryImpl(api, usuarioApi, userStore)

    @Provides @Singleton
    fun provideUsuarioRepository(
        api: UsuarioApi,
        dao: UsuarioDao
    ): UsuarioRepository = UsuarioRepositoryImpl(api, dao)

    @Provides @Singleton
    fun provideRecetaRepository(
        api: RecetaApi,
        recetaDao: RecetaDao,
        ingredienteDao: IngredienteDao
    ): RecetaRepository = RecetaRepositoryImpl(api, recetaDao, ingredienteDao)

    @Provides @Singleton
    fun provideIngredienteRepository(
        api: IngredienteApi,
        dao: IngredienteDao
    ): IngredienteRepository = IngredienteRepositoryImpl(api, dao)

    @Provides @Singleton
    fun provideRutinaRepository(
        api: RutinaApi,
        rutinaDao: RutinaDao,
        rutinaEjercicioDao: RutinaEjercicioDao
    ): RutinaRepository = RutinaRepositoryImpl(api, rutinaDao, rutinaEjercicioDao)

    @Provides @Singleton
    fun provideEjercicioRepository(
        api: EjercicioApi,
        dao: EjercicioDao
    ): EjercicioRepository = EjercicioRepositoryImpl(api, dao)

    @Provides @Singleton
    fun provideSesionRepository(
        api: SesionEntrenamientoApi,
        dao: SesionEntrenamientoDao,
        serieDao: SerieRealizadaDao
    ): SesionEntrenamientoRepository = SesionEntrenamientoRepositoryImpl(api, dao, serieDao)

    @Provides @Singleton
    fun providePostRepository(
        api: PostApi,
        dao: PostDao,
        comentarioDao: ComentarioDao
    ): PostRepository = PostRepositoryImpl(api, dao, comentarioDao)

    @Provides @Singleton
    fun provideComentarioRepository(
        api: ComentarioApi,
        dao: ComentarioDao
    ): ComentarioRepository = ComentarioRepositoryImpl(api, dao)

    @Provides @Singleton
    fun provideMensajeRepository(
        api: MensajeApi,
        dao: MensajeDao
    ): MensajeRepository = MensajeRepositoryImpl(api, dao)

    @Provides @Singleton
    fun providePesoRepository(
        dao: HistorialPesoDao
    ): PesoRepository = PesoRepositoryImpl(dao)

    @Provides @Singleton
    fun provideSerieRealizadaRepository(
        dao: SerieRealizadaDao
    ): SerieRealizadaRepository = SerieRealizadaRepositoryImpl(dao)

    @Provides @Singleton
    fun provideLikeRepository(
        api: LikeApi
    ): LikeRepository = LikeRepositoryImpl(api)
}
