package com.bicutoru.qrcodegeneratorkotlin

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bicutoru.qrcodegeneratorkotlin.databinding.ActivityMainBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var editLink: EditText? = null
    private var btnGenerate: Button? = null
    private var imgQRCode: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()

        btnGenerate!!.setOnClickListener {
            if (TextUtils.isEmpty(editLink!!.text.toString())) {
                editLink!!.error = "Link is required"
                editLink!!.requestFocus()
            } else {
                generateQRCode(editLink!!.text.toString())
            }
        }
    }

    private fun generateQRCode(link: String) {
        val qrCode = QRCodeWriter()
        try {

            val bitMatrix = qrCode.encode(link, BarcodeFormat.QR_CODE, 196, 196)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }

            imgQRCode!!.setImageBitmap(bitmap)

        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    private fun initComponents() {
        editLink = binding.editLink
        btnGenerate = binding.buttonGenerate
        imgQRCode = binding.imgQRCode
    }


}