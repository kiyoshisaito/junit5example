package com.example.junit5sample

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory


typealias BE = BeforeEach
typealias N = DisplayName
typealias T = Test
typealias Ne = Nested
typealias TF = TestFactory

infix fun <T, U: T> T.shouldBe(expected: U): T {
    assertEquals(expected, this)
    return this
}