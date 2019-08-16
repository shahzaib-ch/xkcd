package my.test.xkcd

import my.test.xkcd.data.ApiFactory
import org.junit.Assert
import org.junit.Test

/**
 * Testing api factory methods
 */
class ApiFactoryUnitTest {

    @Test
    fun shouldReturnApiInterface() {
        val comicInfoApiInterface = ApiFactory.provideApi()
        val comicSearchApiInterface = ApiFactory.provideApi()

        Assert.assertNotNull(comicInfoApiInterface)

        Assert.assertNotNull(comicSearchApiInterface)
    }

}
