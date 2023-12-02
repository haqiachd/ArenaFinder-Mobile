package com.c2.arenafinder.data.model

data class PaymentDetailModel(
    val namaLapangan : String,
    val tanggal : String,
    val totalHarga : String,
    val totalSesion : String,
    val rician : ArrayList<RicianSession>
) {

    data class RicianSession(
        val session : String,
        val price : String
    )

}