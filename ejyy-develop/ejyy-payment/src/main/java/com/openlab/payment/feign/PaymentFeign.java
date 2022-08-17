package com.openlab.payment.feign;

import com.openlab.common.dto.CompanyUserInformation;
import com.openlab.common.dto.CompanyUserPart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("userDev-server")
public interface PaymentFeign {
    @GetMapping("/user/companyUser")
    public CompanyUserPart getPropertyCompanyUser(CompanyUserInformation companyUserInformation);
}
