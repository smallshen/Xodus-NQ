# Xodus-NQ

DSL for [Xodus](https://github.com/JetBrains/xodus) based on the
new [context receivers](https://github.com/Kotlin/KEEP/blob/master/proposals/context-receivers.md)

Inspired from [Xodus-DNQ](https://github.com/JetBrains/xodus-dnq)

Goals:

- Lightweight
- Better Output(smaller Java Bytecode output)

## Example

See [Example](core/src/test/kotlin/transactionDsl.kt)
See [Code Generation Example](core/src/test/kotlin/TestGeneratedEntity.kt)

```kotlin
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