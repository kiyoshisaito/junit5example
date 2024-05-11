@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.example.junit5sample

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("ClassName")
@N("fizzBuzz JUnit5 Specスタイル")
// @formatter:off
class ExampleUnitTest_Nested_Spec {
    private lateinit var actor: Calculator
    private lateinit var repository: Repository

    private var arg = 0
    private var stubResult: Result? = null
    private var stubResult2: Result2? = null
    private lateinit var actual: FizzBuzzResult

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        actor = Calculator(
            repository = repository
        )
    }

    @Ne@N("fizzBuzz 副作用テストあり")
    inner class `fizz buzz` {
        private fun t(assert: () -> Unit) {
            // arrange
            every { repository.fetch1() } returns stubResult
            every { repository.fetch2() } returns stubResult2
            // act
            actual = actor.fizzBuzz(arg)
            assert()
        }

        @Ne@N("引数が 3 の倍数の場合") inner class `can divide 3` {
            @BeforeEach fun setup() {
                arg = 3
                t {}
            }
            // 主作用のテスト
            @T@N("fizz が表示") fun `fizz is o`() { actual.fizz shouldBe true }
            @T@N("buzz が非表示") fun `buzz is x`() { actual.buzz shouldBe false }
            @T@N("その他 が非表示") fun `else value is x`() { actual.elseValue shouldBe "" }
            // 副作用のテスト
            @T@N("取得1が実行されない") fun `did not fetch1`() { notExecute { repository.fetch1() } }
            @T@N("取得2が実行されない") fun `did not fetch2`() { notExecute { repository.fetch2() } }
        }

        @Ne@N("引数が 5 の倍数の場合") inner class `can divide 5` {
            @BeforeEach fun setup() {
                arg = 5
                t {}
            }
            @T@N("fizz が非表示") fun `fizz is x`() { actual.fizz shouldBe false }
            @T@N("buzz が表示") fun `buzz is o`() { actual.buzz shouldBe true }
            @T@N("その他 が非表示") fun `else value is x`() { actual.elseValue shouldBe "" }
            @T@N("取得1が実行されない") fun `did not fetch1`() { notExecute { repository.fetch1() } }
            @T@N("取得2が実行されない") fun `did not fetch2`() { notExecute { repository.fetch2() } }
        }

        @Ne@N("引数が 3 と 5 の倍数の場合") inner class `can divide 3 and 5` {
            @BeforeEach fun setup() {
                arg = 15
                t {}
            }
            @T@N("fizz が表示") fun `fizz is o`() { actual.fizz shouldBe true }
            @T@N("buzz が表示") fun `buzz is o`() { actual.buzz shouldBe true }
            @T@N("その他 が非表示") fun `else value is x`() { assertEquals("", actual.elseValue) }
            @T@N("取得1が実行されない") fun `did not fetch1`() { notExecute { repository.fetch1() } }
            @T@N("取得2が実行されない") fun `did not fetch2`() { notExecute { repository.fetch2() } }
        }

        @Ne@N("引数が 3 の倍数でも 5 の倍数でもない場合") inner class `can not divide 3 or 5` {
            @BeforeEach fun setup() {
                arg = 4
            }
            @T@N("fizz が非表示") fun `fizz is o`() { t { actual.fizz shouldBe false } }
            @T@N("buzz が非表示") fun `buzz is o`() { t { actual.buzz shouldBe false } }
            @T@N("取得1が実行される") fun `did fetch1`() { t { execute { repository.fetch1() } } }

            @Ne@N("取得1のタイプが A の場合") inner class `fetch1 type is A` {
                @BeforeEach fun setup() {
                    stubResult = Result("A")
                    t {}
                }
                @T@N("その他 が AA で表示") fun `else value is AA`() { actual.elseValue shouldBe "AA" }
                @T@N("取得2が実行されない") fun `did not fetch2`() { notExecute { repository.fetch2() } }
            }

            @Ne@N("取得1のタイプが B の場合") inner class `fetch1 type is B` {
                @BeforeEach fun setup() {
                    stubResult = Result("B")
                }
                @T@N("取得2が実行される") fun `did fetch repository 2`() { t { execute { repository.fetch2() } } }
                @Ne@N("取得2のタイプが X の場合") inner class `fetch2 type is X` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("X")
                    }
                    @T@N("その他 が XX で表示") fun `else value is XX`() { t { actual.elseValue shouldBe "XX" } }
                }
                @Ne@N("取得2のタイプが Y の場合") inner class `fetch2 type is Y` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("Y")
                    }
                    @T@N("その他 が YY で表示") fun `else value is YY`() { t { actual.elseValue shouldBe "YY" } }
                }
                @Ne@N("取得2のタイプが X でも Y でもない場合") inner class `fetch2 type is not X, Y` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("Z")
                    }
                    @T@N("その他 が BB で表示") fun `else value is BB`() { t { actual.elseValue shouldBe "BB" } }
                }
            }

            @Ne@N("取得1のタイプが A でも B でもない場合") inner class `fetch1 type is C` {
                @BeforeEach fun setup() {
                    every { repository.fetch1() } returns Result("C", "name")
                    t {}
                }
                @T@N("その他 が CC で表示") fun `else value is CC`() { actual.elseValue shouldBe "CC" }
                @T@N("取得2が実行されない") fun `did not fetch2`() { notExecute { repository.fetch2() } }
            }
        }
    }

    @Ne@N("fizzBuzz 副作用テストなし") inner class `fizz buzz non effect` {
        private fun t(assert: () -> Unit) {
            // arrange
            every { repository.fetch1() } returns stubResult
            every { repository.fetch2() } returns stubResult2
            // act
            actual = actor.fizzBuzz(arg)
            assert()
        }

        @Ne@N("引数が 3 の倍数の場合") inner class `can divide 3` {
            @BeforeEach fun setup() {
                arg = 3
                t {}
            }
            // 主作用のテスト
            @T@N("fizz が表示") fun `fizz is o`() { actual.fizz shouldBe true }
            @T@N("buzz が非表示") fun `buzz is x`() { actual.buzz shouldBe false }
            @T@N("その他 が非表示") fun `else value is x`() { actual.elseValue shouldBe "" }
        }

        @Ne@N("引数が 5 の倍数の場合") inner class `can divide 5` {
            @BeforeEach fun setup() {
                arg = 5
                t {}
            }
            @T@N("fizz が非表示") fun `fizz is x`() { actual.fizz shouldBe false }
            @T@N("buzz が表示") fun `buzz is o`() { actual.buzz shouldBe true }
            @T@N("その他 が非表示") fun `else value is x`() { actual.elseValue shouldBe "" }
        }

        @Ne@N("引数が 3 と 5 の倍数の場合") inner class `can divide 3 and 5` {
            @BeforeEach fun setup() {
                arg = 15
                t {}
            }
            @T@N("fizz が表示") fun `fizz is o`() { actual.fizz shouldBe true }
            @T@N("buzz が表示") fun `buzz is o`() { actual.buzz shouldBe true  }
            @T@N("その他 が非表示") fun `else value is x`() { actual.elseValue shouldBe "" }
        }

        @Ne@N("引数が 3 の倍数でも 5 の倍数でもない場合") inner class `can not divide 3 or 5` {
            @BeforeEach fun setup() {
                arg = 4
            }
            @T@N("fizz が非表示") fun `fizz is o`() { t { actual.fizz shouldBe false } }
            @T@N("buzz が非表示") fun `buzz is o`() { t { actual.buzz shouldBe false } }

            @Ne@N("取得1のタイプが A の場合") inner class `fetch1 type is A` {
                @BeforeEach fun setup() {
                    stubResult = Result("A")
                    t {}
                }
                @T@N("その他 が AA で表示") fun `else value is AA`()
                { assertEquals("AA", actual.elseValue) }
            }

            @Ne@N("取得1のタイプが B の場合") inner class `fetch1 type is B` {
                @BeforeEach fun setup() {
                    stubResult = Result("B")
                }
                @Ne@N("取得2のタイプが X の場合") inner class `fetch2 type is X` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("X")
                    }
                    @T@N("その他 が XX で表示") fun `else value is XX`()
                    { t { actual.elseValue shouldBe "XX" } }
                }
                @Ne@N("取得2のタイプが Y の場合") inner class `fetch2 type is Y` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("Y")
                    }
                    @T@N("その他 が YY で表示") fun `else value is YY`()
                    { t { actual.elseValue shouldBe "YY" } }
                }
                @Ne@N("取得2のタイプが A でも B でもない場合") inner class `fetch2 type is not A, B` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("Z")
                    }
                    @T@N("その他 が BB で表示") fun `else value is BB`()
                    { t { actual.elseValue shouldBe "BB" } }
                }
            }

            @Ne@N("取得1のタイプが A でも B でもない場合") inner class `fetch1 type is not A, B` {
                @BeforeEach fun setup() {
                    every { repository.fetch1() } returns Result("C", "name")
                }
                @T@N("その他 が CC で表示") fun `else value is CC`()
                { t { actual.elseValue shouldBe "CC" } }
            }
        }
    }
}
