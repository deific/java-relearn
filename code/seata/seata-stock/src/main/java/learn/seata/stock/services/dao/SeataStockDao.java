package learn.seata.stock.services.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.api.R;
import learn.seata.stock.services.entity.SeataStock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface SeataStockDao extends BaseMapper<SeataStock> {
    /**
     * 扣减库存
     * @param productId
     * @param count
     * @return
     */
    @Update("update seata_stock set stock=stock-#{count} where product_id=#{productId}")
    int reduce(@Param("productId") Integer productId, @Param("count")Integer count);
}
