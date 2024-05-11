package com.example.junit5sample

import io.mockk.MockKVerificationScope
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory


typealias N = DisplayName
typealias T = Test
typealias Ne = Nested
typealias TF = TestFactory

infix fun <T, U: T> T.shouldBe(expected: U): T {
    assertEquals(expected, this)
    return this
}

fun execute(block: MockKVerificationScope.() -> Unit){
    verify(exactly = 1, verifyBlock = block)
}

fun notExecute(block: MockKVerificationScope.() -> Unit) {
    verify(exactly = 0, verifyBlock = block)
}
