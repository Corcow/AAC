package study.junghoon.app.aac_study

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /*https://codelabs.developers.google.com/codelabs/android-databinding/#0
    * codelab 에 있는 DataBinding 예제를 구현해보자
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        go_to_plain_activity.setOnClickListener {

        }

    }
}
