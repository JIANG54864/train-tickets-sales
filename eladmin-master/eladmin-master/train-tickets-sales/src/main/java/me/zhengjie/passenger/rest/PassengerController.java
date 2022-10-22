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
package me.zhengjie.passenger.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.passenger.domain.Passenger;
import me.zhengjie.passenger.service.PassengerService;
import me.zhengjie.passenger.service.dto.PassengerQueryCriteria;
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
* @date 2022-08-30
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "旅客管理管理")
@RequestMapping("/api/passenger")
public class PassengerController {

    private final PassengerService passengerService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('passenger:list')")
    public void exportPassenger(HttpServletResponse response, PassengerQueryCriteria criteria) throws IOException {
        passengerService.download(passengerService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询旅客管理")
    @ApiOperation("查询旅客管理")
    @PreAuthorize("@el.check('passenger:list')")
    public ResponseEntity<Object> queryPassenger(PassengerQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(passengerService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增旅客管理")
    @ApiOperation("新增旅客管理")
    @PreAuthorize("@el.check('passenger:add')")
    public ResponseEntity<Object> createPassenger(@Validated @RequestBody Passenger resources){
        return new ResponseEntity<>(passengerService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改旅客管理")
    @ApiOperation("修改旅客管理")
    @PreAuthorize("@el.check('passenger:edit')")
    public ResponseEntity<Object> updatePassenger(@Validated @RequestBody Passenger resources){
        passengerService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除旅客管理")
    @ApiOperation("删除旅客管理")
    @PreAuthorize("@el.check('passenger:del')")
    public ResponseEntity<Object> deletePassenger(@RequestBody Integer[] ids) {
        passengerService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
