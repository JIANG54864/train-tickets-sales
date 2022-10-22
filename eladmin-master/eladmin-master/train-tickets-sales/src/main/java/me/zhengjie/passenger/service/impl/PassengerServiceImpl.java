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
package me.zhengjie.passenger.service.impl;

import me.zhengjie.passenger.domain.Passenger;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.passenger.repository.PassengerRepository;
import me.zhengjie.passenger.service.PassengerService;
import me.zhengjie.passenger.service.dto.PassengerDto;
import me.zhengjie.passenger.service.dto.PassengerQueryCriteria;
import me.zhengjie.passenger.service.mapstruct.PassengerMapper;
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
* @website https://el-admin.vip
* @description 服务实现
* @author jiangjiang
* @date 2022-08-30
**/
@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public Map<String,Object> queryAll(PassengerQueryCriteria criteria, Pageable pageable){
        Page<Passenger> page = passengerRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(passengerMapper::toDto));
    }

    @Override
    public List<PassengerDto> queryAll(PassengerQueryCriteria criteria){
        return passengerMapper.toDto(passengerRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public PassengerDto findById(Integer idPassenger) {
        Passenger passenger = passengerRepository.findById(idPassenger).orElseGet(Passenger::new);
        ValidationUtil.isNull(passenger.getIdPassenger(),"Passenger","idPassenger",idPassenger);
        return passengerMapper.toDto(passenger);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PassengerDto create(Passenger resources) {
        return passengerMapper.toDto(passengerRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Passenger resources) {
        Passenger passenger = passengerRepository.findById(resources.getIdPassenger()).orElseGet(Passenger::new);
        ValidationUtil.isNull( passenger.getIdPassenger(),"Passenger","id",resources.getIdPassenger());
        passenger.copy(resources);
        passengerRepository.save(passenger);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer idPassenger : ids) {
            passengerRepository.deleteById(idPassenger);
        }
    }

    @Override
    public void download(List<PassengerDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PassengerDto passenger : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("身份证号", passenger.getIdCard());
            map.put("姓名", passenger.getName());
            map.put("性别", passenger.getSex());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}