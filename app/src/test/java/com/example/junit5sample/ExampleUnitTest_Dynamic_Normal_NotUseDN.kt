@file:Suppress("NonAsciiCharacters", "TestFunctionName", "RemoveRedundantBackticks")

package com.example.junit5sample

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicNode

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("ClassName")
// @formatter:off
class `fizzBuzz dynamic test JUnit5標準スタイル DN未使用` {
    private lateinit var actor: Calculator
    private lateinit var repository: Repository

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        actor = Calculator(
            repository = repository
        )
    }

    private var arg = 0
    private lateinit var actual: FizzBuzzResult
    private fun t() {
        actual = actor.fizzBuzz(arg)
    }
    @TF fun `引数が 3 の倍数の場合`(): List<DynamicNode> {
        arg = 3
        t()
        return listOf(
            "fizz が表示" { assertTrue(actual.fizz) },
            "buzz が非表示" { assertFalse(actual.buzz) },
            "その他 が非表示" { assertEquals("", actual.elseValue) },
            "リポジトリの取得1がされない" { verify(exactly = 0) { repository.fetch1() } },
            "リポジトリの取得2がされない" { verify(exactly = 0) { repository.fetch2() } },
        )
    }
    @TF fun `引数が 5 の倍数の場合`(): List<DynamicNode> {
        arg = 5
        t()
        return listOf(
            "fizz が非表示" { assertFalse(actual.fizz) },
            "buzz が表示" { assertTrue(actual.buzz) },
            "その他 が非表示" { assertEquals("", actual.elseValue) },
            "リポジトリの取得1がされない" { verify(exactly = 0) { repository.fetch1() } },
            "リポジトリの取得2がされない" { verify(exactly = 0) { repository.fetch2() } },
        )
    }
    @TF fun `引数が 3 と 5 の倍数の場合`(): List<DynamicNode> {
        arg = 15
        t()
        return listOf(
            "fizz が表示" { assertTrue(actual.fizz) },
            "buzz が表示" { assertTrue(actual.buzz) },
            "その他 が非表示" { assertEquals("", actual.elseValue) },
            "リポジトリ1の取得1がされない" { verify(exactly = 0) { repository.fetch1() } },
            "リポジトリの取得2がされない" { verify(exactly = 0) { repository.fetch2() } },
        )
    }
    @Ne  inner class `引数が 3の倍数でも 5の倍数でもない場合` {
        @BeforeEach fun setup() {
            arg = 4
        }
        @TF fun `共通仕様`(): List<DynamicNode> {
            t()
            return listOf(
                "fizz が非表示" { assertFalse(actual.fizz) },
                "buzz が非表示" { assertFalse(actual.buzz) },
                "リポジトリの取得1がされる" { verify(exactly = 1) { repository.fetch1() } },
            )
        }
        @TF fun `取得1のタイプが A の場合`(): List<DynamicNode> {
            every { repository.fetch1() } returns Result("A")
            t()
            return listOf(
                "その他 が AA で表示" { assertEquals("AA", actual.elseValue) },
                "リポジトリの取得2がされない" { verify(exactly = 0) { repository.fetch2() } },
            )
        }
        @Ne inner class `取得1のタイプが B の場合` {
            @BeforeEach fun setup() {
                every { repository.fetch1() } returns Result("B")
            }
            @T fun `取得2が実行される`() {
                t()
                verify(exactly = 1) { repository.fetch2() }
            }
            @TF fun `取得2のタイプが X の場合`(): DynamicNode {
                every { repository.fetch2() } returns Result2("X")
                t()
                return "その他 が XX で表示" { assertEquals("XX", actual.elseValue) }
            }
            @TF fun `取得2のタイプが Y の場合`(): DynamicNode {
                every { repository.fetch2() } returns Result2("Y")
                t()
                return "その他 が YY で表示" { assertEquals("YY", actual.elseValue) }
            }
            @TF fun `取得2のタイプが A でも B でもない場合`(): DynamicNode {
                every { repository.fetch2() } returns Result2("Z")
                t()
                return "その他 が BB で表示" { assertEquals("BB", actual.elseValue) }
            }
        }
        @TF fun `取得1のタイプが A でも B でもない場合`(): List<DynamicNode> {
            every { repository.fetch1() } returns Result("C")
            t()
            return listOf(
                "その他 が CC で表示" { assertEquals("CC", actual.elseValue) },
                "リポジトリの取得2がされない" { verify(exactly = 0) { repository.fetch2() } },
            )
        }
    }
}
