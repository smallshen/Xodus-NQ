package club.eridani.xodus.nq.generator

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

fun iteratorBuilder(entityName: String): TypeSpec {
    val entityType = ClassName("", entityName)
    return TypeSpec.classBuilder("Iterator")
        .addModifiers(KModifier.VALUE)
        .addAnnotation(JvmInline::class)
        .addSuperinterface(Iterator::class.asClassName().parameterizedBy(entityType))
        .primaryConstructor(
            FunSpec.constructorBuilder()
                .addParameter("entityIterator", entityIterator)
                .build()
        )
        .addProperty(
            PropertySpec.builder("entityIterator", entityIterator)
                .initializer("entityIterator")
                .build()
        )
        .addFunction(
            FunSpec.builder("hasNext")
                .addModifiers(KModifier.OVERRIDE)
                .returns(BOOLEAN)
                .addStatement("return entityIterator.hasNext()")
                .build()
        )
        .addFunction(
            FunSpec.builder("next")
                .addModifiers(KModifier.OVERRIDE)
                .returns(entityType)
                .addStatement("return %T(entityIterator.next())", entityType)
                .build()
        )
        .build()

}