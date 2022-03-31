package club.eridani.xodus.nq.generator

import com.squareup.kotlinpoet.*

fun main(args: Array<String>) {
    val greeterClass = ClassName("", "Greeter")
    val file = FileSpec.builder("", "HelloWorld")
        .indent("    ")
        .addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S, %S, %S", "NOTHING_TO_INLINE", "unused", "RedundantVisibilityModifier")
                .build()
        )
        .addProperty(
            PropertySpec.builder("entityType", String::class)
                .addModifiers(KModifier.PRIVATE, KModifier.CONST)
                .initializer("%S", "TestEntity")
                .build()
        )
        .addType(
            entityBuilder(
                "TestEntity",
                listOf(
                    TypeInfo("name", Type.StringType),
                    TypeInfo("uid", Type.StringType),
                    TypeInfo("email", Type.StringType),
                    TypeInfo("age", Type.IntType),
                    TypeInfo("gender", Type.StringType, true),
                    TypeInfo("bio", Type.BlobString, true)
                )
            ).build()
        )
        .build()


    file.writeTo(System.out)
}

