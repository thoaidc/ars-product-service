package com.ars.productservice.service.impl;

import com.ars.productservice.constants.ReviewConstants;
import com.ars.productservice.dto.request.review.SaveReviewRequest;
import com.ars.productservice.dto.request.review.SearchReviewRequest;
import com.ars.productservice.dto.response.review.ReviewDTO;
import com.ars.productservice.entity.Review;
import com.ars.productservice.repository.ReviewRepository;
import com.ars.productservice.service.ReviewService;

import com.dct.config.common.Common;
import com.dct.config.common.FileUtils;
import com.dct.model.constants.BaseRoleConstants;
import com.dct.model.dto.auth.BaseUserDTO;
import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.exception.BaseBadRequestException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final FileUtils fileUtils = new FileUtils();
    private static final String ENTITY_NAME = "com.ars.productservice.service.impl.ReviewServiceImpl";

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        this.fileUtils.setPrefixPath(ReviewConstants.Upload.PREFIX);
        this.fileUtils.setUploadDirectory(ReviewConstants.Upload.LOCATION);
    }

    @Override
    public BaseResponseDTO getAllWithPaging(SearchReviewRequest request) {
        Page<ReviewDTO> reviewsPage = reviewRepository.getAllWithPaging(request);
        return BaseResponseDTO.builder().total(reviewsPage.getTotalElements()).ok(reviewsPage.getContent());
    }

    @Override
    public BaseResponseDTO saveReview(SaveReviewRequest request) {
        BaseUserDTO userDTO = Common.checkUserAuthorities(request.getCustomerId());

        if (!Objects.equals(userDTO.getId(), request.getCustomerId())) {
            throw new BaseBadRequestException(ENTITY_NAME, "You do not have any permission to perform this function");
        }

        List<Review> reviews = request.getReviews().stream().map(reviewRequest -> {
            Review review = new Review();
            BeanUtils.copyProperties(request, review, "reviews");
            BeanUtils.copyProperties(reviewRequest, review, "image");
            String reviewImageUrl = fileUtils.autoCompressImageAndSave(reviewRequest.getImage());
            review.setImage(reviewImageUrl);
            return review;
        }).toList();

        return BaseResponseDTO.builder().ok(reviewRepository.saveAll(reviews));
    }

    @Override
    public BaseResponseDTO deleteReview(Integer reviewId) {
        BaseUserDTO userDTO = Common.getUserWithAuthorities();

        if (!userDTO.hasAuthority(BaseRoleConstants.ADMIN)) {
            throw new BaseBadRequestException(ENTITY_NAME, "You do not have any permission to perform this function");
        }

        reviewRepository.deleteById(reviewId);
        return BaseResponseDTO.builder().ok();
    }
}
