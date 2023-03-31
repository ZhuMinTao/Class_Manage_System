package cn.source.new_class_system.the_class.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentPerformance {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer id;
    private String commencementTerm;
    private String courseNumber;
    private String courseTitle;
    private String performance;
    private Double credit;
    private Integer totalClassHours;
    private Double gpa;
    private String evaluationMode;
    private String natureOfExamination;
    private String attributeOfCurriculum;
    private String characteristicsOfCourse;

}
