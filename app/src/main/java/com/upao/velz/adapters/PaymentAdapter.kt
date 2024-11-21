package com.upao.velz.adapters

import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.element.Paragraph
import com.upao.velz.R
import com.upao.velz.activities.NiubizActivity
import com.upao.velz.activities.PdfViewActivity
import com.upao.velz.models.responseModel.payment.PaymentResponse
import java.io.File
import com.itextpdf.layout.Document

class PaymentAdapter(
    private val payments: List<PaymentResponse>, private val userName: String
) : RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_detail_payment, parent, false)
        return PaymentViewHolder(view)

    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payments = payments[position]
        holder.bind(payments)
    }

    override fun getItemCount(): Int = payments.size

    inner class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayTextView: TextView = itemView.findViewById(R.id.tv_date_day)
        private val monthTextView: TextView = itemView.findViewById(R.id.tv_date_month)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        private val priceTextView: TextView = itemView.findViewById(R.id.tv_price)
        private val statusTextView: TextView = itemView.findViewById(R.id.tv_status)
        private val dateContainerApp: LinearLayout = itemView.findViewById(R.id.date_container_app)
        private val buttonContainer: LinearLayout = itemView.findViewById(R.id.button_download_app)
        private val buttonDownload: ImageView = itemView.findViewById(R.id.button_download)
        private val cardPayPending: CardView = itemView.findViewById(R.id.card_pay_pending)


        fun bind(payments: PaymentResponse) {
           val dateParts = payments.paymentDate.split("-")
            val day = dateParts[2]
            val monthNumber = dateParts[1].toInt()

            val monthNames = listOf("ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC")
            val monthAbbreviation = monthNames[monthNumber - 1]

            dayTextView.text = day
            monthTextView.text = monthAbbreviation
            titleTextView.text = payments.appointment.treatment.name
            priceTextView.text = "Precio S/${payments.amount}"
            statusTextView.text = payments.status

            buttonDownload.setOnClickListener {
                generatePdf(payments)
            }

           if(payments.status == "Pendiente"){
                dateContainerApp.setBackgroundResource(R.drawable.fondo_amarillo)
                buttonContainer.visibility = View.GONE

               cardPayPending.setOnClickListener {
                   val intentNiubiz = Intent(itemView.context, NiubizActivity::class.java)
                   intentNiubiz.putExtra("appointment_id", payments.appointmentId)
                   itemView.context.startActivity(intentNiubiz)
               }

            } else{
                dateContainerApp.setBackgroundResource(R.drawable.fondo_verde)
                buttonContainer.visibility = View.VISIBLE
            }
        }

        private fun generatePdf(payment: PaymentResponse) {
            val pdfPath = File(
                //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), //CEL REAL
                itemView.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), // EMULADOR
                "pdf_velz_pago_${payment.appointmentId}.pdf"
            )

            try {
                val writer = PdfWriter(pdfPath)
                val pdfDocument = com.itextpdf.kernel.pdf.PdfDocument(writer)
                val document = Document(pdfDocument)

                document.add(Paragraph("************************************ DATOS DE PAGO ************************************"))
                document.add(Paragraph("- Clínica: Velz Odontology"))
                document.add(Paragraph("- Doctor: Juan Peréz"))
                document.add(Paragraph("- Monto: S/${payment.amount}"))
                document.add(Paragraph("- Usuario: ${userName}"))
                document.add(Paragraph("- Tratamiento: ${payment.appointment.treatment.name}"))
                document.add(Paragraph("- Fecha de Pago: ${payment.paymentDate}"))

                document.close()
                Toast.makeText(itemView.context, "PDF generado y guardado en Documentos", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(itemView.context, "Error generando PDF: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }


    }
}