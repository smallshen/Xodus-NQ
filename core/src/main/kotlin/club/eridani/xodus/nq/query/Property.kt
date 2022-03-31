@file:Suppress("NOTHING_TO_INLINE")

package club.eridani.xodus.nq.query

import club.eridani.xodus.nq.TransactionScope
import jetbrains.exodus.entitystore.EntityIterable

@JvmInline
value class Property(val name: String) {
    context(TransactionScope, QueryScope) inline infix fun <T> eq(value: Comparable<T>): EntityIterable {
        return txn.find(entityType, name, value)
    }

    context(TransactionScope, QueryScope) inline infix fun startWith(value: String): EntityIterable {
        return txn.findStartingWith(entityType, name, value)
    }


}