package cn.source.new_class_system.the_class.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class ClassRoster implements Serializable {
    @Excel(name = "姓名",orderNum = "0",width = 30)
    private String userName;
    @Excel(name = "性别",replace = {"男_1","女_0"},orderNum = "1",width = 10)
    private Integer sex;
    @Excel(name="学号",orderNum = "2",width = 50)
    private String stuCode;
    @ExcelIgnore
    private Integer classId;

}
