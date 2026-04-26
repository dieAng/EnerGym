package com.dieang.energym.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dieang.energym.data.local.dao.PostDao
import com.dieang.energym.data.local.dao.SesionEntrenamientoDao
import com.dieang.energym.data.remote.api.PostApi
import com.dieang.energym.data.remote.api.SesionEntrenamientoApi
import com.dieang.energym.data.remote.dto.request.PostCreateRequestDto
import com.dieang.energym.data.remote.dto.request.SesionCreateRequestDto
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val postDao: PostDao,
    private val postApi: PostApi,
    private val sesionDao: SesionEntrenamientoDao,
    private val sesionApi: SesionEntrenamientoApi
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            syncPosts()
            syncSesiones()
            // Paso 3: Borrado inteligente (Cleanup)
            performSmartCleanup()
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    private suspend fun performSmartCleanup() {
        val thirtyDaysAgo = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000)
        
        // Limpiar posts viejos sincronizados
        postDao.deleteOldSynchronized(thirtyDaysAgo)
        // Mantener solo los 100 posts más recientes localmente para ahorrar espacio
        postDao.keepOnlyLatest(100)
        
        // Limpiar sesiones viejas sincronizadas (ej: más de 90 días)
        val ninetyDaysAgo = System.currentTimeMillis() - (90L * 24 * 60 * 60 * 1000)
        sesionDao.deleteOldSynchronized(ninetyDaysAgo)
    }

    private suspend fun syncPosts() {
        val noSincronizados = postDao.getNoSincronizados()
        noSincronizados.forEach { post ->
            val request = PostCreateRequestDto(
                usuarioId = post.usuarioId,
                contenido = post.contenido,
                imagenUrl = post.imagenUrl,
                energiaGenerada = post.energiaGenerada
            )
            // Simular envío a la API
            postApi.createPost(request)
            // Marcar como sincronizado
            postDao.insert(post.copy(sincronizado = true))
        }
    }

    private suspend fun syncSesiones() {
        val noSincronizados = sesionDao.getNoSincronizados()
        noSincronizados.forEach { sesion ->
            val request = SesionCreateRequestDto(
                usuarioId = sesion.usuarioId,
                rutinaId = sesion.rutinaId!!
            )
            // Simular envío a la API
            sesionApi.createSesion(request)
            // Marcar como sincronizado
            sesionDao.insert(sesion.copy(sincronizado = true))
        }
    }
}
