package com.anazumk.baseapp.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "data") val data: List<Regions>
)

@JsonClass(generateAdapter = true)
data class Regions(
    @Json(name = "from") val from: String,
    @Json(name = "to") val to: String,
    @Json(name = "regions") val regionsList: List<RegionalData>
)

@JsonClass(generateAdapter = true)
data class RegionalData(
    @Json(name = "regionid") val regionId: Int,
    @Json(name = "dnoregion") val dnoRegion: String,
    @Json(name = "shortname") val shortName: String,
    @Json(name = "intensity") val intensity: CarbonIntensity,
    @Json(name = "generationmix") val generationMix: List<GenerationMix>
) : Serializable

@JsonClass(generateAdapter = true)
data class CarbonIntensity(
    @Json(name = "forecast") val forecast: String,
    @Json(name = "index") val index: String
)

@JsonClass(generateAdapter = true)
data class GenerationMix(
    @Json(name = "fuel") val fuel: String,
    @Json(name = "perc") val percentage: Float
)

enum class CarbonIndex(val value: String) {
    VERY_LOW("very low"),
    LOW("low"),
    MODERATE("moderate"),
    HIGH("high"),
    VERY_HIGH("very high");

    companion object {
        fun from(index: String): CarbonIndex =
            values().first { it.value == index }
    }
}