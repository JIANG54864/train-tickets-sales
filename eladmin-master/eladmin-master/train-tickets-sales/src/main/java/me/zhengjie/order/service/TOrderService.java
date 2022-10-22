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
package me.zhengjie.order.service;

import me.zhengjie.order.domain.TOrder;
import me.zhengjie.order.service.dto.TOrderDto;
import me.zhengjie.order.service.dto.TOrderQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @description 服务接口
* @author jiangjiang
* @date 2022-09-04
**/
public interface TOrderService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(TOrderQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<TOrderDto>
    */
    List<TOrderDto> queryAll(TOrderQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return TOrderDto
     */
    TOrderDto findById(Integer id);

    /**
    * 创建
    * @param resources /
    * @return TOrderDto
    */
    TOrderDto create(TOrder resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(TOrder resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Integer[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<TOrderDto> all, HttpServletResponse response) throws IOException;
}
