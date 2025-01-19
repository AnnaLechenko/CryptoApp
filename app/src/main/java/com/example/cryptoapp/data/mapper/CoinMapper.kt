package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.model.CoinNameListDto
import com.example.cryptoapp.domain.CoinInfo
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class CoinMapper @Inject  constructor(){
    fun mapDtoToDmModel(dto: CoinInfoDto) : CoinInfoDbModel{
        return  CoinInfoDbModel(
            fromSymbol = dto.fromSymbol,
            price = dto.price,
            lowDay = dto.lowDay,
            highDay = dto.highDay,
            lastMarket = dto.lastMarket,
           lastUpdate = dto.lastUpdate ,
            toSymbol = dto.toSymbol,
            imageUrl = dto.imageUrl

        )
    }

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto)
                :List<CoinInfoDto>{

         val result = mutableListOf<CoinInfoDto>()

        val jsonObject = jsonContainer.json ?: return result

        val coinKeySet = jsonObject.keySet() //получаем набор клюей
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
        }

    fun mapNamesListToString(nameListDto: CoinNameListDto):String{
        return nameListDto.namesList?.map {
            it.coinNameContainerDto?.name }?.joinToString(",")?: ""
    }

    fun mapDBModelToEntity(dbModel: CoinInfoDbModel): CoinInfo{
        return CoinInfo(
            fromSymbol =dbModel.fromSymbol,
            price =dbModel.price,
            lowDay =dbModel.lowDay,
            highDay =dbModel.highDay,
            lastMarket =dbModel.lastMarket,
            lastUpdate =convertTimestampToTime(dbModel.lastUpdate),
            toSymbol =dbModel.toSymbol,
            imageUrl = BASE_IMAGE_URL + dbModel.imageUrl

        )
    }

    private  fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object{
       private const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}