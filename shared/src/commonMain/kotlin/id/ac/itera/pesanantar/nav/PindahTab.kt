package id.ac.itera.pesanantar.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

fun pindahTab(nav: NavController, rute: Any) {
    nav.navigate(rute) {
        popUpTo(nav.graph.findStartDestination().route!!) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}