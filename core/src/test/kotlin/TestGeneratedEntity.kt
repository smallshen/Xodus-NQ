import club.eridani.xodus.nq.TransactionScope
import club.eridani.xodus.nq.query.PropertyName
import club.eridani.xodus.nq.query.QueryScope
import club.eridani.xodus.nq.type.BlobString
import jetbrains.exodus.entitystore.Entity

private const val entityType = "TestEntity"

@JvmInline
value class TestGeneratedEntity private constructor(private val entity: Entity) {

//    TODO: YouTrack Link Here
//    https://github.com/Kotlin/KEEP/blob/master/proposals/context-receivers.md#contextual-classes-and-contextual-constructors
//    context(TransactionScope) constructor(
//        uid: String
//    ): this(txn.newEntity(entityType))

    var name: String
        get() = entity.getProperty("name") as String
        set(value) {
            entity.setProperty("name", value)
        }
    var uid: String
        get() = entity.getProperty("uid") as String
        set(value) {
            entity.setProperty("uid", value)
        }
    var email: String
        get() = entity.getProperty("email") as String
        set(value) {
            entity.setProperty("email", value)
        }
    var age: Int
        get() = entity.getProperty("age") as Int
        set(value) {
            entity.setProperty("age", value)
        }

    var gender: String?
        get() = entity.getProperty("gender") as String?
        set(value) {
            if (value == null) entity.deleteProperty("gender")
            else entity.setProperty("gender", value)
        }

    @BlobString
    var bio: String?
        get() = entity.getBlobString("bio")
        set(value) {
            if (value == null) entity.deleteBlob("bio")
            else entity.setBlobString("bio", value)
        }


    companion object {
        /**
         * This is constructor for creating a new entity
         */
        context(TransactionScope) operator fun invoke(
            name: String,
            uid: String,
            email: String,
            age: Int,
            gender: String?,
            @BlobString bio: String?
        ): TestGeneratedEntity {
            return TestGeneratedEntity(txn.newEntity(entityType)).apply {
                this.name = name
                this.uid = uid
                this.email = email
                this.age = age
                this.gender = gender
                this.bio = bio
            }
        }

        inline fun <T> query(block: Query.() -> T) = block(Query)
    }

    object Query : QueryScope {
        override val entityType: String = "TestEntity"
        val name = PropertyName("name")
        val uid = PropertyName("uid")
        val email = PropertyName("email")
        val age = PropertyName("age")
        val gender = PropertyName("gender")

    }


}