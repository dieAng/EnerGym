package com.dieang.energym.di

import com.dieang.energym.domain.repository.AuthRepository
import com.dieang.energym.domain.repository.ComentarioRepository
import com.dieang.energym.domain.repository.EjercicioRepository
import com.dieang.energym.domain.repository.IngredienteRepository
import com.dieang.energym.domain.repository.MensajeRepository
import com.dieang.energym.domain.repository.PostRepository
import com.dieang.energym.domain.repository.RecetaRepository
import com.dieang.energym.domain.repository.RutinaRepository
import com.dieang.energym.domain.repository.SesionEntrenamientoRepository
import com.dieang.energym.domain.repository.UsuarioRepository
import com.dieang.energym.domain.usecase.auth.GetLoggedUserUseCase
import com.dieang.energym.domain.usecase.auth.LoginUserUseCase
import com.dieang.energym.domain.usecase.auth.LogoutUserUseCase
import com.dieang.energym.domain.usecase.auth.RefreshTokenUseCase
import com.dieang.energym.domain.usecase.comentarios.AddComentarioUseCase
import com.dieang.energym.domain.usecase.comentarios.GetComentariosUseCase
import com.dieang.energym.domain.usecase.ejercicios.GetEjercicioUseCase
import com.dieang.energym.domain.usecase.ejercicios.GetEjerciciosUseCase
import com.dieang.energym.domain.usecase.ingredientes.CreateIngredienteUseCase
import com.dieang.energym.domain.usecase.ingredientes.GetIngredientesByRecetaUseCase
import com.dieang.energym.domain.usecase.mensajes.EnviarMensajeUseCase
import com.dieang.energym.domain.usecase.mensajes.GetConversacionUseCase
import com.dieang.energym.domain.usecase.posts.AddComentarioToPostUseCase
import com.dieang.energym.domain.usecase.posts.CreatePostUseCase
import com.dieang.energym.domain.usecase.posts.GetPostsUseCase
import com.dieang.energym.domain.usecase.posts.LikePostUseCase
import com.dieang.energym.domain.usecase.recetas.CreateRecetaUseCase
import com.dieang.energym.domain.usecase.recetas.DeleteRecetaUseCase
import com.dieang.energym.domain.usecase.recetas.GetRecetaUseCase
import com.dieang.energym.domain.usecase.recetas.GetRecetasUseCase
import com.dieang.energym.domain.usecase.recetas.UpdateRecetaUseCase
import com.dieang.energym.domain.usecase.rutinas.CreateRutinaUseCase
import com.dieang.energym.domain.usecase.rutinas.DeleteRutinaUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinaUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinasUseCase
import com.dieang.energym.domain.usecase.sesiones.AddSerieUseCase
import com.dieang.energym.domain.usecase.sesiones.CreateSesionUseCase
import com.dieang.energym.domain.usecase.sesiones.GetSesionesUseCase
import com.dieang.energym.domain.usecase.usuario.CreateUsuarioUseCase
import com.dieang.energym.domain.usecase.usuario.DeleteUsuarioUseCase
import com.dieang.energym.domain.usecase.usuario.GetUsuarioUseCase
import com.dieang.energym.domain.usecase.usuario.GetUsuariosUseCase
import com.dieang.energym.domain.usecase.usuario.UpdateUsuarioUseCase
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
    @Provides fun provideRefreshTokenUseCase(repo: AuthRepository) = RefreshTokenUseCase(repo)
    @Provides fun provideGetLoggedUserUseCase(repo: AuthRepository) = GetLoggedUserUseCase(repo)

    // USUARIO
    @Provides fun provideGetUsuariosUseCase(repo: UsuarioRepository) = GetUsuariosUseCase(repo)
    @Provides fun provideGetUsuarioUseCase(repo: UsuarioRepository) = GetUsuarioUseCase(repo)
    @Provides fun provideCreateUsuarioUseCase(repo: UsuarioRepository) = CreateUsuarioUseCase(repo)
    @Provides fun provideUpdateUsuarioUseCase(repo: UsuarioRepository) = UpdateUsuarioUseCase(repo)
    @Provides fun provideDeleteUsuarioUseCase(repo: UsuarioRepository) = DeleteUsuarioUseCase(repo)

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

    // EJERCICIOS
    @Provides fun provideGetEjerciciosUseCase(repo: EjercicioRepository) = GetEjerciciosUseCase(repo)
    @Provides fun provideGetEjercicioUseCase(repo: EjercicioRepository) = GetEjercicioUseCase(repo)

    // SESIONES
    @Provides fun provideGetSesionesUseCase(repo: SesionEntrenamientoRepository) = GetSesionesUseCase(repo)
    @Provides fun provideCreateSesionUseCase(repo: SesionEntrenamientoRepository) = CreateSesionUseCase(repo)
    @Provides fun provideAddSerieUseCase(repo: SesionEntrenamientoRepository) = AddSerieUseCase(repo)

    // POSTS
    @Provides fun provideGetPostsUseCase(repo: PostRepository) = GetPostsUseCase(repo)
    @Provides fun provideCreatePostUseCase(repo: PostRepository) = CreatePostUseCase(repo)
    @Provides fun provideLikePostUseCase(repo: PostRepository) = LikePostUseCase(repo)
    @Provides fun provideAddComentarioToPostUseCase(repo: PostRepository) = AddComentarioToPostUseCase(repo)

    // COMENTARIOS
    @Provides fun provideGetComentariosUseCase(repo: ComentarioRepository) = GetComentariosUseCase(repo)
    @Provides fun provideAddComentarioUseCase(repo: ComentarioRepository) = AddComentarioUseCase(repo)

    // MENSAJES
    @Provides fun provideGetConversacionUseCase(repo: MensajeRepository) = GetConversacionUseCase(repo)
    @Provides fun provideEnviarMensajeUseCase(repo: MensajeRepository) = EnviarMensajeUseCase(repo)
}
