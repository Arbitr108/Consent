package club.premiumit.consentlab.di.provider

import kotlin.reflect.KClass

/**
 *  Created by Pavel Gorbatiuk
 *  [https://premiumIt.club]
 **/
interface DI {
    fun <T : Any> get(class_: KClass<T>): T
    fun <T : Any> add(key: KClass<T>, object_: T) : DI
}
object DiProvider {
    lateinit var di: DI
}

inline fun <reified T : Any> consentDi(): Lazy<T> =
    lazy(LazyThreadSafetyMode.SYNCHRONIZED) { DiProvider.di.get(T::class) }
