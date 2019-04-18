package com.zj.product.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zj.product.common.ServerResponse;
import com.zj.product.dao.CategoryMapper;
import com.zj.product.pojo.Category;
import com.zj.product.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 *
 *
 * Created by geely
 */
@Service("iCategoryService")
@Slf4j
public class CategoryServiceImpl implements ICategoryService {

    //private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);//这个分类是可用的

        int rowCount = categoryMapper.insert(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("更新品类参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("更新品类名字成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名字失败");
    }

    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            log.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    public ServerResponse selectCategoryAndChildrenById(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        findChildrenCategory(categorySet, categoryId);
        List<Integer> categoryList = Lists.newArrayList();
        if (categoryId != null) {
            for (Category c : categorySet) {
                categoryList.add(c.getId());
            }

        }
        return ServerResponse.createBySuccess(categoryList);
    }

    //递归算法，获取自身节点和子节点
    private Set<Category> findChildrenCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        //继续获取它所有的子节点
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category cgy : categoryList) {
            findChildrenCategory(categorySet, cgy.getId());
        }
        return categorySet;
    }




}
