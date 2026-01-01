package com.ars.productservice.service;

import com.ars.productservice.dto.request.voucher.SaveVoucherRequestDTO;
import com.ars.productservice.dto.request.voucher.SearchVoucherRequest;
import com.dct.model.dto.response.BaseResponseDTO;

public interface VoucherService {
    BaseResponseDTO getVoucherForUserOrder(SearchVoucherRequest request);
    BaseResponseDTO getVoucherWithPaging(SearchVoucherRequest request);
    BaseResponseDTO getVoucherDetail(Integer voucherId);
    BaseResponseDTO createVoucher(SaveVoucherRequestDTO request);
    BaseResponseDTO updateVoucher(SaveVoucherRequestDTO request);
    BaseResponseDTO deleteVoucher(Integer voucherId);
}
