package com.ars.productservice.repository;

import com.ars.productservice.dto.request.voucher.SearchVoucherRequest;
import com.ars.productservice.dto.response.voucher.VoucherDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VoucherRepositoryCustom {
    List<VoucherDTO> getVoucherForUserOrder(SearchVoucherRequest request);
    Page<VoucherDTO> getWithPaging(SearchVoucherRequest request);
}
