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
package me.zhengjie.train.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author jiangjiang
* @date 2022-08-22
**/
@Entity
@Data
@Table(name="train")
public class Train implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`idtrain`")
    @ApiModelProperty(value = "主键id")
    private Integer idtrain;

    @Column(name = "`start`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "出发站台")
    private String start;

    @Column(name = "`end`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "终点站台")
    private String end;

    @Column(name = "`name`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "车次编号")
    private String name;

    @Column(name = "`startTime`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "开车时间")
    private unknowType starttime;

    @Column(name = "`endTime`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "到达时间")
    private unknowType endtime;

    public void copy(Train source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
