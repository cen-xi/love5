package util;

import org.litepal.crud.DataSupport;


//记得加上这个extends DataSupport继承啊，无语，丢人
public class People extends DataSupport {
    //主键
    private long uid;
    private String name;
    private long age;
    //在java中long型是64位的。范围：-9223372036854775808~9223372036854775807，超出会报错空指针异常


    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }
}
