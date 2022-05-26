package excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 *
 * @Package: excel
 * @author: Marvin-zl
 * @date: 2022/5/26 20:13
 */
public class TestEasyExcel {
    public static void main(String[] args) {
        String fileName = "E:\\project\\write.xlsx";
        EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(getData());
    }
    private static List<DemoData> getData(){
        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSon(i);
            data.setSname("dddd" + i);
            list.add(data);
        }
        return list;
    }
}
