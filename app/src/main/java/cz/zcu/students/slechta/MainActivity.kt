package cz.zcu.students.slechta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.zcu.students.slechta.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // lateinit - we promise to initialize this variable later
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnTakePhoto.setOnClickListener {
            val intent = Intent(this, ScannerActivity::class.java)
            startActivity(intent)
        }
    }
}