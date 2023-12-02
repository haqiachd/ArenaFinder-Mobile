package com.c2.arenafinder.util

import com.c2.arenafinder.api.retrofit.RetrofitClient
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import java.util.UUID

class MidtransUtil(
    private val usersUtil: UsersUtil,
) {

    companion object {
        const val CLIENT_ID = ""
        const val MERCHANT_BASE_URL = "${RetrofitClient.BASE_URL}controllers/payment/merchant.php/"
        const val ORDER_NAME = "ArenaFinder"
        private const val DEF_CITY = "Jombang"
        private const val DEF_ADDRESS = "Kayen, Jombang"
        private const val DEF_POSTAL_CODE = "61462"
    }

    fun generateOrderId(): String {
        return "$ORDER_NAME-${
            UUID.randomUUID().toString().substring(0, 5)
        }${
            System.currentTimeMillis().toString().substring(1)
        }"
    }

    fun generateIdDetail(num: Int): String {
        return "$num-${System.currentTimeMillis().toString().reversed().substring(0, 5)}"
    }

    fun getCustomerDetails(): CustomerDetails {
        val customer = CustomerDetails()
        customer.customerIdentifier = usersUtil.fullName
        customer.email = usersUtil.email
        customer.phone = usersUtil.noHp

        val shippingAddress = ShippingAddress()
        shippingAddress.address = DEF_ADDRESS
        shippingAddress.city = DEF_CITY
        shippingAddress.postalCode = DEF_POSTAL_CODE
        customer.shippingAddress = shippingAddress

        val billingAddress = BillingAddress()
        billingAddress.address = DEF_ADDRESS
        billingAddress.city = DEF_CITY
        billingAddress.postalCode = DEF_POSTAL_CODE
        customer.billingAddress = billingAddress

        return customer
    }

}