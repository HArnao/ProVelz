package com.upao.velz.controllers

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upao.velz.models.RequestModel.PaymentRequest
import com.upao.velz.models.responseModel.payment.PaymentResponse
import com.upao.velz.services.PaymentService
import kotlinx.coroutines.launch

class PaymentController(context: Context) : ViewModel()  {

    private val paymentService = PaymentService(context)

    fun addPayment(payment: PaymentRequest) {
        viewModelScope.launch {
            val success = paymentService.addPayment(payment)
            if (success) {
                Log.d("Pago", "Pago Exitoso")
            } else {
                Log.e("Pago", "Pago Fallido")
            }
        }
    }

    fun getListPayments(userId: Int, callback: (List<PaymentResponse>?) -> Unit) {
        viewModelScope.launch {
            val payments = paymentService.getListPayments(userId)
            callback(payments)
        }
    }
}