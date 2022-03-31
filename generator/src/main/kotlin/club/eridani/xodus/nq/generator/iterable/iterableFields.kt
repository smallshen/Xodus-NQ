package club.eridani.xodus.nq.generator.iterable

import com.squareup.kotlinpoet.*

fun TypeSpec.Builder.iterableFields(): TypeSpec.Builder {
    addProperty(
        PropertySpec.builder("isEmpty", BOOLEAN)
            .getter(
                FunSpec.getterBuilder()
                    .addModifiers(KModifier.INLINE)
                    .addStatement("return entityIterable.isEmpty")
                    .build()
            )
            .build()
    )

    addProperty(
        PropertySpec.builder("size", LONG)
            .getter(
                FunSpec.getterBuilder()
                    .addModifiers(KModifier.INLINE)
                    .addStatement("return entityIterable.size()")
                    .build()
            )
            .build()
    )

    addProperty(
        PropertySpec.builder("count", LONG)
            .getter(
                FunSpec.getterBuilder()
                    .addModifiers(KModifier.INLINE)
                    .addStatement("return entityIterable.count()")
                    .build()
            )
            .build()
    )

    addProperty(
        PropertySpec.builder("roughSize", LONG)
            .getter(
                FunSpec.getterBuilder()
                    .addModifiers(KModifier.INLINE)
                    .addStatement("return entityIterable.roughSize")
                    .build()
            )
            .build()
    )

    addProperty(
        PropertySpec.builder("roughCount", LONG)
            .getter(
                FunSpec.getterBuilder()
                    .addModifiers(KModifier.INLINE)
                    .addStatement("return entityIterable.roughCount")
                    .build()
            )
            .build()
    )

    addProperty(
        PropertySpec.builder("sorted", BOOLEAN)
            .getter(
                FunSpec.getterBuilder()
                    .addModifiers(KModifier.INLINE)
                    .addStatement("return entityIterable.isSortResult")
                    .build()
            )
            .build()
    )

    return this
}