package com.ars.productservice.repository;

import com.ars.productservice.dto.request.review.CheckCustomerReviewRequest;
import com.ars.productservice.dto.request.review.SearchReviewRequest;
import com.ars.productservice.dto.response.review.ReviewDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewRepositoryCustom {
    Page<ReviewDTO> getAllWithPaging(SearchReviewRequest request);
    List<ReviewDTO> getAllByCustomerIdAndProductIds(CheckCustomerReviewRequest request);
}
