package com.ars.productservice.resource;

import com.ars.productservice.dto.request.review.SaveReviewRequest;
import com.ars.productservice.dto.request.review.SearchReviewRequest;
import com.ars.productservice.service.ReviewService;
import com.dct.model.dto.response.BaseResponseDTO;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReviewResource {
    private final ReviewService reviewService;

    public ReviewResource(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/p/v1/reviews")
    public BaseResponseDTO getAllWithPaging(@ModelAttribute SearchReviewRequest request) {
        return reviewService.getAllWithPaging(request);
    }

    @PostMapping("/v1/reviews")
    public BaseResponseDTO saveReview(@Valid @RequestBody SaveReviewRequest request) {
        return reviewService.saveReview(request);
    }

    @DeleteMapping("/v1/reviews/{reviewId}")
    public BaseResponseDTO deleteReview(@PathVariable Integer reviewId) {
        return reviewService.deleteReview(reviewId);
    }
}
