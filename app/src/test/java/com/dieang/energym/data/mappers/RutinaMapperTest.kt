package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.RutinaEntity
import com.dieang.energym.domain.model.Rutina
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class RutinaMapperTest {

    @Test
    fun `toDomain should map RutinaEntity to Rutina`() {
        // Given
        val entity = RutinaEntity(
            id = UUID.randomUUID(),
            usuarioId = UUID.randomUUID(),
            nombre = "Rutina Test",
            descripcion = "Desc Test",
            nivel = "Principiante",
            objetivo = "Hipertrofia",
            esPredisenada = true
        )

        // When
        val domain = entity.toDomain()

        // Then
        assertEquals(entity.id, domain.id)
        assertEquals(entity.usuarioId, domain.usuarioId)
        assertEquals(entity.nombre, domain.nombre)
        assertEquals(entity.descripcion, domain.descripcion)
        assertEquals(entity.nivel, domain.nivel)
        assertEquals(entity.objetivo, domain.objetivo)
        assertEquals(entity.esPredisenada, domain.esPredisenada)
    }

    @Test
    fun `toEntity should map Rutina to RutinaEntity`() {
        // Given
        val domain = Rutina(
            id = UUID.randomUUID(),
            usuarioId = UUID.randomUUID(),
            nombre = "Rutina Domain",
            descripcion = "Desc Domain",
            nivel = "Avanzado",
            objetivo = "Fuerza",
            esPredisenada = false
        )

        // When
        val entity = domain.toEntity()

        // Then
        assertEquals(domain.id, entity.id)
        assertEquals(domain.usuarioId, entity.usuarioId)
        assertEquals(domain.nombre, entity.nombre)
        assertEquals(domain.descripcion, entity.descripcion)
        assertEquals(domain.nivel, entity.nivel)
        assertEquals(domain.objetivo, entity.objetivo)
        assertEquals(domain.esPredisenada, entity.esPredisenada)
    }
}
