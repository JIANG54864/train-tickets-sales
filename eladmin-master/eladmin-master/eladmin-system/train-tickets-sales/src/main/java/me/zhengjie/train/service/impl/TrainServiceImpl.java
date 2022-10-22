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
package me.zhengjie.train.service.impl;

import me.zhengjie.train.domain.Train;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.train.repository.TrainRepository;
import me.zhengjie.train.service.TrainService;
import me.zhengjie.train.service.dto.TrainDto;
import me.zhengjie.train.service.dto.TrainQueryCriteria;
import me.zhengjie.train.service.mapstruct.TrainMapper;
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
* @date 2022-08-22
**/
@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;

    @Override
    public Map<String,Object> queryAll(TrainQueryCriteria criteria, Pageable pageable){
        Page<Train> page = trainRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(trainMapper::toDto));
    }

    @Override
    public List<TrainDto> queryAll(TrainQueryCriteria criteria){
        return trainMapper.toDto(trainRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TrainDto findById(Integer idtrain) {
        Train train = trainRepository.findById(idtrain).orElseGet(Train::new);
        ValidationUtil.isNull(train.getIdtrain(),"Train","idtrain",idtrain);
        return trainMapper.toDto(train);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TrainDto create(Train resources) {
        return trainMapper.toDto(trainRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Train resources) {
        Train train = trainRepository.findById(resources.getIdtrain()).orElseGet(Train::new);
        ValidationUtil.isNull( train.getIdtrain(),"Train","id",resources.getIdtrain());
        train.copy(resources);
        trainRepository.save(train);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer idtrain : ids) {
            trainRepository.deleteById(idtrain);
        }
    }

    @Override
    public void download(List<TrainDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TrainDto train : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("出发站台", train.getStart());
            map.put("终点站台", train.getEnd());
            map.put("车次编号", train.getName());
            map.put("开车时间", train.getStarttime());
            map.put("到达时间", train.getEndtime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}