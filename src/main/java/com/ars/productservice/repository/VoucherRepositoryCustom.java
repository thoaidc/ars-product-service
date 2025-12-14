package com.ars.productservice.repository;

import com.ars.productservice.dto.request.voucher.SearchVoucherRequest;
import com.ars.productservice.dto.response.voucher.VoucherDTO;
import org.springframework.data.domain.Page;

public interface VoucherRepositoryCustom {
    Page<VoucherDTO> getWithPaging(SearchVoucherRequest request);
}
