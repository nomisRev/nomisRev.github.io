// This file was automatically generated from 2021-11-28-continuation-monad-in-kotlin.md by Knit tool. Do not edit.
package example.exampleReadme03

import arrow.*
import arrow.core.*
import arrow.fx.coroutines.*
import kotlinx.coroutines.*
import io.kotest.matchers.collections.*
import io.kotest.assertions.*
import io.kotest.matchers.*
import io.kotest.matchers.types.*
import kotlin.coroutines.cancellation.CancellationException
import io.kotest.property.*
import io.kotest.property.arbitrary.*
import arrow.core.test.generators.*

val failed: Cont<String, Int> =
  cont { shift("failed") }

val resolved: Cont<Nothing, Int> =
  failed.handleError { it.length }

val newError: Cont<List<Char>, Int> =
  failed.handleErrorWith { str ->
    cont { shift(str.reversed().toList()) }
  }

val redeemed: Cont<Nothing, Int> =
  failed.redeem({ str -> str.length }, ::identity)

val captured: Cont<String, Result<Int>> = cont<String, Int> {
  1
}.attempt()

suspend fun test() {
  failed.toEither() shouldBe Either.Left("failed")
  resolved.toEither() shouldBe Either.Right(6)
  newError.toEither() shouldBe Either.Left(listOf('d', 'e', 'l', 'i', 'a', 'f'))
  redeemed.toEither() shouldBe Either.Right(6)
  captured.toEither() shouldBe Either.Right(Result.success(1))
}
