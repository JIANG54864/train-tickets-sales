package me.zhengjie.train.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @description /
* @author jiangjiang
* @date 2022-08-18
**/
@Entity
@Data
@Table(name="train")
public class Train implements Serializable {

    @Id
    @Column(name = "`idtrain`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "`starttime`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "开车时间")
    private String starttime;

    @Column(name = "`endtime`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "到达时间")
    private String endtime;

    public void copy(Train source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
