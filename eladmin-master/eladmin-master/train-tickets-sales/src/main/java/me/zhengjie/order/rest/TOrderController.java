/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.order.rest;

import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.order.domain.TOrder;
import me.zhengjie.order.service.TOrderService;
import me.zhengjie.order.service.dto.TOrderQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author jiangjiang
* @date 2022-09-04
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "订单信息管理")
@RequestMapping("/api/tOrder")
public class TOrderController {

    private final TOrderService tOrderService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tOrder:list')")
    public void exportTOrder(HttpServletResponse response, TOrderQueryCriteria criteria) throws IOException {
        tOrderService.download(tOrderService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询订单信息")
    @ApiOperation("查询订单信息")
//    @PreAuthorize("@el.check('tOrder:list')")
    @AnonymousAccess
    public ResponseEntity<Object> queryTOrder(TOrderQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(tOrderService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增订单信息")
    @ApiOperation("新增订单信息")
//    @PreAuthorize("@el.check('tOrder:add')")
    @AnonymousAccess
    public ResponseEntity<Object> createTOrder(@Validated @RequestBody TOrder resources){
        return new ResponseEntity<>(tOrderService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改订单信息")
    @ApiOperation("修改订单信息")
//    @PreAuthorize("@el.check('tOrder:edit')")
    @AnonymousAccess
    public ResponseEntity<Object> updateTOrder(@Validated @RequestBody TOrder resources){
        tOrderService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除订单信息")
    @ApiOperation("删除订单信息")
//    @PreAuthorize("@el.check('tOrder:del')")
    @AnonymousAccess
    public ResponseEntity<Object> deleteTOrder(@RequestBody Integer[] ids) {
        tOrderService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
