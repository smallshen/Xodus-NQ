# Xodus-NQ

Typesafe DSL for [Xodus](https://github.com/JetBrains/xodus) based on the
new [context receivers](https://github.com/Kotlin/KEEP/blob/master/proposals/context-receivers.md)

Inspired by [Xodus-DNQ](https://github.com/JetBrains/xodus-dnq)

Goals:

- Typesafe
- Lightweight
- Better Output(smaller Java Bytecode output)

## Example

See [Example](core/src/test/kotlin/transactionDsl.kt)

See [Code Generation Example](core/src/test/kotlin/TestGeneratedEntity.kt)

#### Create a new entity

```kotlin
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
```

#### Query

```kotlin
store.executeInTransaction {
    with(it) {
        val testEntities = TestGeneratedEntity.query {
            (name eq "test") or (name startWith "t")
        }
        val anotherQueryX = TestGeneratedEntity { name startWith "x" }
        val anotherQueryY = TestGeneratedEntity { name startWith "y" }
        val anotherQueryZ = TestGeneratedEntity { name startWith "z" }
        val queryOperation = ((anotherQueryX or anotherQueryY) and anotherQueryZ) + testEntities
        queryOperation.forEach {
            println(it.name)
        }
    }
}
```

## Smaller Java Bytecode Output

- Use inline value classes
- Use inline methods

## TODO

- [X] Basic Examples
- [ ] DSL Marker Annotation
- [ ] Entity Link dsl
- [ ] All Query builder
- [X] Source Code Generation
- [ ] Generate through Kotlin Compiler Plugin

## Related

- [Context receiver on constructor](https://github.com/Kotlin/KEEP/blob/master/proposals/context-receivers.md#contextual-classes-and-contextual-constructors)