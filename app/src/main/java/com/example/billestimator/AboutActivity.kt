package com.example.billestimator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // GitHub Link TextView
        val githubLinkTextView: TextView = findViewById(R.id.textView11)

        // Set the GitHub URL dynamically using string resources
        val githubUrl = "https://github.com/sofeabalqis"
        val linkText = getString(R.string.visit_github, githubUrl)

        githubLinkTextView.text = linkText

        // Make the GitHub URL clickable
        githubLinkTextView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl))
            startActivity(intent) // Open the URL in a browser
        }

        // Revert button functionality (already in place)
        val revertButton: ImageButton = findViewById(R.id.imageButton2)
        revertButton.setOnClickListener {
            finish() // Go back to the previous activity
        }
    }
}
