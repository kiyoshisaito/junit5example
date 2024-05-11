@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.example.junit5sample

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("ClassName")
@N("fizzBuzz JUnit5 標準スタイル DN使用")
// @formatter:off
class ExampleUnitTest_Nested_Normal_UseDisplayName {
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

    private fun t(assert: () -> Unit) {
        // arrange
        every { repository.fetch1() } returns stubResult
        every { repository.fetch2() } returns stubResult2
        // act
        actual = actor.fizzBuzz(arg)
        assert()
    }

    @Ne@N("fizzBuzz 副作用テストあり")
    inner class `fizz buzz` {
        @Ne@N("引数が 3 の倍数の場合") inner class `can divide 3` {
            @BeforeEach fun setup() {
                arg = 3
                t {}
            }
            // 主作用のテスト
            @T@N("fizz が表示") fun `fizz is o`() { assertTrue(actual.fizz) }
            @T@N("buzz が非表示") fun `buzz is x`() { assertFalse(actual.buzz) }
            @T@N("その他 が非表示") fun `else value is x`() { assertEquals("", actual.elseValue) }
            // 副作用のテスト
            @T@N("取得1が実行されない") fun `did not fetch1`() { verify(exactly = 0) { repository.fetch1() } }
            @T@N("取得2が実行されない") fun `did not fetch2`() { verify(exactly = 0) { repository.fetch2() } }
        }

        @Ne@N("引数が 5 の倍数の場合") inner class `can divide 5` {
            @BeforeEach fun setup() {
                arg = 5
                t {}
            }
            @T@N("fizz が非表示") fun `fizz is x`() { assertFalse(actual.fizz) }
            @T@N("buzz が表示") fun `buzz is o`() { assertTrue(actual.buzz) }
            @T@N("その他 が非表示") fun `else value is x`() { assertEquals("", actual.elseValue) }
            @T@N("取得1が実行されない") fun `did not fetch1`() { verify(exactly = 0) { repository.fetch1() } }
            @T@N("取得2が実行されない") fun `did not fetch2`() { verify(exactly = 0) { repository.fetch2() } }
        }

        @Ne@N("引数が 3 と 5 の倍数の場合") inner class `can divide 3 and 5` {
            @BeforeEach fun setup() {
                arg = 15
                t {}
            }
            @T@N("fizz が表示") fun `fizz is o`() { assertTrue(actual.fizz) }
            @T@N("buzz が表示") fun `buzz is o`() { assertTrue(actual.buzz) }
            @T@N("その他 が非表示") fun `else value is x`() { assertEquals("", actual.elseValue) }
            @T@N("リポジトリ1の取得1が実行されない") fun `did not fetch1`() { verify(exactly = 0) { repository.fetch1() } }
            @T@N("取得2が実行されない") fun `did not fetch2`() { verify(exactly = 0) { repository.fetch2() } }
        }

        @Ne@N("引数が 3 の倍数でも 5 の倍数でもない場合") inner class `can not divide 3 or 5` {
            @BeforeEach fun setup() {
                arg = 4
            }
            @T@N("fizz が非表示") fun `fizz is x`() { t { assertFalse(actual.fizz) } }
            @T@N("buzz が非表示") fun `buzz is x`() { t { assertFalse(actual.buzz) } }
            @T@N("取得1が実行される") fun `did fetch1`() { t { verify(exactly = 1) { repository.fetch1() } } }

            @Ne@N("取得1のタイプが A の場合") inner class `fetch1 type is A` {
                @BeforeEach fun setup() {
                    stubResult = Result("A")
                }
                @T@N("その他 が AA で表示") fun `else value is AA`() { t { assertEquals("AA", actual.elseValue) } }
                @T@N("取得2が実行されない") fun `did not fetch2`() { t { verify(exactly = 0) { repository.fetch2() } } }
            }

            @Ne@N("取得1のタイプが B の場合") inner class `fetch1 type is B` {
                @BeforeEach fun setup() {
                    stubResult = Result("B")
                }
                @T@N("取得2が実行される") fun `did fetch repository 2`() { t { verify(exactly = 1) { repository.fetch2() } } }
                @Ne@N("取得2のタイプが X の場合") inner class `fetch2 result is A` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("X")
                    }
                    @T@N("その他 が XX で表示") fun `else value is XX`() { t { assertEquals("XX", actual.elseValue) } }
                }
                @Ne@N("取得2のタイプが Y の場合") inner class `fetch2 result is Y` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("Y")
                    }
                    @T@N("その他 が YY で表示") fun `else value is YY`() { t { assertEquals("YY", actual.elseValue) } }
                }
                @Ne@N("取得2のタイプが A でも B でもない場合") inner class `fetch2 result is not X, Y` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("Z")
                    }
                    @T@N("その他 が BB で表示") fun `else value is BB`() { t { assertEquals("BB", actual.elseValue) } }
                }
            }

            @Ne@N("取得1のタイプが A でも B でもない場合") inner class `fetch1 type is not A, B` {
                @BeforeEach fun setup() {
                    every { repository.fetch1() } returns Result("C", "name")
                    t {}
                }
                @T@N("その他 が CC で表示") fun `else value is CC`() { assertEquals("CC", actual.elseValue) }
                @T@N("取得2が実行されない") fun `did not fetch2`() { verify(exactly = 0) { repository.fetch2() } }
            }
        }
    }

    @Ne@N("fizzBuzz 副作用テストなし") inner class `fizz buzz non effect` {
        @Ne@N("引数が 3 の倍数の場合") inner class `can divide 3` {
            @BeforeEach fun setup() {
                arg = 3
                t {}
            }
            // 主作用のテスト
            @T@N("fizz が表示") fun `fizz is o`() { assertTrue(actual.fizz) }
            @T@N("buzz が非表示") fun `buzz is x`() { assertFalse(actual.buzz) }
            @T@N("その他 が非表示") fun `else value is x`() { assertEquals("", actual.elseValue) }
        }

        @Ne@N("引数が 5 の倍数の場合") inner class `can divide 5` {
            @BeforeEach fun setup() {
                arg = 5
                t {}
            }
            @T@N("fizz が非表示") fun `fizz is x`() { assertFalse(actual.fizz) }
            @T@N("buzz が表示") fun `buzz is o`() { assertTrue(actual.buzz) }
            @T@N("その他 が非表示") fun `else value is x`() { assertEquals("", actual.elseValue) }
        }

        @Ne@N("引数が 3 と 5 の倍数の場合") inner class `can divide 3, 5` {
            @BeforeEach fun setup() {
                arg = 15
                t {}
            }
            @T@N("fizz が表示") fun `fizz is o`() { assertTrue(actual.fizz) }
            @T@N("buzz が表示") fun `buzz is o`() { assertTrue(actual.buzz) }
            @T@N("その他 が非表示") fun `else value is x`() { assertEquals("", actual.elseValue) }
        }

        @Ne@N("引数が 3 の倍数でも 5 の倍数でもない場合") inner class `can not divide 3, 5` {
            @BeforeEach fun setup() {
                arg = 4
            }
            @T@N("fizz が非表示") fun `fizz is x`() { t { assertFalse(actual.fizz) } }
            @T@N("buzz が非表示") fun `buzz is x`() { t { assertFalse(actual.buzz) } }

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
                @Ne@N("取得2のタイプが X の場合") inner class `fetch2 result is X` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("X")
                    }
                    @T@N("その他 が XX で表示") fun `else value is XX`()
                    { t { assertEquals("XX", actual.elseValue) } }
                }
                @Ne@N("取得2のタイプが Y の場合") inner class `fetch2 result is Y` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("Y")
                    }
                    @T@N("その他 が YY で表示") fun `else value is YY`()
                    { t { assertEquals("YY", actual.elseValue) } }
                }
                @Ne@N("取得2のタイプが X でも Y でもない場合") inner class `fetch2 result is not X, Y` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("Z")
                    }
                    @T@N("その他 が BB で表示") fun `else value is BB`()
                    { t { assertEquals("BB", actual.elseValue) } }
                }
            }

            @Ne@N("取得1のタイプが A でも B でもない場合") inner class `fetch1 type is not A, B` {
                @BeforeEach fun setup() {
                    every { repository.fetch1() } returns Result("C", "name")
                }
                @T@N("その他 が CC で表示") fun `else value is CC`()
                { t { assertEquals("CC", actual.elseValue) } }
            }
        }
    }
}
