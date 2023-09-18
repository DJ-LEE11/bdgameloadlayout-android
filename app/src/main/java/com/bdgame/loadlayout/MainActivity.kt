package com.bdgame.loadlayout

import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.bdgame.loadlayout.utils.ComposableSimulate
import com.bdgame.loadlayout.utils.setWidthHeight

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainContent())
    }
}

@ComposableSimulate
private fun AppCompatActivity.MainContent() = FrameLayout(this).apply {
    id = R.id.main_content
    setWidthHeight(MATCH_PARENT, MATCH_PARENT)
    supportFragmentManager.commit {
        replace(id, MainFragment())
    }
}