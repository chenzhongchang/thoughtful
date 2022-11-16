package qrcode.weixin.service;

import com.alibaba.fastjson.JSONObject;
import csj.thoughtful.qrcode.weixin.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "根据userId生成微信二维码连接")
public interface WeiXinQrCodeService {

    /**
     * 根据userId生成微信二维码连接
     *
     * @param userId
     * @return
     */
    @GetMapping("/getQrUrl")
    @ApiOperation(value="根据userId生成微信二维码连接")
    BaseResponse<JSONObject> getQrUrl(@RequestParam("userId") Long userId);



}
