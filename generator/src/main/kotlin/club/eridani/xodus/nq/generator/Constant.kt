package club.eridani.xodus.nq.generator

import com.squareup.kotlinpoet.ClassName

val xodusEntity = ClassName("jetbrains.exodus.entitystore", "Entity")
val queryEntity = ClassName("club.eridani.xodus.nq", "QueryEntity")
val storeTransaction = ClassName("jetbrains.exodus.entitystore", "StoreTransaction")
val blobString = ClassName("club.eridani.xodus.nq.type", "BlobString")
val queryScope = ClassName("club.eridani.xodus.nq.query", "QueryScope")
val property = ClassName("club.eridani.xodus.nq.query", "Property")