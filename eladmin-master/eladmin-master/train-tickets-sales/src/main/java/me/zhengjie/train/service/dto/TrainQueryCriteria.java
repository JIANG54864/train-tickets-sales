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
package me.zhengjie.train.service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.zhengjie.annotation.Query;

/**
* @author jiangjiang
* @date 2022-08-18
**/
@Data
public class TrainQueryCriteria{
    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String start;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String end;

    /** 精确 */
    @Query
    private String name;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> starttime;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> endtime;
}
