package com.c2.arenafinder.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

import com.c2.arenafinder.R
import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.data.local.DataShared
import com.c2.arenafinder.data.local.LogApp
import com.c2.arenafinder.data.local.LogTag
import com.c2.arenafinder.data.model.JadwalPickerModel
import com.c2.arenafinder.data.model.PaymentDetailModel
import com.c2.arenafinder.data.response.CreateBookingResponse
import com.c2.arenafinder.ui.adapter.DetailTransaksiAdapter
import com.c2.arenafinder.util.ArenaFinder
import com.c2.arenafinder.util.LanguagesUtil
import com.c2.arenafinder.util.LanguagesUtil.Companion.ENGLISH
import com.c2.arenafinder.util.LanguagesUtil.Companion.INDONESIAN
import com.c2.arenafinder.util.LanguagesUtil.Companion.JAVANESE
import com.c2.arenafinder.util.MidtransUtil
import com.c2.arenafinder.util.UsersUtil
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback

import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Payment Gateway dengan MidTrans
 */
class PaymentGatewayActivity : AppCompatActivity() {

    private val gson = Gson()

    private var payload: String? = null
    private var idVenue: String? = null
    private var venueName: String? = null
    private var dateChooser: String? = null
    private var totalHarga = 0
    private lateinit var itemHash: ArrayList<String>
    private lateinit var jadwalPickerModel: ArrayList<JadwalPickerModel>
    private var status: String? = null

    private lateinit var dataShared: DataShared
    private lateinit var languagesUtil: LanguagesUtil
    private lateinit var usersUtil: UsersUtil
    private lateinit var midtransUtil: MidtransUtil

    private lateinit var imgBack: ImageView
    private lateinit var txtVenueName: TextView
    private lateinit var editBooking: MaterialCardView
    private lateinit var txtPrice: TextView
    private lateinit var btnPembayaran: MaterialButton
    private lateinit var recycler: RecyclerView

    private lateinit var orderID: String
    private var itemDetails = ArrayList<ItemDetails>()

    private lateinit var loading: AlertDialog

    private fun initViews() {
        imgBack = findViewById(R.id.gat_back)
        txtVenueName = findViewById(R.id.gat_veneu_name)
        editBooking = findViewById(R.id.gat_edit)
        txtPrice = findViewById(R.id.gat_nav_txt_data)
        btnPembayaran = findViewById(R.id.gat_nav_button)
        recycler = findViewById(R.id.gat_recycler)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_midtrans_payment)
        initViews()

        // show loading dialog
        loading = AlertDialog.Builder(this)
            .setView(LayoutInflater.from(this).inflate(R.layout.dialog_loading, null))
            .setCancelable(false)
            .create()

        // get intent data
        idVenue = intent.getStringExtra(ID_VENUE)
        venueName = intent.getStringExtra(VENUE_NAME)
        payload = intent.getStringExtra(PAYLOAD)
        dateChooser = intent.getStringExtra(DATE_CHOOSER)
        itemHash = intent.getStringArrayListExtra(ITEM_DETAILS) as ArrayList<String>
        totalHarga = intent.getIntExtra(TOTAL_HARGA, 0)
        status = intent.getStringExtra(STATUS)

        // convert payload to arraylist
        val listType = object : TypeToken<ArrayList<JadwalPickerModel>>() {}.type
        jadwalPickerModel = gson.fromJson(payload, listType)

        // test showing data
        jadwalPickerModel.forEach { model ->
            LogApp.error(this, LogTag.LIFEFCYLE, "id_jadwal ${model.idJadwal}")
            LogApp.error(this, LogTag.LIFEFCYLE, "session ${model.session}")
            LogApp.error(this, LogTag.LIFEFCYLE, "price ${model.price}")
        }

        // show data
        txtVenueName.text = venueName
        txtPrice.text =
            this.getString(R.string.txt_price_value, ArenaFinder.toMoneyCase(totalHarga))

