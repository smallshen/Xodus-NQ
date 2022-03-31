@file:Suppress("NOTHING_TO_INLINE")

package club.eridani.xodus.nq.query

import jetbrains.exodus.entitystore.EntityIterable
import jetbrains.exodus.entitystore.StoreTransaction

@JvmInline
value class Property(val name: String) {
    context(StoreTransaction, QueryScope) inline infix fun <T> eq(value: Comparable<T>): EntityIterable {
        return find(entityType, name, value)
    }

    context(StoreTransaction, QueryScope) inline infix fun startWith(value: String): EntityIterable {
        return findStartingWith(entityType, name, value)
    }


}