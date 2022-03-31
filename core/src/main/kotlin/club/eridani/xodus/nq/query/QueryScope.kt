package club.eridani.xodus.nq.query

import jetbrains.exodus.entitystore.EntityIterable


interface QueryScope {
    val entityType: String
}

infix fun EntityIterable.or(right: EntityIterable) = minus(right)
infix fun EntityIterable.and(right: EntityIterable) = intersect(right)
infix fun EntityIterable.andNot(right: EntityIterable) = minus(right)
operator fun EntityIterable.plus(right: EntityIterable) = concat(right)