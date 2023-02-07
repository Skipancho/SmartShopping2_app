package com.jjsh.smartshopping.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.LayoutCustomScoreViewBinding

class StarScoreView(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private lateinit var binding: LayoutCustomScoreViewBinding

    private var touchDownEventListener: (Int) -> Unit = {}
    private var touchUpEventListener: (Int) -> Unit = {}

    var score: Int = 0
        private set

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        isClickable = true
        initView()
        setStar()
    }

    private fun initView() {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_custom_score_view, this, false)
        binding = LayoutCustomScoreViewBinding.bind(view)
        addView(view)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                getScoreFromX(event.x)
                touchDownEventListener(score)
            }
            MotionEvent.ACTION_UP -> {
                touchUpEventListener(score)
                performClick()
            }
        }
        return super.onTouchEvent(event)
    }


    private fun getScoreFromX(x: Float) {
        with(binding) {
            score = when {
                x < ivStar2.x -> 1
                x >= ivStar2.x && x < ivStar3.x -> 2
                x >= ivStar3.x && x < ivStar4.x -> 3
                x >= ivStar4.x && x < ivStar5.x -> 4
                else -> 5
            }
        }
    }

    private fun setStar() {
        with(binding) {
            setStarImage(ivStar1, score >= 1)
            setStarImage(ivStar2, score >= 2)
            setStarImage(ivStar3, score >= 3)
            setStarImage(ivStar4, score >= 4)
            setStarImage(ivStar5, score >= 5)
        }
    }

    private fun setStarImage(imageView: ImageView, isColored: Boolean) {
        imageView.setImageResource(if (isColored) R.drawable.ic_star_50 else R.drawable.ic_empty_star_50)
    }

    fun setScore(score: Int) {
        this.score = score
        setStar()
    }

    fun setTouchDownEventListener(action: (Int) -> Unit) {
        touchDownEventListener = action
    }

    fun setTouchUpEventListener(action: (Int) -> Unit) {
        touchUpEventListener = action
    }
}

@BindingAdapter("onTouchDown")
fun StarScoreView.setTouchDownEvent(action: (Int) -> Unit) {
    setTouchDownEventListener(action)
}

@BindingAdapter("onTouchUp")
fun StarScoreView.setTouchUpEvent(action: (Int) -> Unit) {
    setTouchUpEventListener(action)
}

@BindingAdapter("score")
fun StarScoreView.setCurrentScore(score: Int) {
    setScore(score)
}