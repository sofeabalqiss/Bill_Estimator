package com.example.billestimator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etValue: EditText
    private lateinit var etRebate: EditText
    private lateinit var tvOutput: TextView
    private lateinit var tvCharges: TextView
    private lateinit var tvRebate: TextView
    private lateinit var btnCalculate: Button
    private lateinit var btnAbout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etValue = findViewById(R.id.etValue)
        etRebate = findViewById(R.id.etRebate)
        tvOutput = findViewById(R.id.tvOutput)
        tvCharges = findViewById(R.id.tvCharges)
        tvRebate = findViewById(R.id.tvRebate)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnAbout = findViewById(R.id.btnAbout)

        btnCalculate.setOnClickListener {
            val unitsUsed = etValue.text.toString().toIntOrNull() ?: 0
            val rebatePercentage = etRebate.text.toString().toFloatOrNull() ?: 0f

            if (unitsUsed <= 0) {
                Toast.makeText(this, getString(R.string.error_units_invalid), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (rebatePercentage < 0 || rebatePercentage > 5) {
                Toast.makeText(this, getString(R.string.error_rebate_invalid), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val totalCharges = calculateCharges(unitsUsed)
            val rebateAmount = totalCharges * (rebatePercentage / 100)
            val finalCharges = totalCharges - rebateAmount

            tvCharges.text = getString(R.string.format_rm, totalCharges)
            tvRebate.text = getString(R.string.format_rm, rebateAmount)
            tvOutput.text = getString(R.string.format_rm, finalCharges)
        }

        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun calculateCharges(unitsUsed: Int): Float {
        var remainingUnits = unitsUsed
        var totalCharges = 0f

        val block1 = minOf(200, remainingUnits)
        totalCharges += block1 * 0.218f
        remainingUnits -= block1

        val block2 = minOf(100, remainingUnits)
        totalCharges += block2 * 0.334f
        remainingUnits -= block2

        val block3 = minOf(300, remainingUnits)
        totalCharges += block3 * 0.516f
        remainingUnits -= block3

        if (remainingUnits > 0) {
            totalCharges += remainingUnits * 0.546f
        }

        return totalCharges
    }
}
