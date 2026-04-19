package com.dieang.energym.domain.usecase.usuario

import com.dieang.energym.domain.repository.SerieRealizadaRepository
import com.dieang.energym.domain.repository.SesionEntrenamientoRepository
import java.util.UUID
import javax.inject.Inject

class GetEstadisticasUsuarioUseCase @Inject constructor(
    private val serieRepo: SerieRealizadaRepository,
    private val sesionRepo: SesionEntrenamientoRepository
) {
    suspend operator fun invoke(usuarioId: UUID): UserStats {
        val volumenTotal = serieRepo.getVolumenTotal(usuarioId)

        // Conversión: 1 kg levantado ≈ 0.0001 kWh generados (Factor de ejemplo EnerGym)
        val energiaTotal = volumenTotal * 0.0001

        // Obtener total de sesiones (en una app real se contaría en BD)
        // val totalSesiones = sesionRepo.getSesionesByUsuario(usuarioId).size

        return UserStats(
            energiaKwh = energiaTotal,
            volumenKg = volumenTotal.toDouble()
        )
    }
}

data class UserStats(
    val energiaKwh: Double,
    val volumenKg: Double
)
