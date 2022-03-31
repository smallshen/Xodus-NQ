package club.eridani.xodus.nq.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName

data class TypeInfo(
    val name: String,
    val type: Type,
    val nullable: Boolean = false
) {
    val typeName = type.className.copy(nullable)

    operator fun component4() = typeName
}

sealed class Type(val className: TypeName) {
    object StringType : Type(String::class.asClassName())
    object BlobString : Type(String::class.asClassName().copy(nullable = true))
    object IntType : Type(Int::class.asClassName())
    object LongType : Type(Long::class.asClassName())
    object FloatType : Type(Float::class.asClassName())
    object DoubleType : Type(Double::class.asClassName())
    object BooleanType : Type(Boolean::class.asClassName())
    class OtherObjectType(packageName: String, className: String) : Type(ClassName(packageName, className))
}

