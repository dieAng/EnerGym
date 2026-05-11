package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.PostEntity
import com.dieang.energym.domain.model.Post
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class PostMapperTest {

    @Test
    fun `toDomain should map PostEntity to Post`() {
        val entity = PostEntity(
            id = UUID.randomUUID(),
            usuarioId = UUID.randomUUID(),
            contenido = "Post content",
            imagenUrl = "url",
            energiaGenerada = 50.5,
            fecha = System.currentTimeMillis()
        )

        val domain = entity.toDomain()

        assertEquals(entity.id, domain.id)
        assertEquals(entity.contenido, domain.contenido)
        assertEquals(entity.energiaGenerada, domain.energiaGenerada, 0.01)
    }

    @Test
    fun `toEntity should map Post to PostEntity`() {
        val domain = Post(
            id = UUID.randomUUID(),
            usuarioId = UUID.randomUUID(),
            contenido = "Domain content",
            imagenUrl = null,
            energiaGenerada = 100.0,
            fecha = System.currentTimeMillis()
        )

        val entity = domain.toEntity()

        assertEquals(domain.id, entity.id)
        assertEquals(domain.contenido, entity.contenido)
        assertEquals(domain.energiaGenerada, entity.energiaGenerada, 0.01)
    }
}
