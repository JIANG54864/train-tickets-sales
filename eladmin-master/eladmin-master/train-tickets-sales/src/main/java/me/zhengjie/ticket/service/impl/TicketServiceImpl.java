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
package me.zhengjie.ticket.service.impl;

import me.zhengjie.ticket.domain.Ticket;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.ticket.repository.TicketRepository;
import me.zhengjie.ticket.service.TicketService;
import me.zhengjie.ticket.service.dto.TicketDto;
import me.zhengjie.ticket.service.dto.TicketQueryCriteria;
import me.zhengjie.ticket.service.mapstruct.TicketMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @description 服务实现
* @author jiangjiang
* @date 2022-08-29
**/
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    public Map<String,Object> queryAll(TicketQueryCriteria criteria, Pageable pageable){
        Page<Ticket> page = ticketRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(ticketMapper::toDto).getContent(),page.getTotalElements());
    }

//    @Override
//    public Map<String,Object> querySale(TicketQueryCriteria criteria, Pageable pageable){
//        Page<Ticket> page = ticketRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
//        return PageUtil.toPage(page.map(ticketMapper::toDto));
//    }

    @Override
    public List<TicketDto> queryAll(TicketQueryCriteria criteria){
        return ticketMapper.toDto(ticketRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TicketDto findById(Integer idticket) {
        Ticket ticket = ticketRepository.findById(idticket).orElseGet(Ticket::new);
        ValidationUtil.isNull(ticket.getIdticket(),"Ticket","idticket",idticket);
        return ticketMapper.toDto(ticket);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TicketDto create(Ticket resources) {
        return ticketMapper.toDto(ticketRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Ticket resources) {
        Ticket ticket = ticketRepository.findById(resources.getIdticket()).orElseGet(Ticket::new);
        ValidationUtil.isNull( ticket.getIdticket(),"Ticket","id",resources.getIdticket());
        ticket.copy(resources);
        ticketRepository.save(ticket);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer idticket : ids) {
            ticketRepository.deleteById(idticket);
        }
    }

    @Override
    public void download(List<TicketDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TicketDto ticket : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("发车日期", ticket.getData());
            map.put("座位编号", ticket.getNameTicket());
            map.put("是否售出", ticket.getIsSold());
            map.put("车厢号", ticket.getCarriage());
            map.put("车次号", ticket.getTrainId());
            map.put("座位类型", ticket.getSeatType());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
