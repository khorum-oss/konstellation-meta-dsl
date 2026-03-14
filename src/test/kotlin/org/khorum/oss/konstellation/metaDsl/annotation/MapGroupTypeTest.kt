package org.khorum.oss.konstellation.metaDsl.annotation

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MapGroupTypeTest {

    @Test
    fun `ACTIVE_TYPES contains SINGLE, LIST, and ALL`() {
        assertEquals(listOf(MapGroupType.SINGLE, MapGroupType.LIST, MapGroupType.ALL), MapGroupType.ACTIVE_TYPES)
    }

    @Test
    fun `ACTIVE_TYPES does not contain NONE`() {
        assertFalse(MapGroupType.ACTIVE_TYPES.contains(MapGroupType.NONE))
    }

    @Test
    fun `all enum values are present`() {
        val values = MapGroupType.entries
        assertEquals(4, values.size)
        assertTrue(values.contains(MapGroupType.NONE))
        assertTrue(values.contains(MapGroupType.SINGLE))
        assertTrue(values.contains(MapGroupType.LIST))
        assertTrue(values.contains(MapGroupType.ALL))
    }
}
