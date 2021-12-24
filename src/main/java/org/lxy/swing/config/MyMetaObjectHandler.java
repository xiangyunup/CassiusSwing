package org.lxy.swing.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "isValid", Integer.class, 1);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateBy", String.class, getUpdateBy());
    }

    /**
     * 严格模式填充策略,默认有值不覆盖,如果提供的值为null也不填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter("updateTime")) {
            if (null != this.getFieldValByName("updateTime", metaObject)) {
                // 默认非空则赋值
                this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
            } else {
                // 起始版本 3.3.3(推荐)
                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
            }
        }
        this.strictUpdateFill(metaObject, "updateBy", String.class, getUpdateBy());
    }

    private String getUpdateBy(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String updateBy = null;
        for (StackTraceElement stackTraceElement : stackTrace) {
            if(stackTraceElement.getClassName().contains("org.lxy")
                && !stackTraceElement.getMethodName().startsWith("getUpdateBy")){
                //调用的类名
                String className = stackTraceElement.getClassName();
                //调用的方法名
                String methodName = stackTraceElement.getMethodName();
                //调用的行数
                int lineNumber = stackTraceElement.getLineNumber();
                updateBy = className+"."+methodName+"."+lineNumber;
                if(!stackTraceElement.getMethodName().startsWith("update") ){
                    break;
                }
            }
        }
        if(updateBy != null && updateBy.length() > 255){
            updateBy = updateBy.substring(updateBy.length() - 255,updateBy.length()-1);
        }
        return updateBy;
    }

}
