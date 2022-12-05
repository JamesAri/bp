package cz.zcu.students.slechta

import android.Manifest.permission.CAMERA
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import cz.zcu.students.slechta.databinding.ActivityScannerBinding

const val CAMERA_PERMISSION_CODE: Int = 500
const val REQUEST_IMAGE_CAPTURE_CODE: Int = 1


class ScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScannerBinding

    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnDetect.setOnClickListener {
            detectText()
        }

        binding.idBtnSnap.setOnClickListener {
            if (checkPermissions()) {
                captureImage()
            } else {
                requestPermissions()
            }
        }
    }

    private fun detectText() {
        val image: InputImage = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image).addOnSuccessListener {
            val builder = StringBuilder()
            for (block: Text.TextBlock in it.textBlocks) {
                val blockText = block.text
                for (line: Text.Line in block.lines) {
                    for(element: Text.Element in line.elements) {
                        val elementText = element.text
                        builder.append(elementText)
                    }
                    binding.idTVDetectText.text = blockText
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to detect text from image", Toast.LENGTH_SHORT)
                .show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {
            val extras = data?.extras
            bitmap = extras?.get("data") as Bitmap
            binding.idIVCaptureImage.setImageBitmap(bitmap)
        }
    }

    // TODO: refactor deprecated API methods

    private fun captureImage() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePicture.resolveActivity(packageManager) != null) {
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE_CODE)
        }
    }

    private fun checkPermissions(): Boolean =
        ContextCompat.checkSelfPermission(
            applicationContext,
            CAMERA
        ) == PackageManager.PERMISSION_GRANTED


    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA), CAMERA_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT)
                        .show()
                    captureImage()
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Toast.makeText(
                        this,
                        "Feature is unavailable, please enable camera permission",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }


}