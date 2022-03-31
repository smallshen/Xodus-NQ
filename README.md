# Xodus-NQ

DSL for [Xodus](https://github.com/JetBrains/xodus) based on the
new [context receivers](https://github.com/Kotlin/KEEP/blob/master/proposals/context-receivers.md)

Inspired by [Xodus-DNQ](https://github.com/JetBrains/xodus-dnq)

Goals:

- Lightweight
- Better Output(smaller Java Bytecode output)

## Example

See [Example](core/src/test/kotlin/transactionDsl.kt)

See [Code Generation Example](core/src/test/kotlin/TestGeneratedEntity.kt)

```kotlin
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
```

## Smaller Java Bytecode Output

- Use inline value classes
- Use inline methods

## TODO

- [X] Basic Examples
- [ ] Entity Link dsl
- [ ] All Query builder
- [ ] Generate sources
- [ ] Generate through Kotlin Compiler Plugin