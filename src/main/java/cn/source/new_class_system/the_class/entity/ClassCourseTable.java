package cn.source.new_class_system.the_class.entity;

import lombok.Data;

import java.util.List;

@Data
public class ClassCourseTable {

    private String whichSection;

    private List<String> courseList;
}
