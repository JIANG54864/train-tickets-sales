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
package me.zhengjie.passenger.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @description /
* @author jiangjiang
* @date 2022-08-30
**/
@Data
public class PassengerDto implements Serializable {

    /** 主键id */
    private Integer idPassenger;

    /** 身份证号 */
    private String idCard;

    /** 姓名 */
    private String name;

    /** 性别 */
    private String sex;
}
