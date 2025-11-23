package com.ars.productservice.service.impl;

import com.ars.productservice.dto.request.attribute.SaveAttributeRequest;
import com.ars.productservice.dto.request.attribute.SearchAttributeRequest;
import com.ars.productservice.dto.response.attribute.AttributeDTO;
import com.ars.productservice.entity.Attribute;
import com.ars.productservice.repository.AttributeRepository;
import com.ars.productservice.service.AttributeService;
import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.exception.BaseBadRequestException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;
    private static final String ENTITY_NAME = "com.ars.productservice.service.impl.AttributeServiceImpl";

    public AttributeServiceImpl(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    @Override
    public BaseResponseDTO getAllWithPaging(SearchAttributeRequest requestDTO) {
        Page<AttributeDTO> attributePage = attributeRepository.getAllWithPaging(requestDTO);
        return BaseResponseDTO.builder().total(attributePage.getTotalElements()).ok(attributePage.getContent());
    }

    @Override
    public BaseResponseDTO getDetail(Integer id) {
        Optional<Attribute> attributeOptional = attributeRepository.findById(id);

        if (attributeOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, "Attribute not found!");
        }

        return BaseResponseDTO.builder().ok(attributeOptional.get());
    }

    @Override
    @Transactional
    public BaseResponseDTO save(SaveAttributeRequest request) {
        Attribute attribute;

        if (Objects.nonNull(request.getId()) && request.getId() > 0) {
            Optional<Attribute> attributeOptional = attributeRepository.findById(request.getId());

            if (attributeOptional.isEmpty()) {
                throw new BaseBadRequestException(ENTITY_NAME, "Attribute not found!");
            }

            attribute = attributeOptional.get();
        } else {
            attribute = new Attribute();
        }

        BeanUtils.copyProperties(request, attribute, "id");
        return BaseResponseDTO.builder().ok(attributeRepository.save(attribute));
    }

    @Override
    @Transactional
    public BaseResponseDTO deleteById(Integer id) {
        attributeRepository.deleteById(id);
        return BaseResponseDTO.builder().ok();
    }
}
