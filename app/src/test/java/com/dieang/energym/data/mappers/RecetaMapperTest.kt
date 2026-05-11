package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.RecetaEntity
import com.dieang.energym.domain.model.Receta
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class RecetaMapperTest {

    @Test
    fun `toDomain should map RecetaEntity to Receta`() {
        val entity = RecetaEntity(
            id = UUID.randomUUID(),
            usuarioId = UUID.randomUUID(),
            nombre = "Pasta",
            tiempoPreparacion = 20,
            alergenos = "Gluten",
            imagenUrl = "url",
            descripcion = "Pasta rica",
            esPredisenada = true
        )

        val domain = entity.toDomain()

        assertEquals(entity.id, domain.id)
        assertEquals(entity.nombre, domain.nombre)
        assertEquals(entity.esPredisenada, domain.esPredisenada)
    }

    @Test
    fun `toEntity should map Receta to RecetaEntity`() {
        val domain = Receta(
            id = UUID.randomUUID(),
            usuarioId = UUID.randomUUID(),
            nombre = "Ensalada",
            tiempoPreparacion = 10,
            alergenos = null,
            imagenUrl = null,
            descripcion = null,
            esPredisenada = false
        )

        val entity = domain.toEntity()

        assertEquals(domain.id, entity.id)
        assertEquals(domain.nombre, entity.nombre)
        assertEquals(domain.esPredisenada, entity.esPredisenada)
    }
}
