package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseCategoryServiceImpl implements CourseCategoryService {
    private final CourseCategoryMapper mapper;
    /**
     * 查询节点的子节点
     * 由数据库进行排序
     * 复杂度：O(nlog(n)) (取决于数据库排序)  (java部分O(n))
     * @param id
     * @return
     */
    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = mapper.selectTreeNodes(id);
        //将list转map,以备使用,排除根节点
        Map<String, CourseCategoryTreeDto> mapTemp = courseCategoryTreeDtos.stream().filter(item->!id.equals(item.getId())).collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));
        //最终返回的list
        List<CourseCategoryTreeDto> categoryTreeDtos = new ArrayList<>();
        //依次遍历每个元素,排除根节点
        courseCategoryTreeDtos.stream().filter(item->!id.equals(item.getId())).forEach(item->{
            if(item.getParentid().equals(id)){
                categoryTreeDtos.add(item);
            }
            //找到当前节点的父节点
            CourseCategoryTreeDto courseCategoryTreeDto = mapTemp.get(item.getParentid());
            if(courseCategoryTreeDto!=null){
                if(courseCategoryTreeDto.getChildrenTreeNodes() ==null){
                    courseCategoryTreeDto.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                //下边开始往ChildrenTreeNodes属性中放子节点
                courseCategoryTreeDto.getChildrenTreeNodes().add(item);
            }
        });
        return categoryTreeDtos;
    }
    /**
     * 查询课程分类的所有树形结构节点（修改数据库查询后可以查询指定节点的子节点）
     * 复杂度O(n^2)
     *
     * 本方法从数据库中查询课程分类信息，并将其组织成树形结构以便于展示或进一步处理
     * 首先查询所有的一级节点，然后在这些节点中寻找其子节点，并递归地构建整个树形结构
     *
     * @return 返回课程分类的树形结构列表
     */
    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        // 查询所有节点
        List<CourseCategoryTreeDto> allNodes = mapper.selectNodes();
        if (allNodes == null || allNodes.size() <= 1) {
            return Collections.emptyList();
        }

        // 将 allNodes 转换为 LinkedList，以便利用 LinkedList 在插入和删除操作上的优势
        LinkedList<CourseCategoryTreeDto> remainingNodes = new LinkedList<>(allNodes);
        // 存储每一层的节点映射
        LinkedList<Map<String, CourseCategoryTreeDto>> nodeLayers = new LinkedList<>();

        // 初始化 nodeLayers 并移除第一个节点
        nodeLayers.add(Map.of("1", remainingNodes.getFirst()));
        remainingNodes.removeFirst();

        // 主循环，处理剩余的节点
        while (!remainingNodes.isEmpty()) {
            // 当前层的节点映射
            HashMap<String, CourseCategoryTreeDto> currentLayerMap = new HashMap<>();
            // 获取上一层的节点映射
            Map<String, CourseCategoryTreeDto> parentLayerMap = nodeLayers.getLast();

            // 内层循环，处理当前层的每个节点
            for (int i = remainingNodes.size() - 1; i >= 0; i--) {
                CourseCategoryTreeDto currentNode = remainingNodes.get(i);
                // 检查当前节点是否有父节点
                if (parentLayerMap.containsKey(currentNode.getParentid())) {
                    CourseCategoryTreeDto parentNode = parentLayerMap.get(currentNode.getParentid());
                    // 如果父节点的子节点列表为空，则初始化
                    if (parentNode.getChildrenTreeNodes() == null) {
                        parentNode.setChildrenTreeNodes(new ArrayList<>());
                    }
                    // 将当前节点添加到父节点的子节点列表中
                    parentNode.getChildrenTreeNodes().add(currentNode);
                    // 将当前节点添加到当前层的节点映射中
                    currentLayerMap.put(currentNode.getId(), currentNode);
                    // 从 remainingNodes 中移除当前节点
                    remainingNodes.remove(i); // LinkedList 的 remove 操作在任意位置都是 O(1)
                }
            }

            // 将当前层的节点映射添加到 nodeLayers 中
            nodeLayers.add(currentLayerMap);
        }

        // 返回第二层的节点列表
        return new ArrayList<>(nodeLayers.get(1).values());
    }



    /**
     * 只适用于二级节点.扩展性较差
     *
     * 查询课程分类的树形结构节点
     *
     * 本方法从数据库中查询课程分类信息，并将其组织成树形结构以便于展示或进一步处理
     * 首先查询所有的一级节点，然后在这些节点中寻找其子节点，并递归地构建整个树形结构
     *
     * @return 返回课程分类的树形结构列表
     */
   /* @Override
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        // 初始查询所有节点
        List<CourseCategoryTreeDto> list=mapper.selectTreeNodes("1");
        // 移除第一个元素，因为按照当前逻辑不需要（根节点）
        list.remove(0);
        // 使用HashMap来存储节点，以便快速查找和构建树形结构
        HashMap<String,CourseCategoryTreeDto> map=new HashMap<>();
        // 遍历所有节点，初始化一级节点的子节点列表，并将其存入Map中
        list.forEach(dto->{
            if("1".equals(dto.getParentid())){
                dto.setChildrenTreeNodes(new ArrayList<>());
                map.put(dto.getId(),dto);
            }
        });
        // 再次遍历所有节点，将非一级节点添加到对应的一级节点的子节点列表中
        list.forEach(dto->{
            if(map.containsKey(dto.getParentid())){
                map.get(dto.getParentid()).getChildrenTreeNodes().add(dto);
            }
        });

        // 返回所有一级节点及其对应的子节点，即整个树形结构
        return new ArrayList<>(map.values());
    }*/

    /**
     * 查询课程分类的树形结构节点
     *
     * 本方法从数据库中查询课程分类信息，并将其组织成树形结构以便于展示或进一步处理
     * 首先查询所有的一级节点，然后在这些节点中寻找其子节点，并递归地构建整个树形结构
     *
     * @return 返回课程分类的树形结构列表，实际上返回的是一级节点下的所有子节点
     *//*
    //遍历次数过多
    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        // 查询所有节点
        List<CourseCategoryTreeDto> list=mapper.selectTreeNodes("1");

        // 当列表中存在多个节点时，循环处理以构建树形结构
        while(list.size()>1) {
            for (int i = 0; i < list.size()-1; i++) {
                // 如果当前节点或当前节点子节点是下一个节点的父节点，则将下一个节点添加到当前节点的子节点列表中，并移除下一个节点
                CourseCategoryTreeDto dto = list.get(i);
                do{
                    CourseCategoryTreeDto nextDto = list.get(i + 1);
                    if(nextDto.getParentid().equals(dto.getId())){
                        if(dto.getChildrenTreeNodes()==null){
                            dto.setChildrenTreeNodes(new ArrayList<>());
                        }
                        dto.getChildrenTreeNodes().add(nextDto);
                        list.remove(i+1);
                        break;
                    }
                    if(dto.getChildrenTreeNodes()==null){
                        break;
                    }
                    List<CourseCategoryTreeDto> childrenTreeNodes = dto.getChildrenTreeNodes();
                    dto=childrenTreeNodes.get(childrenTreeNodes.size()-1);
                }while (true);
                // 否则，移动到下一个节点
                if(dto.getChildrenTreeNodes()==null) {
                    i++;
                }
            }
        }

        // 返回一级节点下的所有子节点
        return list.get(0).getChildrenTreeNodes();
    }*/


}
