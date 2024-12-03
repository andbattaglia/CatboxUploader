package com.battman.core.ui.navigation

import androidx.navigation.NavController

fun NavController.popBackStackOrFinish() {
    if (!popBackStack()) {
        context.getActivity()?.finish()
    }
}
