@file:Suppress("NOTHING_TO_INLINE")

package club.eridani.xodus.nq.query

import jetbrains.exodus.entitystore.EntityIterable


interface QueryScope {
    val entityType: String
}

inline infix fun EntityIterable.or(right: EntityIterable) = union(right)
inline infix fun EntityIterable.and(right: EntityIterable) = intersect(right)
inline infix fun EntityIterable.andNot(right: EntityIterable) = minus(right)
inline operator fun EntityIterable.plus(right: EntityIterable) = concat(right)