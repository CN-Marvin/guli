import com.school.acl.controller.PermissionController;
import com.school.acl.service.PermissionService;
import com.school.common.utils.R;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * 功能描述：
 *
 * @Package: PACKAGE_NAME
 * @author: Marvin-zl
 * @date: 2022/6/15 16:26
 */
public class TestDemo {
    @Resource
    PermissionService permissionService;

    @Resource
    PermissionController permissionController;

    @Test
    public void demo(){
        R r = permissionController.indexAllPermission();
        System.out.println(r);
    }
}
