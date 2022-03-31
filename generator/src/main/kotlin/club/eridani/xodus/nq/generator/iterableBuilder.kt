package club.eridani.xodus.nq.generator

import club.eridani.xodus.nq.generator.iterable.iterableFields
import club.eridani.xodus.nq.generator.iterable.iterableMethod
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

fun iterableBuilder(entityName: String): TypeSpec {
    val entityType = ClassName("", entityName)
    return TypeSpec.classBuilder(localIterable)
        .addModifiers(KModifier.VALUE)
        .addAnnotation(JvmInline::class)
        .addSuperinterface(ITERABLE.parameterizedBy(entityType))
        .primaryConstructor(
            FunSpec.constructorBuilder()
                .addParameter("entityIterable", entityIterable)
                .build()
        )
        .addProperty(
            PropertySpec.builder("entityIterable", entityIterable)
                .initializer("entityIterable")
                .build()
        )
        .iterableFields()
        .iterableMethod(entityType)
        .build()
}


