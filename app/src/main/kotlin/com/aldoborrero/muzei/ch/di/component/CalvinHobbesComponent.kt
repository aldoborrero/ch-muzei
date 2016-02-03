package com.aldoborrero.muzei.ch.di.component

import com.aldoborrero.muzei.ch.di.module.CalvinHobbesModule
import com.aldoborrero.muzei.ch.muzei.CalvinHobbesArtSource
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = arrayOf(CalvinHobbesModule::class)) interface CalvinHobbesComponent {

    fun inject(calvinHobbesArtSource: CalvinHobbesArtSource)
}
