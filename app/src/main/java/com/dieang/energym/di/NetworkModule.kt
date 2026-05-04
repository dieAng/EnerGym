package com.dieang.energym.di

import com.dieang.energym.core.network.RetrofitProvider
import com.dieang.energym.data.remote.api.*
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import java.util.concurrent.TimeUnit
import com.dieang.energym.core.network.NetworkConfig

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(NetworkConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NetworkConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NetworkConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit =
        RetrofitProvider.createRetrofit(client)

    @Provides @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides @Singleton
    fun provideUsuarioApi(retrofit: Retrofit): UsuarioApi = retrofit.create(UsuarioApi::class.java)

    @Provides @Singleton
    fun provideRecetaApi(retrofit: Retrofit): RecetaApi = retrofit.create(RecetaApi::class.java)

    @Provides @Singleton
    fun provideIngredienteApi(retrofit: Retrofit): IngredienteApi = retrofit.create(IngredienteApi::class.java)

    @Provides @Singleton
    fun provideRutinaApi(retrofit: Retrofit): RutinaApi = retrofit.create(RutinaApi::class.java)

    @Provides @Singleton
    fun provideEjercicioApi(retrofit: Retrofit): EjercicioApi = retrofit.create(EjercicioApi::class.java)

    @Provides @Singleton
    fun provideSesionEntrenamientoApi(retrofit: Retrofit): SesionEntrenamientoApi = retrofit.create(SesionEntrenamientoApi::class.java)

    @Provides @Singleton
    fun providePostApi(retrofit: Retrofit): PostApi = retrofit.create(PostApi::class.java)

    @Provides @Singleton
    fun provideComentarioApi(retrofit: Retrofit): ComentarioApi = retrofit.create(ComentarioApi::class.java)

    @Provides @Singleton
    fun provideMensajeApi(retrofit: Retrofit): MensajeApi = retrofit.create(MensajeApi::class.java)
}
