package com.school.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.acl.entity.Permission;
import com.school.acl.entity.RolePermission;
import com.school.acl.entity.User;
import com.school.acl.helper.MemuHelper;
import com.school.acl.helper.PermissionHelper;
import com.school.acl.mapper.PermissionMapper;
import com.school.acl.service.PermissionService;
import com.school.acl.service.RolePermissionService;
import com.school.acl.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private UserService userService;


    //========================递归查询所有菜单================================================

    @Override
    public List<Permission> queryAllMenu() {
        List<Permission> permissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByDesc("id"));
        return buildPermission(permissionList);
    }

    /**
     * 找到顶层菜单 递归查询底层菜单
     * @param permissionList
     * @return
     */
    private List<Permission> buildPermission(List<Permission> permissionList) {

        List<Permission> resList = new ArrayList<>();
        for (Permission permission : permissionList) {
            if ("0".equals(permission.getPid())) {
                permission.setLevel(1);
                resList.add(selectChildList(permission, permissionList));
            }
        }
        return resList;
    }

    private Permission selectChildList(Permission topPermission, List<Permission> permissionList) {

        topPermission.setChildren(new ArrayList<>());
        for (Permission permission : permissionList) {
            if (topPermission.getId().equals(permission.getPid())) {
                permission.setLevel(topPermission.getLevel() + 1);
                if(permission.getChildren() == null){
                    permission.setChildren(new ArrayList<>());
                }
                topPermission.getChildren().add(selectChildList(permission, permissionList));
            }
        }
        return topPermission;
    }

    //===================================递归删除菜单==================================

    @Override
    public void removeChildById(String id) {
        List<String> idList = new ArrayList<>();
        selectPermissionChildren(id,idList);
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    /**
     * 查询菜单的id的所有子菜单id
     * @param id 父菜单id
     * @param idList 子菜单id集合
     */
    private void selectPermissionChildren(String id, List<String> idList) {
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.eq("pid", id);
        permissionQueryWrapper.select("id");
        List<Permission> permissions = baseMapper.selectList(permissionQueryWrapper);
        permissions.stream().forEach(item -> {
            idList.add(item.getId());
            selectPermissionChildren(item.getId(), idList);
        });
    }

    //========================================给角色分配菜单============================================

    @Override
    public void saveRolePermissionRelationship(String roleId, String[] permissionIds) {
        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));

        List<RolePermission> rolePermissionList = new ArrayList<>();

        for (String permissionId : permissionIds) {
            if(StringUtils.isEmpty(permissionId)){
                continue;
            }
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        RolePermission topRolePermission = new RolePermission();
        topRolePermission.setRoleId(roleId);
        topRolePermission.setPermissionId("1");
        rolePermissionList.add(topRolePermission);

        rolePermissionService.saveBatch(rolePermissionList);
    }


    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if (rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }
        List<Permission> permissionList = bulid(allPermissionList);
        return permissionList;
    }



    /**
     * 根据用户id获取用户菜单
     */
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if (this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if (this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        List<JSONObject> result = MemuHelper.bulid(permissionList);
        return result;
    }

    /**
     * 判断用户是否系统管理员
     *
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if (null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     * 使用递归方法建菜单
     *
     * @param treeNodes
     * @return
     */
    private static List<Permission> bulid(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    private static Permission findChildren(Permission treeNode, List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<Permission>());

        for (Permission it : treeNodes) {
            if (treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

}
