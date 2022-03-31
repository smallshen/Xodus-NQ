package club.eridani.xodus.nq.generator

import com.squareup.kotlinpoet.*

fun companionBuilder(entityName: String, fields: List<TypeInfo>): TypeSpec.Builder {
    val entityType = ClassName("", entityName)
    return TypeSpec.companionObjectBuilder()
        .addFunction(
            FunSpec.builder("invoke")
                .addContextReceiver(storeTransaction)
                .addAnnotation(JvmStatic::class)
                .addModifiers(KModifier.OPERATOR)
                .apply {
                    fields.forEach { (name, t, _, type) ->
                        val spec = ParameterSpec.builder(name, type)
                        if (t == Type.BlobString) spec.addAnnotation(blobString)
                        addParameter(spec.build())
                    }
                }
                .returns(entityType)
                .apply {
                    beginControlFlow("return %T(newEntity(entityType)).apply ", entityType)
                    fields.forEach { (name, _, _, _) ->
                        addStatement("this.%N = %N", name, name)
                    }
                    endControlFlow()
                }
                .build()
        )
        .addType(
            TypeSpec.objectBuilder("Query")
                .addSuperinterface(queryScope)
                .addProperty(
                    PropertySpec.builder("entityType", String::class)
                        .initializer(entityName)
                        .build()
                )
                .apply {
                    fields.forEach { (name, t, _, _) ->
                        if (t == Type.BlobString) return@forEach
                        addProperty(
                            PropertySpec.builder(name, property)
                                .addAnnotation(JvmStatic::class)
                                .initializer("%T(%S)", property, name)
                                .build()
                        )
                    }
                }
                .build()
        )
}