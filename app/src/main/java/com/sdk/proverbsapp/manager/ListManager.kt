package com.sdk.proverbsapp.manager

import com.sdk.proverbsapp.model.Proverb
import com.sdk.proverbsapp.util.ObjectLists
import com.sdk.proverbsapp.util.ObjectLists2
import com.sdk.proverbsapp.util.Utils

class ListManager {
    fun getList(): List<Proverb> {
        return when (Utils.pos) {
            0 -> ObjectLists.amalDin()
            1 -> ObjectLists.tilHaq()
            2 -> ObjectLists.faqBoy()
            3 -> ObjectLists.yilFas()
            4 -> ObjectLists.hayQuw()
            5 -> ObjectLists.yolYol()
            6 -> ObjectLists.yosKek()
            7 -> ObjectLists.toyMot()
            8 -> ObjectLists2.sevGam()
            9 -> ObjectLists2.corDeh()
            10 -> ObjectLists2.sogToz()
            11 -> ObjectLists2.mamAf()
            12 -> ObjectLists2.ehEh()
            13 -> ObjectLists.yilFas()
            14 -> ObjectLists.yilFas()
            15 -> ObjectLists.yilFas()
            16 -> ObjectLists.yilFas()
            17 -> ObjectLists.yilFas()
            18 -> ObjectLists.yilFas()
            19 -> ObjectLists.yilFas()
            20 -> ObjectLists.yilFas()
            21 -> ObjectLists.yilFas()
            22 -> ObjectLists.yilFas()
            23 -> ObjectLists.yilFas()
            24 -> ObjectLists.yilFas()
            25 -> ObjectLists.yilFas()
            26 -> ObjectLists.yilFas()
            27 -> ObjectLists.yilFas()
            else -> emptyList()
        }
    }
}