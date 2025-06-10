package cn.master.phoenix.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Created by 11's papa on 2025/6/10
 */
@Data
public class BaseCondition {
    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "过滤字段")
    private Map<String, List<String>> filter;

    @Deprecated
    public void setKeyword(String keyword) {
        this.keyword = transferKeyword(keyword);
    }

    public static String transferKeyword(String keyword) {
        if (StringUtils.contains(keyword, "\\") && !StringUtils.contains(keyword, "\\\\")) {
            keyword = StringUtils.replace(keyword, "\\", "\\\\");
        }
        //判断之前有没有转义过。转义过就不再转义。耍花活的自己想办法解决
        if (StringUtils.contains(keyword, "%") && !StringUtils.contains(keyword, "\\%")) {
            keyword = StringUtils.replace(keyword, "%", "\\%");
        }
        if (StringUtils.contains(keyword, "_") && !StringUtils.contains(keyword, "\\_")) {
            keyword = StringUtils.replace(keyword, "_", "\\_");
        }
        return keyword;
    }

    public void initKeyword(String keyword) {
        //  直接初始化keyword
        this.keyword = keyword;
    }
}
