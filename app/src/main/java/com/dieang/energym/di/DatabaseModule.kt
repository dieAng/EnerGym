package com.dieang.energym.di

import android.content.Context
import androidx.room.Room
import com.dieang.energym.data.local.EnerGymDatabase
import com.dieang.energym.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EnerGymDatabase {
        return Room.databaseBuilder(
            context,
            EnerGymDatabase::class.java,
            "energym_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideUsuarioDao(db: EnerGymDatabase): UsuarioDao = db.usuarioDao()

    @Provides
    fun provideRecetaDao(db: EnerGymDatabase): RecetaDao = db.recetaDao()

    @Provides
    fun provideIngredienteDao(db: EnerGymDatabase): IngredienteDao = db.ingredienteDao()

    @Provides
    fun provideRutinaDao(db: EnerGymDatabase): RutinaDao = db.rutinaDao()

    @Provides
    fun provideEjercicioDao(db: EnerGymDatabase): EjercicioDao = db.ejercicioDao()

    @Provides
    fun provideRutinaEjercicioDao(db: EnerGymDatabase): RutinaEjercicioDao = db.rutinaEjercicioDao()

    @Provides
    fun provideSesionEntrenamientoDao(db: EnerGymDatabase): SesionEntrenamientoDao = db.sesionEntrenamientoDao()

    @Provides
    fun provideSerieRealizadaDao(db: EnerGymDatabase): SerieRealizadaDao = db.serieRealizadaDao()

    @Provides
    fun provideMensajeDao(db: EnerGymDatabase): MensajeDao = db.mensajeDao()

    @Provides
    fun providePostDao(db: EnerGymDatabase): PostDao = db.postDao()

    @Provides
    fun provideLikePostDao(db: EnerGymDatabase): LikePostDao = db.likePostDao()

    @Provides
    fun provideComentarioDao(db: EnerGymDatabase): ComentarioDao = db.comentarioDao()

    @Provides
    fun provideHistorialPesoDao(db: EnerGymDatabase): HistorialPesoDao = db.historialPesoDao()
}