        // initialize util classes
        dataShared = DataShared(this)
        languagesUtil = LanguagesUtil(this, dataShared)
        usersUtil = UsersUtil(this)
        midtransUtil = MidtransUtil(usersUtil)
        orderID = midtransUtil.generateOrderId()

        LogApp.info(this, LogTag.LIFEFCYLE, "OnCreate ${this.localClassName}")
        LogApp.info(this, LogTag.LIFEFCYLE, "CREATE ORDER ID : $orderID")

        // show detail item
        showListBooking()

        try {
            // create sdk flow
            val sdkFlow = SdkUIFlowBuilder.init()
                .setClientKey(MidtransUtil.CLIENT_ID)
                .setContext(applicationContext)
                .setTransactionFinishedCallback { result ->

                    LogApp.info(this, LogTag.LIFEFCYLE, "STATUS -> ${result.status}")
                    LogApp.info(this, LogTag.LIFEFCYLE, "ID -> ${result.response.transactionId}")
                    LogApp.info(this,
                        LogTag.LIFEFCYLE,
                        "STATUS 2 -> ${result.response.transactionStatus}")

                    when (result.status) {
                        TransactionResult.STATUS_SUCCESS -> {
                            LogApp.error(this,
                                LogTag.LIFEFCYLE,
                                "transaction result is SUCCESS -> ${result.statusMessage}")
                        }
                        TransactionResult.STATUS_PENDING -> {
                            LogApp.error(this,
                                LogTag.LIFEFCYLE,
                                "transaction result is PENDING -> ${result.statusMessage}")
                        }
                        TransactionResult.STATUS_INVALID -> {
                            LogApp.error(this,
                                LogTag.LIFEFCYLE,
                                "transaction result is INVALID -> ${result.statusMessage}")
                        }
                        TransactionResult.STATUS_FAILED -> {
                            ArenaFinder.VibratorToast(this,
                                "Status Error On Transaction",
                                Toast.LENGTH_SHORT,
                                ArenaFinder.VIBRATOR_SHORT)
                            LogApp.error(this,
                                LogTag.LIFEFCYLE,
                                "transaction result is FAILED -> ${result.statusMessage}")
                        }
                    }

                    // show error when status is no success
                    if (result.status != "success") {
                        ArenaFinder.VibratorToast(this,
                            "Status Error On Transaction",
                            Toast.LENGTH_SHORT,
                            ArenaFinder.VIBRATOR_SHORT)
                        LogApp.error(this, LogTag.LIFEFCYLE, result.statusMessage)
                    }

                }
                .setMerchantBaseUrl(MidtransUtil.MERCHANT_BASE_URL)
                .enableLog(true)

            // set color payment intent
            sdkFlow.setColorTheme(CustomColorTheme("#022B49", "#022B49", "#022B49"))

            // set language
            when (languagesUtil.getActivatedLanguage()) {
                INDONESIAN, JAVANESE -> sdkFlow.setLanguage(INDONESIAN)
                ENGLISH -> sdkFlow.setLanguage(ENGLISH)
                else -> sdkFlow.setLanguage(INDONESIAN)
            }


            // build sdk flow
            sdkFlow.buildSDK()

        } catch (ex: Throwable) {
            LogApp.info(this, LogTag.LIFEFCYLE, "ONCREATE -> ${ex.message}")
        }

        btnPembayaran.setOnClickListener {
            when (status) {
                "cod" -> {
                    actionWhenCod()
                }
                "online" -> {
                    actionWhenOnline()
                }
            }
        }

