import org.junit.Test;

/**
 * 功能描述：
 *
 * @Package: PACKAGE_NAME
 * @author: Marvin-zl
 * @date: 2022/6/12 14:19
 */
public class MathTest {


    @Test
    public void demo(){
        double i = Math.pow((346-364), 2)+Math.pow((312-364), 2)+Math.pow((387-364), 2)+Math.pow((350-364), 2)
                +Math.pow((406-364), 2) +Math.pow((364-364), 2)+Math.pow((353-364), 2)+Math.pow((338-364), 2)
                +Math.pow((392-364), 2) +Math.pow((385-364), 2)+Math.pow((372-364), 2)+Math.pow((356-364), 2);
        double v = i / 11;
        System.out.println(v);
        double sqrt = Math.sqrt(v);
        System.out.println(sqrt);
    }
    @Test
    public void demo01(){
        int i = (60 + 73 + 36 + 40 + 48 + 55 + 70 + 69 + 30 + 54) / 10;
        System.out.println(i);
    }
}
