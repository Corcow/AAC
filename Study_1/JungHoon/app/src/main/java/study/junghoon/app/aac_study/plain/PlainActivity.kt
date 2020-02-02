package study.junghoon.app.aac_study.plain

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.ViewModelProviders
import study.junghoon.app.aac_study.R
import study.junghoon.app.aac_study.plain.data.Popularity
import study.junghoon.app.aac_study.plain.data.SimpleViewModel

class PlainActivity : AppCompatActivity() {

    /** private val viewModel = ViewModelProviders.of(this)[SimpleViewModel::class.java]
     * java.lang.IllegalStateException: Your activity/fragment is not yet attached to Application. You can't request ViewModel before onCreate call.
     */

    /** private lateinit var viewModel: SimpleViewModel
     * lateinit 을 이용한 viewModel 초기화 방식 -> onCreate() => initViewModel() call 반드시 필요
     */

    private val viewModel by lazy { ViewModelProviders.of(this).get(SimpleViewModel::class.java) }
    // lazy 를 통해 초기화하면 initViewModel() 을 호출할 필요없이 블록을 통해 바로 할 수 있다. 알아두자!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plain)
        // lateinit을 사용해 초기화 할 때
        // initViewModel()

        //초기에 init 값을 명시적으로 설정하는 것은 안좋은 패턴이다!!!!
        updateName()
        updateLikes()
    }

    /** private fun initViewModel() {
     * viewModel = ViewModelProviders.of(this)[SimpleViewModel::class.java]
    }*/

    // Like button onClick
    // But! Business Logic 이 액티비티 내부에 있는 것은 이상적이지 않다.
    fun onLike(view: View) {
        viewModel.onLike()
        updateLikes()
    }

    // 많은 findViewById 는 좋지 않다.
    private fun updateName() {
        findViewById<TextView>(R.id.plain_name).text = viewModel.name
        findViewById<TextView>(R.id.plain_lastname).text = viewModel.lastName
    }

    /**
     * 이 함수의 문제가 존재
     * 1. findViewById 호출을 여러번 하게 된다.
     * 2. 테스트 할 수 없는 로직이 존재
     * 3. 뷰가 변경되지 않아도 두개의 뷰를 업데이트 한다.
     */
    private fun updateLikes() {
        findViewById<TextView>(R.id.likes).text = viewModel.likes.toString()
        findViewById<ProgressBar>(R.id.progressBar).progress =
            (viewModel.likes * 100 / 5).coerceAtMost(100)
        val image = findViewById<ImageView>(R.id.imageView)

        val color = getAssociatedColor(viewModel.popularity, this)

        ImageViewCompat.setImageTintList(image, ColorStateList.valueOf(color))

        image.setImageDrawable(getDrawablePopularity(viewModel.popularity, this))
    }


    private fun getAssociatedColor(popularity: Popularity, context: Context): Int {
        return when (popularity) {
            Popularity.NORMAL -> context.theme.obtainStyledAttributes(
                intArrayOf(android.R.attr.colorForeground)
            ).getColor(0, 0x000000)
            Popularity.POPULAR -> ContextCompat.getColor(context, R.color.popular)
            Popularity.STAR -> ContextCompat.getColor(context, R.color.star)
        }
    }

    private fun getDrawablePopularity(popularity: Popularity, context: Context): Drawable? {
        return when (popularity) {
            Popularity.NORMAL -> {
                ContextCompat.getDrawable(context, R.drawable.ic_person_black_96dp)
            }
            Popularity.POPULAR -> {
                ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
            }
            Popularity.STAR -> {
                ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
            }
        }
    }


}
