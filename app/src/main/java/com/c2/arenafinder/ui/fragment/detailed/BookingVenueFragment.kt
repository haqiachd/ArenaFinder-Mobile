package com.c2.arenafinder.ui.fragment.detailed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ProgressBar
import android.os.Looper
import android.widget.Toast
import android.content.DialogInterface
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.c2.arenafinder.R
import com.c2.arenafinder.ui.adapter.VenueBookingAdapter
import com.c2.arenafinder.data.model.DatePickerModel
import com.c2.arenafinder.ui.adapter.DatePickerAdapter
import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.c2.arenafinder.data.response.VenueBookingResponse
import com.c2.arenafinder.data.model.VenueBookingModel
import com.c2.arenafinder.data.local.LogApp
import com.c2.arenafinder.data.local.LogTag
import com.c2.arenafinder.ui.activity.PaymentGatewayActivity
import com.c2.arenafinder.util.UsersUtil
import com.c2.arenafinder.util.ArenaFinder
import com.c2.arenafinder.util.AdapterActionListener

import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.EventDay
import com.c2.arenafinder.data.model.ListLapanganModel
import com.c2.arenafinder.data.response.ListLapanganResponse
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar
import java.util.Date

private const val ARG_ID = "id"
private const val ARG_VENUE = "venue"

class BookingVenueFragment : Fragment() {

    private var idVenue: String? = null
    private var venueName: String? = null

    private var venueBookingAdapter: VenueBookingAdapter? = null
    private var usersUtil: UsersUtil? = null
    private var dateChooser: String? = null
    private var listLapangan: ArrayList<ListLapanganModel>? = null

    private lateinit var loading: AlertDialog

    private lateinit var recyclerDate: RecyclerView
    private lateinit var recyclerVenue: RecyclerView
    private lateinit var btnChooseDate: ImageView
    private lateinit var imgBack: ImageView
    private lateinit var msgLayout: LinearLayout
    private lateinit var nestedScroll: NestedScrollView
    private lateinit var bottomNav: ConstraintLayout
    private lateinit var shimmerFirstLayout: ShimmerFrameLayout
    private lateinit var shimmerSecondLayout: ShimmerFrameLayout

    private lateinit var txtPriceBot: TextView
    private lateinit var txtRightBot: TextView
    private lateinit var btnPesan: MaterialButton

