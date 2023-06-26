package com.enterprise.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enterprise.entity.Permission;
import com.enterprise.mapper.PermissionMapper;
import com.enterprise.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@DS("prefersmin_permission")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    /**
     * 对传入的菜单集合进行分级
     *
     * @param treeNodes 菜单集合
     * @return 分级完毕的菜单集合
     */
    public static List<Permission> recursiveQuery(List<Permission> treeNodes) {
        // 空集合
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            // 父节点为O的都是顶层菜单
            if ("0".equals(treeNode.getPid().trim())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 使用递归查询传入的节点的子节点
     *
     * @param treeNode  单个菜单节点
     * @param treeNodes 所有菜单集合
     * @return 单个菜单以及其子节点的集合
     */
    private static Permission findChildren(Permission treeNode, List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<>());
        // 循环菜单集合
        for (Permission it : treeNodes) {
            // 将传递的单个菜单节点与循环到的菜单节点对比以获取到其子节点
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

    /**
     * 分级获取菜单
     *
     * @return 已分级的菜单集合
     */
    @Override
    public List<Permission> queryAllMenu() {
        // 将所有菜单根据id排序
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id", "pid", "order_num");
        List<Permission> permissionList = this.baseMapper.selectList(queryWrapper);
        // 返回递归查询的结果
        return recursiveQuery(permissionList);
    }

    /**
     * 获取可以为父节点的菜单
     *
     * @return 所有一级菜单集合
     */
    @Override
    public List<Permission> getParentNodes() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        // 根据其父节点字段进行判断，同时进行升序排序
        queryWrapper.eq("pid", "0").orderByAsc("order_num");
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据角色ID获取对应菜单
     *
     * @param roleId 角色ID
     * @param flag   返回结果类型依据，true为已递归的分级菜单，false为未递归的直接菜单
     * @return 菜单集合
     */
    @Override
    public List<Permission> findMenuByRoleId(String roleId, boolean flag) {
        // 权限表和角色-权限表连接查询获取的指定角色ID的权限列表
        List<Permission> permissionList = this.baseMapper.findMenuByRole(roleId);
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : permissionList) {
            // 父节点为O的都是顶层菜单
            if ("0".equals(treeNode.getPid().trim())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, permissionList));
            }
        }
        return trees;
    }

    /***
     * 添加菜单
     * @param permission 菜单实体类
     * @return 添加结果
     */
    @Override
    public boolean createPermission(Permission permission) {
        int i = this.baseMapper.insert(permission);
        return i >= 1;
    }

    /**
     * 修改菜单
     *
     * @param permission 菜单实体类
     * @return 修改结果
     */
    @Override
    public boolean updatePermission(Permission permission) {
        int i = this.baseMapper.updateById(permission);
        return i >= 1;
    }

    /**
     * 递归删除菜单
     *
     * @param id 菜单ID
     */
    @Override
    public void removeChildById(String id) {
        // 获取要删除的节点的子节点
        List<String> idList = new ArrayList<>();
        this.selectChildListById(id, idList);
        // 将要删除的节点也放入集合
        idList.add(id);
        // 删除
        this.baseMapper.deleteBatchIds(idList);
    }

    /**
     * 递归获取子节点
     *
     * @param id     父节点编号
     * @param idList 存贮集合
     */
    private void selectChildListById(String id, List<String> idList) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

    /**
     * 根据管理员ID获取权限字符
     *
     * @param adminId 管理员ID
     * @return
     */
    @Override
    public List<String> findValueByAdminId(String adminId) {
        return this.baseMapper.findValueByAdminId(adminId);
    }
}
