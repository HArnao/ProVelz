package com.upao.velz.repositories

import android.content.Context
import android.util.Log
import com.upao.velz.apiLaravel.ApiService
import com.upao.velz.apiLaravel.Apiclient
import com.upao.velz.models.RequestModel.PaymentRequest
import com.upao.velz.models.responseModel.AppDetailResponse
import com.upao.velz.models.responseModel.payment.PaymentResponse

class PaymentRepository(context: Context) {

    val apiService = Apiclient.createService(ApiService::class.java)

    suspend fun addPayment(payment: PaymentRequest): Boolean {
        return try {
            val response = apiService.addPayment(payment)
            if (response.isSuccessful) {
                val message = response.body()?.message
                if (message != null) {
                    Log.d("Payment", message)
                    true
                } else {
                    Log.e("Payment Error", "Respuesta sin mensaje")
                    false
                }
            } else {
                Log.e("Payment Error", "Error en la respuesta: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("Payment Exception", "Error en la petición: ${e.message}")
            false
        }
    }

    suspend fun getListPayments(userId: Int): List<PaymentResponse>? {
        return try {
            val response = apiService.getListPayments(userId)
            Log.d("LoadPayment123", "Response: ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.payments
            } else {
                Log.e("LoadPayment123", "Error en la respuesta: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("LoadPayment123", "Exception: ${e.message}", e)
            null
        }
    }
}