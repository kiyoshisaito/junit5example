@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.example.junit5sample

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicNode

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("ClassName")
@DisplayName("fizzBuzz dynamic test Specスタイル")
// @formatter:off
class ExampleUnitTest_Dynamic_Spec {
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
    @TF@N("引数が 3 の倍数の場合") fun `can divide 3`(): List<DynamicNode> {
        arg = 3
        t()
        return listOf(
            "fizz が表示" { actual.fizz shouldBe true },
            "buzz が非表示" { actual.buzz shouldBe false },
            "その他 が非表示" { actual.elseValue shouldBe "" },
            "リポジトリの取得1がされない" { notExecute { repository.fetch1() } },
            "リポジトリの取得2がされない" { notExecute { repository.fetch2() } },
        )
    }
    @TF@N("引数が 5 の倍数の場合") fun `can divide 5`(): List<DynamicNode> {
        arg = 5
        t()
        return listOf(
            "fizz が非表示" { actual.fizz shouldBe false },
            "buzz が表示" { actual.buzz shouldBe true },
            "その他 が非表示" { actual.elseValue shouldBe "" },
            "リポジトリの取得1がされない" { notExecute { repository.fetch1() } },
            "リポジトリの取得2がされない" { notExecute { repository.fetch2() } },
        )
    }
    @TF@N("引数が 3 と 5 の倍数の場合") fun `can divide 3, 5 `(): List<DynamicNode> {
        arg = 15
        t()
        return listOf(
            "fizz が表示" { actual.fizz shouldBe true },
            "buzz が表示" { actual.buzz shouldBe true },
            "その他 が非表示" { actual.elseValue shouldBe "" },
            "リポジトリ1の取得1がされない" { notExecute { repository.fetch1() } },
            "リポジトリの取得2がされない" { notExecute { repository.fetch2() } },
        )
    }
    @Ne @N("引数が 3の倍数でも 5の倍数でもない場合")
    inner class `can not divide 3, 5` {
        @BeforeEach fun setup() {
            arg = 4
        }
        @TF@N("共通仕様") fun `can not divide 3, 5 common`(): List<DynamicNode> {
            t()
            return listOf(
                "fizz が非表示" { actual.fizz shouldBe false },
                "buzz が非表示" { actual.buzz shouldBe false },
                "リポジトリの取得1がされる" { execute { repository.fetch1() } },
            )
        }
        @TF@N("取得1のタイプが A の場合") fun `fetch1 type is A`(): List<DynamicNode> {
            every { repository.fetch1() } returns Result("A")
            t()
            return listOf(
                "その他 が AA で表示" { actual.elseValue shouldBe "AA" },
                "リポジトリの取得2がされない" { notExecute { repository.fetch2() } },
            )
        }
        @Ne@N("取得1のタイプが B の場合") inner class `fetch1 type is B` {
            @BeforeEach fun setup() {
                every { repository.fetch1() } returns Result("B")
            }
            @T@N("取得2が実行される") fun `fetch2 is executed`() {
                t()
                execute { repository.fetch2() }
            }
            @TF@N("取得2のタイプが X の場合") fun `fetch2 type is X`(): DynamicNode {
                every { repository.fetch2() } returns Result2("X")
                t()
                return "その他 が XX で表示" { actual.elseValue shouldBe "XX" }
            }
            @TF@N("取得2のタイプが Y の場合") fun `fetch2 type is Y`(): DynamicNode {
                every { repository.fetch2() } returns Result2("Y")
                t()
                return "その他 が YY で表示" { actual.elseValue shouldBe "YY" }
            }
            @TF@N("取得2のタイプが A でも B でもない場合") fun `fetch2 type is not X, Y`(): DynamicNode {
                every { repository.fetch2() } returns Result2("Z")
                t()
                return "その他 が BB で表示" { actual.elseValue shouldBe "BB" }
            }
        }
        @TF@N("取得1のタイプが A でも B でもない場合") fun `fetch type is not A, B`(): List<DynamicNode> {
            every { repository.fetch1() } returns Result("C")
            t()
            return listOf(
                "その他 が CC で表示" { actual.elseValue shouldBe "CC" },
                "リポジトリの取得2がされない" { notExecute { repository.fetch2() } },
            )
        }
    }
}
