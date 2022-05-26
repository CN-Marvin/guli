package excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.junit.Test;

import java.util.Map;

/**
 * 功能描述：
 *
 * @Package: excel
 * @author: Marvin-zl
 * @date: 2022/5/26 20:24
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {

    @Test
    public void demo(){
        String fileName = "E:\\project\\write.xlsx";
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("*****" + demoData);
    }
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("head" + headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
