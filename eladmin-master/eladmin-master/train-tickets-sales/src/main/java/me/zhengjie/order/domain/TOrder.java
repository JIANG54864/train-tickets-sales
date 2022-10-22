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
package me.zhengjie.order.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @description /
* @author jiangjiang
* @date 2022-09-04
**/
@Entity
@Data
@Table(name="t_order")
public class TOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "主键id")
    private Integer id;

    @Column(name = "`passenger_idcard`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "身份证号")
    private String passengerIdcard;

    @Column(name = "`name`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "姓名")
    private String name;

    @Column(name = "`train_id`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "车次号")
    private String trainId;

    @Column(name = "`data`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "发车日期")
    private Timestamp data;

    @Column(name = "`carriage`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "车厢号")
    private String carriage;

    @Column(name = "`name_ticket`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "座位编号")
    private String nameTicket;

    public void copy(TOrder source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
