package chun.project.movieapp.screen.home.`interface`

import chun.project.movieapp.model.TrendingModel

interface HomeListener {
    fun onTrendingClick(trendingModel: TrendingModel, position: Int)
}