import club.eridani.xodus.nq.query.or
import club.eridani.xodus.nq.transaction
import jetbrains.exodus.entitystore.EntityIterable
import jetbrains.exodus.entitystore.PersistentEntityStore

fun test(store: PersistentEntityStore) {
    store.transaction {
        val newEntity = TestGeneratedEntity(
            name = "test",
            uid = "testUid",
            email = "test@eridani.club",
            age = 23,
            gender = "Male",
            bio = "Hi, this is dsl testing."
        )


        val testEntities: EntityIterable = TestGeneratedEntity.query {
            (name eq "test") or (name startWith "t")
        }


    }
}