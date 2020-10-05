package com.radutopor.dogsshouldvote.render.breeddetails

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.radutopor.dogsshouldvote.ui.breeddetails.BreedDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedDetailsActivity : AppCompatActivity() {
    companion object {
        const val KEY_BREED_ID = "breed_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { BreedDetailsRender() }

        val viewModel = viewModels<BreedDetailsViewModel>().value
        viewModel.init(intent.getStringExtra(KEY_BREED_ID)
            ?: throw IllegalStateException("Must provide KEY_BREED_ID as Intent argument")
        )
        viewModel.observe(this) {
            it.showSubBreedDetailsEvent.consume { breedId ->
                startActivity(Intent(this, BreedDetailsActivity::class.java)
                    .putExtra(KEY_BREED_ID, breedId))
            }
        }
    }
}
