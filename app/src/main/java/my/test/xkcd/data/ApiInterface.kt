package my.test.xkcd.data



import my.test.xkcd.data.model.comic.ComicResponse
import my.test.xkcd.utils.AppWebServices
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by shahzaib on 8/14/2019.
 */

// interface containing all API endpoints used by app
interface ApiInterface {

    // to get comic info using comic number/id
    @GET(AppWebServices.API_COMIC)
    suspend fun getComicInfo(@Path(value = "id", encoded = true) comicId: String?): ComicResponse
}