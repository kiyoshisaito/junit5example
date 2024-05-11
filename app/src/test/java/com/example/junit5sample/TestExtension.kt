package com.example.junit5sample

import io.mockk.MockKVerificationScope
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.function.Executable


typealias N = DisplayName
typealias T = Test
typealias Ne = Nested
typealias TF = TestFactory

infix fun <T, U: T> T.shouldBe(expected: U): T {
    assertEquals(expected, this)
    return this
}

fun should(block: MockKVerificationScope.() -> Unit){
    verify(exactly = 1, verifyBlock = block)
}

fun shouldNot(block: MockKVerificationScope.() -> Unit) {
    verify(exactly = 0, verifyBlock = block)
}

// dynamic test 用の拡張関数
operator fun String.invoke(test: Executable): DynamicTest {
    return DynamicTest.dynamicTest(this, test)
}

