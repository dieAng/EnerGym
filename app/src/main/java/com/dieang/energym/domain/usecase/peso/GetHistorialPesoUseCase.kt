package com.dieang.energym.domain.usecase.peso

import com.dieang.energym.data.local.entity.HistorialPesoEntity
import com.dieang.energym.domain.repository.PesoRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class GetHistorialPesoUseCase @Inject constructor(
    private val repository: PesoRepository
) {
    operator fun invoke(usuarioId: UUID): Flow<List<HistorialPesoEntity>> {
        return repository.getHistorialPeso(usuarioId)
    }
}
