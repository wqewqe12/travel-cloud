package com.travel.lpz.article.feign;

import com.travel.lpz.core.untils.R;
import com.travel.lpz.user.dto.UserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lpz
 * @title UserInfoFeignService
 * @date 2024/10/23 17:23
 * @description TODO
 */
@FeignClient("user-service")
public interface UserInfoFeignService {
    //调用user-service的getById方法
    @GetMapping("/users/getById")
    R<UserInfoDTO> getById(@RequestParam Long id);

}
