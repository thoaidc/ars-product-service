package com.ars.productservice.resource;

import com.ars.productservice.dto.request.voucher.SaveVoucherRequestDTO;
import com.ars.productservice.dto.request.voucher.SearchVoucherRequest;
import com.ars.productservice.service.VoucherService;
import com.dct.model.dto.response.BaseResponseDTO;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VoucherResource {
    private final VoucherService voucherService;

    public VoucherResource(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/p/v1/vouchers")
    public BaseResponseDTO getVouchersForUserOrder(@ModelAttribute SearchVoucherRequest request) {
        return voucherService.getVoucherForUserOrder(request);
    }

    @GetMapping("/v1/vouchers")
    public BaseResponseDTO getVouchersWithPaging(@ModelAttribute SearchVoucherRequest requestDTO) {
        return voucherService.getVoucherWithPaging(requestDTO);
    }

    @GetMapping("/v1/vouchers/{voucherId}")
    public BaseResponseDTO getVoucherDetail(@PathVariable Integer voucherId) {
        return voucherService.getVoucherDetail(voucherId);
    }

    @PostMapping("/v1/vouchers")
    public BaseResponseDTO createVoucher(@Valid @RequestBody SaveVoucherRequestDTO requestDTO) {
        return voucherService.createVoucher(requestDTO);
    }

    @PutMapping("/v1/vouchers")
    public BaseResponseDTO updateVoucher(@Valid @RequestBody SaveVoucherRequestDTO requestDTO) {
        return voucherService.updateVoucher(requestDTO);
    }

    @DeleteMapping("/v1/vouchers/{voucherId}")
    public BaseResponseDTO deleteVoucher(@PathVariable Integer voucherId) {
        return voucherService.deleteVoucher(voucherId);
    }
}
