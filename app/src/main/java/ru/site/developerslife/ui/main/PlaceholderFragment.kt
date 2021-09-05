package ru.site.developerslife.ui.main


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.*
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.site.developerslife.R
import ru.site.developerslife.api.DevLifeApi
import ru.site.developerslife.api.DevLifeFetchr
import ru.site.developerslife.db.PhotoItem
import ru.site.developerslife.db.TabTitlesEn

private const val TAG = "PlaceholderFragment"

class PlaceholderFragment : Fragment() {
    private lateinit var category: TabTitlesEn
    private lateinit var pageViewModel: PageViewModel
    private lateinit var sectionLabel: TextView
    private lateinit var photoView: ImageView
    private lateinit var previous: FloatingActionButton
    private lateinit var next: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider
            .of(this, PageModelCreator(category)).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        /*val textView: TextView =
        val imageView: ImageView =
        val previousButton: FloatingActionButton =
        val nextButton: FloatingActionButton = root.findViewById(R.id.next)*/
        sectionLabel = root.findViewById(R.id.section_label)
        photoView = root.findViewById(R.id.imageView)
        previous = root.findViewById(R.id.previous)
        next = root.findViewById(R.id.next )
        pageViewModel.text.observe(this, Observer<String> {
            sectionLabel.text = it
        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            updateFabVisible()

            previous.setOnClickListener {
                pageViewModel.previous()
            }

            next.setOnClickListener {
                pageViewModel.next()
            }
        }
        val data: LiveData<PhotoItem>? = pageViewModel.getData()
        data?.observe(this, Observer<PhotoItem?> {
            updateFabVisible()

            it?.let {
                sectionLabel.text = it.description

                Glide.with(this).load(it.gifURL)
                    .thumbnail(
                        Glide.with(this).load(Uri.parse("file:///android_asset/loading.gif"))
                    )
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(photoView)
            }


        })

        // Загрузим дефолтное состояние
        sectionLabel.text = getString(R.string.hello_blank_fragment)
        Glide.with(this)
            .load(Uri.parse("file:///android_asset/loading.gif"))
            .fitCenter()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(photoView);
    }

    fun updateFabVisible() {
        if (pageViewModel.yetAnotherPost()) next.show() else next.hide()
        if (pageViewModel.isHistoryEmpty()) previous.hide() else previous.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*pageViewModel.photoItemLiveData.observe(
            viewLifecycleOwner,
            Observer { photoItems ->
                photoRecyclerView.adapter = PhotoAdapter(photoItems)
            }
        )*/
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}