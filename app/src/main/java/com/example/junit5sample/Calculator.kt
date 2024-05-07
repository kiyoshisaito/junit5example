package com.example.junit5sample

class Calculator(
    private val repository: Repository,
) {
    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun fizzBuzz(value: Int): FizzBuzzResult {
        when {
            canDivide3And5(value) -> return FizzBuzzResult(fizz = true, buzz = true, elseValue = "")
            canDivide3(value) -> return FizzBuzzResult(fizz = true, buzz = false, elseValue = "")
            canDivide5(value) -> return FizzBuzzResult(fizz = false, buzz = true, elseValue = "")
        }
        val result = repository.fetch()
        result ?: return FizzBuzzResult(fizz = false, buzz = false, elseValue = "CC")
        return when (result.type) {
            "A" -> FizzBuzzResult(fizz = false, buzz = false, elseValue = "AA")
            "B" -> {
                val result2 = repository.fetch2()
                result2 ?: return FizzBuzzResult(fizz = false, buzz = false, elseValue = "BB")
                when (result2.type) {
                    "A" -> FizzBuzzResult(fizz = false, buzz = false, elseValue = "AAA")
                    "B" -> FizzBuzzResult(fizz = false, buzz = false, elseValue = "BBB")
                    else -> FizzBuzzResult(fizz = false, buzz = false, elseValue = "BB")
                }
            }
            else -> FizzBuzzResult(fizz = false, buzz = false, elseValue = "CC")
        }
    }

    private fun canDivide3(value: Int) = value % 3 == 0
    private fun canDivide5(value: Int) = value % 5 == 0
    private fun canDivide3And5(value: Int) = canDivide3(value) && canDivide5(value)
}

data class FizzBuzzResult(
    val fizz: Boolean,
    val buzz: Boolean,
    val elseValue: String,
)

open class Result(
    open val type: String,
    open val name: String = "",
)

data class ResultData(
    override val type: String,
    override val name: String = "",
): Result(type, name)

open class Result2(
    open val type: String,
    open val name: String = "",
)

class Repository {
    fun fetch(): Result? {
        return null
    }
    fun fetch2(): Result2? {
        return null
    }
}