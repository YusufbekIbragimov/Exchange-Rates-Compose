package uz.yusufbekibragimov.valyutauz

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.appodeal.ads.Appodeal
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import dagger.hilt.android.AndroidEntryPoint
import io.bidmachine.AdsType
import uz.yusufbekibragimov.valyutauz.navigation.nav_graph.SetUpNavGraph
import uz.yusufbekibragimov.valyutauz.ui.theme.ValyutaUzTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this,  OnInitializationCompleteListener() {
            fun onInitializationComplete( initializationStatus:InitializationStatus) {
            }
        })

        Appodeal.initialize(this, "757d52a5f32ae2eee8c1d6499b39f48c756428063283794f", AdsType.Interstitial.ordinal)
        Appodeal.initialize(this, "757d52a5f32ae2eee8c1d6499b39f48c756428063283794f", AdsType.Banner.ordinal)

        Appodeal.show(this, Appodeal.INTERSTITIAL)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setContent {
            ValyutaUzTheme {
                navController = rememberNavController()
                SetUpNavGraph(navController = navController)
            }
        }
    }
}