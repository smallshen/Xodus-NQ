package club.eridani.xodus.nq.generator

inline fun <T : Any?> T.applyIf(condition: Boolean, block: T.() -> Unit): T {
    if (condition) {
        block()
    }
    return this
}