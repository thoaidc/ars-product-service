package com.ars.productservice.service.impl;

import com.ars.productservice.dto.request.voucher.SaveVoucherRequestDTO;
import com.ars.productservice.dto.request.voucher.SearchVoucherRequest;
import com.ars.productservice.dto.response.voucher.VoucherDTO;
import com.ars.productservice.entity.Voucher;
import com.ars.productservice.repository.VoucherRepository;
import com.ars.productservice.service.VoucherService;
import com.dct.model.common.DateUtils;
import com.dct.model.constants.BaseDatetimeConstants;
import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.exception.BaseBadRequestException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {
    private static final String ENTITY_NAME = "com.ars.productservice.service.impl.VoucherServiceImpl";
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public BaseResponseDTO getVoucherWithPaging(SearchVoucherRequest request) {
        Page<VoucherDTO> voucherPage = voucherRepository.getWithPaging(request);
        return BaseResponseDTO.builder().total(voucherPage.getTotalElements()).ok(voucherPage.getContent());
    }

    @Override
    public BaseResponseDTO getVoucherDetail(Integer voucherId) {
        Optional<Voucher> voucherOptional = voucherRepository.findById(voucherId);

        if (voucherOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, "Voucher not found");
        }

        Voucher voucher = voucherOptional.get();
        VoucherDTO voucherDTO = new VoucherDTO();
        BeanUtils.copyProperties(voucher, voucherDTO);
        return BaseResponseDTO.builder().ok(voucherDTO);
    }

    @Override
    @Transactional
    public BaseResponseDTO saveVoucher(SaveVoucherRequestDTO request) {
        Voucher voucher;
        String nowStr = DateUtils.now().toString(BaseDatetimeConstants.Formatter.YYYY_MM_DD_NORMALIZED);
        int now = Integer.parseInt(nowStr);
        Integer dateStarted = Optional.ofNullable(request.getDateStarted()).orElse(now);
        Integer dateExpired = request.getDateExpired();

        if (Objects.nonNull(request.getId())) {
            voucher = voucherRepository.findById(request.getId()).orElse(null);

            if (Objects.isNull(voucher)) {
                throw new BaseBadRequestException(ENTITY_NAME, "Voucher does not exists");
            }

            if (!Objects.equals(request.getDateStarted(), voucher.getDateStarted())) {
                throw new BaseBadRequestException(ENTITY_NAME, "Voucher date started must not be update");
            }
        } else {
            if (voucherRepository.existsByShopIdAndCode(request.getShopId(), request.getCode())) {
                throw new BaseBadRequestException(ENTITY_NAME, "Voucher existed");
            }

            if (dateStarted < now) {
                throw new BaseBadRequestException(ENTITY_NAME, "Invalid voucher started date");
            }

            voucher = new Voucher();
        }

        if (dateExpired != null && (dateExpired < now || dateExpired < dateStarted)) {
            throw new BaseBadRequestException(ENTITY_NAME, "Invalid voucher expired date");
        }

        BeanUtils.copyProperties(request, voucher, "id");
        return BaseResponseDTO.builder().ok(voucherRepository.save(voucher));
    }

    @Override
    @Transactional
    public BaseResponseDTO deleteVoucher(Integer voucherId) {
        voucherRepository.deleteById(voucherId);
        return BaseResponseDTO.builder().ok();
    }
}
