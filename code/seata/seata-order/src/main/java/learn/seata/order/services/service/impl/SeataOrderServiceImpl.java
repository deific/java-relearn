package learn.seata.order.services.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.seata.order.services.dao.SeataOrderDao;
import learn.seata.order.services.entity.SeataOrder;
import learn.seata.order.services.service.SeataOrderService;
import org.springframework.stereotype.Service;

/**
 * @description: $desc
 * @author: deific
 * @createDate: 2021-02-09 18:23
 * @version: 1.0
 */
@Service("seataOrderService")
public class SeataOrderServiceImpl extends ServiceImpl<SeataOrderDao, SeataOrder> implements SeataOrderService {

}
