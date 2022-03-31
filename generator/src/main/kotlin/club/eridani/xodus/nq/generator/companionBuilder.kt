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
        .addFunction(
            FunSpec.builder("query")
                .addAnnotation(JvmStatic::class.asClassName())
                .addModifiers(KModifier.INLINE)
                .addParameter("block", LambdaTypeName.get(receiver = localQuery, returnType = entityIterable))
                .returns(localIterable)
                .addStatement("return %T(block(Query))", localIterable)
                .build()
        )
        .addFunction(
            FunSpec.builder("invoke")
                .addAnnotation(JvmStatic::class.asClassName())
                .addModifiers(KModifier.INLINE)
                .addModifiers(KModifier.OPERATOR)
                .addParameter("block", LambdaTypeName.get(receiver = localQuery, returnType = entityIterable))
                .returns(localIterable)
                .addStatement("return %T(block(Query))", localIterable)
                .build()
        )

}