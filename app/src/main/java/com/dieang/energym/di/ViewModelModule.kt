package com.dieang.energym.di

import com.dieang.energym.domain.usecase.auth.GetLoggedUserUseCase
import com.dieang.energym.domain.usecase.auth.IsUserLoggedInUseCase
import com.dieang.energym.domain.usecase.auth.LoginUserUseCase
import com.dieang.energym.domain.usecase.auth.LogoutUserUseCase
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
import com.dieang.energym.domain.usecase.posts.GetComentariosByPostUseCase
import com.dieang.energym.domain.usecase.posts.GetPostsUseCase
import com.dieang.energym.domain.usecase.posts.LikePostUseCase
import com.dieang.energym.domain.usecase.recetas.CreateRecetaUseCase
import com.dieang.energym.domain.usecase.recetas.DeleteRecetaUseCase
import com.dieang.energym.domain.usecase.recetas.GetRecetaUseCase
import com.dieang.energym.domain.usecase.recetas.GetRecetasUseCase
import com.dieang.energym.domain.usecase.recetas.UpdateRecetaUseCase
import com.dieang.energym.domain.usecase.rutinas.CreateRutinaUseCase
import com.dieang.energym.domain.usecase.rutinas.DeleteRutinaUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinaEjerciciosUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinaUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinasUseCase
import com.dieang.energym.domain.usecase.sesiones.AddSerieUseCase
import com.dieang.energym.domain.usecase.sesiones.CreateSesionUseCase
import com.dieang.energym.domain.usecase.sesiones.GetSesionesUseCase
import com.dieang.energym.domain.usecase.sesiones.SaveSesionUseCase
import com.dieang.energym.domain.usecase.usuario.CreateUsuarioUseCase
import com.dieang.energym.domain.usecase.usuario.DeleteUsuarioUseCase
import com.dieang.energym.domain.usecase.usuario.GetEstadisticasUsuarioUseCase
import com.dieang.energym.domain.usecase.usuario.GetUsuarioUseCase
import com.dieang.energym.domain.usecase.usuario.GetUsuariosUseCase
import com.dieang.energym.domain.usecase.usuario.UpdateUsuarioUseCase
import com.dieang.energym.ui.feature.auth.AuthViewModel
import com.dieang.energym.ui.feature.comentarios.ComentarioViewModel
import com.dieang.energym.ui.feature.ejercicios.EjercicioViewModel
import com.dieang.energym.ui.feature.ingredientes.IngredienteViewModel
import com.dieang.energym.ui.feature.mensajes.MensajeViewModel
import com.dieang.energym.ui.feature.posts.PostViewModel
import com.dieang.energym.ui.feature.recetas.RecetaViewModel
import com.dieang.energym.ui.feature.rutinas.RutinaViewModel
import com.dieang.energym.ui.feature.sesiones.SesionViewModel
import com.dieang.energym.ui.feature.splash.SplashViewModel
import com.dieang.energym.ui.feature.usuario.UsuarioViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideAuthViewModel(
        login: LoginUserUseCase,
        logout: LogoutUserUseCase,
        getLoggedUser: GetLoggedUserUseCase
    ): AuthViewModel =
        AuthViewModel(login, logout, getLoggedUser)

    @Provides
    fun provideUsuarioViewModel(
        getLoggedUser: GetLoggedUserUseCase,
        getSesiones: GetSesionesUseCase,
        updateUsuario: UpdateUsuarioUseCase,
        getEstadisticas: GetEstadisticasUsuarioUseCase,
        logoutUser: LogoutUserUseCase
    ): UsuarioViewModel =
        UsuarioViewModel(getLoggedUser, getSesiones, updateUsuario, getEstadisticas, logoutUser)

    @Provides
    fun provideRecetaViewModel(
        getRecetas: GetRecetasUseCase,
        getReceta: GetRecetaUseCase,
        getIngredientes: GetIngredientesByRecetaUseCase
    ): RecetaViewModel =
        RecetaViewModel(getRecetas, getReceta, getIngredientes)

    @Provides
    fun provideIngredienteViewModel(
        getIngredientes: GetIngredientesByRecetaUseCase,
        createIngrediente: CreateIngredienteUseCase
    ): IngredienteViewModel =
        IngredienteViewModel(getIngredientes, createIngrediente)

    @Provides
    fun provideRutinaViewModel(
        getRutinas: GetRutinasUseCase,
        getRutina: GetRutinaUseCase,
        getRutinaEjercicios: GetRutinaEjerciciosUseCase
    ): RutinaViewModel =
        RutinaViewModel(getRutinas, getRutina, getRutinaEjercicios)

    @Provides
    fun provideEjercicioViewModel(
        getEjercicios: GetEjerciciosUseCase,
        getEjercicio: GetEjercicioUseCase
    ): EjercicioViewModel =
        EjercicioViewModel(getEjercicios, getEjercicio)

    @Provides
    fun provideSesionViewModel(
        getRutina: GetRutinaUseCase,
        getRutinaEjercicios: GetRutinaEjerciciosUseCase,
        createSesion: CreateSesionUseCase,
        addSerie: AddSerieUseCase,
        saveSesion: SaveSesionUseCase,
        getLoggedUser: GetLoggedUserUseCase,
        createPost: CreatePostUseCase
    ): SesionViewModel =
        SesionViewModel(getRutina, getRutinaEjercicios, createSesion, addSerie, saveSesion, getLoggedUser, createPost)

    @Provides
    fun providePostViewModel(
        getPosts: GetPostsUseCase,
        createPost: CreatePostUseCase,
        likePost: LikePostUseCase,
        getComentariosByPost: GetComentariosByPostUseCase,
        addComentario: AddComentarioToPostUseCase,
        getSesiones: GetSesionesUseCase
    ): PostViewModel =
        PostViewModel(getPosts, createPost, likePost, getComentariosByPost, addComentario, getSesiones)

    @Provides
    fun provideComentarioViewModel(
        getComentarios: GetComentariosUseCase,
        addComentario: AddComentarioUseCase
    ): ComentarioViewModel =
        ComentarioViewModel(getComentarios, addComentario)

    @Provides
    fun provideMensajeViewModel(
        getConversacion: GetConversacionUseCase,
        getLoggedUser: GetLoggedUserUseCase,
        getUsuarios: GetUsuariosUseCase
    ): MensajeViewModel =
        MensajeViewModel(getConversacion, getLoggedUser, getUsuarios)

    @Provides
    fun provideSplashViewModel(
        isUserLoggedIn: IsUserLoggedInUseCase
    ): SplashViewModel =
        SplashViewModel(isUserLoggedIn)
}
