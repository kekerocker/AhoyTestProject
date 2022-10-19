package com.dsoft.presentation

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun filter_test() {
        var currentDate = ""
        val someList = listOf(
            Test(0, "2022-10-19"),
            Test(1, "2022-10-19"),
            Test(2, "2022-11-22"),
            Test(3, "2022-11-22"),
            Test(4, "2022-11-22"),
            Test(5, "2022-11-23"),
            Test(6, "2022-11-24"),
            Test(7, "2022-11-24"),
            Test(8, "2022-11-26"),
        )
        val filteredList = someList.filter { item ->
            if (currentDate != item.date) {
                currentDate = item.date
                true
            } else false
        }
        assertEquals(someList[2], filteredList[1])
    }
}

data class Test(
    val id: Int,
    val date: String
)