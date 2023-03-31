package cn.source.new_class_system.base.constants;

public enum SexEnum {

    MAN(0,"男"),

    WOMAN(1,"女");

    private Integer id;

    private String sexName;

    SexEnum(Integer id,String sexName){
        this.id = id;
        this.sexName = sexName;
    }
}
