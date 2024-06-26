package com.raineyi.moviekp.data.mapper

import com.raineyi.moviekp.data.database.dbmodel.DescriptionDbModel
import com.raineyi.moviekp.data.network.model.DescriptionDto
import com.raineyi.moviekp.domain.entities.Description
import javax.inject.Inject

class DescriptionMapper @Inject constructor() {

    fun mapDtoToDescription(descriptionDto: DescriptionDto): Description {
        return Description(
            movieId = descriptionDto.movieId,
            description = descriptionDto.description ?: ""
        )
    }

    fun mapDbModelToDescription(descriptionDbModel: DescriptionDbModel): Description {
        return Description(
            movieId = descriptionDbModel.movieId,
            description = descriptionDbModel.description ?: ""
        )
    }

    fun mapDescriptionToDescriptionDbModel(description: Description): DescriptionDbModel {
        return DescriptionDbModel(
            movieId = description.movieId,
            description = description.description
        )
    }
}