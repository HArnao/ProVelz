package com.upao.velz.services

import android.content.Context
import com.upao.velz.models.RequestModel.PaymentRequest
import com.upao.velz.models.responseModel.payment.PaymentResponse
import com.upao.velz.repositories.PaymentRepository

class PaymentService(context: Context) {

    private val paymentRepository = PaymentRepository(context)

    suspend fun addPayment(payment: PaymentRequest): Boolean {
        return paymentRepository.addPayment(payment)
    }

    suspend fun getListPayments(userId: Int): List<PaymentResponse>?{
        return paymentRepository.getListPayments(userId)
    }

}