package com.radutopor.dogsshouldvote.render.breeds

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.radutopor.dogsshouldvote.render.breeddetails.BreedDetailsActivity
import com.radutopor.dogsshouldvote.ui.breeds.BreedsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { BreedsRender() }

        viewModels<BreedsViewModel>().value.observe(this) {
            it.showBreedDetailsEvent.consume { breedId ->
                startActivity(Intent(this, BreedDetailsActivity::class.java)
                    .putExtra(BreedDetailsActivity.KEY_BREED_ID, breedId))
            }
        }
    }
}
