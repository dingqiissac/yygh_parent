package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "获取所有的医院设置信息")
    @GetMapping("findAll")
    public Result findAllHospitalSet(){
        List<HospitalSet> list = hospitalSetService.list();
        Result<List<HospitalSet>> ok = Result.ok(list);
        return ok;
    }

    @ApiOperation(value = "逻辑删除医院信息")
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable Long id){
        boolean b = hospitalSetService.removeById(id);
        if (b) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //条件查询接口
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospSet (@PathVariable Long current, @PathVariable Long limit,
                                   @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
        Page<HospitalSet> page = new Page<>(current, limit);

        QueryWrapper<HospitalSet> hospitalSetQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHosname())){
            hospitalSetQueryWrapper.like("hosname",hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHoscode())){
            hospitalSetQueryWrapper.eq("hoscode",hospitalSetQueryVo.getHoscode());
        }

        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, hospitalSetQueryWrapper);
        return Result.ok(hospitalSetPage);
    }
    //添加医院接口
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody(required = true) HospitalSet hospitalSet){
        //状态
        hospitalSet.setStatus(1);
        //key
        Random random = new Random();
        String encrypt = MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000));
        hospitalSet.setSignKey(encrypt);
        hospitalSet.setCreateTime(new Date());
        boolean save = hospitalSetService.save(hospitalSet);

        if (save) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    //根据id查找数据
    @GetMapping("getHospitalSet/{id}")
    public Result getHospitalSet(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }
    //修改医院数据
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
        boolean b = hospitalSetService.updateById(hospitalSet);

        if (b) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //批量删除接口
    @PostMapping("deleteBatchHospitalSet")
    public Result deleteBatchHospitalSet(@RequestBody List<Long> ids){
        boolean b = hospitalSetService.removeByIds(ids);
        if (b) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

}


