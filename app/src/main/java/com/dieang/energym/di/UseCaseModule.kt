package com.dieang.energym.di

import com.dieang.energym.data.local.dao.*
import com.dieang.energym.domain.repository.*
import com.dieang.energym.domain.usecase.auth.*
import com.dieang.energym.domain.usecase.ejercicios.*
import com.dieang.energym.domain.usecase.ingredientes.*
import com.dieang.energym.domain.usecase.mensajes.*
import com.dieang.energym.domain.usecase.peso.*
import com.dieang.energym.domain.usecase.posts.*
import com.dieang.energym.domain.usecase.recetas.*
import com.dieang.energym.domain.usecase.rutinas.*
import com.dieang.energym.domain.usecase.sesiones.*
import com.dieang.energym.domain.usecase.usuario.*
import com.dieang.energym.domain.usecase.comentarios.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    // AUTH
    @Provides fun provideLoginUserUseCase(repo: AuthRepository) = LoginUserUseCase(repo)
    @Provides fun provideLogoutUserUseCase(repo: AuthRepository) = LogoutUserUseCase(repo)
    @Provides fun provideGetLoggedUserUseCase(repo: AuthRepository) = GetLoggedUserUseCase(repo)
    @Provides fun provideRegisterUserUseCase(repo: AuthRepository) = RegisterUserUseCase(repo)

    // USUARIO
    @Provides fun provideGetUsuariosUseCase(repo: UsuarioRepository) = GetUsuariosUseCase(repo)
    @Provides fun provideGetUsuarioUseCase(repo: UsuarioRepository) = GetUsuarioUseCase(repo)
    @Provides fun provideCreateUsuarioUseCase(repo: UsuarioRepository) = CreateUsuarioUseCase(repo)
    @Provides fun provideUpdateUsuarioUseCase(repo: UsuarioRepository) = UpdateUsuarioUseCase(repo)
    @Provides fun provideDeleteUsuarioUseCase(repo: UsuarioRepository) = DeleteUsuarioUseCase(repo)
    @Provides fun provideGetEstadisticasUsuarioUseCase(serieRepo: SerieRealizadaRepository, sesionRepo: SesionEntrenamientoRepository) = GetEstadisticasUsuarioUseCase(serieRepo, sesionRepo)

    // RECETAS
    @Provides fun provideGetRecetasUseCase(repo: RecetaRepository) = GetRecetasUseCase(repo)
    @Provides fun provideGetRecetaUseCase(repo: RecetaRepository) = GetRecetaUseCase(repo)
    @Provides fun provideCreateRecetaUseCase(repo: RecetaRepository) = CreateRecetaUseCase(repo)
    @Provides fun provideUpdateRecetaUseCase(repo: RecetaRepository) = UpdateRecetaUseCase(repo)
    @Provides fun provideDeleteRecetaUseCase(repo: RecetaRepository) = DeleteRecetaUseCase(repo)

    // INGREDIENTES
    @Provides fun provideGetIngredientesUseCase(repo: IngredienteRepository) = GetIngredientesByRecetaUseCase(repo)
    @Provides fun provideCreateIngredienteUseCase(repo: IngredienteRepository) = CreateIngredienteUseCase(repo)

    // RUTINAS
    @Provides fun provideGetRutinasUseCase(repo: RutinaRepository) = GetRutinasUseCase(repo)
    @Provides fun provideGetRutinaUseCase(repo: RutinaRepository) = GetRutinaUseCase(repo)
    @Provides fun provideCreateRutinaUseCase(repo: RutinaRepository) = CreateRutinaUseCase(repo)
    @Provides fun provideDeleteRutinaUseCase(repo: RutinaRepository) = DeleteRutinaUseCase(repo)
    @Provides fun provideGetRutinaEjerciciosUseCase(dao: RutinaEjercicioDao) = GetRutinaEjerciciosUseCase(dao)

    // EJERCICIOS
    @Provides fun provideGetEjerciciosUseCase(repo: EjercicioRepository) = GetEjerciciosUseCase(repo)
    @Provides fun provideGetEjercicioUseCase(repo: EjercicioRepository) = GetEjercicioUseCase(repo)

    // SESIONES
    @Provides fun provideGetSesionesUseCase(repo: SesionEntrenamientoRepository) = GetSesionesUseCase(repo)
    @Provides fun provideCreateSesionUseCase(repo: SesionEntrenamientoRepository) = CreateSesionUseCase(repo)
    @Provides fun provideAddSerieUseCase(repo: SesionEntrenamientoRepository) = AddSerieUseCase(repo)
    @Provides fun provideSaveSesionUseCase(repo: SesionEntrenamientoRepository) = SaveSesionUseCase(repo)
    @Provides fun provideGetSesionDetalleUseCase(repo: SesionEntrenamientoRepository, serieDao: SerieRealizadaDao, ejercicioDao: EjercicioDao) = GetSesionDetalleUseCase(repo, serieDao, ejercicioDao)

    // PESO
    @Provides fun provideGetHistorialPesoUseCase(repo: PesoRepository) = GetHistorialPesoUseCase(repo)
    @Provides fun provideRegistrarPesoUseCase(repo: PesoRepository) = RegistrarPesoUseCase(repo)

    // POSTS
    @Provides fun provideGetPostsUseCase(repo: PostRepository) = GetPostsUseCase(repo)
    @Provides fun provideCreatePostUseCase(repo: PostRepository) = CreatePostUseCase(repo)
    @Provides fun provideLikePostUseCase(repo: PostRepository) = LikePostUseCase(repo)
    @Provides fun provideAddComentarioToPostUseCase(repo: PostRepository) = AddComentarioToPostUseCase(repo)
    @Provides fun provideGetComentariosByPostUseCase(dao: ComentarioDao) = GetComentariosByPostUseCase(dao)

    // COMENTARIOS
    @Provides fun provideGetComentariosUseCase(repo: ComentarioRepository) = GetComentariosUseCase(repo)
    @Provides fun provideAddComentarioUseCase(repo: ComentarioRepository) = AddComentarioUseCase(repo)

    // MENSAJES
    @Provides fun provideGetConversacionUseCase(repo: MensajeRepository) = GetConversacionUseCase(repo)
    @Provides fun provideEnviarMensajeUseCase(repo: MensajeRepository) = EnviarMensajeUseCase(repo)
}
