@file:Suppress("NonAsciiCharacters", "TestFunctionName", "RemoveRedundantBackticks")

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
@N("fizzBuzz JUnit5 標準スタイル")
// @formatter:off
class `fizzBuzz JUnit5 標準スタイル DN未使用` {
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

    @Ne inner class `fizzBuzz 副作用テストあり` {
        @Ne inner class `引数が 3 の倍数の場合` {
            @BeforeEach fun setup() {
                arg = 3
                t {}
            }
            // 主作用のテスト
            @T fun `fizz が表示`() { assertTrue(actual.fizz) }
            @T fun `buzz が非表示`() { assertFalse(actual.buzz) }
            @T fun `その他 が非表示`() { assertEquals("", actual.elseValue) }
            // 副作用のテスト
            @T fun `取得1が実行されない`() { verify(exactly = 0) { repository.fetch1() } }
            @T fun `取得2が実行されない`() { verify(exactly = 0) { repository.fetch2() } }
        }

        @Ne inner class `引数が 5 の倍数の場合` {
            @BeforeEach fun setup() {
                arg = 5
                t {}
            }
            @T fun `fizz が非表示`() { assertFalse(actual.fizz) }
            @T fun `buzz が表示`() { assertTrue(actual.buzz) }
            @T fun `その他 が非表示`() { assertEquals("", actual.elseValue) }
            @T fun `取得1が実行されない`() { verify(exactly = 0) { repository.fetch1() } }
            @T@N("取得2が実行されない") fun `did not fetch2`() { verify(exactly = 0) { repository.fetch2() } }
        }

        @Ne inner class `引数が 3 と 5 の倍数の場合` {
            @BeforeEach fun setup() {
                arg = 15
                t {}
            }
            @T fun `fizz が表示`() { assertTrue(actual.fizz) }
            @T fun `buzz が表示`() { assertTrue(actual.buzz) }
            @T fun `その他 が非表示`() { assertEquals("", actual.elseValue) }
            @T fun `取得1が実行されない`() { verify(exactly = 0) { repository.fetch1() } }
            @T fun `取得2が実行されない`() { verify(exactly = 0) { repository.fetch2() } }
        }

        @Ne@N("引数が 3 の倍数でも 5 の倍数でもない場合") inner class `can not divide 3 or 5` {
            @BeforeEach fun setup() {
                arg = 4
            }
            @T fun `fizz が非表示`() { t { assertFalse(actual.fizz) } }
            @T fun `buzz が非表示`() { t { assertFalse(actual.buzz) } }
            @T fun `取得1が実行される`() { t { verify(exactly = 1) { repository.fetch1() } } }

            @Ne inner class `取得1のタイプが A の場合` {
                @BeforeEach fun setup() {
                    stubResult = Result("A")
                }
                @T fun `その他 が AA で表示`() { t { assertEquals("AA", actual.elseValue) } }
                @T fun `取得2が実行されない`() { t { verify(exactly = 0) { repository.fetch2() } } }
            }

            @Ne inner class `取得1のタイプが B の場合` {
                @BeforeEach fun setup() {
                    stubResult = Result("B")
                }
                @T fun `取得2が実行される`() { t { verify(exactly = 1) { repository.fetch2() } } }
                @Ne inner class `取得2のタイプが X の場合` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("X")
                    }
                    @T fun `その他 が XX で表示`() { t { assertEquals("XX", actual.elseValue) } }
                }
                @Ne inner class `取得2のタイプが Y の場合` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("Y")
                    }
                    @T fun `その他 が YY で表示`() { t { assertEquals("YY", actual.elseValue) } }
                }
                @Ne inner class `取得2のタイプが A でも B でもない場合` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("Z")
                    }
                    @T fun `その他 が BB で表示`() { t { assertEquals("BB", actual.elseValue) } }
                }
            }

            @Ne inner class `取得1のタイプが A でも B でもない場合` {
                @BeforeEach fun setup() {
                    every { repository.fetch1() } returns Result("C", "name")
                    t {}
                }
                @T fun `その他 が CC で表示`() { assertEquals("CC", actual.elseValue) }
                @T fun `取得2が実行されない`() { verify(exactly = 0) { repository.fetch2() } }
            }
        }
    }

    @Ne inner class `fizzBuzz 副作用テストなし` {
        @Ne inner class `引数が 3 の倍数の場合` {
            @BeforeEach fun setup() {
                arg = 3
                t {}
            }
            // 主作用のテスト
            @T fun `fizz が表示`() { assertTrue(actual.fizz) }
            @T fun `buzz が非表示`() { assertFalse(actual.buzz) }
            @T fun `その他 が非表示`() { assertEquals("", actual.elseValue) }
        }

        @Ne inner class `引数が 5 の倍数の場合` {
            @BeforeEach fun setup() {
                arg = 5
                t {}
            }
            @T fun `fizz が非表示`() { assertFalse(actual.fizz) }
            @T fun `buzz が表示`() { assertTrue(actual.buzz) }
            @T fun `その他 が非表示`() { assertEquals("", actual.elseValue) }
        }

        @Ne inner class `引数が 3 と 5 の倍数の場合` {
            @BeforeEach fun setup() {
                arg = 15
                t {}
            }
            @T fun `fizz が表示`() { assertTrue(actual.fizz) }
            @T fun `buzz が表示`() { assertTrue(actual.buzz) }
            @T fun `その他 が非表示`() { assertEquals("", actual.elseValue) }
        }

        @Ne inner class `引数が 3 の倍数でも 5 の倍数でもない場合` {
            @BeforeEach fun setup() {
                arg = 4
            }
            @T fun `fizz が非表示`() { t { assertFalse(actual.fizz) } }
            @T fun `buzz が非表示`() { t { assertFalse(actual.buzz) } }

            @Ne inner class `取得1のタイプが A の場合` {
                @BeforeEach fun setup() {
                    stubResult = Result("A")
                    t {}
                }
                @T fun `その他 が AA で表示`() { assertEquals("AA", actual.elseValue) }
            }

            @Ne inner class `取得1のタイプが B の場合` {
                @BeforeEach fun setup() {
                    stubResult = Result("B")
                }
                @Ne inner class `取得2のタイプが X の場合` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("X")
                    }
                    @T fun `その他 が XX で表示`() { t { assertEquals("XX", actual.elseValue) } }
                }
                @Ne inner class `取得2のタイプが Y の場合` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("Y")
                    }
                    @T fun `その他 が YY で表示`() { t { assertEquals("YY", actual.elseValue) } }
                }
                @Ne inner class `取得2のタイプが X でも Y でもない場合` {
                    @BeforeEach fun setup() {
                        stubResult2 = Result2("Z")
                    }
                    @T fun `その他 が BB で表示`() { t { assertEquals("BB", actual.elseValue) } }
                }
            }

            @Ne inner class `取得1のタイプが A でも B でもない場合` {
                @BeforeEach fun setup() {
                    every { repository.fetch1() } returns Result("C", "name")
                }
                @T fun `その他 が CC で表示`()
                { t { assertEquals("CC", actual.elseValue) } }
            }
        }
    }
}
