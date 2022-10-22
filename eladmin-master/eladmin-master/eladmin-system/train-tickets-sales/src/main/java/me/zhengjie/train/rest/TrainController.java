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
package me.zhengjie.train.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.train.domain.Train;
import me.zhengjie.train.service.TrainService;
import me.zhengjie.train.service.dto.TrainQueryCriteria;
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
* @website https://el-admin.vip
* @author jiangjiang
* @date 2022-08-22
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "车次管理管理")
@RequestMapping("/api/train")
public class TrainController {

    private final TrainService trainService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('train:list')")
    public void exportTrain(HttpServletResponse response, TrainQueryCriteria criteria) throws IOException {
        trainService.download(trainService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询车次管理")
    @ApiOperation("查询车次管理")
    @PreAuthorize("@el.check('train:list')")
    public ResponseEntity<Object> queryTrain(TrainQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(trainService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增车次管理")
    @ApiOperation("新增车次管理")
    @PreAuthorize("@el.check('train:add')")
    public ResponseEntity<Object> createTrain(@Validated @RequestBody Train resources){
        return new ResponseEntity<>(trainService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改车次管理")
    @ApiOperation("修改车次管理")
    @PreAuthorize("@el.check('train:edit')")
    public ResponseEntity<Object> updateTrain(@Validated @RequestBody Train resources){
        trainService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除车次管理")
    @ApiOperation("删除车次管理")
    @PreAuthorize("@el.check('train:del')")
    public ResponseEntity<Object> deleteTrain(@RequestBody Integer[] ids) {
        trainService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}