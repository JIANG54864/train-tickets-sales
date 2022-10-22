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
import java.io.Serializable;

/**
* @description /
* @author jiangjiang
* @date 2022-08-18
**/
@Data
public class TrainDto implements Serializable {

    /** 主键id */
    private Integer idtrain;

    /** 出发站台 */
    private String start;

    /** 终点站台 */
    private String end;

    /** 车次编号 */
    private String name;

    /** 开车时间 */
    private String starttime;

    /** 到达时间 */
    private String endtime;
}
