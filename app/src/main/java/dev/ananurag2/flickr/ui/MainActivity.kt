package dev.ananurag2.flickr.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dev.ananurag2.flickr.R
import dev.ananurag2.flickr.adapters.ImageAdapter
import dev.ananurag2.flickr.data.db.PhotoData
import dev.ananurag2.flickr.databinding.ActivityMainBinding
import dev.ananurag2.flickr.utils.AppUtils
import dev.ananurag2.flickr.utils.PHOTO_DATA_EXTRA
import dev.ananurag2.flickr.utils.RecyclerViewEventListener
import dev.ananurag2.flickr.utils.checkForInternet
import dev.ananurag2.flickr.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, RecyclerViewEventListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val gridLayoutManager by lazy {
        StaggeredGridLayoutManager(viewModel.spanCount, StaggeredGridLayoutManager.VERTICAL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        binding.searchView.setOnQueryTextListener(this)

        val imageAdapter = ImageAdapter(this)
        binding.imageRv.let {
            it.adapter = imageAdapter
            it.layoutManager = gridLayoutManager
        }

        subscribeToData(imageAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        //when span count = 2 i.e 0th item is selected
        //when span count = 3 i.e 1st item is selected and so on....
        menu?.getItem(viewModel.spanCount - 2)?.isChecked = true
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isChecked = true

        when (item.itemId) {
            R.id.two_grid -> {
                viewModel.spanCount = 2
                gridLayoutManager.spanCount = 2

            }
            R.id.three_grid -> {
                viewModel.spanCount = 3
                gridLayoutManager.spanCount = 3
            }
            R.id.four_grid -> {
                viewModel.spanCount = 4
                gridLayoutManager.spanCount = 4
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        AppUtils.hideKeyboard(this)
        currentFocus?.clearFocus()
        query ?: return true
        viewModel.fetchImage(query, checkForInternet())
        return true
    }

    //Ignore SearchView text change
    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    //callback from ImageAdapter
    override fun onPhotoSelect(data: PhotoData?, view: View) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(PHOTO_DATA_EXTRA, data)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, ViewCompat.getTransitionName(view) ?: "")
        startActivity(intent, options.toBundle())
    }

    override fun onBottomReached() {
        with(viewModel) {
            if (true == shouldLoad)
                fetchImage(isConnected = checkForInternet())
        }
    }

    private fun subscribeToData(adapter: ImageAdapter) {
        viewModel.imageList.observe(this, Observer {
            it?.let {
                if (it.size > 0) {
                    //DiffUtil is not invoked until a new list is passed, hence I am passing a new reference
                    //Issue-https://stackoverflow.com/questions/49726385/listadapter-not-updating-item-in-recyclerview
                    adapter.submitList(it.toMutableList())
                    binding.emptyTv.visibility = View.GONE
                    binding.imageRv.visibility = View.VISIBLE
                }
            }
        })

        viewModel.errorMsg.observe(this, Observer { msg ->
            msg?.let {
                binding.imageRv.visibility = View.GONE
                binding.emptyTv.run {
                    visibility = View.VISIBLE
                    text = msg
                }
            }
        })
    }
}
