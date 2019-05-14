package com.atguigu.gmall.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageInfoVo implements Serializable{

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("总页数")
    private Long totalPage;

    @ApiModelProperty("当前页记录数")
    private Long pageSize;

    @ApiModelProperty("分页数据")
    private List<? extends Object> list;

    @ApiModelProperty("当前页数")
    private Long pageNum;

    public static PageInfoVo getVo(IPage iPage,Long size){
        PageInfoVo pageInfoVo = new PageInfoVo(iPage.getTotal(), iPage.getPages(), size, iPage.getRecords(),
                iPage.getCurrent());
        return pageInfoVo;
    }

}
