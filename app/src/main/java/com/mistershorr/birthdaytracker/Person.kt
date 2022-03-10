package com.mistershorr.birthdaytracker

import java.util.*

//default, no parameter constructor
//give each file a default value
data class Person(
    var name : String = "",
    var birthday : Date = Date(1646932068223),
    var budget : Double = .99,
    var desiredGift : String = "String",
    var previousGifts : List<String> = listOf(),
    var previousGiftsToMe : List<String> = listOf(),
    var giftPurchased : Boolean = false
) {
    //TODO: have methods to return the calculated values of age, days until birthday
}
