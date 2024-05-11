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
// @formatter:off
class ExampleUnitTest_Nested_Normal {
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
            every { repository.fetch() } returns stubResult
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
            @T@N("fizz が表示") fun `fizz is displayed`() { assertTrue(actual.fizz) }
            @T@N("buzz が非表示") fun `buzz is not displayed`() { assertFalse(actual.buzz) }
            @T@N("その他 が非表示") fun `else value is not displayed`() { assertEquals("", actual.elseValue) }
            // 副作用のテスト
            @T@N("リポジトリの取得1がされない") fun `did not fetch by repository`() { verify(exactly = 0) { repository.fetch() } }
            @T@N("リポジトリの取得2がされない") fun `did not fetch2 by repository`() { verify(exactly = 0) { repository.fetch2() } }
        }

        @Ne@N("引数が 5 の倍数の場合") inner class `can divide 5` {
            @BeforeEach fun setup() {
                arg = 5
                t {}
            }
            @T@N("fizz が非表示") fun `fizz is not displayed`() { assertFalse(actual.fizz) }
            @T@N("buzz が表示") fun `buzz is displayed`() { assertTrue(actual.buzz) }
            @T@N("その他 が非表示") fun `else value is not displayed`() { assertEquals("", actual.elseValue) }
            @T@N("リポジトリの取得1がされない") fun `did not fetch by repository`() { verify(exactly = 0) { repository.fetch() } }
            @T@N("リポジトリの取得2がされない") fun `did not fetch2 by repository`() { verify(exactly = 0) { repository.fetch2() } }
        }

        @Ne@N("引数が 3 と 5 の倍数の場合") inner class `can divide 3 and 5` {
            @BeforeEach fun setup() {
                arg = 15
                t {}
            }
            @T@N("fizz が表示") fun `fizz is displayed`() { assertTrue(actual.fizz) }
            @T@N("buzz が表示") fun `buzz is displayed`() { assertTrue(actual.buzz) }
            @T@N("その他 が非表示") fun `else value is not displayed`() { assertEquals("", actual.elseValue) }
            @T@N("リポジトリ1の取得1がされない") fun `did not fetch by repository`() { verify(exactly = 0) { repository.fetch() } }
            @T@N("リポジトリの取得2がされない") fun `did not fetch2 by repository`() { verify(exactly = 0) { repository.fetch2() } }
        }

        @Ne@N("引数が 3 の倍数でも 5 の倍数でもない場合") inner class `can not divide 3 or 5` {
            @BeforeEach fun setup() {
                arg = 4
            }
            @T@N("fizz が非表示") fun `fizz is displayed`() { t { assertFalse(actual.fizz) } }
            @T@N("buzz が非表示") fun `buzz is displayed`() { t { assertFalse(actual.buzz) } }
            @T@N("リポジトリの取得1がされる") fun `did fetch by repository`() { t { verify(exactly = 1) { repository.fetch() } } }

            @Ne@N("リポジトリから取得される値が A の場合") inner class `fetch result is A` {
                @BeforeEach fun setup() {
                    stubResult = Result("A")
                }
                @T@N("その他 が AA で表示") fun `else value is AA`() { t { assertEquals("AA", actual.elseValue) } }
                @T@N("リポジトリの取得2がされない") fun `did not fetch2 by repository`() { t { verify(exactly = 0) { repository.fetch2() } } }
            }

            @Ne@N("リポジトリから取得される値が B の場合") inner class `fetch result is B` {
                @BeforeEach fun setup() {
                    stubResult = Result("B")
                }
                @T@N("リポジトリの取得2がされる") fun `did fetch repository 2`() { t { verify(exactly = 1) { repository.fetch2() } } }
                @Ne@N("取得2のタイプが A の場合") inner class `fetch2 result is A` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("A")
                    }
                    @T@N("その他 が AAA で表示") fun `else value is AAA`() { t { assertEquals("AAA", actual.elseValue) } }
                }
                @Ne@N("取得2のタイプが B の場合") inner class `fetch2 result is B` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("B")
                    }
                    @T@N("その他 が BBB で表示") fun `else value is BBB`() { t { assertEquals("BBB", actual.elseValue) } }
                }
                @Ne@N("取得2のタイプが A でも B でもない場合") inner class `fetch2 result is C` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("C")
                    }
                    @T@N("その他 が BB で表示") fun `else value is BB`() { t { assertEquals("BB", actual.elseValue) } }
                }
            }

            @Ne@N("リポジトリから取得される値が A でも B でもない場合") inner class `fetch result is C` {
                @BeforeEach fun setup() {
                    every { repository.fetch() } returns Result("C", "name")
                    t {}
                }
                @T@N("その他 が CC で表示") fun `else value is CC`() { assertEquals("CC", actual.elseValue) }
                @T@N("リポジトリの取得2がされない") fun `did not fetch2 by repository`() { verify(exactly = 0) { repository.fetch2() } }
            }
        }
    }

    @Ne@N("fizzBuzz 副作用テストなし") inner class `fizz buzz non effect` {
        private fun t(assert: () -> Unit) {
            // arrange
            every { repository.fetch() } returns stubResult
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
            @T@N("fizz が表示") fun `fizz is displayed`() { assertTrue(actual.fizz) }
            @T@N("buzz が非表示") fun `buzz is not displayed`() { assertFalse(actual.buzz) }
            @T@N("その他 が非表示") fun `else value is not displayed`() { assertEquals("", actual.elseValue) }
        }

        @Ne@N("引数が 5 の倍数の場合") inner class `can divide 5` {
            @BeforeEach fun setup() {
                arg = 5
                t {}
            }
            @T@N("fizz が非表示") fun `fizz is not displayed`() { assertFalse(actual.fizz) }
            @T@N("buzz が表示") fun `buzz is displayed`() { assertTrue(actual.buzz) }
            @T@N("その他 が非表示") fun `else value is not displayed`() { assertEquals("", actual.elseValue) }
        }

        @Ne@N("引数が 3 と 5 の倍数の場合") inner class `can divide 3 and 5` {
            @BeforeEach fun setup() {
                arg = 15
                t {}
            }
            @T@N("fizz が表示") fun `fizz is displayed`() { assertTrue(actual.fizz) }
            @T@N("buzz が表示") fun `buzz is displayed`() { assertTrue(actual.buzz) }
            @T@N("その他 が非表示") fun `else value is not displayed`() { assertEquals("", actual.elseValue) }
        }

        @Ne@N("引数が 3 の倍数でも 5 の倍数でもない場合") inner class `can not divide 3 or 5` {
            @BeforeEach fun setup() {
                arg = 4
            }
            @T@N("fizz が非表示") fun `fizz is displayed`() { t { assertFalse(actual.fizz) } }
            @T@N("buzz が非表示") fun `buzz is displayed`() { t { assertFalse(actual.buzz) } }

            @Ne@N("リポジトリから取得される値が A の場合") inner class `fetch result is A` {
                @BeforeEach fun setup() {
                    stubResult = Result("A")
                    t {}
                }
                @T@N("その他 が AA で表示") fun `else value is AA`()
                { assertEquals("AA", actual.elseValue) }
            }

            @Ne@N("リポジトリから取得される値が B の場合") inner class `fetch result is B` {
                @BeforeEach fun setup() {
                    stubResult = Result("B")
                }
                @Ne@N("取得2のタイプが A の場合") inner class `fetch2 result is A` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("A")
                    }
                    @T@N("その他 が AAA で表示") fun `else value is AAA`()
                    { t { assertEquals("AAA", actual.elseValue) } }
                }
                @Ne@N("取得2のタイプが B の場合") inner class `fetch2 result is B` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("B")
                    }
                    @T@N("その他 が BBB で表示") fun `else value is BBB`()
                    { t { assertEquals("BBB", actual.elseValue) } }
                }
                @Ne@N("取得2のタイプが A でも B でもない場合") inner class `fetch2 result is C` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("C")
                    }
                    @T@N("その他 が BB で表示") fun `else value is BB`()
                    { t { assertEquals("BB", actual.elseValue) } }
                }
            }

            @Ne@N("リポジトリから取得される値が A でも B でもない場合") inner class `fetch result is C` {
                @BeforeEach fun setup() {
                    every { repository.fetch() } returns Result("C", "name")
                }
                @T@N("その他 が CC で表示") fun `else value is CC`()
                { t { assertEquals("CC", actual.elseValue) } }
            }
        }
    }
}
