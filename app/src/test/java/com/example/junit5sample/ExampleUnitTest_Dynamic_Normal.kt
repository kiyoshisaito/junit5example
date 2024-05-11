@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.example.junit5sample

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.function.Executable

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("ClassName")
@DisplayName("fizzBuzz 副作用テストあり dynamic test")
// @formatter:off
class ExampleUnitTest_Dynamic_Normal {
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
            "リポジトリの取得1がされない" { verify(exactly = 0) { repository.fetch() } },
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
            "リポジトリの取得1がされない" { verify(exactly = 0) { repository.fetch() } },
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
            "リポジトリ1の取得1がされない" { verify(exactly = 0) { repository.fetch() } },
            "リポジトリの取得2がされない" { verify(exactly = 0) { repository.fetch2() } },
        )
    }
    @Ne @N("引数が 3の倍数でも 5の倍数でもない場合")
    inner class `can not divide 3 or 5` {
        @BeforeEach
        fun setup() {
            arg = 4
        }
        @TF fun 共通仕様(): List<DynamicNode> {
            t()
            return listOf(
                "fizz が非表示" { assertFalse(actual.fizz) },
                "buzz が非表示" { assertFalse(actual.buzz) },
                "リポジトリの取得1がされる" { verify(exactly = 1) { repository.fetch() } },
            )
        }
        @TF fun `取得1のタイプが A の場合`(): List<DynamicNode> {
            every { repository.fetch() } returns Result("A")
            t()
            return listOf(
                "その他 が AA で表示" { assertEquals("AA", actual.elseValue) },
                "リポジトリの取得2がされない" { verify(exactly = 0) { repository.fetch2() } },
            )
        }
        @Ne @N("取得1のタイプが B の場合") inner class `fetch result is B` {
            @BeforeEach
            fun setup() {
                every { repository.fetch() } returns Result("B")
            }
            @TF fun `取得2のタイプが A の場合`(): DynamicNode {
                every { repository.fetch2() } returns Result2("A")
                t()
                return "その他 が AAA で表示" { assertEquals("AAA", actual.elseValue) }
            }
            @TF fun `取得2のタイプが B の場合`(): DynamicNode {
                every { repository.fetch2() } returns Result2("B")
                t()
                return "その他 が BBB で表示" { assertEquals("BBB", actual.elseValue) }
            }
            @TF fun `取得2のタイプが A でも B でもない場合`(): DynamicNode {
                every { repository.fetch2() } returns Result2("C")
                t()
                return "その他 が BB で表示" { assertEquals("BB", actual.elseValue) }
            }
        }
        @TF fun `取得1のタイプが A でも B でもない場合`(): List<DynamicNode> {
            every { repository.fetch() } returns Result("C")
            t()
            return listOf(
                "その他 が CC で表示" { assertEquals("CC", actual.elseValue) },
                "リポジトリの取得2がされない" { verify(exactly = 0) { repository.fetch2() } },
            )
        }
    }
}

private operator fun String.invoke(test: Executable): DynamicTest {
    return DynamicTest.dynamicTest(this, test)
}
