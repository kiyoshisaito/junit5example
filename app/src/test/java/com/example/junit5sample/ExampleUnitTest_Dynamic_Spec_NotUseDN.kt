@file:Suppress("NonAsciiCharacters", "TestFunctionName", "RemoveRedundantBackticks")

package com.example.junit5sample

import io.mockk.every
import io.mockk.mockk
import net.bytebuddy.asm.Advice.This
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicNode

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("ClassName")
// @formatter:off
class `fizzBuzz dynamic test Specスタイル DN未使用` {
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
    private fun t(assert: () -> Unit) {
        actual = actor.fizzBuzz(arg)
        assert()
    }
    @TF fun `引数が 3 の倍数の場合`(): List<DynamicNode> {
        arg = 3
        t {}
        return listOf(
            "fizz が表示" { actual.fizz shouldBe true },
            "buzz が非表示" { actual.buzz shouldBe false },
            "その他 が非表示" { actual.elseValue shouldBe "" },
            "リポジトリの取得1がされない" { shouldNot { repository.fetch1() } },
            "リポジトリの取得2がされない" { shouldNot { repository.fetch2() } },
        )
    }
    @TF fun `引数が 5 の倍数の場合`(): List<DynamicNode> {
        arg = 5
        t {}
        return listOf(
            "fizz が非表示" { actual.fizz shouldBe false },
            "buzz が表示" { actual.buzz shouldBe true },
            "その他 が非表示" { actual.elseValue shouldBe "" },
            "リポジトリの取得1がされない" { shouldNot { repository.fetch1() } },
            "リポジトリの取得2がされない" { shouldNot { repository.fetch2() } },
        )
    }
    @TF fun `引数が 3 と 5 の倍数の場合`(): List<DynamicNode> {
        arg = 15
        t {}
        return listOf(
            "fizz が表示" { actual.fizz shouldBe true },
            "buzz が表示" { actual.buzz shouldBe true },
            "その他 が非表示" { actual.elseValue shouldBe "" },
            "リポジトリ1の取得1がされない" { shouldNot { repository.fetch1() } },
            "リポジトリの取得2がされない" { shouldNot { repository.fetch2() } },
        )
    }
    @Ne inner class `引数が 3の倍数でも 5の倍数でもない場合` {
        @BeforeEach fun setup() {
            arg = 4
        }
        @TF fun `共通仕様`(): List<DynamicNode> {
            t {}
            return listOf(
                "fizz が非表示" { actual.fizz shouldBe false },
                "buzz が非表示" { actual.buzz shouldBe false },
                "リポジトリの取得1がされる" { should { repository.fetch1() } },
            )
        }
        @TF fun `取得1のタイプが A の場合`(): List<DynamicNode> {
            every { repository.fetch1() } returns Result("A")
            t {}
            return listOf(
                "その他 が AA で表示" { actual.elseValue shouldBe "AA" },
                "リポジトリの取得2がされない" { shouldNot { repository.fetch2() } },
            )
        }
        @Ne inner class `取得1のタイプが B の場合` {
            @BeforeEach fun setup() {
                every { repository.fetch1() } returns Result("B")
            }
            @T fun `取得2が実行される`() { t { should { repository.fetch2() } } }
            @TF fun `取得2のタイプが X の場合`(): DynamicNode {
                every { repository.fetch2() } returns Result2("X")
                t {}
                return "その他 が XX で表示" { actual.elseValue shouldBe "XX" }
            }
            @TF fun `取得2のタイプが Y の場合`(): DynamicNode {
                every { repository.fetch2() } returns Result2("Y")
                t {}
                return "その他 が YY で表示" { actual.elseValue shouldBe "YY" }
            }
            @TF fun `取得2のタイプが A でも B でもない場合`(): DynamicNode {
                every { repository.fetch2() } returns Result2("Z")
                t {}
                return "その他 が BB で表示" { actual.elseValue shouldBe "BB" }
            }
        }
        @TF fun `取得1のタイプが A でも B でもない場合`(): List<DynamicNode> {
            every { repository.fetch1() } returns Result("C")
            t {}
            return listOf(
                "その他 が CC で表示" { actual.elseValue shouldBe "CC" },
                "リポジトリの取得2がされない" { shouldNot { repository.fetch2() } },
            )
        }
    }
}

