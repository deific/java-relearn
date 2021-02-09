package learn.seata.order.services.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.seata.spring.annotation.GlobalTransactional;
import learn.seata.order.feign.SeataStockFeignClient;
import learn.seata.order.services.entity.SeataOrder;
import learn.seata.order.services.service.SeataOrderService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @description: $desc
 * @author: deific
 * @createDate: 2021-02-09 18:23
 * @version: 1.0
 */
@RestController
@RequestMapping("seataOrder")
public class SeataOrderController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SeataOrderService seataOrderService;

    @Resource
    private SeataStockFeignClient seataStockFeignClient;

    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param seataOrder 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SeataOrder> page, SeataOrder seataOrder) {
        return success(this.seataOrderService.page(page, new QueryWrapper<>(seataOrder)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.seataOrderService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param seataOrder 实体对象
     * @return 新增结果
     */
    @PostMapping
//    @Transactional
    @GlobalTransactional
    public R insert(@RequestBody SeataOrder seataOrder) {
        // 扣减库存
        seataStockFeignClient.reduce(Integer.valueOf(seataOrder.getCommodityCode()), seataOrder.getCount());
        // 创建订单
        boolean isOk = this.seataOrderService.save(seataOrder);
        // 模拟订单创建失败
        int result = 10 / 0;
        return success(result);
    }

    /**
     * 修改数据
     *
     * @param seataOrder 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SeataOrder seataOrder) {
        return success(this.seataOrderService.updateById(seataOrder));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.seataOrderService.removeByIds(idList));
    }
}
