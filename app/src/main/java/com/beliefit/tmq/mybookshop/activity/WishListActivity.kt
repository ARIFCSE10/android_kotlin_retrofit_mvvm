package com.beliefit.tmq.mybookshop.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.beliefit.tmq.mybookshop.R
import com.beliefit.tmq.mybookshop.adapter.WishListRVAdapter
import com.beliefit.tmq.mybookshop.util.BookRepo
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_wish_list.*
import kotlin.concurrent.thread

class WishListActivity : AppCompatActivity() {

    var rvAdapter: WishListRVAdapter? = null
    private lateinit var bookRepo: BookRepo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)
        setupRecyclerView()
        bookRepo = BookRepo(this)
        dataLoading()
    }

    private fun dataLoading() {
        thread {
            rvAdapter?.books = bookRepo.wishdb
            rvAdapter?.notifyDataSetChanged()
        }
        runOnUiThread {
            loading_progress_wish.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        rvAdapter = WishListRVAdapter(this)
        wish_recycle_view.layoutManager = LinearLayoutManager(this)
        wish_recycle_view.adapter = rvAdapter
    }

    fun loadImageFromNetworkOrCache(
        holder: WishListRVAdapter.ViewHolder,
        position: Int,
        preview: String?
    ) {
        Picasso.get()
            .load(preview)
            .placeholder(R.drawable.ic_shopping_cart_black_24dp)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(holder.image, object : Callback {
                override fun onSuccess() {
//                    Toast.makeText(context, "Loaded From Cache", Toast.LENGTH_LONG).show()
                }

                override fun onError(e: Exception?) {
                    Picasso.get()
                        .load(preview)
                        .into(holder.image)
                }
            })
    }
}
