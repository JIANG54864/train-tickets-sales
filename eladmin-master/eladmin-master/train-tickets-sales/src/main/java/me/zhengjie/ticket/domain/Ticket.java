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
package me.zhengjie.ticket.domain;

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
* @date 2022-08-29
**/
@Entity
@Data
@Table(name="ticket")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`idticket`")
    @ApiModelProperty(value = "主键id")
    private Integer idticket;

    @Column(name = "`data`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "发车日期")
    private Timestamp data;

    @Column(name = "`name_ticket`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "座位编号")
    private String nameTicket;

    @Column(name = "`is_sold`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "是否售出")
    private String isSold;

    @Column(name = "`carriage`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "车厢号")
    private String carriage;

    @Column(name = "`train_id`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "车次号")
    private String trainId;

    @Column(name = "`seat_type`")
    @ApiModelProperty(value = "座位类型")
    private String seatType;

    public void copy(Ticket source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
