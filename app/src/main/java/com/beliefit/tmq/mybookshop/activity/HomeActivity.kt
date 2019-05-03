package com.beliefit.tmq.mybookshop.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.beliefit.tmq.mybookshop.R
import com.beliefit.tmq.mybookshop.adapter.BookListRVAdapter
import com.beliefit.tmq.mybookshop.apicontext.ApiService
import com.beliefit.tmq.mybookshop.model.BookEntity
import com.beliefit.tmq.mybookshop.model.BookListResponse
import com.beliefit.tmq.mybookshop.util.BookRepo
import com.beliefit.tmq.mybookshop.util.SharedPrefUtil
import com.beliefit.tmq.mybookshop.viewmodel.HomeActivityViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.concurrent.thread

class HomeActivity : AppCompatActivity() {


    private lateinit var homeViewModel: HomeActivityViewModel
    var rvAdapter: BookListRVAdapter? = null
    private lateinit var bookRepo: BookRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        bookRepo = BookRepo(this)
        homeViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel::class.java)

        homeViewModel.bookListResponseSuccessful.observe(this, Observer<BookListResponse> { response ->
            loading_progress_home.visibility = View.GONE
            var bookList: List<BookEntity>? = response.data as List<BookEntity>?
            updateMapping(bookList)
        })

        homeViewModel.bookEntity.observe(this, Observer {
            bookListResponseSuccessfull()
        })

        homeViewModel.bookListResponseError.observe(this, Observer<Throwable> { error ->
            bookListResponseError(error)
        })

        homeViewModel.fetchBookListData()
    }

    override fun onResume() {
        super.onResume()
        updateMapping(homeViewModel.bookEntity.value)
    }

    private fun updateMapping(bookList: List<BookEntity>?) {
        thread {
            bookList ?: return@thread
            bookList.forEach {
                it.isWished = bookRepo.getWishStatus(it.id)
                it.isCarted = bookRepo.getCartStatus(it.id)
            }
            homeViewModel.bookEntity.postValue(bookList)
        }
    }


    private fun bookListResponseError(error: Throwable?) {
        loading_progress_home.visibility = View.GONE
        Toast.makeText(this, error?.localizedMessage, Toast.LENGTH_LONG).show()
    }

    private fun bookListResponseSuccessfull() {
        runOnUiThread {
            rvAdapter?.books = homeViewModel.bookEntity.value
            rvAdapter?.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {
        rvAdapter = BookListRVAdapter(this)
        home_recycle_view.layoutManager = LinearLayoutManager(this)
        home_recycle_view.adapter = rvAdapter
    }

    fun loadImageFromNetworkOrCache(
        holder: BookListRVAdapter.ViewHolder,
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.wish_list -> {
                gotoWishListActivity()
            }
            R.id.cart_list -> {
                gotoCartListActivity()
            }
            R.id.signout -> {
                doSignout()
            }
        }

        return true
    }

    private fun gotoWishListActivity() {
        val intent = Intent(this, WishListActivity::class.java)
        startActivity(intent)
    }

    private fun gotoCartListActivity() {
        val intent = Intent(this, CartListActivity::class.java)
        startActivity(intent)
    }

    fun gotoDetailViewActivity(book: BookEntity?) {
        val intent = Intent(this, DetailViewActivity::class.java)
        intent.putExtra(getString(R.string.book_parcelable), book)
        startActivity(intent)
    }

    private fun doSignout() {
        SharedPrefUtil.setUserHasLoggedInPref(false, this)
        gotoLoginActivity()
    }

    private fun gotoLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    fun removeBookFromCartList(position: Int) {
        val book: BookEntity = homeViewModel.bookEntity.value?.get(position) ?: return
        bookRepo.insertOrUpdateCart(book, false)
    }

    fun addBookToCartList(position: Int) {
        val book: BookEntity = homeViewModel.bookEntity.value?.get(position) ?: return
        bookRepo.insertOrUpdateCart(book, true)
    }

    fun removeBookFromWishList(position: Int) {
        val book: BookEntity = homeViewModel.bookEntity.value?.get(position) ?: return
        bookRepo.insertOrUpdateWish(book, false)
    }

    fun addBookToWishList(position: Int) {
        val book: BookEntity = homeViewModel.bookEntity.value?.get(position) ?: return
        bookRepo.insertOrUpdateWish(book, true)
    }

    override fun onDestroy() {
        ApiService.disposable?.dispose()
        super.onDestroy()
    }

}
