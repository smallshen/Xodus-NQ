@file:Suppress("NOTHING_TO_INLINE")

import club.eridani.xodus.nq.QueryEntity
import club.eridani.xodus.nq.query.Property
import club.eridani.xodus.nq.query.QueryScope
import club.eridani.xodus.nq.type.BlobString
import jetbrains.exodus.entitystore.Entity
import jetbrains.exodus.entitystore.EntityIterable
import jetbrains.exodus.entitystore.StoreTransaction

private const val entityType = "TestEntity"

/**
 * Template for code generation
 */
@JvmInline
value class TestGeneratedEntity(val entity: Entity) : QueryEntity {

    override val entityType: String get() = "TestEntity"

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

        context(StoreTransaction) @JvmStatic operator fun invoke(
            name: String,
            uid: String,
            email: String,
            age: Int,
            gender: String?,
            @BlobString bio: String?
        ): TestGeneratedEntity {
            return TestGeneratedEntity(newEntity(entityType)).apply {
                this.name = name
                this.uid = uid
                this.email = email
                this.age = age
                this.gender = gender
                this.bio = bio
            }
        }

        @JvmStatic inline fun query(block: Query.() -> EntityIterable): Iterable = Iterable(block(Query))
        @JvmStatic inline operator fun invoke(block: Query.() -> EntityIterable): Iterable = Iterable(block(Query))


        object Query : QueryScope {
            override val entityType: String = "TestEntity"

            @JvmStatic val name = Property("name")
            @JvmStatic val uid = Property("uid")
            @JvmStatic val email = Property("email")
            @JvmStatic val age = Property("age")
            @JvmStatic val gender = Property("gender")

        }
    }

    @JvmInline
    value class Iterable(val entityIterable: EntityIterable) {

        val isEmpty: Boolean inline get() = entityIterable.isEmpty
        val size: Long inline get() = entityIterable.size()
        val count: Long inline get() = entityIterable.count()
        val roughCount: Long inline get() = entityIterable.roughCount
        val roughSize: Long inline get() = entityIterable.roughSize
        val sorted: Boolean inline get() = entityIterable.isSortResult


        inline fun indexOf(entity: TestGeneratedEntity): Int {
            return entityIterable.indexOf(entity.entity)
        }

        inline fun contains(entity: TestGeneratedEntity): Boolean {
            return entityIterable.contains(entity.entity)
        }

        inline infix fun or(right: Iterable) = Iterable(entityIterable.union(right.entityIterable))
        inline infix fun and(right: Iterable) = Iterable(entityIterable.intersect(right.entityIterable))
        inline infix fun andNot(right: Iterable) = Iterable(entityIterable.minus(right.entityIterable))
        inline operator fun plus(right: Iterable) = Iterable(entityIterable.concat(right.entityIterable))

        inline fun skip(number: Int) = Iterable(entityIterable.skip(number))
        inline fun take(number: Int) = Iterable(entityIterable.take(number))
        inline fun distinct() = Iterable(entityIterable.distinct())
        inline fun selectDistinct(linkName: String) = Iterable(entityIterable.selectDistinct(linkName))
        inline fun selectManyDistinct(linkName: String) = Iterable(entityIterable.selectManyDistinct(linkName))
        inline fun first() = TestGeneratedEntity(entityIterable.first!!)
        inline fun firstOrNull() = entityIterable.first?.let { TestGeneratedEntity(it) }
        inline fun last() = TestGeneratedEntity(entityIterable.last!!)
        inline fun lastOrNull() = entityIterable.last?.let { TestGeneratedEntity(it) }
        inline fun reverse() = Iterable(entityIterable.reverse())
        inline fun asSorted() = Iterable(entityIterable.asSortResult())


    }


}