package learn.seata.order.feign;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 库存接口服务
 * @author: deific
 * @createDate: 2021-02-09 21:37
 * @version: 1.0
 */
@FeignClient(name = "seata-stock", path = "seataStock")
public interface SeataStockFeignClient {

    @PutMapping("/reduce")
    R reduce(@RequestParam("productId") Integer productId, @RequestParam("count") Integer count);
}
