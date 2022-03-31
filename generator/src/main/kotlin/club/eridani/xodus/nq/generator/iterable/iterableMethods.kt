package club.eridani.xodus.nq.generator.iterable

import club.eridani.xodus.nq.generator.localIterable
import club.eridani.xodus.nq.generator.localIterator
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy


fun TypeSpec.Builder.iterableMethod(entityType: TypeName): TypeSpec.Builder {
    addFunction(
        FunSpec.builder("indexOf")
            .addModifiers(KModifier.INLINE)
            .returns(INT)
            .addParameter("entity", entityType)
            .addStatement("return entityIterable.indexOf(entity.entity)")
            .build()
    )

    addFunction(
        FunSpec.builder("contains")
            .addModifiers(KModifier.INLINE)
            .returns(BOOLEAN)
            .addParameter("entity", entityType)
            .addStatement("return entityIterable.contains(entity.entity)")
            .build()
    )

    addFunction(queryOperation("or", "union").build())
    addFunction(queryOperation("and", "intersect").build())
    addFunction(queryOperation("andNot", "minus").build())
    addFunction(queryOperation("plus", "concat").addModifiers(KModifier.OPERATOR).build())

    addFunction(
        basicBuilder("skip")
            .addParameter("number", INT)
            .returns(localIterable)
            .addStatement("return %T(entityIterable.skip(number))", localIterable)
            .build()
    )

    addFunction(
        basicBuilder("take")
            .addParameter("number", INT)
            .returns(localIterable)
            .addStatement("return %T(entityIterable.take(number))", localIterable)
            .build()
    )

    addFunction(
        basicBuilder("distinct")
            .returns(localIterable)
            .addStatement("return %T((entityIterable.distinct()))", localIterable)
            .build()
    )

    addFunction(
        basicBuilder("selectDistinct")
            .addParameter("linkName", STRING)
            .returns(localIterable)
            .addStatement("return %T(entityIterable.selectDistinct(linkName))", localIterable)
            .build()
    )

    addFunction(
        basicBuilder("selectManyDistinct")
            .addParameter("linkName", STRING)
            .returns(localIterable)
            .addStatement("return %T(entityIterable.selectManyDistinct(linkName))", localIterable)
            .build()
    )

    fastGetting("first", entityType)
    fastGetting("last", entityType)

    addFunction(
        basicBuilder("reverse")
            .returns(localIterable)
            .addStatement("return %T((entityIterable.reverse()))", localIterable)
            .build()
    )

    addFunction(
        basicBuilder("asSorted")
            .returns(localIterable)
            .addStatement("return %T((entityIterable.asSortResult()))", localIterable)
            .build()
    )

    addFunction(
        FunSpec.builder("iterator")
            .addModifiers(KModifier.OVERRIDE)
            .returns(Iterator::class.asClassName().parameterizedBy(entityType))
            .addStatement("return %T(entityIterable.iterator())", localIterator)
            .build()
    )


    return this
}

fun basicBuilder(name: String) = FunSpec.builder(name)
    .addModifiers(KModifier.INLINE)

fun queryOperation(name: String, targetName: String) = basicBuilder(name)
    .addModifiers(KModifier.INFIX)
    .addParameter("right", localIterable)
    .returns(localIterable)
    .addStatement("return %T(entityIterable.%N(right.entityIterable))", localIterable, targetName)


fun TypeSpec.Builder.fastGetting(name: String, entityType: TypeName) {
    addFunction(
        basicBuilder(name)
            .returns(entityType)
            .addStatement("return %T(entityIterable.first!!)", entityType)
            .build()
    )
    addFunction(
        basicBuilder("${name}OrNull")
            .returns(entityType.copy(true))
            .addStatement("return entityIterable.first?.let { %T(it) }", entityType)
            .build()
    )
}