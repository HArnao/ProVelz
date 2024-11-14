package com.upao.velz.activities

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.upao.velz.R
import java.io.File

class PdfViewActivity : AppCompatActivity() {

    private lateinit var pdfRenderer: PdfRenderer
    private lateinit var pdfFileDescriptor: ParcelFileDescriptor
    private var currentPageIndex = 0
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)

        imageView = findViewById(R.id.imageViewPdf)

        val pdfPath = intent.getStringExtra("pdf_path")
        if (pdfPath != null) {
            val pdfFile = File(pdfPath)
            if (pdfFile.exists()) {
                openPdf(pdfFile)
                showPage(currentPageIndex)
            }
        }
    }

    private fun openPdf(file: File) {
        pdfFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        pdfRenderer = PdfRenderer(pdfFileDescriptor)
    }

    private fun showPage(index: Int) {
        val page = pdfRenderer.openPage(index)
        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        imageView.setImageBitmap(bitmap)
        page.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        pdfRenderer.close()
        pdfFileDescriptor.close()
    }
}
