package com.tv.maze.utils

import com.tv.maze.models.Episode
import com.tv.maze.models.Season

//TODO use for UI tests later
class SeasonsMocks {
    companion object {
        val seasons = arrayListOf(
            Season(
                id = 1,
                number = 1,
                episodes = arrayListOf(
                    Episode(
                        id = 1,
                        number = 1,
                        name = "The National Anthem",
                        summary = "",
                        season = 1,
                    ),
                    Episode(
                        id = 2,
                        number = 2,
                        name = "Fifteen Million Merits",
                        summary = "",
                        season = 1,
                    ),
                    Episode(
                        id = 3,
                        number = 3,
                        name = "The Entire History of You",
                        summary = "",
                        season = 1,
                    )
                )
            ),
            Season(
                id = 2,
                number = 2,
                episodes = arrayListOf(
                    Episode(
                        id = 4,
                        number = 4,
                        name = "Be Right Back",
                        summary = "",
                        season = 2,
                    ),
                    Episode(
                        id = 5,
                        number = 5,
                        name = "White Bear",
                        summary = "",
                        season = 2,
                    ),
                    Episode(
                        id = 6,
                        number = 6,
                        name = "The Waldo Moment",
                        summary = "",
                        season = 2,
                    )
                )
            ),
            Season(
                id = 3,
                number = 3,
                episodes = arrayListOf(
                    Episode(
                        id = 7,
                        number = 7,
                        name = "Nosedive",
                        summary = "",
                        season = 3,
                    ),
                    Episode(
                        id = 8,
                        number = 8,
                        name = "Playtest",
                        summary = "",
                        season = 3,
                    ),
                    Episode(
                        id = 9,
                        number = 9,
                        name = "Shut Up and Dance",
                        summary = "",
                        season = 3,
                    ),
                    Episode(
                        id = 10,
                        number = 10,
                        name = "San Junipero",
                        summary = "",
                        season = 3,
                    )
                )
            )
        )
    }
}