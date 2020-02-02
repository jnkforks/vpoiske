package com.egorshustov.vpoiske.util

import androidx.annotation.StyleRes
import com.egorshustov.vpoiske.R

enum class AppTheme(@StyleRes val themeId: Int) {
    LIGHT_THEME(R.style.AppTheme_OverlapSystemBar_Light),
    DARK_THEME(R.style.AppTheme_OverlapSystemBar_Dark);

    fun getNext(): AppTheme {
        return values()[((ordinal + 1) % values().size)]
    }
}

enum class Sex(val value: Int) {
    ANY(0),
    FEMALE(1),
    MALE(2)
}

enum class HasPhoto(val value: Int) {
    NOT_NECESSARY(0),
    NECESSARY(1)
}

enum class Relation(
    val value: Int?,
    private val descriptionFemale: String,
    private val descriptionMale: String
) {
    NOT_DEFINED(null, "Любое", "Любое"),
    NOT_MARRIED(1, "Не замужем", "Не женат"),
    HAS_FRIEND(2, "Есть друг", "Есть подруга"),
    ENGAGED(3, "Помолвлена", "Помолвлен"),
    MARRIED(4, "Замужем", "Женат"),
    ALL_COMPLICATED(5, "Всё сложно", "Всё сложно"),
    IN_ACTIVE_SEARCH(6, "В активном поиске", "В активном поиске"),
    IN_LOVE(7, "Влюблена", "Влюблён"),
    IN_CIVIL_MARRIAGE(8, "В гражданском браке", "В гражданском браке");

    override fun toString() = if (sex == Sex.MALE) descriptionMale else descriptionFemale

    companion object {
        var sex = Sex.FEMALE
    }
}

enum class SortType(val value: Int) {
    BY_POPULARITY(0),
    BY_REGISTRATION_DATE(1)
}