        imgBack.setOnClickListener {
            this.onBackPressed()
        }

    }

    private fun actionWhenCod() {
        LogApp.info(this, LogTag.LIFEFCYLE, "ActionWhenCoe")
        bookingLapangan()
    }

    private fun actionWhenOnline() {
        itemDetails = ArrayList() // reset item details
        LogApp.info(this, LogTag.LIFEFCYLE, "ActionWhenOnline")
        var index = 1
        // add item detail
        for (data in itemHash) {
            val harga = data.substringAfterLast("-")
            var ttlharga = 0
            val detailItem = ItemDetails(
                midtransUtil.generateIdDetail(index),
                harga.toDouble(),
                1,
                data.substring(0, data.lastIndexOf("-"))
            )
            LogApp.error(this, LogTag.LIFEFCYLE, "data -> ${detailItem.name}")
            itemDetails.add(detailItem)
            ttlharga += harga.toInt()
            index++
        }

        LogApp.error(this, LogTag.LIFEFCYLE, "total harga -> $totalHarga")
        LogApp.error(this, LogTag.LIFEFCYLE, "ttl harga -> $totalHarga")

        try {
            // create transaction request
            val transactionRequest = TransactionRequest(orderID, totalHarga.toDouble())
            transactionRequest.itemDetails = itemDetails
            transactionRequest.customerDetails = midtransUtil.getCustomerDetails()

            // show payment intent
            MidtransSDK.getInstance().transactionRequest = transactionRequest
            MidtransSDK.getInstance().startPaymentUiFlow(this)

        } catch (ex: Throwable) {
            ex.printStackTrace()
            if (ex.message == "Error Get Payment Option") {
                Toast.makeText(applicationContext, "jfladsf", Toast.LENGTH_SHORT).show()
            }
            LogApp.info(this, LogTag.LIFEFCYLE, "SDK -> ${ex.message}")
        }

    }

    companion object {
        const val ID_VENUE = "id"
        const val VENUE_NAME = "venue"
        const val DATE_CHOOSER = "date"
        const val PAYLOAD = "payload"
        const val ITEM_DETAILS = "item"
        const val TOTAL_HARGA = "total"
        const val STATUS = "status"
        private var ID_BOOKING = 0
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        ArenaFinder.playVibrator(this, ArenaFinder.VIBRATOR_SHORT)
        AlertDialog.Builder(this)
            .setTitle(R.string.dia_title_warning)
            .setMessage("Jika Anda kembali, maka pesanan Anda akan dibatalkan")
            .setPositiveButton(getString(R.string.btn_dia_kembali)) { _, _ ->
                super.onBackPressed()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
            .setNegativeButton(R.string.dia_negative_cancel) { _, _ -> }
            .create().show()
    }

    private fun showListBooking() {
        val ricianPesan = ArrayList<PaymentDetailModel.RicianSession>()
        jadwalPickerModel.forEach {
            ricianPesan.add(PaymentDetailModel.RicianSession(it.session, it.price.toString()))
        }
        val detailTransaksi = ArrayList<PaymentDetailModel>()
        detailTransaksi.add(
            PaymentDetailModel("", dateChooser!!, "", ricianPesan.size.toString(), ricianPesan)
        )

        recycler.adapter = DetailTransaksiAdapter(this, detailTransaksi)
    }

    private fun bookingLapangan(showDialog: Boolean = true) {
        // get data jadwal
        val pickerModels = jadwalPickerModel

        // show dialog konfirmasi
        AlertDialog.Builder(this)
            .setTitle(R.string.dia_title_confirm)
            .setMessage(getString(R.string.dia_confirm_booking))
            .setCancelable(false)
            .setPositiveButton(R.string.dia_btn_yakin) { _, _ ->
                // show loading dialog
                if (showDialog) {
                    loading.show()
                }
                // create new booking
                RetrofitClient.getInstance()
                    .createBooking(
                        idVenue, usersUtil.email, totalHarga.toString()
                    )
                    .enqueue(object : Callback<CreateBookingResponse> {
                        override fun onResponse(
                            call: Call<CreateBookingResponse>,
                            response: Response<CreateBookingResponse>,
                        ) {

                            // cek apakah booking berhasil dibuat atau tidak
                            if (response.body() != null && response.body()!!.status == RetrofitClient.SUCCESSFUL_RESPONSE) {
                                // save jadwal yang dipilih
                                saveBookingDetails(pickerModels,
                                    response.body()!!.data.idBooking.toString()
                                )
                            } else {
                                ArenaFinder.VibratorToast(applicationContext,
                                    response.body()!!.message,
                                    Toast.LENGTH_SHORT,
                                    ArenaFinder.VIBRATOR_SHORT
                                )
                            }

                        }

                        override fun onFailure(call: Call<CreateBookingResponse>, t: Throwable) {
                            ArenaFinder.VibratorToast(applicationContext,
                                t.message,
                                Toast.LENGTH_LONG,
                                ArenaFinder.VIBRATOR_MEDIUM
                            )
                        }

                    })
            }
            .setNegativeButton(R.string.dia_negative_cancel) { _, _ -> }
            .create().show()
    }

    private fun saveBookingDetails(
        pickerModels: ArrayList<JadwalPickerModel>,
        idBooking: String,
    ) {
        var successful = 0
        val totalModels = pickerModels.size

        // membaca semua data jadwal yang dipilih
        pickerModels.forEach { model ->
            // menyimpan data jadwal yang dipilih ke server
            RetrofitClient.getInstance().bookingDetail(
                idBooking,
                dateChooser, model.idJadwal.toString()
            ).enqueue(object : Callback<CreateBookingResponse> {
                override fun onResponse(
                    call: Call<CreateBookingResponse>,
                    response: Response<CreateBookingResponse>,
                ) {
                    successful++
                    // menampilkan status penyimpanan
                    LogApp.info(this,
                        LogTag.LIFEFCYLE,
                        "saving id price -> " + model.price
                    )

                    // booking success
                    if (successful >= totalModels) {
                        loading.dismiss()
                        // show dialog success
                        showDialogBooking(R.string.dia_title_inform,
                            R.string.dia_msg_booking_success
                        ) { _, _ ->
                            // membuka activity main
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                        }
                    }
                }

                override fun onFailure(
                    call: Call<CreateBookingResponse>,
                    t: Throwable,
                ) {
                    t.printStackTrace()
                    // menampilkan pesan bahwa booking gagal
                    cancelBooking(idBooking)
                }
            })
        }
    }

    private fun cancelBooking(idBooking: String) {
        // jika gagal maka booking akan dibatalkan
        RetrofitClient.getInstance()
            .cancelBooking(idBooking)
            .enqueue(object : Callback<CreateBookingResponse> {
                override fun onResponse(
                    call: Call<CreateBookingResponse>,
                    response: Response<CreateBookingResponse>,
                ) {
                    loading.dismiss()
                    // menampilkan pesan bahwa booking gagal
                    showDialogBooking(
                        R.string.dia_title_warning,
                        R.string.dia_msg_booking_fail
                    ) { _, _ ->
                        // membuka activity main
                        startActivity(Intent(applicationContext,
                            MainActivity::class.java)
                        )
                    }
                }

                override fun onFailure(
                    call: Call<CreateBookingResponse>,
                    t: Throwable,
                ) {
                    t.printStackTrace()
                    loading.dismiss()
                    // menampilkan pesan bahwa booking gagal
                    showDialogBooking(
                        R.string.dia_title_warning,
                        R.string.dia_msg_booking_fail
                    ) { _, _ ->
                        // membuka activity main
                        startActivity(Intent(applicationContext,
                            MainActivity::class.java)
                        )
                    }
                }

            })
    }

    private fun showDialogBooking(
        @StringRes title: Int,
        @StringRes msg: Int,
        listener: DialogInterface.OnClickListener,
    ) {
        // menampilkan pesan bahwa booking gagal
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton(R.string.dia_positive_ok) { d, w ->
                listener.onClick(d, w)
            }.setCancelable(false)
            .create().show()
    }

}