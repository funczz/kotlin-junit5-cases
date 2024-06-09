package com.github.funczz.kotlin.junit5

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.assertAll

interface Cases {

    /**
     *  各@Testメソッドでは各assertAllテストの前に実行され、
     *  各@TestFactoryメソッドでは各dynamicTestテストの前に実行される
     */
    fun setUpCases()

    /**
     *  各@Testメソッドでは各assertAllテストの後に実行され、
     *  各@TestFactoryメソッドでは各dynamicTestテストの後に実行される
     */
    fun tearDownCases()

    /**
     * DynamicTestにより動的にテストを生成して実行する
     * メソッドには@TestFactoryを付与する
     * @param cases テストで使用する値
     * @param function テストを記述した関数
     */
    fun <T> casesDynamicTest(
        vararg cases: T, function: (T) -> Unit
    ): List<DynamicTest> {
        return cases.map {
            DynamicTest.dynamicTest("Case: `%s`".format(it.toString())) {
                try {
                    setUpCases()
                    function(it)
                } catch (e: Throwable) {
                    throw e
                } finally {
                    tearDownCases()
                }
            }
        }
    }

    /**
     * assertAllにより順にテストを実行する
     * メソッドには@Testを付与する
     * @param cases テストで使用する値
     * @param function テストを記述した関数
     */
    fun <T> casesAssertAll(
        vararg cases: T, function: (T) -> Unit
    ) {
        assertAll(
            *cases.map {
                {
                    try {
                        setUpCases()
                        function(it)
                    } catch (e: Throwable) {
                        throw e
                    } finally {
                        tearDownCases()
                    }
                }
            }.toTypedArray()
        )
    }

}