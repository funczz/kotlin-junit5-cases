package com.github.funczz.kotlin.junit5

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.properties.Delegates

class CasesTest : Cases {

    @TestFactory
    fun casesDynamicTest() = casesDynamicTest(
        Pair(1, 1),
        //Pair(1, 2),
        Pair(2, 2),
        //Pair(2, 3),
    ) { (add, expected) ->
        repeat(add) { actual++ }
        assertEquals(expected, actual)
    }

    @Test
    fun casesAssertAllTest() = casesAssertAll(
        Pair(1, 1),
        //Pair(1, 2),
        Pair(2, 2),
        //Pair(2, 3),
    ) { (add, expected) ->
        try {
            repeat(add) { actual++ }
            assertEquals(expected, actual)
        } catch (e: Throwable) {
            println("Failure case: add=%s, expected=%s".format(add, expected))
            throw e
        }
    }

    private var actual by Delegates.notNull<Int>()

    override fun setUpCases() {
        System.err.println("setUpCases")
        actual = 0
    }

    override fun tearDownCases() {
        System.err.println("tearDownCases")
        actual = 0
    }

    @BeforeEach
    fun beforeEach() {
        System.err.println("beforeEach")
    }

    @AfterEach
    fun afterEach() {
        System.err.println("afterEach")
    }

}