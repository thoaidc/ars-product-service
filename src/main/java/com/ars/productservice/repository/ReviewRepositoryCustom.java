package com.ars.productservice.repository;

import com.ars.productservice.dto.request.review.SearchReviewRequest;
import com.ars.productservice.dto.response.review.ReviewDTO;
import org.springframework.data.domain.Page;

public interface ReviewRepositoryCustom {
    Page<ReviewDTO> getAllWithPaging(SearchReviewRequest request);
}
