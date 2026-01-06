package com.ars.productservice.service;

import com.ars.productservice.dto.request.review.SaveReviewRequest;
import com.ars.productservice.dto.request.review.SearchReviewRequest;
import com.dct.model.dto.response.BaseResponseDTO;

public interface ReviewService {
    BaseResponseDTO getAllWithPaging(SearchReviewRequest request);
    BaseResponseDTO saveReview(SaveReviewRequest request);
    BaseResponseDTO deleteReview(Integer reviewId);
}
