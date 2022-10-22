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
package me.zhengjie.order.service.impl;

import me.zhengjie.order.domain.TOrder;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.order.repository.TOrderRepository;
import me.zhengjie.order.service.TOrderService;
import me.zhengjie.order.service.dto.TOrderDto;
import me.zhengjie.order.service.dto.TOrderQueryCriteria;
import me.zhengjie.order.service.mapstruct.TOrderMapper;
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
* @date 2022-09-04
**/
@Service
@RequiredArgsConstructor
public class TOrderServiceImpl implements TOrderService {

    private final TOrderRepository tOrderRepository;
    private final TOrderMapper tOrderMapper;

    @Override
    public Map<String,Object> queryAll(TOrderQueryCriteria criteria, Pageable pageable){
        Page<TOrder> page = tOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(tOrderMapper::toDto));
    }

    @Override
    public List<TOrderDto> queryAll(TOrderQueryCriteria criteria){
        return tOrderMapper.toDto(tOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TOrderDto findById(Integer id) {
        TOrder tOrder = tOrderRepository.findById(id).orElseGet(TOrder::new);
        ValidationUtil.isNull(tOrder.getId(),"TOrder","id",id);
        return tOrderMapper.toDto(tOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TOrderDto create(TOrder resources) {
        return tOrderMapper.toDto(tOrderRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TOrder resources) {
        TOrder tOrder = tOrderRepository.findById(resources.getId()).orElseGet(TOrder::new);
        ValidationUtil.isNull( tOrder.getId(),"TOrder","id",resources.getId());
        tOrder.copy(resources);
        tOrderRepository.save(tOrder);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            tOrderRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TOrderDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TOrderDto tOrder : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("身份证号", tOrder.getPassengerIdcard());
            map.put("姓名", tOrder.getName());
            map.put("车次号", tOrder.getTrainId());
            map.put("发车日期", tOrder.getData());
            map.put("车厢号", tOrder.getCarriage());
            map.put("座位编号", tOrder.getNameTicket());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
