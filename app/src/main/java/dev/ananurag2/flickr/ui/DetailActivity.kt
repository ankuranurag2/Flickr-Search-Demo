package dev.ananurag2.flickr.ui

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.ananurag2.flickr.R
import dev.ananurag2.flickr.data.db.PhotoData
import dev.ananurag2.flickr.databinding.ActivityDetailBinding
import dev.ananurag2.flickr.utils.PHOTO_DATA_EXTRA
import dev.ananurag2.flickr.utils.loadImage


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            supportPostponeEnterTransition()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }

        val photoData = intent?.getParcelableExtra<PhotoData>(PHOTO_DATA_EXTRA)
        binding.photo = photoData
        binding.fullSizeIv.loadImage(photoData?.url ?: "") {
            supportStartPostponedEnterTransition()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> supportFinishAfterTransition()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}
