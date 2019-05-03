package com.beliefit.tmq.mybookshop.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.beliefit.tmq.mybookshop.R
import com.beliefit.tmq.mybookshop.model.BookEntity
import com.beliefit.tmq.mybookshop.util.BookRepo
import com.beliefit.tmq.mybookshop.viewmodel.DetailActivityViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_view.*
import kotlin.concurrent.thread

class DetailViewActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailActivityViewModel
    private lateinit var bookRepo: BookRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)
        setupViewModel()
        setupListeners()
    }

    private fun setupListeners() {
        wish_detail.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                addBookToWishList()
            } else {
                removeBookFromWishList()
            }
        }

        cart_detail.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                addBookToCartList()
            } else {
                removeBookFromCartList()
            }
        }
    }

    private fun removeBookFromCartList() {
        val book: BookEntity = detailViewModel.book.value ?: return
        bookRepo.insertOrUpdateCart(book, false)
    }

    private fun addBookToCartList() {
        val book: BookEntity = detailViewModel.book.value ?: return
        bookRepo.insertOrUpdateCart(book, true)
    }

    private fun removeBookFromWishList() {
        val book: BookEntity = detailViewModel.book.value ?: return
        bookRepo.insertOrUpdateWish(book, false)
    }

    private fun addBookToWishList() {
        val book: BookEntity = detailViewModel.book.value ?: return
        bookRepo.insertOrUpdateWish(book, true)
    }

    private fun setupViewModel() {
        bookRepo = BookRepo(this)
        detailViewModel = ViewModelProviders.of(this).get(DetailActivityViewModel::class.java)

        detailViewModel.book.observe(this, Observer<BookEntity> { response ->
            showBookDetail(response)
        })

        detailViewModel.wishChecked.observe(this, Observer<Boolean> { isChecked ->
            wish_detail.isChecked = isChecked
        })

        detailViewModel.cartChecked.observe(this, Observer<Boolean> { isChecked ->
            cart_detail.isChecked = isChecked
        })

        detailViewModel.setBook(intent.extras.getParcelable(getString(R.string.book_parcelable)))
    }

    private fun showBookDetail(book: BookEntity) {
        Picasso.get()
            .load(book.preview)
            .placeholder(R.drawable.ic_shopping_cart_black_24dp)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(preview_detail, object : Callback {
                override fun onSuccess() {
//                    Toast.makeText(context, "Loaded From Cache", Toast.LENGTH_LONG).show()
                }

                override fun onError(e: Exception?) {
                    Picasso.get()
                        .load(book.preview)
                        .into(preview_detail)
                }
            })

        title_detail.text = book.title
        subtitle_detail.text = book.subTitle
        description_detail.text = book.description
        created_at_detail.text = book.createdAt
        updated_at_detail.text = book.updatedAt

        thread {
            detailViewModel.wishChecked.postValue(bookRepo.getWishStatus(book.id) ?: false)
            detailViewModel.cartChecked.postValue(bookRepo.getCartStatus(book.id) ?: false)
        }
    }
}
