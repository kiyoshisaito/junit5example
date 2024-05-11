@file:Suppress("NonAsciiCharacters", "RemoveRedundantBackticks")

package com.example.junit5sample

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("ClassName")
// @formatter:off
class `fizzBuzz JUnit5 Parameterizedスタイル DN未使用` {
    private lateinit var actor: Calculator
    private lateinit var repository: Repository

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        actor = Calculator(
            repository = repository
        )
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Ne inner class `fizzBuzz 副作用テスト(verify)なし parameterized` {
        private var arg = 0
        private var stubResult: Result? = null
        private var stubResult2: Result2? = null
        private lateinit var actual: FizzBuzzResult
        private fun t(arg: Int,
            assert: (FizzBuzzResult) -> Unit) {
            // arrange
            every { repository.fetch1() } returns stubResult
            every { repository.fetch2() } returns stubResult2
            // act
            actual = actor.fizzBuzz(arg)
            // assert
            assert(actual)
        }
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @Ne inner class `項目表示制御` {
            @ParameterizedTest(name = "引数が {0} の場合、fizz は {2}")
            @CsvSource(
                " 3,  true, 表示",
                " 5, false, 非表示",
                "15,  true, 表示",
                " 4, false, 非表示",
            )
            fun fizz(value: Int, expected: Boolean, expectedLabel: String) {
                t(arg = value) { actual -> Assertions.assertEquals(expected, actual.fizz) }
            }
            @ParameterizedTest(name = "引数が {0} の場合、buzz は {2}")
            @CsvSource(
                " 3, false, 非表示",
                " 5,  true, 表示",
                "15,  true, 表示",
                " 4, false, 非表示",
            )
            fun buzz(value: Int, expected: Boolean, expectedLabel: String) {
                arg = value
                t(arg = value) { actual -> Assertions.assertEquals(expected, actual.buzz) }
            }
            @ParameterizedTest(name = "引数: {0}, ◯◯取得結果のタイプ: {1}, △△取得結果のタイプ: {2} -> その他 の表示は {3}")
            @MethodSource("provideArgs")
            fun `else value`(
                value: Int,
                mockResult: LV<String>,
                mockResult2: LV<String>,
                expected: LV<String>,
            ) {
                arg = value
                stubResult = mockResult.value?.let { Result(it) }
                stubResult2 = mockResult2.value?.let { Result2(it) }
                t(arg = value) { actual -> Assertions.assertEquals(expected.value, actual.elseValue) }
            }
            private fun provideArgs(): Stream<Arguments> {
                // @formatter:off
                return Stream.of(
                    Arguments.arguments( 3, LV("-", null), LV("-", null), LV("空白", "")),
                    Arguments.arguments( 5, LV("-", null), LV("-", null), LV("空白", "")),
                    Arguments.arguments(15, LV("-", null), LV("-", null), LV("空白", "")),
                    Arguments.arguments( 4, LV("A", "A"), LV("-", null), LV("AA", "AA")),
                    Arguments.arguments( 4, LV("B", "B"), LV("X", "X"), LV("XX", "XX")),
                    Arguments.arguments( 4, LV("B", "B"), LV("Y", "Y"), LV("YY", "YY")),
                    Arguments.arguments( 4, LV("B", "B"), LV("A,B以外", "C"), LV("BB", "BB")),
                    Arguments.arguments( 4, LV("C", "C"), LV("-", null), LV("CC", "CC")),
                )
                // @formatter:on
            }
        }
    }

    data class LV<T>(
        val label: String,
        val value: T?,
    ) {
        override fun toString(): String {
            return label
        }
    }
}
