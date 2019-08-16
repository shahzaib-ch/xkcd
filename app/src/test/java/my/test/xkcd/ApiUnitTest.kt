package my.test.xkcd

import kotlinx.coroutines.runBlocking
import my.test.xkcd.data.ApiFactory
import org.junit.Assert
import org.junit.Test

/**
 * Testing api factory methods
 */
class ApiUnitTest {

    @Test
    fun shouldReturnComicResponse() {
        val apiInterface = ApiFactory.provideApi()
        val comicInfo = runBlocking {
            apiInterface.getComicInfo("5")
        }
        Assert.assertNotNull(comicInfo)
        Assert.assertEquals(5, comicInfo.num)
        Assert.assertNotNull(comicInfo.img)
    }

    @Test
    fun shouldReturnStringWithComicIds() {
        val apiInterface = ApiFactory.provideSearchApi()
        // returns most relevant comic id
        val comicId = runBlocking {
            apiInterface.comicSearchByText("xkcd", "boy")
        }
        Assert.assertNotNull(comicId)
    }

}
