package com.dieang.energym.ui.feature.usuario

import com.dieang.energym.domain.model.Usuario

data class UsuarioState(
    val isLoading: Boolean = false,
    val usuario: Usuario? = null,
    val username: String = "@usuario",
    val tags: List<String> = emptyList(),
    val stats: PerfilStats = PerfilStats(),
    val objetivo: ObjetivoActual? = null,
    val historialSesiones: List<SesionHistorialUI> = emptyList(),
    val logros: List<Logro> = emptyList(),
    val error: String? = null
)

data class PerfilStats(
    val entrenamientos: Int = 0,
    val rachaMaxima: Int = 0,
    val prsLogrados: Int = 0,
    val energiaTotalWh: Int = 0
)

data class SesionHistorialUI(
    val id: String,
    val fecha: String,
    val duracion: String,
    val energia: Int,
    val calorias: Int,
    val rutinaNombre: String
)

data class ObjetivoActual(
    val titulo: String,
    val progreso: Float, // 0.0 a 1.0
    val descripcion: String,
    val detalleProgreso: String // Ej: "75kg -> 80kg"
)

data class Logro(
    val id: String,
    val nombre: String,
    val icono: String,
    val desbloqueado: Boolean
)
