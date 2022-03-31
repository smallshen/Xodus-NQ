import club.eridani.xodus.nq.query.or
import jetbrains.exodus.entitystore.PersistentEntityStore

fun newEntityExample(store: PersistentEntityStore) {
    store.executeInTransaction {
        with(it) {
            val newEntity = TestGeneratedEntity(
                name = "test",
                uid = "testUid",
                email = "test@eridani.club",
                age = 23,
                gender = "Male",
                bio = "Hi, this is dsl testing."
            )
        }
    }
}

fun queryExample(store: PersistentEntityStore) {
    store.executeInTransaction {
        with(it) {
            val testEntities = TestGeneratedEntity.query {
                (name eq "test") or (name startWith "t")
            }

            val anotherQueryX = TestGeneratedEntity { name startWith "x" }
            val anotherQueryY = TestGeneratedEntity { name startWith "y" }
            val anotherQueryZ = TestGeneratedEntity { name startWith "z" }

            val queryOperation = ((anotherQueryX or anotherQueryY) and anotherQueryZ) + testEntities
        }
    }
}