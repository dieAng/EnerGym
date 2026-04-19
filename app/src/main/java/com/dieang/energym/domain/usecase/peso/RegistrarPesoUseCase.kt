package com.dieang.energym.domain.usecase.peso

import com.dieang.energym.domain.repository.PesoRepository
import java.util.UUID
import javax.inject.Inject

class RegistrarPesoUseCase @Inject constructor(
    private val repository: PesoRepository
) {
    suspend operator fun invoke(usuarioId: UUID, peso: Float) {
        repository.registrarPeso(usuarioId, peso, System.currentTimeMillis())
    }
}
