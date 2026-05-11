package com.dieang.energym.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.dieang.energym.data.local.dao.PostDao
import com.dieang.energym.data.local.dao.SesionEntrenamientoDao
import com.dieang.energym.data.local.entity.PostEntity
import com.dieang.energym.data.remote.api.PostApi
import com.dieang.energym.data.remote.api.SesionEntrenamientoApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.UUID

class SyncWorkerTest {

    private val context: Context = mockk(relaxed = true)
    private val workerParams: WorkerParameters = mockk(relaxed = true)
    private val postDao: PostDao = mockk()
    private val postApi: PostApi = mockk()
    private val sesionDao: SesionEntrenamientoDao = mockk()
    private val sesionApi: SesionEntrenamientoApi = mockk()

    private lateinit var syncWorker: SyncWorker

    @Before
    fun setup() {
        syncWorker = SyncWorker(context, workerParams, postDao, postApi, sesionDao, sesionApi)
    }

    @Test
    fun `doWork should sync posts and return success`() = runBlocking {
        // Given
        val post = PostEntity(
            id = UUID.randomUUID(),
            usuarioId = UUID.randomUUID(),
            contenido = "Test",
            imagenUrl = null,
            energiaGenerada = 10.0,
            fecha = System.currentTimeMillis(),
            sincronizado = false
        )
        coEvery { postDao.getNoSincronizados() } returns listOf(post)
        coEvery { postApi.createPost(any()) } returns mockk()
        coEvery { postDao.insert(any()) } returns Unit
        coEvery { sesionDao.getNoSincronizados() } returns emptyList()
        coEvery { postDao.deleteOldSynchronized(any()) } returns Unit
        coEvery { postDao.keepOnlyLatest(any()) } returns Unit
        coEvery { sesionDao.deleteOldSynchronized(any()) } returns Unit

        // When
        val result = syncWorker.doWork()

        // Then
        assertEquals(ListenableWorker.Result.success(), result)
        coVerify { postApi.createPost(any()) }
        coVerify { postDao.insert(match { it.sincronizado }) }
    }

    @Test
    fun `doWork should retry on error`() = runBlocking {
        // Given
        coEvery { postDao.getNoSincronizados() } throws Exception("Network Error")
        coEvery { workerParams.runAttemptCount } returns 0

        // When
        val result = syncWorker.doWork()

        // Then
        assertEquals(ListenableWorker.Result.retry(), result)
    }
}