    private fun initViews(view: View) {
        recyclerDate = view.findViewById(R.id.fbv_recycler_date)
        recyclerVenue = view.findViewById(R.id.fbv_recycler_lapangan)
        btnChooseDate = view.findViewById(R.id.fbv_date_picker)
        imgBack = view.findViewById(R.id.fbv_back)
        txtPriceBot = view.findViewById(R.id.fbv_nav_txt_data)
        txtRightBot = view.findViewById(R.id.fbv_nav_txt_right)
        btnPesan = view.findViewById(R.id.fbv_nav_button)
        msgLayout = view.findViewById(R.id.fbv_message)
        nestedScroll = view.findViewById(R.id.fbv_scrollview)
        bottomNav = view.findViewById(R.id.fvd_bottom_nav)
        shimmerFirstLayout = view.findViewById(R.id.fbv_shimmer_1)
        shimmerSecondLayout = view.findViewById(R.id.fbv_shimmer_2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            idVenue = arguments!!.getString(ARG_ID)
            venueName = arguments!!.getString(ARG_VENUE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_venue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        // show loading dialog
        loading = AlertDialog.Builder(requireContext())
            .setView(LayoutInflater.from(requireContext()).inflate(R.layout.dialog_loading, null))
            .setCancelable(false)
            .create()

        usersUtil = UsersUtil(requireContext())

        ArenaFinder.setStatusBarColor(requireActivity(),
            ArenaFinder.WHITE_STATUS_BAR,
            R.color.white,
            true
        )

        // get current date
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val currentDate = sdf.format(calendar.time)

        // show tanggal dan jadwal
        dateChooser = currentDate

        btnPesan.isClickable = false

        showFirstShimmer(true)
        getListLapangan(idVenue!!)
        showDatePicker()
        updateBottomNav()
        onClickGroups()
    }

    /**
     * Menampilkan first shimmer
     */
    private fun showFirstShimmer(show: Boolean) {
        if (show) {
            shimmerFirstLayout.visibility = View.VISIBLE
            shimmerFirstLayout.startShimmer()
            nestedScroll.visibility = View.GONE
            btnPesan.isClickable = false
        } else {
            shimmerFirstLayout.visibility = View.GONE
            shimmerFirstLayout.stopShimmer()
            nestedScroll.visibility = View.VISIBLE
            btnPesan.isClickable = true
        }
    }

    /**
     * Menampilkan second shimmer
     */
    private fun showSecondShimmer(show: Boolean) {
        if (show) {
            shimmerSecondLayout.visibility = View.VISIBLE
            shimmerSecondLayout.startShimmer()
            recyclerVenue.visibility = View.GONE
            btnPesan.isClickable = false
        } else {
            shimmerSecondLayout.visibility = View.GONE
            shimmerSecondLayout.stopShimmer()
            recyclerVenue.visibility = View.VISIBLE
            btnPesan.isClickable = true
        }
    }

    /**
     * Handler aksi saat button-button yang ada didalam fragment di-klik
     */
    private fun onClickGroups() {

        /*
         * Aksi saat button img back di klik
         */
        imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        /*
         * Aksi saat button choose data di klik
         */
        btnChooseDate.setOnClickListener {
            btnChooseDate.isClickable = false

            // initialized dialog view
            val customView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_material_calendar, null)
            val calendarView = customView.findViewById<CalendarView>(R.id.dia_pick_calendar)
            val txtCancel = customView.findViewById<TextView>(R.id.dia_pick_cancel)
            val prog = customView.findViewById<ProgressBar>(R.id.dia_pick_prog)

            // show dialog
            val dialog = AlertDialog.Builder(requireContext())
                .setView(customView).create()
            dialog.show()
            Handler(Looper.getMainLooper())
                .postDelayed({
                    prog.visibility = View.GONE
                    calendarView.visibility = View.VISIBLE
                }, 1000L)

            // set minimun date
            val minDate = Calendar.getInstance()
            minDate[minDate[Calendar.YEAR], minDate[Calendar.MONTH]] =
                minDate[Calendar.DAY_OF_MONTH] - 1
            calendarView.setMinimumDate(minDate)

            // set date now background
            val days = ArrayList<CalendarDay>()
            val cday = CalendarDay(Calendar.getInstance())
            days.add(cday)
            changeDateBackground(calendarView, days, R.drawable.bg_calendar_view_grey)

            // action when users selected a date
            calendarView.setOnDayClickListener(object : OnDayClickListener {
                override fun onDayClick(eventDay: EventDay) {
                    // get date selected
                    val clickedDayCalendar = eventDay.calendar
                    // get yesterday date
                    val kemarin = Calendar.getInstance()
                    kemarin[kemarin[Calendar.YEAR], kemarin[Calendar.MONTH]] =
                        kemarin[Calendar.DAY_OF_MONTH] - 1

                    // check whether the selected date is before today's date
                    if (clickedDayCalendar.before(kemarin)) {
                        Toast.makeText(requireContext(),
                            R.string.date_picker_bef_date,
                            Toast.LENGTH_SHORT).show()
                    } else {
                        val date = "${clickedDayCalendar.get(Calendar.YEAR)}-${
                            clickedDayCalendar.get(Calendar.MONTH)
                        }-${clickedDayCalendar.get(Calendar.DAY_OF_MONTH)}";
//                        Toast.makeText(requireContext(), "Date -> $date", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                        updateBottomNav()
                        showFirstShimmer(true)
                        btnChooseDate.isClickable = true
                        venueBookingAdapter!!.resetTotalHarga()
                        showDatePicker(false, clickedDayCalendar)
                        getListLapangan(idVenue!!)
                    }
                }
            })

            // action when user canceled select a date
            txtCancel.setOnClickListener { p: View? ->
                dialog.dismiss()
                btnChooseDate.isClickable = true
            }
            dialog.setOnCancelListener { c: DialogInterface? ->
                btnChooseDate.isClickable = true
            }
        }

        /*
         * Aksi saat button pesan di klik
         */
        btnPesan.setOnClickListener {

            if (venueBookingAdapter == null) {
                return@setOnClickListener
            }

            if (venueBookingAdapter!!.jadwalDipilih.size > 0) {
                val old = venueBookingAdapter!!.jadwalDipilih
                val detailNew = venueBookingAdapter!!.itemDetails

                for (x in old) {
                    LogApp.error(requireContext(), LogTag.LIFEFCYLE, "old data -> ${x.price}")
                }

                LogApp.error(requireContext(), LogTag.LIFEFCYLE, "===========================")

                for (d in detailNew) {
                    LogApp.error(requireContext(), LogTag.LIFEFCYLE, "item detail new  -> $d")
                }
                LogApp.error(requireContext(),
                    LogTag.LIFEFCYLE,
                    "total harga -> ${venueBookingAdapter!!.totalHarga}")


                ArenaFinder.playVibrator(requireActivity(), ArenaFinder.VIBRATOR_SHORT)
                val sheet = BottomSheetDialog(requireActivity(), R.style.BottomSheetTheme)
                val sheetInflater =
                    requireActivity().layoutInflater.inflate(R.layout.sheet_choose_payment, null)
                sheet.setContentView(sheetInflater)

                var choosing = ""

                val btnOnline = sheetInflater.findViewById<MaterialCardView>(R.id.scp_card_online)
                val btnCod = sheetInflater.findViewById<MaterialCardView>(R.id.scp_card_cod)
                val imgOnlineIndicator =
                    sheetInflater.findViewById<ImageView>(R.id.scp_online_indicator)
                val imgCodIndicator = sheetInflater.findViewById<ImageView>(R.id.scp_cod_indicator)

                btnOnline.setOnClickListener {
                    btnOnline.strokeColor = ContextCompat.getColor(requireContext(), R.color.azure)
                    btnCod.strokeColor = ContextCompat.getColor(requireContext(), R.color.dimgray)
                    imgOnlineIndicator.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_indicator_selected))
                    imgCodIndicator.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_indicator_unselected))
                    choosing = "online"
                }

                btnCod.setOnClickListener {
                    btnOnline.strokeColor =
                        ContextCompat.getColor(requireContext(), R.color.dimgray)
                    btnCod.strokeColor = ContextCompat.getColor(requireContext(), R.color.azure)
                    imgOnlineIndicator.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_indicator_unselected))
                    imgCodIndicator.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_indicator_selected))
                    choosing = "cod"
                }

                sheetInflater.findViewById<MaterialButton>(R.id.scp_button).setOnClickListener {

                    when (choosing) {
                        "cod" -> {
                            actionButton("cod")
                            sheet.dismiss()
                        }
                        "online" -> {
//                            actionButton("online")
//                            sheet.dismiss()
                            Toast.makeText(requireContext(),
                                getString(R.string.toast_segera),
                                Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(requireContext(),
                                getString(R.string.txt_payment_method_d),
                                Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                // show sheet
                sheet.show()
                sheet.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
                sheet.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                sheet.window!!.attributes.windowAnimations = R.style.BottomSheetAnim
                sheet.window!!.setGravity(Gravity.BOTTOM)
            } else {
                ArenaFinder.VibratorToast(requireContext(),
                    "Tidak ada jadwal yang dipilih",
                    Toast.LENGTH_SHORT,
                    ArenaFinder.VIBRATOR_SHORT
                )
            }

        }
    }

    private fun updateBottomNav() {
        try {
            txtPriceBot.text = getString(R.string.txt_price_value,
                ArenaFinder.toMoneyCase(venueBookingAdapter!!.totalHarga))
            txtRightBot.text =
                getString(R.string.fbv_total_jadwal, venueBookingAdapter!!.jadwalDipilih.size)

        } catch (ex: Throwable) {
            ex.printStackTrace()
        }
    }

    private fun actionButton(status: String) {
        val models = venueBookingAdapter!!.jadwalDipilih

        val gson = Gson()
        val jsonPayload = gson.toJson(models)

        val intent = Intent(requireContext(), PaymentGatewayActivity::class.java)
        intent.putExtra(PaymentGatewayActivity.ID_VENUE, idVenue)
        intent.putExtra(PaymentGatewayActivity.VENUE_NAME, venueName)
        intent.putExtra(PaymentGatewayActivity.DATE_CHOOSER, dateChooser)
        intent.putExtra(PaymentGatewayActivity.PAYLOAD, jsonPayload)
        intent.putExtra(PaymentGatewayActivity.ITEM_DETAILS, venueBookingAdapter?.itemDetails)
        intent.putExtra(PaymentGatewayActivity.TOTAL_HARGA, venueBookingAdapter?.totalHarga)
        intent.putExtra(PaymentGatewayActivity.STATUS, status)

        LogApp.error(this, LogTag.LIFEFCYLE, "PAYLOAD : $jsonPayload")

        venueBookingAdapter!!.resetTotalHarga()
        updateBottomNav()
        startActivity(intent)
    }

    private fun changeDateBackground(
        calendarView: CalendarView,
        days: ArrayList<CalendarDay>,
        @DrawableRes draw: Int,
    ) {
        for (day in days) {
            day.backgroundDrawable = ContextCompat.getDrawable(requireContext(), draw)
        }
        calendarView.setCalendarDays(days)
    }

    /**
     * Menampilkan list tanggal
     */
    private fun showDatePicker(
        isToday: Boolean = true,
        startFrom: Calendar = Calendar.getInstance(),
    ) {
        // mendapatkan data tanggal
        val models = ArrayList<DatePickerModel>()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val sdfDayOfWeek = SimpleDateFormat("E", Locale.getDefault())
        val sdfDate = SimpleDateFormat("dd MMM", Locale.getDefault())

        if (!isToday) {
            startFrom.time = Date((startFrom.get(Calendar.YEAR) - 1900),
                startFrom.get(Calendar.MONTH),
                startFrom.get(Calendar.DAY_OF_MONTH))
        }

        // Mendapatkan data tanggal hari ini sampai 7 hari kedepan
        for (i in 0..6) {
            // Format tanggal menjadi "yyyy-MM-dd"
            val currentDate = sdf.format(startFrom.time)

            // Mengambil nama hari dan format "dd MMM" dari tanggal saat ini
            val dayOfWeek = sdfDayOfWeek.format(startFrom.time)
            val formattedDate = sdfDate.format(startFrom.time)

            // Membuat objek DatePickerModel dan menambahkannya ke dalam list
            models.add(DatePickerModel(formattedDate, dayOfWeek, currentDate, false))

            // Menambah satu hari ke tanggal saat ini
            startFrom.add(Calendar.DAY_OF_MONTH, 1)
        }

        dateChooser = models[0].dateMonth
        updateBottomNav()

        // menampilkan date picker
        if (isAdded) {
            val pickerAdapter =
                DatePickerAdapter(requireContext(), models, object : AdapterActionListener {
                    override fun onClickListener(position: Int) {
                        venueBookingAdapter!!.resetTotalHarga()
                        dateChooser = models[position].dateMonth
                        updateBottomNav()
                        getListLapangan(idVenue!!)
                        showSecondShimmer(true)
                    }
                })
            recyclerDate.adapter = pickerAdapter
        }
    }

    private fun getListLapangan(idVenue: String) {
        // mendapatkan list lapangan
        RetrofitClient.getInstance().getListLapangan(idVenue)
            .enqueue(object : Callback<ListLapanganResponse> {
                override fun onResponse(
                    call: Call<ListLapanganResponse>,
                    response: Response<ListLapanganResponse>,
                ) {
                    // jika response sukses
                    if (response.body() != null && response.body()!!.status == RetrofitClient.SUCCESSFUL_RESPONSE) {
                        // get list lapangan
                        listLapangan = response.body()!!.data
                        showListJadwal(dateChooser!!)
                    } else {
                        ArenaFinder.playVibrator(requireActivity(), ArenaFinder.VIBRATOR_SHORT)
                        msgLayout.visibility = View.VISIBLE
                        bottomNav.visibility = View.GONE
                        nestedScroll.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<ListLapanganResponse>, t: Throwable) {
                    ArenaFinder.VibratorToast(requireContext(),
                        t.message,
                        Toast.LENGTH_SHORT,
                        ArenaFinder.VIBRATOR_SHORT)
                }

            })
    }

    private fun showListJadwal(tanggal: String) {

        val bookingModels = ArrayList<VenueBookingModel>()
        listLapangan!!.forEach { model ->
            // get data jadwal dari server
            RetrofitClient.getInstance().getJadwal(idVenue, model.idLapangan.toString(), tanggal)
                .enqueue(object : Callback<VenueBookingResponse?> {
                    override fun onResponse(
                        call: Call<VenueBookingResponse?>,
                        response: Response<VenueBookingResponse?>,
                    ) {
                        if (response.body() != null && response.body()!!
                                .status.equals("success", ignoreCase = true)
                        ) {
                            showFirstShimmer(false)
                            showSecondShimmer(false)
                            // menambahkan data lapangan ke list
                            bookingModels.add(response.body()!!.data)

                            if (bookingModels.size >= listLapangan!!.size) {
                                if (isAdded) {
                                    // menampilkan jadwal di recyclerview
                                    venueBookingAdapter = VenueBookingAdapter(requireContext(),
                                        bookingModels,
                                        object : AdapterActionListener {
                                            override fun onClickListener(position: Int) {
                                                updateBottomNav()
                                            }
                                        })
                                    recyclerVenue.adapter = venueBookingAdapter
                                    btnPesan.isClickable = true
                                }
                            }
                        } else {
                            showFirstShimmer(false)
                            showSecondShimmer(false)
                            Toast.makeText(requireContext(),
                                response.body()!!.message,
                                Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<VenueBookingResponse?>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                        showFirstShimmer(false)
                        showSecondShimmer(false)
                    }
                })
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(id: String?, venue: String?): BookingVenueFragment {
            val fragment = BookingVenueFragment()
            val args = Bundle()
            args.putString(ARG_ID, id)
            args.putString(ARG_VENUE, venue)
            fragment.arguments = args
            return fragment
        }
    }
}