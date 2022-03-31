import club.eridani.xodus.nq.query.or
import club.eridani.xodus.nq.transaction
import jetbrains.exodus.entitystore.PersistentEntityStore

fun test(store: PersistentEntityStore) {
    store.transaction {
        val newEntity = TestGeneratedEntity(
            "test",
            "testUid",
            "test@eridani.club",
            23,
            "Male",
            "Hi, this is dsl testing."
        )


        val testEntities = TestGeneratedEntity.query {
            (name eq "test") or (name startWith "t")
        }


    }
}