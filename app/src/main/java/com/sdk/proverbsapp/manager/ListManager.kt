package com.sdk.proverbsapp.manager

import com.sdk.proverbsapp.model.Proverb
import com.sdk.proverbsapp.util.*

class ListManager {
    fun getList(): ArrayList<Proverb> {
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
            13 -> ObjectLists2.farYet()
            14 -> ObjectLists3.imkImk()
            15 -> ObjectLists3.vaqFur()
            16 -> ObjectLists3.taqTad()
            17 -> ObjectLists3.masMas()
            18 -> ObjectLists3.foyZar()
            19 -> ObjectLists3.kuchOjiz()
            20 -> ObjectLists3.meyMey()
            21 -> ObjectLists3.rejRej()
            22 -> ObjectLists3.zarEht()
            23 -> ObjectLists3.naqNas()
            24 -> ObjectLists4.tejIsr()
            25 -> ObjectLists4.rizNa()
            26 -> ObjectLists4.samBe()
            27 -> ObjectLists4.bahNat()
            28 -> ObjectLists4.farFar()
            29 -> ObjectLists4.andAnd()
            else -> ObjectLists.tez()
        }
    }
}