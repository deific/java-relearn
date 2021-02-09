package learn.seata.stock.services.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.seata.stock.services.dao.SeataStockDao;
import learn.seata.stock.services.entity.SeataStock;
import learn.seata.stock.services.service.SeataStockService;
import org.springframework.stereotype.Service;

@Service("seataStockService")
public class SeataStockServiceImpl extends ServiceImpl<SeataStockDao, SeataStock> implements SeataStockService {

    /**
     * 扣减库存
     * @param productId
     * @param count
     * @return
     */
    @Override
    public int reduce(Integer productId, Integer count) {
        return this.baseMapper.reduce(productId, count);
    }
}
