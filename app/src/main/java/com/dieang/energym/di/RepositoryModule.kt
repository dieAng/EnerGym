package com.dieang.energym.di

import com.dieang.energym.data.local.dao.ComentarioDao
import com.dieang.energym.data.local.dao.EjercicioDao
import com.dieang.energym.data.local.dao.HistorialPesoDao
import com.dieang.energym.data.local.dao.IngredienteDao
import com.dieang.energym.data.local.dao.MensajeDao
import com.dieang.energym.data.local.dao.PostDao
import com.dieang.energym.data.local.dao.RecetaDao
import com.dieang.energym.data.local.dao.RutinaDao
import com.dieang.energym.data.local.dao.RutinaEjercicioDao
import com.dieang.energym.data.local.dao.SerieRealizadaDao
import com.dieang.energym.data.local.dao.SesionEntrenamientoDao
import com.dieang.energym.data.local.dao.UsuarioDao
import com.dieang.energym.data.local.datastore.TokenProvider
import com.dieang.energym.data.local.datastore.UserStore
import com.dieang.energym.data.remote.api.AuthApi
import com.dieang.energym.data.remote.api.ComentarioApi
import com.dieang.energym.data.remote.api.EjercicioApi
import com.dieang.energym.data.remote.api.IngredienteApi
import com.dieang.energym.data.remote.api.MensajeApi
import com.dieang.energym.data.remote.api.PostApi
import com.dieang.energym.data.remote.api.RecetaApi
import com.dieang.energym.data.remote.api.RutinaApi
import com.dieang.energym.data.remote.api.SesionEntrenamientoApi
import com.dieang.energym.data.remote.api.UsuarioApi
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
        tokenStore: TokenProvider,
        userStore: UserStore
    ): AuthRepository = AuthRepositoryImpl(api, tokenStore, userStore)

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
}
