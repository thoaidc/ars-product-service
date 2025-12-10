package com.ars.productservice.service.impl;

import com.ars.productservice.dto.request.voucher.SaveVoucherRequestDTO;
import com.ars.productservice.dto.request.voucher.SearchVoucherRequest;
import com.ars.productservice.repository.VoucherRepository;
import com.ars.productservice.service.VoucherService;
import com.dct.model.dto.response.BaseResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public BaseResponseDTO getVoucherWithPaging(SearchVoucherRequest request) {
        return null;
    }

    @Override
    public BaseResponseDTO getVoucherDetail(Integer voucherId) {
        return null;
    }

    @Override
    @Transactional
    public BaseResponseDTO saveVoucher(SaveVoucherRequestDTO request) {
        return null;
    }

    @Override
    @Transactional
    public BaseResponseDTO deleteVoucher(Integer voucherId) {
        voucherRepository.deleteById(voucherId);
        return BaseResponseDTO.builder().ok();
    }
}
