package learn.seata.stock.services.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import learn.seata.stock.services.entity.SeataStock;
import learn.seata.stock.services.service.SeataStockService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;


@RestController
@RequestMapping("seataStock")
public class SeataStockController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SeataStockService seataStockService;

    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param seataStock 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SeataStock> page, SeataStock seataStock) {
        return success(this.seataStockService.page(page, new QueryWrapper<>(seataStock)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.seataStockService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param seataStock 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SeataStock seataStock) {
        return success(this.seataStockService.save(seataStock));
    }

    /**
     * 修改数据
     * @param productId
     * @param count
     * @return
     */
    @PutMapping("/reduce")
    public R reduce(Integer productId, Integer count) {
        return success(this.seataStockService.reduce(productId, count));
    }

    /**
     * 修改数据
     *
     * @param seataStock 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SeataStock seataStock) {
        return success(this.seataStockService.updateById(seataStock));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.seataStockService.removeByIds(idList));
    }
}
