package club.eridani.xodus.nq

import jetbrains.exodus.entitystore.PersistentEntityStore
import jetbrains.exodus.entitystore.StoreTransaction

@JvmInline
value class TransactionScope(val txn: StoreTransaction)

inline fun PersistentEntityStore.transaction(block: TransactionScope.() -> Unit) {
    val txn = beginTransaction()
    try {
        TransactionScope(txn).apply(block)
    } catch (e: Exception) {
        txn.abort()
    } finally {
        txn.flush()
    }
}