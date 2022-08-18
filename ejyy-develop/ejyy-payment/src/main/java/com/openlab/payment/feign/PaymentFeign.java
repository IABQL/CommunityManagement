package com.openlab.payment.feign;

import com.openlab.common.dto.AllNameInfo;
import com.openlab.common.dto.CompanyUserInformation;
import com.openlab.common.dto.CompanyUserPart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("userLocal-server")
public interface PaymentFeign {
    @GetMapping("/user/companyUser")
    public CompanyUserPart getPropertyCompanyUser(CompanyUserInformation companyUserInformation);

    @GetMapping("/user/community/name")
    public AllNameInfo getAllName(@RequestParam("userId") Integer userId,@RequestParam("communityId") Long communityId);
}
