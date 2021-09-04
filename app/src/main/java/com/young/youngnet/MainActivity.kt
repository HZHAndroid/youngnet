package com.young.youngnet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.young.youngnet.databinding.ActivityMainBinding
import com.young.youngnet.demo.BodyClientCreatorActivity
import com.young.youngnet.demo.CommonClientCreatorActivity
import com.young.youngnet.demo.DownUpClientCreatorActivity

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.button.setOnClickListener {
            startActivity(Intent(this@MainActivity, CommonClientCreatorActivity::class.java))
        }

        mBinding.button2.setOnClickListener {
            startActivity(Intent(this@MainActivity, BodyClientCreatorActivity::class.java))
        }

        mBinding.button3.setOnClickListener {
            startActivity(Intent(this@MainActivity, DownUpClientCreatorActivity::class.java))
        }
    }
}