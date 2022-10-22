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
package me.zhengjie.ticket.rest;

import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.ticket.domain.Ticket;
import me.zhengjie.ticket.service.TicketService;
import me.zhengjie.ticket.service.dto.TicketQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author jiangjiang
* @date 2022-08-29
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "车票管理管理")
@RequestMapping("/api/ticket")
@Transactional(rollbackFor=Exception.class)
public class TicketController {

    private final TicketService ticketService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ticket:list')")
    public void exportTicket(HttpServletResponse response, TicketQueryCriteria criteria) throws IOException {
        ticketService.download(ticketService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询车票管理")
    @ApiOperation("查询车票管理")
//    @PreAuthorize("@el.check('ticket:list')")
    @AnonymousAccess
    public ResponseEntity<Object> queryTicket(TicketQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(ticketService.queryAll(criteria,pageable),HttpStatus.OK);
    }


    @PostMapping
    @Log("新增车票管理")
    @ApiOperation("新增车票管理")
    @PreAuthorize("@el.check('ticket:add')")
    public ResponseEntity<Object> createTicket(@Validated @RequestBody Ticket resources){
        return new ResponseEntity<>(ticketService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改车票管理")
    @ApiOperation("修改车票管理")
    @PreAuthorize("@el.check('ticket:edit')")
    public ResponseEntity<Object> updateTicket(@Validated @RequestBody Ticket resources){
        ticketService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除车票管理")
    @ApiOperation("删除车票管理")
    @PreAuthorize("@el.check('ticket:del')")
    public ResponseEntity<Object> deleteTicket(@RequestBody Integer[] ids) {
        ticketService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
