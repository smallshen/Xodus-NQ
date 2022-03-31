package club.eridani.xodus.nq.query

import club.eridani.xodus.nq.TransactionScope

@JvmInline
value class PropertyName(val name: String) {
    context(TransactionScope, QueryScope) infix fun <T> eq(value: Comparable<T>) = txn.find(entityType, name, value)

    context(TransactionScope, QueryScope) infix fun startWith(value: String) =
        txn.findStartingWith(entityType, name, value)


}