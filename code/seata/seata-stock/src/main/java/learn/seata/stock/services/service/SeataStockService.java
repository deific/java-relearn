package learn.seata.stock.services.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import learn.seata.stock.services.entity.SeataStock;

public interface SeataStockService extends IService<SeataStock> {

    int reduce(Integer productId, Integer count);
}
