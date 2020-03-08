package com.example.imagesearch.ui.mapper

import com.example.imagesearch.Constants
import com.example.imagesearch.domain.entity.ImageSearchDocumentsData
import com.example.imagesearch.ui.base.BaseItemViewModel

class ImageSearchDocumentMapper : EntityMapper<BaseItemViewModel,ImageSearchDocumentsData>{
    override fun fromEntity(entity: ImageSearchDocumentsData): BaseItemViewModel {
        return BaseItemViewModel().apply {
            viewType = Constants.ITEMVIEW_IMAGE
            imageUrl = entity.image_url
        }
    }

    override fun toEntity(model: BaseItemViewModel): ImageSearchDocumentsData {
        return ImageSearchDocumentsData(model.imageUrl)
    }
}