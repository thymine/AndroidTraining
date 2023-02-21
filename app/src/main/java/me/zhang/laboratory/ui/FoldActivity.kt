package me.zhang.laboratory.ui

import android.os.Bundle
import android.widget.Toast
import me.zhang.laboratory.databinding.ActivityFoldBinding

class FoldActivity : BaseActivity() {

    private lateinit var binding: ActivityFoldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoldBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            Toast.makeText(it.context, "被折叠的小按钮", Toast.LENGTH_SHORT).show()
        }
    }

}