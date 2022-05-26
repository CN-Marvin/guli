package excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 功能描述：
 *
 * @Package: excel
 * @author: Marvin-zl
 * @date: 2022/5/26 20:11
 */
@Data
public class DemoData {
    @ExcelProperty("学生编号")
    private Integer son;
    @ExcelProperty("学生姓名")
    private String sname;
}
