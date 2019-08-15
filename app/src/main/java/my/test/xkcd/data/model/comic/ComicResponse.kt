package my.test.xkcd.data.model.comic

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ComicResponse(

	@field:SerializedName("news")
	val news: String? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("transcript")
	val transcript: String? = null,

	@field:SerializedName("month")
	val month: String? = null,

	@field:SerializedName("year")
	val year: String? = null,

	@field:SerializedName("num")
	val num: Int? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("alt")
	val alt: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("day")
	val day: String? = null,

	@field:SerializedName("safe_title")
	val safeTitle: String? = null
) : Parcelable {
	constructor(source: Parcel) : this(
		source.readString(),
		source.readString(),
		source.readString(),
		source.readString(),
		source.readString(),
		source.readValue(Int::class.java.classLoader) as Int?,
		source.readString(),
		source.readString(),
		source.readString(),
		source.readString(),
		source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(news)
		writeString(img)
		writeString(transcript)
		writeString(month)
		writeString(year)
		writeValue(num)
		writeString(link)
		writeString(alt)
		writeString(title)
		writeString(day)
		writeString(safeTitle)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<ComicResponse> = object : Parcelable.Creator<ComicResponse> {
			override fun createFromParcel(source: Parcel): ComicResponse = ComicResponse(source)
			override fun newArray(size: Int): Array<ComicResponse?> = arrayOfNulls(size)
		}
	}
}