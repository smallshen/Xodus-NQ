package club.eridani.xodus.nq.generator

import com.squareup.kotlinpoet.*

@OptIn(DelicateKotlinPoetApi::class)
fun entityBuilder(entityName: String, fields: List<TypeInfo>): TypeSpec.Builder {
    return TypeSpec
        .classBuilder(entityName)
        .addAnnotation(JvmInline::class.java)
        .addModifiers(KModifier.VALUE)
        .addSuperinterface(queryEntity)
        .primaryConstructor(
            FunSpec
                .constructorBuilder()
                .addParameter("entity", xodusEntity)
                .build()
        )
        .addProperty(
            PropertySpec
                .builder("entity", xodusEntity)
                .initializer("entity")
                .build()
        )
        .addProperty(
            PropertySpec
                .builder("entityType", String::class)
                .addModifiers(KModifier.OVERRIDE)
                .getter(
                    FunSpec.getterBuilder()
                        .addStatement("return %S", entityName)
                        .build()
                )
                .build()
        )
        .apply {
            fields.forEach { (name, t, nullable, type) ->
                when (t) {
                    Type.BlobString -> {
                        addProperty(
                            PropertySpec
                                .builder(name, type)
                                .addAnnotation(blobString)
                                .mutable()
                                .getter(
                                    FunSpec.getterBuilder()
                                        .addStatement("return entity.getBlobString(%S)", name)
                                        .build()
                                )
                                .setter(
                                    FunSpec.setterBuilder()
                                        .addParameter("v", type)
                                        .addStatement(
                                            "if (v == null) entity.deleteBlob(%S) else entity.setBlobString(%S, v)",
                                            name,
                                            name
                                        )
                                        .build()
                                )
                                .build()
                        )
                    }
                    else -> {
                        addProperty(
                            PropertySpec
                                .builder(name, type)
                                .mutable()
                                .getter(
                                    FunSpec.getterBuilder()
                                        .addStatement("return entity.getProperty(%S) as %T", name, type)
                                        .build()
                                )
                                .setter(
                                    FunSpec.setterBuilder()
                                        .addParameter("v", type)
                                        .apply {
                                            if (nullable) {
                                                addStatement(
                                                    "if (v == null) entity.deleteProperty(%S) else entity.setProperty(%S, v)",
                                                    name,
                                                    name
                                                )
                                            } else {
                                                addStatement(
                                                    "entity.setProperty(%S, v)",
                                                    name
                                                )
                                            }
                                        }
                                        .build()
                                )
                                .build()
                        )
                    }
                }

            }
        }
        .addType(companionBuilder(entityName, fields).build())
        .addType(
            TypeSpec.objectBuilder("Query")
                .addSuperinterface(queryScope)
                .addProperty(
                    PropertySpec.builder("entityType", String::class)
                        .addModifiers(KModifier.OVERRIDE)
                        .initializer("%S", entityName)
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
        .addType(iteratorBuilder(entityName))
        .addType(iterableBuilder(entityName))


}